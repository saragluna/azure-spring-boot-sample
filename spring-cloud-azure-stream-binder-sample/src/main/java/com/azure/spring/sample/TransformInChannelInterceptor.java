// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample;

import com.azure.spring.messaging.checkpoint.Checkpointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.concurrent.atomic.AtomicInteger;

import static com.azure.spring.messaging.AzureHeaders.CHECKPOINTER;

@Component
@GlobalChannelInterceptor(patterns = { "transform*-in-*" })
public class TransformInChannelInterceptor implements ChannelInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformInChannelInterceptor.class);
    private static final String ID_HEADER = "id";
    private final AtomicInteger totalSent = new AtomicInteger();

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        intercept(message, channel, sent, ex);
    }

    public void intercept(@NotNull Message<?> message,
                          @NotNull MessageChannel channel,
                          boolean sent,
                          Exception ex) {
        Object id = message.getHeaders().get(ID_HEADER);
        Checkpointer checkpointer = checkpointer(message);
        if (checkpointer == null) {
            LOGGER.error("Checkpointer is null in channel='{}', id=''{}", channel, id);
        } else {
            if (sent) {
                LOGGER.debug("Checkpoint success, channel='{}', id=''{}", channel, id);
                totalSent.incrementAndGet();
                checkpointer.success()
                            .doOnSuccess(s -> LOGGER.debug("Message sent and marked: '{}'", id))
                            .doOnError(e -> LOGGER.debug("Message sent, failed to mark: '{}'", id, e))
                            .subscribe();
            } else {
                LOGGER.info("Checkpoint failure,  channel='{}', id=''{}", channel, id);
                checkpointer.failure()
                            .doOnSuccess(s -> LOGGER.debug("Message not sent, marked as failure: '{}'", id))
                            .doOnError(e -> LOGGER.debug("Message not sent, failed to mark: '{}'", id, e))
                            .subscribe();
            }
        }
    }

    private Checkpointer checkpointer(Message<?> message) {
        try {
            return (Checkpointer) message.getHeaders().get(CHECKPOINTER);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

}
