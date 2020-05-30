package com.Medhanialem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.payment.PaymentLookup;


@Repository
public interface PaymentLookUpRepository extends JpaRepository<PaymentLookup,Long> {
	
//	@Query(value = "SELECT * FROM medhanie_alem_dallas.membershippaymentlookupfee WHERE `id` > :id  and year <= :year and month <= :month", 
//			  nativeQuery = true)
//	List<MembershipPaymentLookupFee> findpaylookUpfees(@Param(value = "id") Long id,@Param(value = "year") int year,@Param(value = "month") int month);
	
	@Query(value = "SELECT * FROM PaymentLookupe WHERE `id` > :id  and id <= :currentId",
			  nativeQuery = true)
	List<PaymentLookup> findpaylookUpfees(@Param(value = "id") Long id,@Param(value = "currentId") Long currentId);

	@Query(value = "SELECT * FROM PaymentLookup where year = :year and month = :month",
			  nativeQuery = true)
	PaymentLookup getIdbymonthAndyear(@Param(value = "year") int year,@Param(value = "month") int month);

	List<PaymentLookup> findByYear(@Param(value = "year") int year);
	
	@Query(value= "SELECT * FROM PaymentLookup where year = :year and tierId = :tierId", nativeQuery = true)
	List<PaymentLookup> findByTierAndYear(@Param(value = "year")Long year, @Param(value = "tierId")Long tier);
	
	
	int countByTierIdAndMonthAndYear(Long id, int month, int year);

}
