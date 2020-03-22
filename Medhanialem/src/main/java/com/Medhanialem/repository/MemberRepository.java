
package com.Medhanialem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.Member;

 @Repository 
 public interface MemberRepository extends JpaRepository<Member,Long>{


	@Query(value = "select * from Member where superId=:parentId", nativeQuery=true )
	List<Member> getDependents(@Param("parentId") Long parentId);
}
