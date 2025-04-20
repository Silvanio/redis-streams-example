package com.example.redis.streams.controller;

import com.example.redis.streams.data.ExampleMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ExampleController {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    @PostMapping("/example")
    public void postExampleMessage(@RequestBody ExampleMessage message) {
        log.info("Example event consumer reals => on message start: [stream={}, message={}]", "example-stream", message);
        ObjectRecord<String, ExampleMessage> event = StreamRecords.newRecord().ofObject(message).withStreamKey("example-stream");
        this.redisTemplate.opsForStream().add(event).subscribe();

    }
}
