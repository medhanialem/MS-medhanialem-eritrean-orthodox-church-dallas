package com.Medhanialem.model.payment;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PaymentlogDTORowMapper implements RowMapper<PaymentlogDTO> {

	    @Override
	    public PaymentlogDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
		 PaymentlogDTO paymentlogDTO = new PaymentlogDTO();
		 
		 paymentlogDTO.setMemberId(rs.getLong("memberId"));
		 paymentlogDTO.setChurchId(rs.getString("ChurchId"));
		 paymentlogDTO.setFirstName(rs.getString("FirstName"));
		 paymentlogDTO.setMiddleName(rs.getString("MiddleName"));
		 paymentlogDTO.setLastName(rs.getString("LastName"));
		 paymentlogDTO.setHomePhoneNo(rs.getString("homePhoneNo"));
		 paymentlogDTO.setMonth(rs.getInt("month"));
		 paymentlogDTO.setYear(rs.getInt("year"));
		 paymentlogDTO.setPaymentLogId(rs.getLong("PaymentLogId"));
		 paymentlogDTO.setRegistrationDate(rs.getDate("registrationDate"));
		 paymentlogDTO.setTier(rs.getLong("tierId"));
		 paymentlogDTO.setAmount(rs.getDouble("Amount"));
		 
		return paymentlogDTO;
		
	}

}
