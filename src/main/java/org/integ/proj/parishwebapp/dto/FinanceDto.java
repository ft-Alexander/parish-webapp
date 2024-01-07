package org.integ.proj.parishwebapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinanceDto {
	private Long id;
	
	@NotEmpty
	private String description;
	
	@NotEmpty
	private String notes;
	
	@NotNull
	private LocalDate date;
	
	@NotEmpty
	private BigDecimal amount;
	
	@NotEmpty
	private String transaction_type;
	
	private BigDecimal balance;
}
