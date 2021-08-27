#!/bin/sh

echo "--Step 1: Building projects."
./gradlew clean build

echo "--Step 2: Creating cpb file."
cordapp-builder create --cpk contracts/build/libs/corda5-template-contracts-1.0-SNAPSHOT-cordapp.cpk --cpk workflows/build/libs/corda5-template-workflows-1.0-SNAPSHOT-cordapp.cpk -o template.cpb

echo "--Step 3: Creating docker compose yaml file."
corda-cli network deploy -n template-network -f c5cordapp-template.yaml -t 5.0.0-devpreview-rc03 > docker-compose.yaml

echo "--Step 4: Configure the network"
coroa-cli network config docker-compose template-network

echo "--Step 5: Starting docker containers."
docker-compose -f docker-compose.yaml up -d

echo "--Nodes Status: "
corda-cli network status -n template-network


