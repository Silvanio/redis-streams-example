package com.example.redis.streams.consumer;

import com.example.redis.streams.data.ExampleMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExampleEventConsumer implements StreamListener<String, ObjectRecord<String, ExampleMessage>> {

    @Override
    public void onMessage(ObjectRecord<String, ExampleMessage> record) {
        log.info("Example event consumer reals => on message start: [stream={}, message={}]", record.getStream(), record.getValue());
    }

}
