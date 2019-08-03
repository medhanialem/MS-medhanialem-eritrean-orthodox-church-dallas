
package com.Medhanialem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.Memberhistory;

@Repository
public interface MemberHistRepository extends JpaRepository<Memberhistory, Long> {

}
