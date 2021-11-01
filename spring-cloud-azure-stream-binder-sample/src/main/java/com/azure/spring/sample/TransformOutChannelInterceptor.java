// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static com.azure.spring.messaging.AzureHeaders.CHECKPOINTER;

@Component
@GlobalChannelInterceptor(patterns = { "transform*-out-*" })
public class TransformOutChannelInterceptor implements ChannelInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformOutChannelInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return MessageBuilder.fromMessage(message).removeHeader(CHECKPOINTER).build();
    }

}
