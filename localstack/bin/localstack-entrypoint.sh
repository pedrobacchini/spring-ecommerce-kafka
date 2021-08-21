#!/bin/bash

printf "Configuring localstack components"

printf "Creating User..."

awslocal dynamodb create-table \
    --table-name User \
    --attribute-definitions \
      "AttributeName=pk, AttributeType=S" \
    --key-schema \
      "AttributeName=pk,KeyType=HASH" \
    --provisioned-throughput \
      "ReadCapacityUnits=10,WriteCapacityUnits=10"

printf "Done create components"