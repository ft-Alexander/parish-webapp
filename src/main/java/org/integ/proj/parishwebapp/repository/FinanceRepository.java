package org.integ.proj.parishwebapp.repository;

import java.math.BigDecimal;
import java.util.List;

import org.integ.proj.parishwebapp.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
	@Query("SELECT f FROM Finance f WHERE f.notes LIKE %?1% OR f.transaction_type LIKE %?1% OR f.id LIKE %?1% OR f.amount LIKE %?1%")
	List<Finance> search(String keyword);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Finance f WHERE f.id = %?1%")
    void deleteByFinanceId(Long id);
	
	List<Finance> findAllByOrderByIdAsc();
	
	@Query("SELECT f.balance FROM Finance f ORDER BY f.id DESC")
	List<BigDecimal> findLastValueOfBalance();
	
	@Query("SELECT MAX(f.id) FROM Finance f WHERE f.id < ?1")
	Long findPreviousId(Long id);
	
	List<Finance> findAllByIdGreaterThanOrderByIdAsc(Long id);
	
	List<Finance> findAllByOrderByIdDesc();
}
