// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.core.util.IterableStream;
import com.azure.messaging.eventhubs.EventHubConsumerClient;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 *
 */
@Service
public class EventHubsConsumerService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHubsConsumerService.class);

    @Autowired(required = false)
    private EventHubConsumerClient consumerClient;

    @Override
    public void run() {
        final IterableStream<String> partitionIds = consumerClient.getPartitionIds();
        for (String partitionId : partitionIds) {
            final IterableStream<PartitionEvent> partitionEvents = consumerClient.receiveFromPartition(partitionId, 10,
                                                                                                       EventPosition.earliest(),
                                                                                                       Duration.ofSeconds(4));
            for (PartitionEvent event : partitionEvents) {
                LOGGER.info("########## Received from event hub {}", event.getData().getBodyAsString());
            }
        }
    }

    @Override
    public boolean isAvailable() {
        return this.consumerClient != null;
    }

    @Override
    public String getServiceName() {
        return "Event Hubs Consumer";
    }
}
