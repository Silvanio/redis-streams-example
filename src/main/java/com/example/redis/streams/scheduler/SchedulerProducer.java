package com.example.redis.streams.scheduler;

import com.example.redis.streams.data.ExampleMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SchedulerProducer {

    private static final String STREAM_NAME = "example-stream";

    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Scheduled(fixedRate = 2000)
    public void publishEvent() {
        String message = "Execution time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        log.info("[...SchedulerProducer] Sending message to Redis stream: {}", message);
        ObjectRecord<String, ExampleMessage> event = StreamRecords.newRecord()
                .ofObject(ExampleMessage.builder().message(message).build())
                .withStreamKey(STREAM_NAME);
        this.redisTemplate.opsForStream().add(event).subscribe();
    }
}

