package org.integ.proj.parishwebapp.repository;

import java.util.List;
import org.integ.proj.parishwebapp.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);
    
    @Query("SELECT s FROM Staff s WHERE s.fname LIKE %?1% OR s.mname LIKE %?1% OR s.lname LIKE %?1% OR s.email LIKE %?1% OR s.employementDate LIKE %?1%")
    List<Staff> search(String keyword);
}
