package com.github.pedrobacchini.springecommercekafka.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class DynamoDBConfiguration {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.region}")
    private String amazonRegion;

    @Value("${amazon.aws.dynamo.table}")
    private String tableName;

    private AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonRegion);
    }

    private DynamoDBMapperConfig dynamoDBMapperConfig() {
        return new DynamoDBMapperConfig.Builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
                .build();
    }

    @Bean("amazonDynamoDB")
    @Profile("!prod")
    DynamoDBMapper amazonDynamoDBLocal() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration())
                .build();
        return new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig());
    }

    @Bean("amazonDynamoDB")
    @Profile("prod")
    DynamoDBMapper amazonDynamoDBProduction() {
        return new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard().build(), dynamoDBMapperConfig());
    }
}
