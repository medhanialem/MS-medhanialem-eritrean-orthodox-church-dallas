
package com.Medhanialem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentLog;


@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {
	
		@Query(value = "SELECT PaymentLog.* FROM PaymentLog Where memberId=:id AND PaymentLog.paymentLookupId IN (select PaymentLookup.id from PaymentLookup where PaymentLookup.year=:year)",
		 nativeQuery=true )
	    List<PaymentLog> getAllJournals(@Param("id") Long id,@Param("year") int year );
	
	/*
	 * @Query("select new com.Medhanialem.model.payment.Paymentlog(p.paymentLookupId,l.year,l.month,l.amount) from PaymentLog p INNER JOIN PaymentLookup l ON p.paymentLookupId=l.id where p.memberId=:id AND year=:year ORDER BY l.month"
	 * ) List<Paymentlog> getYearpayments(@Param("id") Long id,@Param("year") int
	 * year );
	 */
		
//		@Query("DELETE FROM PaymentLog p where p.receiptId=:receiptId")
//		void deletePaymentLogs(@Param("receiptId") Long receiptId);
		
		List<PaymentLog> deleteByPayment(Payment payment);
}
