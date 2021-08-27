./gradlew clean build
cordapp-builder create --cpk contracts/build/libs/corda5-template-contracts-1.0-SNAPSHOT-cordapp.cpk --cpk workflows/build/libs/corda5-template-workflows-1.0-SNAPSHOT-cordapp.cpk -o template.cpb
corda-cli network deploy -n template-network -f c5cordapp-template.yaml -t 5.0.0-devpreview-rc03 > docker-compose.yaml
docker-compose -f docker-compose.yaml up -d
corda-cli package install -n template-network template.cpb


corda-cli network status -n template-network

https://localhost:12112/api/v1/swagger
angelenos - password
```
{
  "rpcStartFlowRequest": {
    "clientId": "launchpad-2", 
    "flowName": "net.corda.c5template.flows.TemplateFlow", 
    "parameters": { 
      "parametersInJson": "{\"msg\": \"Hello-World\", \"receiver\": \"C=GB, L=London, O=PartyB, OU=INC\"}" 
    } 
  } 
}
```


corda-cli network terminate -n template-network -ry

docker-compose -f docker-compose.yaml logs -f