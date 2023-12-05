package org.integ.proj.parishwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Finance")
public class Finance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String description;
	
	@Column(nullable=false)
	private String notes;
	
	@Column(nullable=false)
	private LocalDate date;
	
	@Column(nullable=false)
	private BigDecimal income;
	
	@Column(nullable=false)
	private BigDecimal expense;
	
	@Column(nullable=false)
	private BigDecimal balance;
}
