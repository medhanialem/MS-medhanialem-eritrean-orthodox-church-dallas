
package com.Medhanialem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.payment.Tier;



@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

}
