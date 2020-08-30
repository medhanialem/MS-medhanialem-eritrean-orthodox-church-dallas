package com.Medhanialem.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.MonthlyPaymentEmailRequest;
import com.Medhanialem.model.payment.objects.MembershipReceiptHistory;
import com.Medhanialem.repository.MembershipReceiptHistoryRepository;
import com.Medhanialem.service.EmailService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
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

	@Override
	public boolean sendEmail(MonthlyPaymentEmailRequest monthlyPaymentEmailRequest) {
		
		logger.info("Inside sendEmail() method, {}", logger.getName());
		logger.info("Request object: {}", monthlyPaymentEmailRequest);
		
		boolean sendEmailResult = false;
		
		MembershipReceiptHistory  membershipReceiptHistory = membershipReceiptHistoryRepository.findByReceiptId(monthlyPaymentEmailRequest.getReceiptId()).orElseThrow(
				() -> new BackendException("There is no receipt found with receiptId = " + monthlyPaymentEmailRequest.getReceiptId()));
		
		String smtpHost = "smtp.gmail.com";
	    int smtpPort = 587;
//	    int smtpPort = 465;

	    String sender = "medhanialemdallas@gmail.com";
	    String recipient = monthlyPaymentEmailRequest.getEmail();
	    String content = "Hi " + membershipReceiptHistory.getFullName() + ". Thank you! We have received your monthly membership payment and you will find your receipt attached.";
	    String subject = "Monthly Membership Payment Receipt";
	    
	    Properties properties = new Properties();
	    properties.put("mail.smtp.host", smtpHost);
	    properties.put("mail.smtp.port", smtpPort);  
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.debug", "true");
	    
	    Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
	    	protected PasswordAuthentication getPasswordAuthentication() {
	    		return new PasswordAuthentication("medhanialemdallas@gmail.com", "Medhanit@Alem1");
	    		}
	    		});

	    ByteArrayOutputStream outputStream = null;
	    
	    try {           
	        //construct the text body part
	        MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(content);

	        //now write the PDF content to the output stream
	        outputStream = new ByteArrayOutputStream();
//	        writePdf(outputStream, membershipReceiptHistory);
	        byte[] bytes = outputStream.toByteArray();

	        //construct the pdf body part
	        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
	        MimeBodyPart pdfBodyPart = new MimeBodyPart();
	        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	        pdfBodyPart.setFileName("MedhanieAlem_Monthly_Membership_Payment.pdf");

	        //construct the mime multi part
	        MimeMultipart mimeMultipart = new MimeMultipart();
	        mimeMultipart.addBodyPart(textBodyPart);
	        mimeMultipart.addBodyPart(pdfBodyPart);

	        //create the sender/recipient addresses
	        InternetAddress iaSender = new InternetAddress(sender);
	        InternetAddress iaRecipient = new InternetAddress(recipient);

	        //construct the mime message
	        MimeMessage mimeMessage = new MimeMessage(session);
	        mimeMessage.setSender(iaSender);
	        mimeMessage.setSubject(subject);
	        mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
	        mimeMessage.setContent(mimeMultipart);

	        //send off the email
	        Transport.send(mimeMessage);
	        sendEmailResult = true;
	        System.out.println("sent from " + sender + ", to " + recipient + "; server = " + smtpHost + ", port = " + smtpPort);         
	    } catch(Exception ex) {
	    	 sendEmailResult = false;
	        ex.printStackTrace();
	    } finally {
	        //clean off
	        if(null != outputStream) {
	            try { outputStream.close(); outputStream = null; }
	            catch(Exception ex) { }
	        }
	    }
	    
		return sendEmailResult;
	}
	
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

}
