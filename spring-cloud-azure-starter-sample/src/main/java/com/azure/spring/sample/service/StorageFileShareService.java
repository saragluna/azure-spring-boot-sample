// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

/**
 *
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StorageFileShareService implements AzureService {

    @Autowired(required = false)
    private ShareServiceClient shareServiceClient;

    @Value("${file-name}")
    private String fileName;

    @Value("${STORAGE_SHARE_NAME}")
    private String shareName;

    
    @Override
    public void run() {
        final ShareClient fileShare = this.shareServiceClient.getShareClient(shareName);
        final ShareFileClient file = fileShare.getFileClient(fileName);
        if (!file.exists()) {
            final String content = "this is my fileShare";
            file.create(content.length() + 10);
            file.upload(new ByteArrayInputStream(content.getBytes()), content.length());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        file.download(baos);
        LOGGER.info("########## File resource {}", StreamUtils.copyToString(baos, Charset.defaultCharset()));
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
