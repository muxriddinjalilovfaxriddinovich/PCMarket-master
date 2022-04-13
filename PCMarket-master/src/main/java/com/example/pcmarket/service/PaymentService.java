package com.example.pcmarket.service;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.dto.PaymentDto;
import com.example.pcmarket.entity.Invoice;
import com.example.pcmarket.entity.Payment;
import com.example.pcmarket.repository.InvoiceRepository;
import com.example.pcmarket.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    final PaymentRepository paymentRepository;
    final InvoiceRepository invoiceRepository;

    public ApiResponse add(PaymentDto dto) {
        Optional<Invoice> byId = invoiceRepository.findById(dto.getInvoice_id());
        if (!byId.isPresent()) {
            return new ApiResponse("Xatolik",false);
        }
        Invoice invoice=byId.get();
        Payment payment=new Payment();
        payment.setAmount(dto.getAmount());
        payment.setInvoice(invoice);
        Payment save = paymentRepository.save(payment);
        return new ApiResponse("Added",true,save);
    }

}
