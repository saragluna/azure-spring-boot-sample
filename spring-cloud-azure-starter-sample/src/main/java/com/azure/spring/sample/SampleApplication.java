// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample;

import com.azure.spring.sample.service.AzureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SampleApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplication.class);

    private List<AzureService> azureServices;

    SampleApplication(List<AzureService> azureServices) {
        this.azureServices = azureServices;
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

    public void run(String[] args) {
        for (AzureService service : azureServices) {
            service.callService();
        }
    }

}
