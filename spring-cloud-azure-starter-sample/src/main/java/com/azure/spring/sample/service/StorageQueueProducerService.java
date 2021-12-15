// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StorageQueueProducerService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageQueueProducerService.class);
    
    @Autowired(required = false)
    private QueueServiceClient queueServiceClient;

    @Value("${storage.queue.name}")
    private String queueName;
    
    @Override
    public void run() {
        final QueueClient queueClient = queueServiceClient.getQueueClient(queueName);
        String msg = "storage-queue-test";
        queueClient.sendMessage(msg);
        LOGGER.info("########## Storage Queue sent message {}", msg);
    }

    @Override
    public boolean isAvailable() {
        return this.queueServiceClient != null;
    }

    @Override
    public String getServiceName() {
        return "Storage Queue Producer";
    }
}
