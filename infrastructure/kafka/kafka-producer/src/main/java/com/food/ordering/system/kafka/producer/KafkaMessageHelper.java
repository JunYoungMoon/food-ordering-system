package com.food.ordering.system.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaMessageHelper {

    private final ObjectMapper objectMapper;

    public KafkaMessageHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T getOrderEventPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            log.error("Could not read {} object!", outputType.getName(), e);
            throw new OrderDomainException("Could not read " + outputType.getName() + " object!", e);
        }
    }

    /**
     * 성공 시 로깅 + Outbox 상태 업데이트
     */
    public <T, U> void handleSuccess(String orderId,
                                     T message,
                                     SendResult<String, T> result,
                                     U outboxMessage,
                                     BiConsumer<U, OutboxStatus> outboxCallback) {
        RecordMetadata metadata = result.getRecordMetadata();

        log.info("Received successful response from Kafka for order id: {} " +
                        "Topic: {} Partition: {} Offset: {} Timestamp: {}",
                orderId,
                metadata.topic(),
                metadata.partition(),
                metadata.offset(),
                metadata.timestamp());

        // Outbox 상태 업데이트
        outboxCallback.accept(outboxMessage, OutboxStatus.COMPLETED);
    }

    /**
     * 실패 시 로깅 + Outbox 상태 업데이트
     */
    public <T, U> void handleFailure(String orderId,
                                     T message,
                                     Throwable throwable,
                                     U outboxMessage,
                                     BiConsumer<U, OutboxStatus> outboxCallback) {
        log.error("Error while sending message to kafka for order id: {} " +
                "and message: {}", orderId, message.toString(), throwable);

        // Outbox 상태 업데이트
        outboxCallback.accept(outboxMessage, OutboxStatus.FAILED);
    }
}