package com.Medhanialem.service;

import com.Medhanialem.model.payment.PaymentLookup;
import com.Medhanialem.model.payment.objects.PaymentLookUps;
import com.Medhanialem.model.payment.objects.PaymentLookupResponseByYear;

import java.util.List;

public interface PaymentLookUpService {
    List<PaymentLookup> createPaymentLookUp(List<PaymentLookup> paymentLookups, Long tierId);

    List<PaymentLookUps> getPaymentLookupsByYearATier(Long tierId, Long year);

    List<PaymentLookupResponseByYear> getPaymentLookupInfo(Long year);

    List<PaymentLookup> getAllPaymentLookupByYear(Long year);
}
