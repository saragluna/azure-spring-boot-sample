// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.models.QueueMessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class StorageQueueConsumerService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageQueueConsumerService.class);
    
    @Autowired(required = false)
    private QueueServiceClient queueServiceClient;

    @Value("${storage.queue.name}")
    private String queueName;
    
    @Override
    public void run() {
        final QueueClient queueClient = queueServiceClient.getQueueClient(queueName);
        QueueMessageItem queueMessageItem = queueClient.receiveMessage();
        LOGGER.info("########## Storage Queue received message {}", queueMessageItem.getBody().toString());
    }

    @Override
    public boolean isAvailable() {
        return this.queueServiceClient != null;
    }

    @Override
    public String getServiceName() {
        return "Storage Queue Consumer";
    }
}
