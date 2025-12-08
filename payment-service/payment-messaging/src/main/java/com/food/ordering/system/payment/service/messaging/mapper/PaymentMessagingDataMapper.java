package com.food.ordering.system.payment.service.messaging.mapper;

import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus;
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {
    public PaymentResponseAvroModel paymentCompletedEventToPaymentResponseAvroModel(PaymentCompletedEvent paymentCompletedEvent){
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(""))
                .setPaymentId(paymentCompletedEvent.getPayment().getId().getValue())
                .setCustomerId(paymentCompletedEvent.getPayment().getCustomerId().getValue())
                .setOrderId(paymentCompletedEvent.getPayment().getOrderId().getValue())
                .setPrice(paymentCompletedEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCompletedEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCompletedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCompletedEvent.getFailureMessages())
                .build();
    }
}
