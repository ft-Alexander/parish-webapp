package org.integ.proj.parishwebapp.repository;

import java.util.List;
import org.integ.proj.parishwebapp.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);
}
