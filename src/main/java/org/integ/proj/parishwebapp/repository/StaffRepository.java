package org.integ.proj.parishwebapp.repository;


import org.integ.proj.parishwebapp.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
	 Staff findByEmail(String email);
}
