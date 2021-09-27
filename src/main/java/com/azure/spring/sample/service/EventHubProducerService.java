// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class EventHubProducerService implements AzureService {

    @Autowired(required = false)
    private EventHubProducerClient producerClient;


    @Override
    public void run() {
        List<EventData> eventDataList = new ArrayList<>();
        eventDataList.add(new EventData("send to eventhub 1"));
        eventDataList.add(new EventData("send to eventhub 2"));
        producerClient.send(eventDataList);
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
