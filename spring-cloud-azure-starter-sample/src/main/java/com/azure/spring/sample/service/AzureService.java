// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public interface AzureService {

    Logger LOGGER = LoggerFactory.getLogger(AzureService.class);

    default void callService() {
        if (isAvailable()) {
            LOGGER.info("========== Enter the {} Service =======", getServiceName());
            run();
            LOGGER.info("========== Exit the {} Service =======", getServiceName());
        }
    }

    void run();

    boolean isAvailable();

    String getServiceName();

}
