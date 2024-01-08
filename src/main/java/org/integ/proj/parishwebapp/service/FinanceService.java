package org.integ.proj.parishwebapp.service;

import java.math.BigDecimal;
import java.util.List;

import org.integ.proj.parishwebapp.entity.Finance;
import org.integ.proj.parishwebapp.dto.FinanceDto;

public interface FinanceService {
	void addFinancialRecord(FinanceDto financeDto, Long issuedBy);
	
	void deleteFinancialRecordById(Long id);
	
	List<Finance> getAllFinancialRecordsOrderedByIdDesc();
	
	List<Finance> search(String keyword);
	
	Finance findFinancialRecordById(Long id);
	
	void editFinancialRecord(Finance finance, Long id, Long issuedBy);
	
	BigDecimal getLastValueOfBalance();
	
	BigDecimal getPreviousBalance(Long id);
}
