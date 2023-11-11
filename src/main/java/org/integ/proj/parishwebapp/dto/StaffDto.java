package org.integ.proj.parishwebapp.dto;


import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {
	 private Long id;
	    @NotEmpty
	    private String fname;
	    @NotEmpty
	    private String mname;
	    @NotEmpty
	    private String lname;
	    @NotEmpty(message = "Email should not be empty")
	    @Email
	    private String email;
	    @NotEmpty(message = "Password should not be empty")
	    private String password;
	    @NotNull
	    private LocalDate employementDate;
	    
}
