// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class KeyVaultSecretService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyVaultSecretService.class);

    @Autowired(required = false)
    private SecretClient secretClient;

    @Override
    public void run() {
        final KeyVaultSecret secret = secretClient.getSecret("spring-cosmos-db-key");
        LOGGER.info("========== Received from kv {}", secret.getValue());
    }

    @Override
    public boolean isAvailable() {
        return this.secretClient != null;
    }

    @Override
    public String getServiceName() {
        return "Key Vault Secret";
    }
}
