package com.payu.payly.service.interfaces;

import com.payu.payly.dto.InvoiceDto;
import com.payu.payly.dto.request.InvoiceRequest;
import com.payu.payly.dto.request.InvoiceUrlRequest;
import com.payu.payly.dto.request.PaymentLink;

public interface InvoiceUseCase {

    InvoiceDto generateInvoice(InvoiceRequest invoiceRequest);
    InvoiceDto generateInvoiceDetailsLink(InvoiceUrlRequest invoiceUrlRequest);
    String viewInvoice(String merchantId, String invoiceId);
    PaymentLink generatePaymentLink(String invoiceId);
}
