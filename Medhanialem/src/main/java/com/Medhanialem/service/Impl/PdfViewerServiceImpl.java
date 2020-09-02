package com.Medhanialem.service.Impl;

import com.Medhanialem.exception.ApplicationException;
import com.Medhanialem.exception.BackendException;
import com.Medhanialem.model.payment.objects.MembershipReceiptHistory;
import com.Medhanialem.repository.MembershipReceiptHistoryRepository;
import com.Medhanialem.service.PdfViewerService;
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
import java.util.stream.Stream;

@Service
public class PdfViewerServiceImpl implements PdfViewerService {

    Logger logger = LoggerFactory.getLogger(PdfViewerServiceImpl.class);

    @Autowired
    MembershipReceiptHistoryRepository membershipReceiptHistoryRepository;


    @Override
    public byte[] generatePdf(Long receiptId) {

        MembershipReceiptHistory membershipReceiptHistory = membershipReceiptHistoryRepository.findById(receiptId).orElseThrow(
                () -> new BackendException("There is no receipt found with receiptId = " + receiptId));

        ByteArrayOutputStream outputStream = null;
        byte[] bytes = null;
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
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();

		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Receipt", font);
		document.add(chunk);

		Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk(" " ));

        document.add(paragraph);

		PdfPTable table = new PdfPTable(4);
		addTableHeader(table);
		addRows(table,membershipReceiptHistory);
		document.add(table);

        document.close();
    }

	private void addRows(PdfPTable table,MembershipReceiptHistory membershipReceiptHistory) {
        table.addCell(membershipReceiptHistory.getChurchId());
		table.addCell(membershipReceiptHistory.getFullName().toUpperCase());
		table.addCell(membershipReceiptHistory.getCreatedDate().toString());
		table.addCell(membershipReceiptHistory.getTotal().toString());
	}

	private void addTableHeader(PdfPTable table) {
		Stream.of("Receipt No.","Full Name", "Date", "Total paid")
				.forEach(columnTitle -> {
					PdfPCell header = new PdfPCell();
					header.setBackgroundColor(BaseColor.LIGHT_GRAY);
					header.setBorderWidth(2);
					header.setPhrase(new Phrase(columnTitle));
					table.addCell(header);
				});
	}
}
