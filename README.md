#Corda5 Cordapp Template 

##Environment Requirements: 
1. Download and install Java 11
2. Acquire R3 private engineering artifact access & Docker repository artifacts access 
3. Download and install `cordapp-builder` (Current version: 5.0.0-DevPreview-RC03)
4. Download and install `corda-cli` (Current version: 1.0.0-DevPreview-RC04)

Step 2 - 4 can find detail instructions at [here](https://engineering.r3.com/product-areas/corda-platform/blt/docs/setting-up-local-corda5-dev-network/setting-up-corda5-network/)

##App Functionalities 
This app is a skeleton corda 5 cordapp. The app has a TemplateState, a TemplateStateContract, and a TemplateFlow. The flow will send a p2p transaction that carries the TemplateState to the target party. The TemplateState always carries a Hello-World String. 

##How to run the template

Corda 5 re-engineering the test development experience, utilizing the dockers for test deployment. we need to follow a couple of steps to test deploy the app. 
```
#Build the projects.
./gradlew clean build

#Create the cpb file from the compiled cpk files in both contracts and workflows.
cordapp-builder create --cpk contracts/build/libs/corda5-template-contracts-1.0-SNAPSHOT-cordapp.cpk --cpk workflows/build/libs/corda5-template-workflows-1.0-SNAPSHOT-cordapp.cpk -o template.cpb

#Creating the docker compose yaml file.
corda-cli network deploy -n template-network -f c5cordapp-template.yaml -t 5.0.0-devpreview-rc03 > docker-compose.yaml

#Configure the mock network
coroa-cli network config docker-compose template-network

#Starte docker containers.
docker-compose -f docker-compose.yaml up -d
    .
    .
    . This step will take roughly a mintute to complete.
    . Open a new terminal and run: docker-compose -f docker-compose.yaml logs -f 
    . to look at the log of the docker, and wait for the Corda logo to populate. 
    
#Install the cpb file into the network.
corda-cli package install -n template-network template.cpb
```
You can always look at the status of the network by the command: 
```
corda-cli network status -n template-network
```
You can shut down the test network by the command: 
```
corda-cli network terminate -n template-network -ry
```
Thus far, your app is successfully running on a corda 5 test deployment network. 

##Interact with the app 
Open a browser and go to `https://localhost:<port>/api/v1/swagger`

For this app, the ports are: 
* PartyA's node: 12112
* PartyB's node: 12116

NOTE: This information is in the status printout of the network. Use the status command that documented above. 

The url will bring you to the swagger API interface, it is a set of HTTP API which you can use out of the box. In order to continue interacting with your app, you would need to log in now. 

Depends on the node that you chose to go to, you would need to log into the node use the correct credentials. 
For this app, the logins are: 
* PartyA - Login: angelenos, password: password
* PartyB - Login: londoner, password: password

NOTE: This information is in the c5cordapp-template.yaml file. 

Lets test if you have successfully logged in by go to the RegistedFlows 
![img.png](registeredflows.png)

You should expect a 200 success callback code, and a response body of such: 
```
[
  "net.corda.c5template.flows.TemplateFlow"
]
```

Now, let's look at the `startflow` API, we will test our tempalteFlow with it. 
in the request body put in: 
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
This request carries three pieces of information: 
1. The clientID of this call 
2. The flow we are triggering 
3. The flow parameters that we are providing. 

After the call, you shuold expect a 200 success call code, and a response body as such: 
```
{
  "flowId": {
    "uuid": "81e1415e-be7c-4038-8d06-8e76bdfd8bc7"
  },
  "clientId": "launchpad-2"
}
```
NOTE: This does not mean the transaction is passed through, it means the flow is successfully executed, but the success of the transaction is not guaranteed. 

You would need either go to `flowoutcomeforclientid` or `flowoutcome` to see the result of the flow. In this case, we will use the clientID to query the flow result: 

Enter the clientID of our previous flow call: `launchpad-2`
We will getting the following response: 
```
{
  "status": "COMPLETED",
  "resultJson": "{ \n \"txId\" : \"SHA-256:D2A3CEBB7C8E1FA7F35CBA9D9DA6076521D543235EE2867B047FEE8B893CB2EC\",\n \"outputStates\" : [\"{\\\"msg\\\":\\\"Hello-World\\\",\\\"sender\\\":\\\"OU\\u003dLLC, O\\u003dPartyA, L\\u003dLos Angeles, C\\u003dUS\\\",\\\"receiver\\\":\\\"OU\\u003dINC, O\\u003dPartyB, L\\u003dLondon, C\\u003dGB\\\"}\"], \n \"signatures\": [\"UnYM33YZ0G6//FrYNJh6mAfsm9SWfsD1p/aNHWm0focWg+uxp627fV7iuFpubq/t1ufb8yNo0/Awlcs9/b+tBg==\", \"T/0lOTwVKIRtWydTL2KbF4Q1XPeJaN+NkIv9by5unJ1jLDAJ89rfxTuytc5xQvyLQfPtnHIrtK42jzpFO8osAA==\"]\n}",
  "exceptionDigest": null
}
```
The completed status of the flow means the success of the flow and its carried transaction. 

Thus far, we had completed a full cycle of running a flow. 
