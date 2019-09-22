package com.relay42.browser.processor;

public interface KafkaMessageProcessor<T> {
    void process(T t);
}
