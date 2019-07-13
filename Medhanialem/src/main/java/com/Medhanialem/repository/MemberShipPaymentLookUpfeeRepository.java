package com.Medhanialem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.payment.MembershipPaymentLookupFee;


@Repository
public interface MemberShipPaymentLookUpfeeRepository extends JpaRepository<MembershipPaymentLookupFee,Long> {
	
	@Query(value = "SELECT * FROM medhanie_alem_dallas.membership_payment_lookup_fee WHERE `id` > 6 and year <= 2019 and month <= 7", 
			  nativeQuery = true)
	List<MembershipPaymentLookupFee> findpaylookUpfees();

}
