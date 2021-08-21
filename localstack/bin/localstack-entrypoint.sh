#!/bin/bash

printf "Configuring localstack components"

printf "Creating User..."

awslocal dynamodb create-table \
    --table-name User \
    --attribute-definitions \
      "AttributeName=pk, AttributeType=S" \
      "AttributeName=email, AttributeType=S" \
    --key-schema \
      "AttributeName=pk,KeyType=HASH" \
    --global-secondary-indexes \
      "IndexName=EmailIndex,KeySchema=[{AttributeName=email,KeyType=HASH}],Projection={ProjectionType=ALL},ProvisionedThroughput={ReadCapacityUnits=10,WriteCapacityUnits=10}" \
    --provisioned-throughput \
      "ReadCapacityUnits=10,WriteCapacityUnits=10"

printf "Done create components"