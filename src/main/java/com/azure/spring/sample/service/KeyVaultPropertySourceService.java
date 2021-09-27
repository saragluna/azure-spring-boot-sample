// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 */
@Service
class KeyVaultPropertySourceService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyVaultSecretService.class);

    @Value("${spring.cloud.azure.keyvault.property-source-enabled:false}")
    private boolean propertySourceEnabled;

    @Value("${spring-cosmos-db-key:abc}")
    private String value;


    @Override
    public void run() {
        Assert.isTrue("rock".equals(value), "The 'spring-cosmos-db-key' should have value 'rock'");
        LOGGER.info("========== Property 'spring-cosmos-db-key' in Azure Key Vault has value: {}", value);
    }

    @Override
    public boolean isAvailable() {
        return this.propertySourceEnabled;
    }

    @Override
    public String getServiceName() {
        return "Key Vault Property Source";
    }
}
