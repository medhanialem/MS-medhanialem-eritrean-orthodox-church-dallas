package com.Medhanialem.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentLog;
import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.model.payment.PaymentlogDTO;
import com.Medhanialem.model.payment.objects.MonthlyPaid;
import com.Medhanialem.model.payment.objects.PaymentInformation;
import com.Medhanialem.model.payment.objects.PaymentLogs;
import com.Medhanialem.model.payment.objects.PaymentResponse;
import com.Medhanialem.model.payment.objects.Paymentrequest;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.repository.PaymentLogRepository;
import com.Medhanialem.repository.PaymentLookUpRepository;
import com.Medhanialem.repository.PaymentRepository;
import com.Medhanialem.repository.PaymentlogRepositoryjdbc;

@Service
public class PaymentJournalServiceImpl implements PaymentJournalService {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	PaymentLookUpRepository memberShipPaymentLookUpfee;
	
	@Autowired
	PaymentLogRepository paymentLogRepository;
	
	@Autowired
	PaymentlogRepositoryjdbc paymentlogRepositoryjdbc;
	
	@Override
	public List<PaymentInformation> getAllPayment(int year) {
		List<Member> memberList = memberRepository.findAll().parallelStream()
				.filter(t -> null == t.getParent() && checkPaymentStartDate(t.getPaymentStartDate(), year))
				.collect(Collectors.toList());

		List<PaymentInformation> list = new ArrayList<>();

		List<PaymentlogDTO> listPaymentdto = paymentlogRepositoryjdbc.findAll(year);

		for (Member member : memberList) {
			PaymentInformation response = new PaymentInformation();

			response.setMemberId(member.getMemberId());
			response.setChurchId(member.getChurchId());
			response.setFirstName(member.getFirstName());
			response.setMiddleName(member.getMiddleName());
			response.setLastName(member.getLastName());
			response.setHomePhoneNo(member.getHomePhoneNo());
			response.setTier(member.getTier().getId());
			response.setRegistrationDate(member.getRegistrationDate());
			response.setPaymentStartDate(member.getPaymentStartDate());

			List<PaymentLogs> journals = getPaymentLogs(listPaymentdto, member);
			response.setPaymentLogs(journals);

			list.add(response);
		}
		return list;
	}
	
	@Override
	public PaymentResponse payMonthlyFee(Paymentrequest paymentRequest) {
		Member member = memberRepository.findById(paymentRequest.getMemberId()).get();
		PaymentResponse paymentResponse = new PaymentResponse();
		Map<String, Double> amountsPaidPerMonth = new HashMap<>();
		
		if (null == member) {
			throw new BackendException("Member doesnot exist");
		}
		
		paymentResponse.setMemberId(member.getMemberId());
		paymentResponse.setChurchId(member.getChurchId());
		paymentResponse.setFullName(member.getFirstName() + " " + member.getMiddleName() + " " + member.getLastName());
		paymentResponse.setTierDescription(member.getTier().getDescription());
		paymentResponse.setNumberOfMonthsPaid(paymentRequest.getPayments().size());
		paymentResponse.setTotal(paymentRequest.getTotal());
		
		Payment payment = new Payment();
		payment.setMember(member);
		payment.setTotal(paymentRequest.getTotal());

		payment = paymentRepository.save(payment);
		
		if (null == payment) {
			throw new BackendException("Payment didnot get saved to Receipt table.");
		}

		for (MonthlyPaid monthlypaid : paymentRequest.getPayments()) {

			PaymentLog paymentLog = new PaymentLog();

			paymentLog.setAmount(monthlypaid.getAmount());

			paymentLog.setPayment(payment);
			paymentLog.setMember(member);
			
			PaymentLookup paymentLookup = memberShipPaymentLookUpfee.findById(monthlypaid.getPaymentLookupId()).get();
			amountsPaidPerMonth.put(intToMonth(paymentLookup.getMonth()), (Double)paymentLookup.getAmount());
			
			paymentLog.setPaymentLookupfee(paymentLookup);

			if (null == paymentLogRepository.save(paymentLog)) {
				throw new BackendException("Payment didnot get saved to PaymentLog table.");
			}
		}
		paymentResponse.setAmountsPaidPerMonth(amountsPaidPerMonth);
		
		return paymentResponse;
	}
	
	@Override
	public boolean unpaidPreviousYearPaymentExist(Long memberId, Long paymentStartYear, Long year) {
		
		Integer lastPaidYear = paymentlogRepositoryjdbc.findLastPaidYear(memberId);
		
		// Member has never paid and is trying to pay next year's payment -ve scenario
		if (null == lastPaidYear && paymentStartYear < year) {
			return true;
		}
		
		// Member has never paid but he is trying to pay the year where it is equal to payment start year +ve scenario
		if (null == lastPaidYear && paymentStartYear.equals(year)) {
			return false;
		}
		
		Integer lastPaidMonth = paymentlogRepositoryjdbc.findLastPaidMonth(memberId, lastPaidYear);
		
		// +ve scenario
		if (lastPaidYear < year && lastPaidMonth == 12 && lastPaidYear + 1 == year) {
			return false;
		}
		
		// +ve scenario
		if ((int)lastPaidYear == year) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkPaymentStartDate(Date paymentStartDate, int year) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(paymentStartDate);
		return calendar.get(Calendar.YEAR) <= year;
		
	}
	
	private List<PaymentLogs> getPaymentLogs(List<PaymentlogDTO> listPaymentdto, Member member) {

		List<PaymentLogs> paymentLoglist = new ArrayList<>();

		for (PaymentlogDTO paymentdto : listPaymentdto) {

			if (paymentdto.getMemberId().equals(member.getMemberId())) {

				PaymentLogs paylog = new PaymentLogs();

				paylog.setPaymentLogId(paymentdto.getPaymentLogId());

				paylog.setMonth(paymentdto.getMonth());
				paylog.setYear(paymentdto.getYear());
				paylog.setAmount(paymentdto.getAmount());

				paymentLoglist.add(paylog);
			}
		}
		return paymentLoglist;
	}
	
	private String intToMonth(int numberMonth) {
		
		String month = null;
		
		switch(numberMonth) {
			case(1): 
				month = "Jan";
	    		break;
			case(2): 
				month = "Feb";
	    		break;
			case(3): 
				month = "Mar";
	    		break;
			case(4): 
				month = "Apr";
	    		break;
			case(5): 
				month = "May";
	    		break;
			case(6): 
				month = "Jun";
	    		break;
			case(7): 
				month = "Jul";
	    		break;
			case(8): 
				month = "Aug";
	    		break;
			case(9): 
				month = "Sep";
	    		break;
			case(10): 
				month = "Oct";
	    		break;
			case(11): 
				month = "Nov";
	    		break;
			case(12): 
				month = "Dec";
	    		break;
		}
		
		return month;
		
	}

}
