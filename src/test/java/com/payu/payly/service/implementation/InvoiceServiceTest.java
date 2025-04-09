package com.payu.payly.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payu.payly.dto.InvoiceDto;
import com.payu.payly.dto.request.InvoiceRequest;
import com.payu.payly.dto.request.InvoiceUrlRequest;
import com.payu.payly.dto.request.PaymentLink;
import com.payu.payly.model.Invoice;
import com.payu.payly.model.Merchant;
import com.payu.payly.model.ProductDetail;
import com.payu.payly.repository.InvoiceRepository;
import com.payu.payly.repository.MerchantRepository;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2

class InvoiceServiceTest {
  @Autowired
   private MerchantRepository merchantRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ObjectMapper objectMapper;
    private InvoiceRequest invoiceRequest;
    private InvoiceUrlRequest invoiceUrlRequest;
     Merchant merchant;
     Invoice invoice;


    @BeforeEach
    void setUp() {
invoiceRequest = InvoiceRequest.builder()
        .merchantName("Bokku")
        .merchantAddress("234, Lekki Pennisula")
        .merchantEmail("bokku@gmail.com")
        .productDetails(List.of(
            new ProductDetail("HotBread", BigDecimal.valueOf(1300),2),
               new ProductDetail("Malt", BigDecimal.valueOf(700),7)))
        .build();

    }

    @AfterEach
    void tearDown() {
        removeInvoice();
        removeMerchant();
    }

    @Test
    void testThatInvoiceCanBeGenerated() throws JsonProcessingException {
        merchant = Merchant.builder()
                .name(invoiceRequest.getMerchantName())
                .address(invoiceRequest.getMerchantAddress())
                .email(invoiceRequest.getMerchantEmail())
                .hotline("234-678-99")
                .dateCreated(LocalDateTime.now())
                .build();

        invoice = Invoice.builder()
                .merchantId(merchant.getId())
                .productDetails(getProductDetails(invoiceRequest))
                .totalAmount(getProductTotal(invoiceRequest))
                .build();
        assertNotNull(invoice);
        assertThat(merchant.getEmail()).isEqualTo("bokku@gmail.com");
        assertThat(invoice.getTotalAmount()).isEqualTo(BigDecimal.valueOf(7500));
        log.info("Invoice generated");
    }

   @Test
    void testThatInvoiceAndMerchantIdCanBeGotten(){
        InvoiceDto invoiceDto = invoiceService.generateInvoice(invoiceRequest);
        assertNotNull(invoiceDto);
    log.info("Invoice id ->{}", invoiceDto.getInvoiceId());
    log.info("Merchant id ->{}", invoiceDto.getMerchantId());

   }

    @Test
    void testThatInvoiceLinkCanBeGenerated() {
        InvoiceDto invoiceDto1 = invoiceService.generateInvoice(invoiceRequest);
        invoiceUrlRequest = InvoiceUrlRequest.builder()
                .invoiceId(invoiceDto1.getInvoiceId())
                .merchantId(invoiceDto1.getMerchantId())
                .build();
        InvoiceDto invoiceDto = invoiceService.generateInvoiceDetailsLink(invoiceUrlRequest);
        assertNotNull(invoiceDto);
        MatcherAssert.assertThat(invoiceDto.getUrl(), instanceOf(String.class));
    }

    @Test
    void testThatInvoiceCanBeViewed() {
        InvoiceDto invoiceDto = invoiceService.generateInvoice(invoiceRequest);
        invoiceUrlRequest = InvoiceUrlRequest.builder()
                .invoiceId(invoiceDto.getInvoiceId())
                .merchantId(invoiceDto.getMerchantId())
                .build();
        InvoiceDto invoiceDto1 = invoiceService.generateInvoiceDetailsLink(invoiceUrlRequest);
        assertNotNull(invoiceDto1);
        MatcherAssert.assertThat(invoiceDto1.getUrl(), instanceOf(String.class));

        String invoice = invoiceService.viewInvoice(String.valueOf(invoiceDto.getMerchantId()), String.valueOf(invoiceDto.getInvoiceId()));
        assertNotNull(invoice);
        MatcherAssert.assertThat(invoiceDto1.getUrl(), instanceOf(String.class));
    }

    @Test
    void testThatPaymentLinkCanBeGenerated() {
        InvoiceDto invoiceDto = invoiceService.generateInvoice(invoiceRequest);
        invoiceUrlRequest = new InvoiceUrlRequest();
        invoiceUrlRequest.setInvoiceId(invoiceDto.getInvoiceId());
        invoiceUrlRequest.setMerchantId(invoiceDto.getMerchantId());
        InvoiceDto invoiceDto1 = invoiceService.generateInvoiceDetailsLink(invoiceUrlRequest);
        assertNotNull(invoiceDto1);
        assertNotNull(invoiceUrlRequest, "Invoice with URL should not be null");

       MatcherAssert.assertThat(invoiceDto1.getUrl(), instanceOf(String.class));

        String invoice = invoiceService.viewInvoice(String.valueOf(invoiceDto.getMerchantId()), String.valueOf(invoiceDto.getInvoiceId()));
        assertNotNull(invoice);
        MatcherAssert.assertThat(invoiceDto1.getUrl(), instanceOf(String.class));
        PaymentLink paymentLink = invoiceService.generatePaymentLink(String.valueOf(invoiceDto.getInvoiceId()));
        assertNotNull(paymentLink);
        assertNotNull(paymentLink, "Payment link should not be null");

        MatcherAssert.assertThat(paymentLink.getPaymentUrl(), instanceOf(String.class));
    }

    void removeInvoice() {
        invoiceRepository.deleteAll();
    }

    void removeMerchant() {
        merchantRepository.deleteAll();
    }

    private String getProductDetails(InvoiceRequest invoiceRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(invoiceRequest.getProductDetails());

    }
    private static BigDecimal getProductTotal(InvoiceRequest invoiceRequest) {
        return invoiceRequest.getProductDetails().stream().map(productDetails1 -> productDetails1.getProductPrice()
                .multiply(BigDecimal.valueOf(productDetails1.getProductQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}