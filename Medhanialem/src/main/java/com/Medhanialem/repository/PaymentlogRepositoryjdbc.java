package com.Medhanialem.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Medhanialem.model.payment.PaymentlogDTO;
import com.Medhanialem.model.payment.PaymentlogDTORowMapper;

@Repository
public class PaymentlogRepositoryjdbc {
	
	  @Autowired
	  private JdbcTemplate jdbcTemplate;
	
	  @Transactional(readOnly=true)
	    public List<PaymentlogDTO> findAll(int year) {
		  
		  return jdbcTemplate.query("select m.memberId as memberId,m.ChurchId,m.FirstName,m.MiddleName," +
		  		"      m.LastName,m.homePhoneNo,m.registrationDate,m.tierId ," +
		  		"      l.id as PaymentlogId,p.Year,p.month,p.Amount from Member m left outer join PaymentLookup p on m.tierId=p.tierId left outer join" +
		  		"               PaymentLog l on (p.id=l.paymentLookupId and l.memberId=m.memberId) where p.year=?",  new Object[] { year }, new PaymentlogDTORowMapper());

	  }

	@Transactional(readOnly=true)
	public Integer findLastPaidYear(Long memberId) {

		return jdbcTemplate.queryForObject("SELECT max(PaymentLookup.year)\n" +
				"FROM PaymentLog\n" +
				"INNER JOIN PaymentLookup ON PaymentLog.paymentLookupId = PaymentLookup.id where PaymentLog.memberId=?",  new Object[] { memberId },Integer.class);
	}

	@Transactional(readOnly=true)
	public Integer findLastPaidMonth(Long memberId,int year) {

		return jdbcTemplate.queryForObject("SELECT max(PaymentLookup.month) \n" +
				"FROM PaymentLog\n" +
				"INNER JOIN PaymentLookup ON PaymentLog.paymentLookupId = PaymentLookup.id where memberId=? AND PaymentLookup.year=?;",  new Object[] { memberId , year},Integer.class);
	}

}

