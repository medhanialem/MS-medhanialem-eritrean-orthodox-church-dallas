package com.Medhanialem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.payment.MembershipPaymentLookupFee;


@Repository
public interface MemberShipPaymentLookUpfeeRepository extends JpaRepository<MembershipPaymentLookupFee,Long> {
	
//	@Query(value = "SELECT * FROM medhanie_alem_dallas.membershippaymentlookupfee WHERE `id` > :id  and year <= :year and month <= :month", 
//			  nativeQuery = true)
//	List<MembershipPaymentLookupFee> findpaylookUpfees(@Param(value = "id") Long id,@Param(value = "year") int year,@Param(value = "month") int month);
	
	@Query(value = "SELECT * FROM test_db.PaymentLookupe WHERE `id` > :id  and id <= :currentId", 
			  nativeQuery = true)
	List<MembershipPaymentLookupFee> findpaylookUpfees(@Param(value = "id") Long id,@Param(value = "currentId") Long currentId);

	@Query(value = "SELECT * FROM test_db.PaymentLookup where year = :year and month = :month", 
			  nativeQuery = true)
	MembershipPaymentLookupFee getIdbymonthAndyear(@Param(value = "year") int year,@Param(value = "month") int month);

	@Query(value= "SELECT * FROM test_db.PaymentLookup where year = :year", nativeQuery = true)
	List<MembershipPaymentLookupFee> getPaymentLookupInfo(@Param(value = "year")Long year);

}
