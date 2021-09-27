// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class ServiceBusProducerService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBusProducerService.class);

    @Autowired(required = false)
    private ServiceBusSenderClient senderClient;
    
    @Override
    public void run() {
        final ServiceBusMessage message = new ServiceBusMessage("service bus message");
        this.senderClient.sendMessage(message);

        LOGGER.info("Send message to Service Bus {}", message.getBody().toString());
    }

    @Override
    public boolean isAvailable() {
        return this.senderClient != null;
    }

    @Override
    public String getServiceName() {
        return "Service Bus Producer";
    }
}
