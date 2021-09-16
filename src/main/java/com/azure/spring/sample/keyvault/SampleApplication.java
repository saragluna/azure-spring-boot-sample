// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.keyvault;

import com.azure.core.util.BinaryData;
import com.azure.core.util.IterableStream;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubConsumerClient;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionEvent;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SampleApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplication.class);

    @Value("${spring-data-source-url}")
    private String springDataSourceUrl;

    @Value("azure-blob://container1/abc")
    private Resource storageResource;

    @Value("azure-file://fileshare1/abc")
    private Resource fileResource;


    @Autowired
    private SecretClient secretClient;

    @Autowired
    private EventHubConsumerClient consumerClient;

    @Autowired
    private EventHubProducerClient producerClient;

    @Autowired
    private BlobContainerClient blobContainerClient;

    @Autowired
    private ShareServiceClient shareServiceClient;

    @Autowired
    private ServiceBusReceiverClient receiverClient;

    @Autowired
    private ServiceBusSenderClient senderClient;


    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

    public void run(String[] args) {
        LOGGER.info("========== Property springDataSourceUrl in Azure Key Vault: {}", springDataSourceUrl);

        testSecret();
        testEventHub();
        testFileShare();

        testStorageResource();
        testFileResource();
        testStorage();
        testServiceBus();
    }

    private void testStorageResource() {
        try {
            LOGGER.info("========== Storage resource {}", StreamUtils.copyToString(this.storageResource.getInputStream(),
                                                                              Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testFileResource() {
        try {
            LOGGER.info("========== File resource {}", StreamUtils.copyToString(this.fileResource.getInputStream(),
                                                                              Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testSecret() {
        final KeyVaultSecret secret = secretClient.getSecret("spring-data-source-url");
        LOGGER.info("========== Received from kv {}", secret.getValue());
    }

    private void testEventHub() {
        List<EventData> eventDataList = new ArrayList<>();
        eventDataList.add(new EventData("send to eventhub 1"));
        eventDataList.add(new EventData("send to eventhub 2"));
        producerClient.send(eventDataList);

        final IterableStream<String> partitionIds = consumerClient.getPartitionIds();
        for (String partitionId : partitionIds) {
            final IterableStream<PartitionEvent> partitionEvents = consumerClient.receiveFromPartition(partitionId, 10,
                                                                                                       EventPosition.earliest(),
                                                                                                       Duration.ofSeconds(4));
            for (PartitionEvent event : partitionEvents) {
                LOGGER.info("========== Received from event hub {}", event.getData().getBodyAsString());
            }
        }
    }

    private void testStorage() {
        final BlobClient abc = blobContainerClient.getBlobClient("abc");

        if (!abc.exists()) {
            abc.upload(BinaryData.fromString("this is my content"));
        }

        final String read = abc.downloadContent().toString();
        LOGGER.info("========== Received from storage {}", read);

    }

    private void testFileShare() {
        final ShareClient fileshare1 = this.shareServiceClient.getShareClient("fileshare1");
        final ShareFileClient abc = fileshare1.getFileClient("abcd");
        if (!abc.exists()) {
            final String content = "this is my file";
            abc.create(content.length() + 10);
            abc.upload(new ByteArrayInputStream(content.getBytes()), content.length());
        }

        abc.download(System.out);
    }

    private void testServiceBus() {
        this.senderClient.sendMessage(new ServiceBusMessage("service bus message"));

        final IterableStream<ServiceBusReceivedMessage> serviceBusReceivedMessages =
            this.receiverClient.receiveMessages(2);

        for (ServiceBusReceivedMessage message : serviceBusReceivedMessages) {
            LOGGER.info("========== Received from service bus {}", message.getBody().toString());
        }
    }


}
