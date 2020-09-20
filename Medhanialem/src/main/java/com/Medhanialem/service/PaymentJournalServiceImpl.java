package com.Medhanialem.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.Medhanialem.model.MonthlyPaymentEmailRequest;
import com.Medhanialem.service.Impl.MailServiceImpl;
import com.Medhanialem.utils.BuildReceiptByEmailUtility;
import com.Medhanialem.utils.PaymentReceiptConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.payment.Payment;
import com.Medhanialem.model.payment.PaymentLog;
import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.model.payment.PaymentlogDTO;
import com.Medhanialem.model.payment.objects.MembershipReceiptHistory;
import com.Medhanialem.model.payment.objects.MonthlyPaid;
import com.Medhanialem.model.payment.objects.PaymentInformation;
import com.Medhanialem.model.payment.objects.PaymentLogs;
import com.Medhanialem.model.payment.objects.PaymentResponse;
import com.Medhanialem.model.payment.objects.Paymentrequest;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.repository.MembershipReceiptHistoryRepository;
import com.Medhanialem.repository.PaymentLogRepository;
import com.Medhanialem.repository.PaymentLookUpRepository;
import com.Medhanialem.repository.PaymentRepository;
import com.Medhanialem.repository.PaymentlogRepositoryjdbc;

@Service
public class PaymentJournalServiceImpl implements PaymentJournalService {

	Logger logger = LoggerFactory.getLogger(PaymentJournalServiceImpl.class);

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
	
	@Autowired
	MembershipReceiptHistoryRepository membershipReceiptHistoryRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	private MailServiceImpl mailService;

	@Autowired
	private BuildReceiptByEmailUtility buildReceiptByEmailUtility;

	@Value("${receipt.subject}")
	String subject;

	@Value("${receipt.from}")
	String from;

	@Value("${receipt.personal}")
	String personal;
	
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
		String createdBy = userService.getCurrentUserDetails().getUsername();
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
		payment.setCreatedBy(createdBy);

		payment = paymentRepository.save(payment);
		
		if (null == payment) {
			throw new BackendException("Payment didnot get saved to Receipt table.");
		}

		int membershipReceiptHistoryYear = 0;
		StringBuilder monthBuilder = new StringBuilder("");
		
		for (MonthlyPaid monthlypaid : paymentRequest.getPayments()) {

			PaymentLog paymentLog = new PaymentLog();

			paymentLog.setAmount(monthlypaid.getAmount());

			paymentLog.setPayment(payment);
			paymentLog.setMember(member);
			
			PaymentLookup paymentLookup = memberShipPaymentLookUpfee.findById(monthlypaid.getPaymentLookupId()).get();
			amountsPaidPerMonth.put(intToMonth(paymentLookup.getMonth()), (Double)paymentLookup.getAmount());
			
			paymentLog.setPaymentLookupfee(paymentLookup);
			
			membershipReceiptHistoryYear = paymentLookup.getYear();
			monthBuilder.append(intToMonth(paymentLookup.getMonth()) + ",");
			

			if (null == paymentLogRepository.save(paymentLog)) {
				throw new BackendException("Payment didnot get saved to PaymentLog table.");
			}
		}
		
		paymentResponse.setAmountsPaidPerMonth(amountsPaidPerMonth);
		
		// Save to MembershipReceiptHistory table
		MembershipReceiptHistory membershipReceiptHistory = populateMembershipReceiptHistory(member, payment, paymentRequest, monthBuilder, membershipReceiptHistoryYear);
		
		if (null == saveMembershipReceiptHistory(membershipReceiptHistory)) {
			throw new BackendException("Payment didnot get saved to MembershipReceiptHistory table.");
		}
		
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

	@Override
	public List<MembershipReceiptHistory> getAllReceipts(int year) {
		return membershipReceiptHistoryRepository.getAllReciptsByYear(year);
	}

	@Override
	public List<MembershipReceiptHistory> getReceipts(int year, String searchCriteria) {
		return membershipReceiptHistoryRepository.getReceiptsByYearAndSearchCriteria(year, searchCriteria);
	}
	
	private MembershipReceiptHistory saveMembershipReceiptHistory(MembershipReceiptHistory membershipReceiptHistory) {
		return membershipReceiptHistoryRepository.save(membershipReceiptHistory);
	}
	

	@Transactional
	@Override
	public PaymentResponse refundMonthlyFee(Long receiptId) {
		String createdBy = userService.getCurrentUserDetails().getUsername();
		
		Payment existingPayment = this.paymentRepository.findById(receiptId).orElseThrow(
				() -> new BackendException("There is no receipt found with receiptId = " + receiptId));
		
		MembershipReceiptHistory existingMembershipReceiptHistory = membershipReceiptHistoryRepository.findByReceiptId(receiptId).orElseThrow(
				() -> new BackendException("There is no receipt found with receiptId = " + receiptId));
		
		// Making the existing receipt to be voided
		if (existingMembershipReceiptHistory.isVoided() == true) {
			throw new BackendException("The receipt is already voided. You cannnot refund it again. ReceiptId = " + receiptId);
		}
		
		// Check if the person is trying to refund a refund receiptID
		if (null != existingMembershipReceiptHistory.getParentReceipt()) {
			throw new BackendException("Trying to refund a refunded receiptId. ReceiptId = " + receiptId);
		}
		
		existingMembershipReceiptHistory.setVoided(true);
		saveMembershipReceiptHistory(existingMembershipReceiptHistory);
		
		// First insert payment which is the negative amount of original payment
		Payment refundPayment = new Payment();
		refundPayment.setMember(existingPayment.getMember());
		refundPayment.setTotal(-1 * existingPayment.getTotal());
		refundPayment.setCreatedBy(createdBy);
		refundPayment.setParentReceiptId(existingPayment.getId());
		
		refundPayment = paymentRepository.save(refundPayment);
		if (null == refundPayment) {
			throw new BackendException("Refund Payment didnot get saved to Receipt table. Please try again later.");
		}
		
		// Delete related monthly payment logs
		List<PaymentLog> deletedPaymentLogs = paymentLogRepository.deleteByPayment(existingPayment);
		
		if (null == deletedPaymentLogs || deletedPaymentLogs.size() == 0) {
			throw new BackendException("Monthly payment logs didnot get deleted. Looks like there is an issue.");
		}
		
		Member member = existingPayment.getMember();
		if (null == member) {
			throw new BackendException("Member associated with the payment doesnot exist.");
		}
		
		Map<String, Double> amountsPaidPerMonth = new HashMap<>();
		int membershipReceiptHistoryYear = 0;
		StringBuilder monthBuilder = new StringBuilder("");
		
		// Getting details of old saved payment logs
		for (PaymentLog paymentLog : deletedPaymentLogs) {
			
			PaymentLookup paymentLookup = memberShipPaymentLookUpfee.findById((paymentLog.getPaymentLookupfee()).getId()).orElseThrow(
					() -> new BackendException("There is no lookup associated to the payment log with ID = " + paymentLog.getId()));
			
			amountsPaidPerMonth.put(intToMonth(paymentLookup.getMonth()), (Double)paymentLookup.getAmount());
			membershipReceiptHistoryYear = paymentLookup.getYear();
			monthBuilder.append(intToMonth(paymentLookup.getMonth()) + ",");
		}
		
		// Save to MembershipReceiptHistory table
		MembershipReceiptHistory membershipReceiptHistory = populateMembershipReceiptHistoryForRefund(member, refundPayment, deletedPaymentLogs.size(), monthBuilder, membershipReceiptHistoryYear);
		
		if (null == saveMembershipReceiptHistory(membershipReceiptHistory)) {
			throw new BackendException("Payment didnot get saved to MembershipReceiptHistory table for refund.");
		}
		
		PaymentResponse refundResponse = new PaymentResponse();
		refundResponse.setMemberId(member.getMemberId());
		refundResponse.setChurchId(member.getChurchId());
		refundResponse.setFullName(member.getFirstName() + " " + member.getMiddleName() + " " + member.getLastName());
		refundResponse.setTierDescription(member.getTier().getDescription());
		refundResponse.setNumberOfMonthsPaid(deletedPaymentLogs.size());
		refundResponse.setTotal(refundPayment.getTotal());
		refundResponse.setAmountsPaidPerMonth(amountsPaidPerMonth);
		
		return refundResponse;
		
	}

	@Override
	public boolean sendReceiptByEmail(MonthlyPaymentEmailRequest monthlyPaymentEmailRequest) {
		logger.info("Inside sendReceiptByEmail() method, {}", logger.getName());
		logger.info("Request object: {}", monthlyPaymentEmailRequest);

		boolean sendEmailResult = false;

		MembershipReceiptHistory  membershipReceiptHistory = membershipReceiptHistoryRepository.findByReceiptId(monthlyPaymentEmailRequest.getReceiptId()).orElseThrow(
				() -> new BackendException("There is no receipt found with receiptId = " + monthlyPaymentEmailRequest.getReceiptId()));

		try {
			mailService.sendEmail(from, monthlyPaymentEmailRequest.getEmail(), subject, buildReceiptByEmailUtility.getReceiptBody(membershipReceiptHistory), personal);
			sendEmailResult = true;
		} catch (Exception e) {
			logger.error("Exception in sending email: ", e.getMessage());
		}

		return sendEmailResult;
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
	
	// For Regular monthly payment
	private MembershipReceiptHistory populateMembershipReceiptHistory(Member member, Payment payment, Paymentrequest paymentRequest, StringBuilder monthBuilder, int membershipReceiptHistoryYear) {
		String createdBy = userService.getCurrentUserDetails().getUsername();
		MembershipReceiptHistory membershipReceiptHistory = new MembershipReceiptHistory();
		membershipReceiptHistory.setReceiptId(payment.getId());
		membershipReceiptHistory.setMemberId(member.getMemberId());
		membershipReceiptHistory.setFullName(member.getFirstName() + " " + member.getMiddleName() + " " + member.getLastName());
		membershipReceiptHistory.setChurchId(member.getChurchId());
		membershipReceiptHistory.setPhone(member.getHomePhoneNo());
		membershipReceiptHistory.setTierDescription(member.getTier().getDescription());
		membershipReceiptHistory.setTotal(paymentRequest.getTotal());
		membershipReceiptHistory.setMonths(paymentRequest.getPayments().size());
		membershipReceiptHistory.setYear(membershipReceiptHistoryYear);
		membershipReceiptHistory.setMonthsDetail(removeLastChar(monthBuilder.toString()));
		membershipReceiptHistory.setCreatedBy(createdBy);
		membershipReceiptHistory.setEmail(member.getEmail());
		
		return membershipReceiptHistory;
	}
	
	// For refunding a monthly payment
	private MembershipReceiptHistory populateMembershipReceiptHistoryForRefund(Member member, Payment refundPayment, int paymentLogSize, StringBuilder monthBuilder, int membershipReceiptHistoryYear) {
		String createdBy = userService.getCurrentUserDetails().getUsername();
		MembershipReceiptHistory membershipReceiptHistory = new MembershipReceiptHistory();
		membershipReceiptHistory.setReceiptId(refundPayment.getId());
		membershipReceiptHistory.setMemberId(member.getMemberId());
		membershipReceiptHistory.setFullName(member.getFirstName() + " " + member.getMiddleName() + " " + member.getLastName());
		membershipReceiptHistory.setChurchId(member.getChurchId());
		membershipReceiptHistory.setPhone(member.getHomePhoneNo());
		membershipReceiptHistory.setTierDescription(member.getTier().getDescription());
		membershipReceiptHistory.setTotal(refundPayment.getTotal());
		membershipReceiptHistory.setMonths(paymentLogSize);
		membershipReceiptHistory.setYear(membershipReceiptHistoryYear);
		membershipReceiptHistory.setMonthsDetail(removeLastChar(monthBuilder.toString()));
		membershipReceiptHistory.setCreatedBy(createdBy);
		membershipReceiptHistory.setVoided(true);
		membershipReceiptHistory.setParentReceipt(refundPayment.getParentReceiptId());
		membershipReceiptHistory.setRemarks("Refunded months " + removeLastChar(monthBuilder.toString()));
		membershipReceiptHistory.setEmail(member.getEmail());
		
		return membershipReceiptHistory;
	}
	
	private String intToMonth(int numberMonth) {
		
		String month = null;
		
		switch(numberMonth) {
			case(1): 
				month = PaymentReceiptConstants.JAN;
	    		break;
			case(2): 
				month = PaymentReceiptConstants.FEB;
	    		break;
			case(3): 
				month = PaymentReceiptConstants.MAR;
	    		break;
			case(4): 
				month = PaymentReceiptConstants.APR;
	    		break;
			case(5): 
				month = PaymentReceiptConstants.MAY;
	    		break;
			case(6): 
				month = PaymentReceiptConstants.JUN;
	    		break;
			case(7): 
				month = PaymentReceiptConstants.JUL;
	    		break;
			case(8): 
				month = PaymentReceiptConstants.AUG;
	    		break;
			case(9): 
				month = PaymentReceiptConstants.SEP;
	    		break;
			case(10): 
				month = PaymentReceiptConstants.OCT;
	    		break;
			case(11): 
				month = PaymentReceiptConstants.NOV;
	    		break;
			case(12): 
				month = PaymentReceiptConstants.DEC;
	    		break;
		}
		
		return month;
		
	}
	
	private static String removeLastChar(String s) {
	    return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 1));
	}

}
