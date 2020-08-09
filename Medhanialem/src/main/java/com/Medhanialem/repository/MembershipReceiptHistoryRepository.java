package com.Medhanialem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.payment.objects.MembershipReceiptHistory;

@Repository
public interface MembershipReceiptHistoryRepository extends JpaRepository<MembershipReceiptHistory, Long>{
	@Query(value = "select * from MembershipReceiptHistory WHERE year =:year ORDER BY createdDate DESC", nativeQuery=true)
	List<MembershipReceiptHistory> getAllReciptsByYear(@Param("year") int year);
	
	@Query(value = "select * from MembershipReceiptHistory WHERE year =:year AND (receiptID =:searchCriteria OR churchId =:searchCriteria OR fullName like :searchCriteria%) ORDER BY createdDate DESC", nativeQuery=true)
	List<MembershipReceiptHistory> getReceiptsByYearAndSearchCriteria(@Param("year") int year, @Param("searchCriteria") String searchCriteria);
	
	Optional<MembershipReceiptHistory> findByReceiptId(Long receiptId);
}
