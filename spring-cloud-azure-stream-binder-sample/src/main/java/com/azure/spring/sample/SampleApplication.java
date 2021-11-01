// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

//
//    // Replace destination with spring.cloud.stream.bindings.consume-in-0.destination
//    // Replace group with spring.cloud.stream.bindings.consume-in-0.group
//    @ServiceActivator(inputChannel = "{destination}.{group}.errors")
//    public void consumerError(Message<?> message) {
//        LOGGER.error("Handling customer ERROR: " + message);
//    }
//
//    // Replace destination with spring.cloud.stream.bindings.supply-out-0.destination
//    @ServiceActivator(inputChannel = "{destination}.errors")
//    public void producerError(Message<?> message) {
//        LOGGER.error("Handling Producer ERROR: " + message);
//    }
}
