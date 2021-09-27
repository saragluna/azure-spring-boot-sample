// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

/**
 *
 */
@Service
public class StorageFileShareService implements AzureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageFileShareService.class);

    @Autowired(required = false)
    private ShareServiceClient shareServiceClient;
    
    @Override
    public void run() {
        final ShareClient fileshare1 = this.shareServiceClient.getShareClient("fileshare1");
        final ShareFileClient abc = fileshare1.getFileClient("abcd");
        if (!abc.exists()) {
            final String content = "this is my file";
            abc.create(content.length() + 10);
            abc.upload(new ByteArrayInputStream(content.getBytes()), content.length());
        }

        abc.download(System.out);
    }

    @Override
    public boolean isAvailable() {
        return this.shareServiceClient != null;
    }

    @Override
    public String getServiceName() {
        return "Storage File Share";
    }
}
