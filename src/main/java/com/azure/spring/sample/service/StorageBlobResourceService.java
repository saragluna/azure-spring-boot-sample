// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 */
@Service
public class StorageBlobResourceService implements AzureService {

    @Autowired(required = false)
    private StorageBlobService storageBlobService;

    @Value("${storage-blob-resource-name}")
    private Resource storageResource;

    @Override
    public void run() {
        try {
            LOGGER.info("########## Storage resource {}", StreamUtils.copyToString(this.storageResource.getInputStream(),
                                                                                   Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAvailable() {
        return this.storageBlobService.isAvailable();
    }

    @Override
    public String getServiceName() {
        return "Storage Bob Resource";
    }
}
