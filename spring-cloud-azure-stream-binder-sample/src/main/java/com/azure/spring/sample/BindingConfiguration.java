// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class BindingConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplication.class);
    private static final String SEND_TO = "spring.cloud.stream.sendto.destination";

    private int i = 0;

    @Bean
    public Supplier<String> supply() {
        return () -> {
            LOGGER.info("Sending message, sequence " + i);
            return "Hello world, " + i++;
        };
    }

    @Bean
    public Function<String, Message<String>> transform1() {
        return message -> MessageBuilder.withPayload(message + ", transform1")
                                    .setHeader(SEND_TO, "transform1-out-0")
                                    .build();
    }

    @Bean
    public Function<String, Message<String>> transform2() {
        return message -> MessageBuilder.withPayload(message + ", transform2")
                                        .setHeader(SEND_TO, "transform2-out-0")
                                        .build();
    }

    @Bean
    public Consumer<String> consume() {
        return message -> LOGGER.info("===== {}", message);
    }

}
