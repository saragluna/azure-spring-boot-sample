// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class StorageBlobService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageBlobService.class);
    
    @Autowired(required = false)
    private BlobContainerClient blobContainerClient;

    @Value("${STORAGE_BLOB_NAME:none}")
    private String blobName;
    
    @Override
    public void run() {
        final BlobClient blob = blobContainerClient.getBlobClient(blobName);

        if (!blob.exists()) {
            blob.upload(BinaryData.fromString("this is my content"));
        }

        final String read = blob.downloadContent().toString();
        LOGGER.info("========== Received from storage {}", read);
    }

    @Override
    public boolean isAvailable() {
        return this.blobContainerClient != null;
    }

    @Override
    public String getServiceName() {
        return "Storage Blob";
    }
}
