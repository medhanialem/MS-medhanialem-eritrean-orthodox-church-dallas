package com.Medhanialem.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.Medhanialem.model.MonthlyPaymentEmailRequest;
import com.Medhanialem.service.Impl.MailServiceImpl;
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

		MembershipReceiptHistory  membershipReceiptHistory = membershipReceiptHistoryRepository.findByReceiptId(monthlyPaymentEmailRequest.getReceiptId()).orElseThrow(
				() -> new BackendException("There is no receipt found with receiptId = " + monthlyPaymentEmailRequest.getReceiptId()));

		return mailService.sendEmail(from, monthlyPaymentEmailRequest.getEmail(), subject, getReceiptBody(membershipReceiptHistory), personal);
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

	public String getReceiptBody(MembershipReceiptHistory membershipReceiptHistory) {
		StringBuilder sb = new StringBuilder();

		if (!membershipReceiptHistory.isVoided() || null == membershipReceiptHistory.getParentReceipt()) {
			sb.append("<p>Hi <b><i><span style=\"color:blue\">" + membershipReceiptHistory.getFullName() + "</span><i></b>. <br>Thank you! We have received your monthly membership payment and below is your receipt.</p>");
		} else {
			sb.append("<p>Hi <b><i><span style=\"color:blue\">" + membershipReceiptHistory.getFullName() + "</span><i></b>. <br>Thank you! Please find your monthly membership refund receipt below.</p>");
		}
		sb.append("<div class='nameMonthTotal'>");
		sb.append("<head><style type = \"text/css\">table {border-collapse:collapse;}</style></head>");
		sb.append("<table class='memberReceiptTbl' style=\"background-color: #F2FAFA; width: 80%\">");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table class='memberPaymentInfoTbl' style=\"margin-top: 8px; margin-left: 5px; width: 40%;\">");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Full Name</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getFullName() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Church Id</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getChurchId() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Phone</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getPhone() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Tier</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getTierDescription() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Months</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getMonths() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Date</td>");

		sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getCreatedDate() + "</td>");
		sb.append("</tr>");

		if (!membershipReceiptHistory.isVoided() || null == membershipReceiptHistory.getParentReceipt()) {
			sb.append("<tr>");
			sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Total</td>");
			sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">$" + membershipReceiptHistory.getTotal() + "</td>");
			sb.append("</tr>");
		} else {
			sb.append("<tr>");
			sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Total</td>");
			sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">-$" + (-1 * membershipReceiptHistory.getTotal()) + "</td>");
			sb.append("</tr>");
		}

		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Receipt No</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getReceiptId() + "</td>");
		sb.append("</tr>");

		if (membershipReceiptHistory.isVoided() && null != membershipReceiptHistory.getParentReceipt()) {
			sb.append("<tr>");
			sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Original Receipt No</td>");
			sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">" + membershipReceiptHistory.getParentReceipt() + "</td>");
			sb.append("</tr>");
		}

		if (membershipReceiptHistory.isVoided() && null == membershipReceiptHistory.getParentReceipt()) {
			sb.append("<tr>");
			sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px;\">Voided</td>");
			sb.append("<td id='value' style=\"border: 1px solid lightblue; padding-left: 5px; background-color: #FFFFFF; width: 60%;\">TRUE</td>");
			sb.append("</tr>");
		}

		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<table class='memberPaymentInfoMonthsTbl' style=\"margin-top: 8px; margin-left: 5px;\">");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"padding-left: 5px;\"><b>Year: </b></td>");
		sb.append("<td id='leftLabels' style=\"padding-left: 5px;\">" + membershipReceiptHistory.getYear() + "</td>");
		sb.append("</tr>");
		sb.append("</table>");

		sb.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>");

		sb.append("<table class='memberPaymentInfoMonthsTbl' style=\"margin-bottom: 8px; margin-left: 5px; margin-right: 5px;\">");
		sb.append("<tr>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>January</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>February</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>March</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>April</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>May</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>June</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>July</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>August</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>September</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>October</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>November</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%;\"><b>December</b></td>");
		sb.append("</tr>");

		Map<String, String> monthsPaymentDetail = new HashMap<>();
		fetchMonthsPaymentDetail(monthsPaymentDetail, membershipReceiptHistory);

		sb.append("<tr>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.JAN) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.FEB) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.MAR) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.APR) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.MAY) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.JUN) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.JUL) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.AUG) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.SEP) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.OCT) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.NOV) + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; padding-left: 5px; width: 8%; background-color: #FFFFFF;\">" + monthsPaymentDetail.get(PaymentReceiptConstants.DEC) + "</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");

		sb.append("<br><br><div>");
		sb.append("Regards,<br>");
		sb.append("Finance Department<br>");
		sb.append("Medhani Alem Eritrean Orthodox Church<br>");
		sb.append("Dallas, TX");
		sb.append("</div>");

		return sb.toString();
	}

	public Map<String, String> fetchMonthsPaymentDetail(Map<String, String> monthsPaymentDetail, MembershipReceiptHistory membershipReceiptHistory) {
		String monthsDetail = membershipReceiptHistory.getMonthsDetail();
		Boolean refund = (membershipReceiptHistory.isVoided() && null != membershipReceiptHistory.getParentReceipt()) ? true : false;
		monthsPaymentDetail.put(PaymentReceiptConstants.JAN, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.FEB, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.MAR, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.APR, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.MAY, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.JUN, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.JUL, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.AUG, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.SEP, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.OCT, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.NOV, PaymentReceiptConstants.EMPTY_STRING);
		monthsPaymentDetail.put(PaymentReceiptConstants.DEC, PaymentReceiptConstants.EMPTY_STRING);

		String[] monthDetailArray = monthsDetail.split(",");

		for (int i = 0; i < monthDetailArray.length; i++) {
			switch (monthDetailArray[i]) {
				case PaymentReceiptConstants.JAN:
					 monthsPaymentDetail.put(PaymentReceiptConstants.JAN, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.FEB:
					monthsPaymentDetail.put(PaymentReceiptConstants.FEB, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.MAR:
					monthsPaymentDetail.put(PaymentReceiptConstants.MAR, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.APR:
					monthsPaymentDetail.put(PaymentReceiptConstants.APR, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.MAY:
					monthsPaymentDetail.put(PaymentReceiptConstants.MAY, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.JUN:
					monthsPaymentDetail.put(PaymentReceiptConstants.JUN, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.JUL:
					monthsPaymentDetail.put(PaymentReceiptConstants.JUL, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.AUG:
					monthsPaymentDetail.put(PaymentReceiptConstants.AUG, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.SEP:
					monthsPaymentDetail.put(PaymentReceiptConstants.SEP, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.OCT:
					monthsPaymentDetail.put(PaymentReceiptConstants.OCT, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.NOV:
					monthsPaymentDetail.put(PaymentReceiptConstants.NOV, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
				case PaymentReceiptConstants.DEC:
					monthsPaymentDetail.put(PaymentReceiptConstants.DEC, !refund ? PaymentReceiptConstants.PAID : PaymentReceiptConstants.REFUNDED);
					break;
			}
		}

		return monthsPaymentDetail;
	}

}
