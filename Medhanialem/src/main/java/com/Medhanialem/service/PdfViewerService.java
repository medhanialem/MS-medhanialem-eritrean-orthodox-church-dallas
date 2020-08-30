package com.Medhanialem.service;

import com.Medhanialem.model.MonthlyPaymentEmailRequest;
import org.springframework.stereotype.Service;

@Service
public interface PdfViewerService {

    byte[] generatePdf(Long receiptId);

}
