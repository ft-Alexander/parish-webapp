package org.integ.proj.parishwebapp.service;

import java.util.List;

import org.integ.proj.parishwebapp.entity.Finance;
import org.integ.proj.parishwebapp.dto.FinanceDto;

public interface FinanceService {
	void addFinancialRecord(FinanceDto financeDto);
	
	void deleteFinancialRecordById(Long id);
	
	List<FinanceDto> findAllRecords();
	
	Finance findFinancialRecordById(Long id);
	
	Finance editFinancialRecord(Finance finance);
}
