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
public class StorageFileResourceService implements AzureService {

    @Autowired(required = false)
    private StorageFileShareService storageFileShareService;

    @Value("${storage-file-resource-name}")
    private Resource fileResource;

    @Override
    public void run() {
        try {
            LOGGER.info("########## File resource {}", StreamUtils.copyToString(this.fileResource.getInputStream(),
                                                                                Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAvailable() {
        return this.storageFileShareService.isAvailable();
    }

    @Override
    public String getServiceName() {
        return "Storage File Resource";
    }
}
