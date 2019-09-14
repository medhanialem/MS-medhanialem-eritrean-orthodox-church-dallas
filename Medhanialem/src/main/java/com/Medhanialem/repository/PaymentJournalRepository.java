
package com.Medhanialem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.payment.PaymentJournal;

@Repository
public interface PaymentJournalRepository extends JpaRepository<PaymentJournal, Long> {
	
		@Query(value = "SELECT * FROM test_db.PaymentLog where memberId=:id",
		 nativeQuery=true )
	List<PaymentJournal> getAllJournals(@Param("id") Long id );

}
