// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.core.util.IterableStream;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 *
 */
@Service
public class ServiceBusConsumerService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBusConsumerService.class);

    @Autowired(required = false)
    private ServiceBusReceiverClient receiverClient;
    
    @Override
    public void run() {
        final IterableStream<ServiceBusReceivedMessage> serviceBusReceivedMessages =
            this.receiverClient.receiveMessages(2, Duration.ofSeconds(2));

        for (ServiceBusReceivedMessage message : serviceBusReceivedMessages) {
            LOGGER.info("========== Received from service bus {}", message.getBody().toString());
        }
    }

    @Override
    public boolean isAvailable() {
        return this.receiverClient != null;
    }

    @Override
    public String getServiceName() {
        return "Service Bus Consumer";
    }
}
