package com.Medhanialem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medhanialem.model.payment.PaymentJournal;

@Repository
public interface PaymentJournalRepository extends JpaRepository<PaymentJournal,Long> {

}
