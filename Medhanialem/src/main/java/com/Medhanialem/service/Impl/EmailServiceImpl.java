package com.Medhanialem.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.MonthlyPaymentEmailRequest;
import com.Medhanialem.model.payment.objects.MembershipReceiptHistory;
import com.Medhanialem.repository.MembershipReceiptHistoryRepository;
import com.Medhanialem.service.EmailService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;

@Service
public class EmailServiceImpl implements EmailService {
	
	Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	MembershipReceiptHistoryRepository membershipReceiptHistoryRepository;

//	@Override
//	public boolean sendEmail(MonthlyPaymentEmailRequest monthlyPaymentEmailRequest) {
//
//		logger.info("Inside sendEmail() method, {}", logger.getName());
//		logger.info("Request object: {}", monthlyPaymentEmailRequest);
//
//		boolean sendEmailResult = false;
//
//		MembershipReceiptHistory  membershipReceiptHistory = membershipReceiptHistoryRepository.findByReceiptId(monthlyPaymentEmailRequest.getReceiptId()).orElseThrow(
//				() -> new BackendException("There is no receipt found with receiptId = " + monthlyPaymentEmailRequest.getReceiptId()));
//
//		String smtpHost = "smtp.gmail.com";
//	    int smtpPort = 587;
////	    int smtpPort = 465;
//
//	    String sender = "medhanialemdallas@gmail.com";
//	    String recipient = monthlyPaymentEmailRequest.getEmail();
//	    String content = "Hi " + membershipReceiptHistory.getFullName() + ". Thank you! We have received your monthly membership payment and you will find your receipt attached.";
//	    String subject = "Monthly Membership Payment Receipt";
//
//	    Properties properties = new Properties();
//	    properties.put("mail.smtp.host", smtpHost);
//	    properties.put("mail.smtp.port", smtpPort);
//	    properties.put("mail.smtp.starttls.enable", "true");
//	    properties.put("mail.smtp.auth", "true");
//	    properties.put("mail.debug", "true");
//
//	    Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
//	    	protected PasswordAuthentication getPasswordAuthentication() {
//	    		return new PasswordAuthentication("medhanialemdallas@gmail.com", "Medhanit@Alem1");
//	    		}
//	    		});
//
//	    ByteArrayOutputStream outputStream = null;
//
//	    try {
//	        //construct the text body part
//	        MimeBodyPart textBodyPart = new MimeBodyPart();
//	        textBodyPart.setText(content);
//
//	        //now write the PDF content to the output stream
//	        outputStream = new ByteArrayOutputStream();
////	        writePdf(outputStream, membershipReceiptHistory);
//	        byte[] bytes = outputStream.toByteArray();
//
//	        //construct the pdf body part
//	        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
//	        MimeBodyPart pdfBodyPart = new MimeBodyPart();
//	        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
//	        pdfBodyPart.setFileName("MedhanieAlem_Monthly_Membership_Payment.pdf");
//
//	        //construct the mime multi part
//	        MimeMultipart mimeMultipart = new MimeMultipart();
//	        mimeMultipart.addBodyPart(textBodyPart);
//	        mimeMultipart.addBodyPart(pdfBodyPart);
//
//	        //create the sender/recipient addresses
//	        InternetAddress iaSender = new InternetAddress(sender);
//	        InternetAddress iaRecipient = new InternetAddress(recipient);
//
//	        //construct the mime message
//	        MimeMessage mimeMessage = new MimeMessage(session);
//	        mimeMessage.setSender(iaSender);
//	        mimeMessage.setSubject(subject);
//	        mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
//	        mimeMessage.setContent(mimeMultipart);
//
//	        //send off the email
//	        Transport.send(mimeMessage);
//	        sendEmailResult = true;
//	        System.out.println("sent from " + sender + ", to " + recipient + "; server = " + smtpHost + ", port = " + smtpPort);
//	    } catch(Exception ex) {
//	    	 sendEmailResult = false;
//	        ex.printStackTrace();
//	    } finally {
//	        //clean off
//	        if(null != outputStream) {
//	            try { outputStream.close(); outputStream = null; }
//	            catch(Exception ex) { }
//	        }
//	    }
//
//		return sendEmailResult;
//	}
	
//	public void writePdf(OutputStream outputStream, MembershipReceiptHistory membershipReceiptHistory) throws Exception {
//	    Document document = new Document();
//	    PdfWriter.getInstance(document, outputStream);
//	    document.open();
//	    Paragraph paragraph = new Paragraph();
//	    paragraph.add(new Chunk("Full Name: \t" + membershipReceiptHistory.getFullName().toUpperCase()));
//	    paragraph.add(new Chunk("ChurchId: \t" + membershipReceiptHistory.getChurchId()));
//	    paragraph.add(new Chunk("Tier: \t" + membershipReceiptHistory.getTierDescription()));
//	    paragraph.add(new Chunk("Date: \t" + membershipReceiptHistory.getCreatedDate()));
//	    paragraph.add(new Chunk("Total: \t" + membershipReceiptHistory.getTotal()));
//	    document.add(paragraph);
//	    document.close();
//	}

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	JavaMailSenderImpl mailSenderImpl;

	@Override
	public boolean sendEmail(MonthlyPaymentEmailRequest monthlyPaymentEmailRequest) {

		logger.info("Inside sendEmail() method, {}", logger.getName());
		logger.info("Request object: {}", monthlyPaymentEmailRequest);

		boolean sendEmailResult = false;

		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587);

		Properties props = mailSenderImpl.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		MembershipReceiptHistory  membershipReceiptHistory = membershipReceiptHistoryRepository.findByReceiptId(monthlyPaymentEmailRequest.getReceiptId()).orElseThrow(
				() -> new BackendException("There is no receipt found with receiptId = " + monthlyPaymentEmailRequest.getReceiptId()));

		boolean mailStatus= false;

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject("Monthly Membership Payment Receipt");
			mimeMessageHelper.setFrom(new InternetAddress("medhanialemdallas@gmail.com", "Medhani Alem Eritrean Orthodox Church Dallas, TX"));
			mimeMessageHelper.setTo(monthlyPaymentEmailRequest.getEmail());
			mimeMessageHelper.setText(getReceiptBody(membershipReceiptHistory), true);

			mailSenderImpl.send(mimeMessageHelper.getMimeMessage());
			mailStatus= true;
			return mailStatus;
		} catch (MessagingException e) {
			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		}
		return mailStatus;
	}

	public String getReceiptBody(MembershipReceiptHistory membershipReceiptHistory) {
		StringBuilder sb = new StringBuilder();

		sb.append("<p>Hi <b><i><span style=\"color:blue\">" + membershipReceiptHistory.getFullName() + "</span><i></b>. <br>Thank you! We have received your monthly membership payment and you will find your receipt below.</p>");
		sb.append("<div class='nameMonthTotal'>");
		sb.append("<table class='memberPaymentInfoTbl' style=\"border: 1px solid lightblue;\">");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px; background-color: #f5f5f5;\">Full Name</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue;\">" + membershipReceiptHistory.getFullName() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px; background-color: #f5f5f5;\">Church Id</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue;\">" + membershipReceiptHistory.getChurchId() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px; background-color: #f5f5f5;\">Phone</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue;\">" + membershipReceiptHistory.getPhone() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px; background-color: #f5f5f5;\">Tier</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue;\">" + membershipReceiptHistory.getTierDescription() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px; background-color: #f5f5f5;\">Months</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue;\">" + membershipReceiptHistory.getMonths() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px; background-color: #f5f5f5;\">Date</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue;\">" + membershipReceiptHistory.getCreatedDate() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px; background-color: #f5f5f5;\">Total</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue;\">$" + membershipReceiptHistory.getTotal() + "</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels' style=\"border: 1px solid lightblue; font-weight: bold; padding-right: 50px; padding-left: 5px; background-color: #f5f5f5;\">Receipt No</td>");
		sb.append("<td id='value' style=\"border: 1px solid lightblue;\">" + membershipReceiptHistory.getReceiptId() + "</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</div>");

		sb.append("<br><div>");
		sb.append("<table class='memberPaymentInfoMonthsTbl'>");
		sb.append("<tr>");
		sb.append("<td id='leftLabels'><b>Year: </b></td>");
		sb.append("<td id='leftLabels'>" + membershipReceiptHistory.getYear() + "</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("<table class='memberPaymentInfoMonthsTbl' style=\"border: 1px solid lightblue;\">");
		sb.append("<tr>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>January</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>February</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>March</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>April</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>May</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>June</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>July</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>August</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>September</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>October</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>November</b></td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; background-color: #f5f5f5; width: 70px;\"><b>December</b></td>");
		sb.append("</tr>");

		Map<String, String> monthsPaymentDetail = new HashMap<>();
		fetchMonthsPaymentDetail(monthsPaymentDetail, membershipReceiptHistory.getMonthsDetail());

		sb.append("<tr>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Jan") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Feb") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Mar") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Apr") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("May") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Jun") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Jul") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Aug") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Sep") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Oct") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Nov") + "</td>");
		sb.append("<td id='leftLabelsMonths' style=\"border: 1px solid lightblue; width: 70px;\">" + monthsPaymentDetail.get("Dec") + "</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</div>");

		sb.append("<br><br><div>");
		sb.append("Regards,<br>");
		sb.append("Finance Department<br>");
		sb.append("Medhani Alem Eritrean Orthodox Church<br>");
		sb.append("Dallas, Tx");
		sb.append("</div>");

		return sb.toString();
	}

	public Map<String, String> fetchMonthsPaymentDetail(Map<String, String> monthsPaymentDetail, String monthsDetail) {
		monthsPaymentDetail.put("Jan", "");
		monthsPaymentDetail.put("Feb", "");
		monthsPaymentDetail.put("Mar", "");
		monthsPaymentDetail.put("Apr", "");
		monthsPaymentDetail.put("May", "");
		monthsPaymentDetail.put("Jun", "");
		monthsPaymentDetail.put("Jul", "");
		monthsPaymentDetail.put("Aug", "");
		monthsPaymentDetail.put("Sep", "");
		monthsPaymentDetail.put("Oct", "");
		monthsPaymentDetail.put("Nov", "");
		monthsPaymentDetail.put("Dec", "");

		String[] monthDetailArray = monthsDetail.split(",");

		for (int i = 0; i < monthDetailArray.length; i++) {
			switch (monthDetailArray[i]) {
				case "Jan":
					monthsPaymentDetail.put("Jan", "Paid");
				break;
				case "Feb":
					monthsPaymentDetail.put("Feb", "Paid");
					break;
				case "Mar":
					monthsPaymentDetail.put("Mar", "Paid");
					break;
				case "Apr":
					monthsPaymentDetail.put("Apr", "Paid");
					break;
				case "May":
					monthsPaymentDetail.put("May", "Paid");
					break;
				case "Jun":
					monthsPaymentDetail.put("Jun", "Paid");
					break;
				case "Jul":
					monthsPaymentDetail.put("Jul", "Paid");
					break;
				case "Aug":
					monthsPaymentDetail.put("Aug", "Paid");
					break;
				case "Sep":
					monthsPaymentDetail.put("Sep", "Paid");
					break;
				case "Oct":
					monthsPaymentDetail.put("Oct", "Paid");
					break;
				case "Nov":
					monthsPaymentDetail.put("Nov", "Paid");
					break;
				case "Dec":
					monthsPaymentDetail.put("Dec", "Paid");
					break;
			}
		}

		return monthsPaymentDetail;
	}
}
