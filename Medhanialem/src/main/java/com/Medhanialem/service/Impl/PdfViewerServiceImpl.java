package com.Medhanialem.service.Impl;

import com.Medhanialem.exception.ApplicationException;
import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.payment.objects.MembershipReceiptHistory;
import com.Medhanialem.repository.MembershipReceiptHistoryRepository;
import com.Medhanialem.service.PdfViewerService;
import com.Medhanialem.utils.BuildReceiptByEmailUtility;
import com.Medhanialem.utils.PaymentReceiptConstants;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class PdfViewerServiceImpl implements PdfViewerService {

    Logger logger = LoggerFactory.getLogger(PdfViewerServiceImpl.class);

    @Autowired
    MembershipReceiptHistoryRepository membershipReceiptHistoryRepository;

    @Autowired
    BuildReceiptByEmailUtility buildReceiptByEmailUtility;


    @Override
    public byte[] generatePdf(Long receiptId) {

        MembershipReceiptHistory membershipReceiptHistory = membershipReceiptHistoryRepository.findByReceiptId(receiptId).orElseThrow(
                () -> new BackendException("There is no receipt found with receiptId = " + receiptId));

        ByteArrayOutputStream outputStream = null;
        byte[] bytes;
        try {
            outputStream = new ByteArrayOutputStream();
            writePdf(outputStream, membershipReceiptHistory);
            bytes = outputStream.toByteArray();
        } catch (Exception e) {
			logger.error("Pdf write failed with error"+e.getMessage());
			throw new ApplicationException("Pdf write failed  failed!");
        } finally {
            //clean off
            if (null != outputStream) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (Exception ex) {
                }
            }
        }
        return bytes;
    }

    public void writePdf(OutputStream outputStream, MembershipReceiptHistory membershipReceiptHistory) throws Exception {
        Rectangle pageSize = new Rectangle(PageSize.A4.rotate());
        pageSize.setBackgroundColor(new BaseColor(242,250,250));
        Document document = new Document(pageSize,30,30,60,35);
        PdfWriter.getInstance(document, outputStream);

        document.open();

		Font font = FontFactory.getFont(Font.FontFamily.HELVETICA.name(), 14, new BaseColor(0,0,139));

        document.add(new Paragraph(""));
        Chunk chunk1 = new Chunk("ናይ ኤርትራውያን ካቴድራል መድሓኔኣለም ኦርቶዶክስ ተዋሕዶ ቤተ ክርስትያን", font);
        Paragraph po= new Paragraph("CATHEDRAL ERITREAN ORTHODOX TEWAHDO MEDHANEALEM CHURCH ", font);
        po.setAlignment(Element.ALIGN_CENTER);

		document.add(chunk1);
        document.add(po);

		Paragraph p1= new Paragraph(" 1720 GUS THOMASSON RD.",font);
		p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);

        Paragraph p2= new Paragraph(" DALLAS TX 75228",font);
        p2.setAlignment(Element.ALIGN_CENTER);
        document.add(p2);

        document.add(new Paragraph("\n\n\n"));
		PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(50);
        table.setWidths(new float[] {1, 2});
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

		addRows(table,membershipReceiptHistory);
		document.add(table);

        document.add(new Paragraph("\n"));
        PdfPTable table2 = new PdfPTable(12);
        table2.setWidthPercentage(100);
        table2.setHorizontalAlignment(Element.ALIGN_LEFT);

        Font font2 = FontFactory.getFont("Arial", 11, Font.BOLD);
        Paragraph table2Heading= new Paragraph("Year: " + membershipReceiptHistory.getYear(), font2);
        table2Heading.setAlignment(Element.ALIGN_LEFT);
        document.add(table2Heading);
        document.add(new Paragraph("\n"));

        addMonthsHeadersTable(table2);

        Map<String, String> monthsPaymentDetail = new HashMap<>();
        buildReceiptByEmailUtility.fetchMonthsPaymentDetail(monthsPaymentDetail, membershipReceiptHistory);

        addPaidMonthsTable(table2, monthsPaymentDetail);
        document.add(table2);

        document.close();
    }

    private void addMonthsHeadersTable(PdfPTable table2) {
        Font monthsDetailFont = FontFactory.getFont("Arial", 11, Font.BOLD);
        Stream.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(1);
                    header.setBorderColor(new BaseColor(173, 216, 230));
                    header.setPhrase(new Phrase(columnTitle, monthsDetailFont));
                    header.setPaddingLeft(3);
                    table2.addCell(header);
                });
    }

    private void addPaidMonthsTable(PdfPTable table2, Map<String, String> monthsPaymentDetail) {
        Stream.of(monthsPaymentDetail.get(PaymentReceiptConstants.JAN),monthsPaymentDetail.get(PaymentReceiptConstants.FEB) ,  monthsPaymentDetail.get(PaymentReceiptConstants.MAR), monthsPaymentDetail.get(PaymentReceiptConstants.APR),monthsPaymentDetail.get(PaymentReceiptConstants.MAY), monthsPaymentDetail.get(PaymentReceiptConstants.JUN),monthsPaymentDetail.get(PaymentReceiptConstants.JUL) ,monthsPaymentDetail.get(PaymentReceiptConstants.AUG),monthsPaymentDetail.get(PaymentReceiptConstants.SEP),monthsPaymentDetail.get(PaymentReceiptConstants.OCT),monthsPaymentDetail.get(PaymentReceiptConstants.NOV),monthsPaymentDetail.get(PaymentReceiptConstants.DEC))
                .forEach(monthCol -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(1);
                    header.setBorderColor(new BaseColor(173, 216, 230));
                    header.setPhrase(new Phrase(monthCol));
                    header.setPaddingLeft(3);
                    header.setBackgroundColor(BaseColor.WHITE);
                    table2.addCell(header);
                });
    }

    private void addRows(PdfPTable table,MembershipReceiptHistory membershipReceiptHistory) {
        Font memberDetailsFont = FontFactory.getFont("Arial", 11, Font.BOLD);
        PdfPCell header3 = new PdfPCell();
        header3.setBorderWidth(1);
        header3.setPhrase(new Phrase("Full Name", memberDetailsFont));
        header3.setPaddingLeft(4);
        table.addCell(header3);

        PdfPCell nameVal = new PdfPCell();
        nameVal.setBackgroundColor(BaseColor.WHITE);
        nameVal.setPhrase(new Phrase(membershipReceiptHistory.getFullName()));
        nameVal.setPaddingLeft(4);
        table.addCell(nameVal);

        PdfPCell header2 = new PdfPCell();
        header2.setBorderWidth(1);
        header2.setPhrase(new Phrase("Church Id", memberDetailsFont));
        header2.setPaddingLeft(4);
        table.addCell(header2);

        PdfPCell churchidVal = new PdfPCell();
        churchidVal.setBackgroundColor(BaseColor.WHITE);
        churchidVal.setPhrase(new Phrase(membershipReceiptHistory.getChurchId()));
        churchidVal.setPaddingLeft(4);
        table.addCell(churchidVal);


        PdfPCell header4 = new PdfPCell();
        header4.setBorderWidth(1);
        header4.setPhrase(new Phrase("Phone", memberDetailsFont));
        header4.setPaddingLeft(4);
        table.addCell(header4);

        PdfPCell phoneVal = new PdfPCell();
        phoneVal.setBackgroundColor(BaseColor.WHITE);
        phoneVal.setPhrase(new Phrase(membershipReceiptHistory.getPhone()));
        phoneVal.setPaddingLeft(4);
        table.addCell(phoneVal);

        PdfPCell header5 = new PdfPCell();
        header5.setBorderWidth(1);
        header5.setPhrase(new Phrase("Tier", memberDetailsFont));
        header5.setPaddingLeft(4);
        table.addCell(header5);

        PdfPCell tierVal = new PdfPCell();
        tierVal.setBackgroundColor(BaseColor.WHITE);
        tierVal.setPhrase(new Phrase(membershipReceiptHistory.getTierDescription()));
        tierVal.setPaddingLeft(4);
        table.addCell(tierVal);

        PdfPCell header6 = new PdfPCell();
        header6.setBorderWidth(1);
        header6.setPhrase(new Phrase("Months", memberDetailsFont));
        header6.setPaddingLeft(4);
        table.addCell(header6);

        PdfPCell monthsVal = new PdfPCell();
        monthsVal.setBackgroundColor(BaseColor.WHITE);
        monthsVal.setPhrase(new Phrase(String.valueOf(membershipReceiptHistory.getMonths())));
        monthsVal.setPaddingLeft(4);
        table.addCell(monthsVal);

        PdfPCell header7 = new PdfPCell();
        header7.setBorderWidth(1);
        header7.setPhrase(new Phrase("Date", memberDetailsFont));
        header7.setPaddingLeft(4);
        table.addCell(header7);

        PdfPCell dateVal = new PdfPCell();
        dateVal.setBackgroundColor(BaseColor.WHITE);
        dateVal.setPhrase(new Phrase(membershipReceiptHistory.getCreatedDate().toString()));
        dateVal.setPaddingLeft(4);
		table.addCell(dateVal);

        PdfPCell header8 = new PdfPCell();
        header8.setBorderWidth(1);
        header8.setPhrase(new Phrase("Total", memberDetailsFont));
        header8.setPaddingLeft(4);
        table.addCell(header8);

        PdfPCell totValue = new PdfPCell();
        totValue.setBackgroundColor(BaseColor.WHITE);

        if (!membershipReceiptHistory.isVoided() || null == membershipReceiptHistory.getParentReceipt()) {
            totValue.setPhrase(new Phrase("$" + membershipReceiptHistory.getTotal()));
        } else {
            totValue.setPhrase(new Phrase("-$" + (-1 * membershipReceiptHistory.getTotal())));
        }

        totValue.setPaddingLeft(4);
        table.addCell(totValue);


        PdfPCell header1 = new PdfPCell();
        header1.setBorderWidth(1);
        header1.setPhrase(new Phrase("Receipt No", memberDetailsFont));
        header1.setPaddingLeft(4);
        table.addCell(header1);

        PdfPCell header10 = new PdfPCell();
        header10.setBackgroundColor(BaseColor.WHITE);
        header10.setPhrase(new Phrase(membershipReceiptHistory.getReceiptId().toString()));
        header10.setPaddingLeft(4);
        table.addCell(header10);

        if (membershipReceiptHistory.isVoided() && null != membershipReceiptHistory.getParentReceipt()) {
            PdfPCell originalReceipt = new PdfPCell();
            originalReceipt.setBorderWidth(1);
            originalReceipt.setPhrase(new Phrase("Original Receipt No", memberDetailsFont));
            originalReceipt.setPaddingLeft(4);
            table.addCell(originalReceipt);

            PdfPCell originalReceiptValue = new PdfPCell();
            originalReceiptValue.setBackgroundColor(BaseColor.WHITE);
            originalReceiptValue.setPhrase(new Phrase("" + membershipReceiptHistory.getParentReceipt()));
            originalReceiptValue.setPaddingLeft(4);
            table.addCell(originalReceiptValue);
        }

        if (membershipReceiptHistory.isVoided() && null == membershipReceiptHistory.getParentReceipt()) {
            PdfPCell voided = new PdfPCell();
            voided.setBorderWidth(1);
            voided.setPhrase(new Phrase("Voided", memberDetailsFont));
            voided.setPaddingLeft(4);
            table.addCell(voided);

            PdfPCell voidedValue = new PdfPCell();
            voidedValue.setBackgroundColor(BaseColor.WHITE);
            voidedValue.setPhrase(new Phrase("TRUE"));
            voidedValue.setPaddingLeft(4);
            table.addCell(voidedValue);
        }
	}
}
