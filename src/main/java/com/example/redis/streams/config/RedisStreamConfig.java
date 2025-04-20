package com.example.redis.streams.config;

import com.example.redis.streams.consumer.ExampleEventConsumer;
import com.example.redis.streams.data.ExampleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;
import java.util.UUID;

@Configuration
@Slf4j
public class RedisStreamConfig {

    @Value("${stream.group:example-group}")
    private String group;

    @Value("${stream.example.name:example-stream}")
    private String streamKey;

    private final StreamListener<String, ObjectRecord<String, ExampleMessage>> streamListener;

    public RedisStreamConfig(StreamListener<String, ObjectRecord<String, ExampleMessage>> streamListener) {
        this.streamListener = streamListener;
    }

    @Bean
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory) {

        log.info("Creating consumer group: [stream={}, group={}]", streamKey, group);
        createConsumerGroupIfNotExists(redisConnectionFactory, streamKey, group);

        StreamMessageListenerContainerOptions<String, ObjectRecord<String, ExampleMessage>> options =
                StreamMessageListenerContainerOptions
                        .builder()
                        .batchSize(1)
                        .pollTimeout(Duration.ofSeconds(1))
                        .targetType(ExampleMessage.class)
                        .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, ExampleMessage>> listenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory, options);

        Subscription subscription = listenerContainer.register(
                StreamMessageListenerContainer.StreamReadRequest
                        .builder(StreamOffset.create(streamKey, ReadOffset.lastConsumed()))
                        .cancelOnError(t -> false)
                        .consumer(Consumer.from(group, UUID.randomUUID().toString()))
                        .autoAcknowledge(true)
                        .build(), streamListener);

        log.info("Starting listener container: [stream={}, group={}]", streamKey, group);
        listenerContainer.start();

        return subscription;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    private void createConsumerGroupIfNotExists(RedisConnectionFactory redisConnectionFactory, String streamKey, String groupName) {
        try {
            try {
                redisConnectionFactory.getConnection().streamCommands().xGroupCreate(streamKey.getBytes(), groupName, ReadOffset.from("0-0"), true);
            } catch (RedisSystemException exception) {
                log.warn(exception.getCause().getMessage());
            }
        } catch (RedisSystemException ex) {
            log.error(ex.getMessage());
        }
    }
}
