package org.integ.proj.parishwebapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.integ.proj.parishwebapp.dto.FinanceDto;
import org.integ.proj.parishwebapp.entity.Finance;
import org.integ.proj.parishwebapp.repository.FinanceRepository;
import org.integ.proj.parishwebapp.service.FinanceService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImpl implements FinanceService {
	private FinanceRepository financeRepository;

	public FinanceServiceImpl(FinanceRepository financeRepository) {
		super();
		this.financeRepository = financeRepository;
	}
	
	@Override
	public void addFinancialRecord(FinanceDto financeDto) {
		Finance newFinanceRecord = new Finance();
		newFinanceRecord.setNotes(financeDto.getNotes());
		newFinanceRecord.setDescription(financeDto.getDescription());
		newFinanceRecord.setDate(financeDto.getDate());
		newFinanceRecord.setIncome(financeDto.getIncome());
		newFinanceRecord.setExpense(financeDto.getExpense());
		newFinanceRecord.setBalance(financeDto.getBalance());
		financeRepository.save(newFinanceRecord);
	}

	@Override
	public Finance editFinancialRecord(Finance finance) {
		return financeRepository.save(finance);
	}
	
	@Override
    public void deleteFinancialRecordById(Long id) {
    	financeRepository.deleteById(id);
    }
	
	@Override
	public Finance findFinancialRecordById(Long id) {
		return financeRepository.findById(id).get();
	}
	
	@Override
	public List<FinanceDto> findAllRecords() {
		List<Finance> finance = financeRepository.findAll();
		return finance.stream()
				.map((finances) -> mapToFinanceDto(finances))
				.collect(Collectors.toList());
	}
	
	private FinanceDto mapToFinanceDto(Finance finance) {
		FinanceDto financeDto = new FinanceDto();
		financeDto.setId(finance.getId());
		financeDto.setNotes(finance.getNotes());
		financeDto.setDescription(finance.getDescription());
		financeDto.setIncome(finance.getIncome());
		financeDto.setExpense(finance.getIncome());
		financeDto.setBalance(finance.getBalance());
		return financeDto;
	}
}
