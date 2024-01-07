package org.integ.proj.parishwebapp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.integ.proj.parishwebapp.dto.FinanceDto;
import org.integ.proj.parishwebapp.entity.Finance;
import org.integ.proj.parishwebapp.entity.Staff;
import org.integ.proj.parishwebapp.repository.FinanceRepository;
import org.integ.proj.parishwebapp.service.FinanceService;
import org.integ.proj.parishwebapp.service.StaffService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImpl implements FinanceService {
	private FinanceRepository financeRepository;
	private StaffService staffService;
	
	public FinanceServiceImpl(FinanceRepository financeRepository, StaffService staffService) {
		super();
		this.financeRepository = financeRepository;
		this.staffService = staffService;
	}

	@Override
	public void addFinancialRecord(FinanceDto financeDto, Long issuedBy) {
		Finance newFinanceRecord = new Finance();
		BigDecimal newBalance = BigDecimal.valueOf(0);
		
		newFinanceRecord.setId(financeDto.getId());
		
		Staff staff = new Staff();
		staff = staffService.findUserById(issuedBy);
		
		newFinanceRecord.setNotes(financeDto.getNotes());
		newFinanceRecord.setTransaction_type(financeDto.getTransaction_type());
		newFinanceRecord.setDescription(financeDto.getDescription());
		newFinanceRecord.setDate(financeDto.getDate());
		newFinanceRecord.setAmount(financeDto.getAmount());
//		newFinanceRecord.setBalance(financeDto.getBalance());
		
		newFinanceRecord.setStaff(staff);
		
		if(financeDto.getBalance() != null) {
			newBalance = financeDto.getBalance();
		}else {
			newBalance = BigDecimal.valueOf(0.00);
		}
		
		if(financeDto.getTransaction_type().equals("Income")) {
			newBalance = newBalance.add(financeDto.getAmount());
			newFinanceRecord.setBalance(newBalance);
		}else if(financeDto.getTransaction_type().equals("Expense")) {
			newBalance = newBalance.subtract(financeDto.getAmount());
			newFinanceRecord.setBalance(newBalance);
		}
		
		financeRepository.save(newFinanceRecord);
	}

//	@Override
//	public Finance editFinancialRecord(Finance finance) {
//		return financeRepository.save(finance);
//	}
	
	@Override
    public void deleteFinancialRecordById(Long id) {
    	financeRepository.deleteById(id);
    }
	
	@Override
	public Finance findFinancialRecordById(Long id) {
		return financeRepository.findById(id).get();
	}
	
	@Override
	public List<Finance> getAllFinancialRecordsOrderedByIdDesc() {
		return financeRepository.findAllByOrderByIdDesc();
	}
	
	private FinanceDto mapToFinanceDto(Finance finance) {
		FinanceDto financeDto = new FinanceDto();
		financeDto.setId(finance.getId());
		financeDto.setNotes(finance.getNotes());
		financeDto.setDate(finance.getDate());
		financeDto.setTransaction_type(finance.getTransaction_type());
		financeDto.setAmount(finance.getAmount());
		financeDto.setBalance(finance.getBalance());
		
		return financeDto;
	}

	@Override
	public void editFinancialRecord(Finance userData, Long id, Long issuedBy) {
		Finance existingRecord = findFinancialRecordById(id);
		BigDecimal newBalance = BigDecimal.valueOf(0);
		
		Staff staff = new Staff();
		staff = staffService.findUserById(issuedBy);
		
		existingRecord.setDate(userData.getDate());
		existingRecord.setNotes(userData.getNotes());
		existingRecord.setTransaction_type(userData.getTransaction_type());
		existingRecord.setDescription(userData.getDescription());
		existingRecord.setAmount(userData.getAmount());
//		existingRecord.setBalance(userData.getBalance());
		
		existingRecord.setStaff(staff);
		
		if(userData.getBalance().compareTo(BigDecimal.valueOf(0)) > 0) {
			newBalance = userData.getBalance();
		}else {
			newBalance = BigDecimal.valueOf(0.00);
		}
		
		if(userData.getTransaction_type().equals("Income")) {
			newBalance = newBalance.add(userData.getAmount());
			existingRecord.setBalance(newBalance);
		}else if(userData.getTransaction_type().equals("Expense")) {
			newBalance = newBalance.subtract(userData.getAmount());
			existingRecord.setBalance(newBalance);
		}
		
		financeRepository.save(existingRecord);
		
		List<Finance> subsequentEntries = financeRepository.findAllByIdGreaterThanOrderByIdAsc(existingRecord.getId());
		
		BigDecimal runningBalance = existingRecord.getBalance();
		
		for(Finance entry : subsequentEntries) {
			if("Income".equals(entry.getTransaction_type())) {
				runningBalance = runningBalance.add(entry.getAmount());
			}else if("Expense".equals(entry.getTransaction_type())) {
				runningBalance = runningBalance.subtract(entry.getAmount());
			}
			
			entry.setBalance(runningBalance);
			financeRepository.save(entry);
		}
	}
	
	@Override
	public List<Finance> search(String keyword) {
		if(keyword != null) {
			return financeRepository.search(keyword);
		}
		return financeRepository.findAll();
	}

	@Override
	public BigDecimal getLastValueOfBalance() {
		List<BigDecimal> lastValue = financeRepository.findLastValueOfBalance();
		if(lastValue.isEmpty()) {
			return null;
		}else if(lastValue.size() > 1) {
			return lastValue.get(0);
		}
		
		return lastValue.get(0);
	}

	@Override
	public BigDecimal getPreviousBalance(Long id) {
		Long previousId = financeRepository.findPreviousId(id);
		
		if(previousId != null) {
			Optional<Finance> previousFinanceOptional = financeRepository.findById(previousId);
			if(previousFinanceOptional.isPresent()) {
				Finance previousFinance = previousFinanceOptional.get();
				return previousFinance.getBalance();
			}
		}
		return BigDecimal.valueOf(0.00);
	}
}
