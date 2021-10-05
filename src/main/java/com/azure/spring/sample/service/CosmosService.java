// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.spring.sample.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CosmosService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CosmosService.class);

    @Autowired(required = false)
    private CosmosClient cosmosClient;

    @Value("${COSMOS_DATABASE_NAME}")
    private String databaseName;

    @Override
    public void run() {
        String containerName = "my-container";
        cosmosClient.getDatabase(databaseName).createContainerIfNotExists(containerName, "/lastName");
        User item = User.randomUser();
        CosmosContainer container = cosmosClient.getDatabase(databaseName).getContainer(containerName);
        container.createItem(item);

        LOGGER.info("Save new item {} to cosmos database {}", item, databaseName);
    }

    @Override
    public boolean isAvailable() {
        return this.cosmosClient != null;
    }

    @Override
    public String getServiceName() {
        return "Cosmos";
    }
}
