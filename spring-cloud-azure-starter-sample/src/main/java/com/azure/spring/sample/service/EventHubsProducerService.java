// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EventHubsProducerService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHubsProducerService.class);

    @Autowired(required = false)
    private EventHubProducerClient producerClient;


    @Override
    public void run() {
        List<EventData> eventDataList = new ArrayList<>();
        eventDataList.add(new EventData("send to eventhub 1"));
        eventDataList.add(new EventData("send to eventhub 2"));
        producerClient.send(eventDataList);

        LOGGER.info("########## Sent two messages to Event Hub");
    }

    @Override
    public boolean isAvailable() {
        return this.producerClient != null;
    }

    @Override
    public String getServiceName() {
        return "Event Hubs Producer";
    }
}
