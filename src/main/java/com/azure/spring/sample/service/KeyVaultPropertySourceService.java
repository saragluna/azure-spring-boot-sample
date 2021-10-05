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

    @Value("${KEY_VAULT_SECRET_NAME}")
    private String secretName;

    @Value("${${KEY_VAULT_SECRET_NAME}:abc}")
    private String value;

    @Value("${STORAGE_ACCOUNT_KEY}")
    private String storageAccountKey;



    @Override
    public void run() {
        Assert.isTrue(storageAccountKey.equals(value), "The " + secretName + " shouldn't have value " + storageAccountKey);
        LOGGER.info("########## Property '{}' in Azure Key Vault has value: {}", secretName, value);
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
