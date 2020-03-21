package com.Medhanialem.model.payment.objects;

import java.util.List;

public class PaymentLookupResponseByYear {

	private Long tierId;
	private List<PaymentLookUps> paymentLookUps;

	public Long getTierId() {
		return tierId;
	}

	public void setTierId(Long tierId) {
		this.tierId = tierId;
	}

	public List<PaymentLookUps> getPaymentLookUps() {
		return paymentLookUps;
	}

	public void setPaymentLookUps(List<PaymentLookUps> paymentLookUps) {
		this.paymentLookUps = paymentLookUps;
	}

}
