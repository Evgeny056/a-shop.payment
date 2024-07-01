package com.ashoppayment.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

public class CustomJsonDeserializer<T> extends JsonDeserializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomJsonDeserializer(Class<T> targetType) {
        super(targetType);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return super.deserialize(topic, data);
        } catch (SerializationException e) {
            // Handle exception if necessary
            throw e;
        }
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            return super.deserialize(topic, headers, data);
        } catch (SerializationException e) {
            // Handle exception if necessary
            throw e;
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Perform any additional configuration
        super.configure(configs, isKey);
    }

    @Override
    public void close() {
        // Perform any closing operations
        super.close();
    }
}