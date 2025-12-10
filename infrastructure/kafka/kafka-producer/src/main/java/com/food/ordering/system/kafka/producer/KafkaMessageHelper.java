package com.food.ordering.system.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMessageHelper {
    public <T> void handleSuccess(String orderId,
                               T message,
                               SendResult<String, T> result) {
        RecordMetadata metadata = result.getRecordMetadata();

        log.info("Received successful response from Kafka for order id: {} " +
                        "Topic: {} Partition: {} Offset: {} Timestamp: {}",
                orderId,
                metadata.topic(),
                metadata.partition(),
                metadata.offset(),
                metadata.timestamp());
    }

    public <T> void handleFailure(String orderId,
                               T message,
                               Throwable throwable) {
        log.error("Error while sending PaymentRequestAvroModel message to kafka for order id: {} " +
                "and message: {}", orderId, message.toString(), throwable);

        // 실패 처리 로직 (재시도, DLQ, 알림 등)
        // retryTemplate.execute(context -> kafkaProducer.send(...));
        // 또는 실패 이벤트 저장
        // failureEventStore.save(new FailedPublishEvent(orderId, message, throwable));
    }
}
