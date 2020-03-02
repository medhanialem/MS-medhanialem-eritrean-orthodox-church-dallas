
package com.Medhanialem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.Member;

 @Repository 
 public interface MemberRepository extends JpaRepository<Member,Long>{


}
