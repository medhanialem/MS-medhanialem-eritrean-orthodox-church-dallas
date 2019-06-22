package com.Medhanialem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.Memberhistory;
import com.Medhanialem.model.payment.Tier;
import com.Medhanialem.model.payment.Tiername;


@Repository
public interface TiersRepository extends JpaRepository<Tier,Long> {
	
	/* Tier findbytiername(String tiername); */
}
