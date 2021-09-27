// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

/**
 *
 */
@Service
public class StorageFileShareService implements AzureService {

    @Autowired(required = false)
    private ShareServiceClient shareServiceClient;

    @Value("${STORAGE_FILE_NAME:none}")
    private String fileName;

    @Value("${STORAGE_FILE_SHARE_NAME:none}")
    private String fileShareName;

    
    @Override
    public void run() {
        final ShareClient fileShare = this.shareServiceClient.getShareClient(fileShareName);
        final ShareFileClient file = fileShare.getFileClient(fileName);
        if (!file.exists()) {
            final String content = "this is my fileShare";
            file.create(content.length() + 10);
            file.upload(new ByteArrayInputStream(content.getBytes()), content.length());
        }

        file.download(System.out);
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
