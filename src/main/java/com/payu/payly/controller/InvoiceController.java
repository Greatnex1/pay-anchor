package com.payu.payly.controller;

import com.payu.payly.dto.InvoiceDto;
import com.payu.payly.dto.request.InvoiceRequest;
import com.payu.payly.dto.request.InvoiceUrlRequest;
import com.payu.payly.dto.request.PaymentLink;
import com.payu.payly.service.implementation.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.payu.payly.constants.Url.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
@Tag(name="Payment Link Endpoint")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        InvoiceDto invoiceDto = invoiceService.generateInvoice(invoiceRequest);
        return new ResponseEntity<>(invoiceDto, HttpStatus.CREATED);
    }


    @PostMapping(value = "/generate-invoice-link", produces = "application/json")
    public ResponseEntity<InvoiceDto> generateInvoiceLink(@RequestBody InvoiceUrlRequest invoiceUrlRequest) {
        InvoiceDto invoiceDto = invoiceService.generateInvoiceDetailsLink(invoiceUrlRequest);
        return new ResponseEntity<>(invoiceDto, HttpStatus.OK);
    }


    @GetMapping(value = "/view-invoice/{merchantId}/{invoiceId}", produces = "text/html")
    public ResponseEntity<String> viewInvoice(@PathVariable(value = "merchantId") String merchantId,
                                              @PathVariable(value = "invoiceId") String invoiceId) {
        return new ResponseEntity<>(invoiceService.viewInvoice(merchantId, invoiceId), HttpStatus.FOUND);
    }


    @PostMapping(value = "/payment-link", produces = "application/json")
    public ResponseEntity<PaymentLink> paymentLink(@RequestParam(value = "invoiceId") String invoiceId) {
        return new ResponseEntity<>(invoiceService.generatePaymentLink(invoiceId), HttpStatus.ACCEPTED);
    }
}
