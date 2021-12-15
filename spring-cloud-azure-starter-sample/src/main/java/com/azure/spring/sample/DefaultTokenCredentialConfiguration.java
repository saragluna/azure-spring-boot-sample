package com.azure.spring.sample;


import com.azure.core.credential.TokenCredential;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.spring.cloud.autoconfigure.context.AzureContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultTokenCredentialConfiguration {

    @Bean(name = AzureContextUtils.DEFAULT_TOKEN_CREDENTIAL_BEAN_NAME)
    TokenCredential azureCliTokenCredential() {
        return new AzureCliCredentialBuilder().build();
    }

}
