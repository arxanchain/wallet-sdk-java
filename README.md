# Status
[![Build Status](https://travis-ci.org/arxanchain/wallet-sdk-java.svg?branch=master)](https://travis-ci.org/arxanchain/wallet-sdk-java)

## Wallet-Sdk-Java
Blockchain Wallet SDK includes APIs for managing wallet accounts(Decentralized Identifiers, DID), digital assets(Proof of Existence, POE), colored tokens etc.

You need not care about how the backend blockchain runs or the unintelligible techniques, such as consensus, endorsement and decentralization. Simply use the SDK we provide to implement your business logics, we will handle the caching, tagging, compressing, encrypting and high availability.

## Contributions

We appreciate all kinds of contributions, such as opening issues, fixing bugs and improving documentation.


# Usage
wallet-sdk-java is Maven projcet, we have already put this project to Maven Repository.
When you use wallet-sdk-java, you should reference project like this:

```pom.xml```

```
<dependency>
    <groupId>com.arxanfintech</groupId>
    <artifactId>wallet-sdk-java</artifactId>
    <version>3.0.0</version>
</dependency>
```


## Wallet Platform API
wallet-sdk-java have some import APIs. For more details please refer to [Wallet APIs Documentation](http://chain.arxanfintech.com/infocenter/html/development/wallet.html)

Before you use wallet-sdk-java, you should prepare to import certificates into keystore for using https protocol.

The certificates include:

* The CA certificate of ArxanChain BaaS Platform (rootca.crt) which is used to
  verify the server is trusty in communication. You can download it from the
  ArxanChain BaaS ChainConsole -> System Management -> API Certs Management
* The certificate of the client user including private key(`APIKey.key`) and 
  cert file(`APIKey.pem`) signed by CA. You can download it in Client Certs List.

After downloading three files, use the following command to import CA and 
client cert into p12 file that can be used as keystore.

```sh
$ openssl pkcs12 -export -clcerts -in apikey.pem -inkey apikey.key -out apikey.p12 (need passwd)
$ keytool -import -alias arxanbaas -file rootca.crt -keystore apikey.p12 (need passwd)
```


### Init a wallet client

```java
        String address = https://IP:PORT"; // **Address** is the IP address of the BAAS server entrance.
        String apiKey = "pgZ2CzcTp1530257507"; // Param **apikey** is set to the API access key applied on `ChainConsole` management page
        String signParamsCreator = sign_params_creator; //the enterprise's wallet did
        String signParamsNonce = sign_params_nonce; //the enterprise's nonce
        String signParamsPrivatekeyBase64 = sign_params_privatekeyBase64; //the enterprise's wallet private key 
        String signParamsCreated = "1534723900";
        String keyStorePath = "/path/to/keystore" // abs path of keystore file
        String storePasswd = "123456" // the password of keystore

        Client client = new Client(
            apiKey, signParamsCreator, signParamsCreated,
            signParamsNonce, signParamsPrivatekeyBase64, address, 
            keyStorePath, storePasswd);
        Wallet wallet = new Wallet(client);
        
        // Each of the APIs to invoke blockchain has two invoking modes: - `sync` and `async`. You can set it in http header.
        // header = {"Bc-Invoke-Mode:"sync"} for sync mode.
        // default or header = {"Bc-Invoke-Mode:"async"} for async mode.In asynchronous mode, you should set 'Callback-Url'.
         String strheader = "{\"Callback-Url\":\"http://something.com\"}";
         
         //the full path for your sign-util
         String signToolPath = "/Users/ivy/src/github.com/arxanchain/sdk-go-common/crypto/tools/build/sign-util";
         
```
*Note:* SignToolPath

#### sdk-go-common

you should build the `sdk-go-common` executables `sign-util` on your OS.
For more details please refer to [sdk-go-common](https://github.com/arxanchain/sdk-go-common/tree/master/crypto/tools)

#### Build
After successfully installed **sdk-go-common**, you need to install golang and you should've configured your **GOPATH** environment variable, use the following command to build sign-util executables.

```sh
$ cd $GOPATH/src/github.com/arxanchain/sdk-go-common/crypto/tools
$ make
```

The executables full path is your signToolPath like `/Users/ivy/src/github.com/arxanchain/sdk-go-common/crypto/tools/build/bin/sign-util`.

Also you need to confirm the `sign-util` has the executable permission, when you use linux or Mac, you should run `chmod +x sign-util`.


### Register Wallet
```java
        // register wallet body, please notice type: you should use WalletType which in com.arxanfintech.common.structs.WalletType
        String strdata = "{\"access\": \"92c62e1c-43ac-11e8-b377-186590cc5d36\", \"secret\": \"Integrate1230\", \"type\"
                + WalletType.ORGANIZATION.getIndex() + ", \"id\": \"\"}";
        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);
        
        JSONObject response = wallet.register(jsonheader, jsondata);
       
```


### Create POE
```java
        String privatekeyBase64 = "bx0jOwALZ0hLDxwyHyct3xoH4KjFL3wZ6dDYd2O6Bxmh0qnfEFLK9BjiCfwHoUkU/ryNMBbFWYz9HpFGgwKt6Q==";
        String nonce = "nonce";
        String created = "1526613187";
        String did = "did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2";

        String strdata = "{\"hash\": \"\", \"name\": \"name\", \"parent_id\": \"\", \"owner\": \"did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2\", \"id\": \"\", \"metadata\": [123, 34, 112, 104, 111, 110, 101, 34, 58, 32, 34, 49, 56, 50, 48, 49, 51, 57, 49, 56, 48, 57, 34, 125]}";
        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);
        
        JSONObject response = wallet.createPOE(jsonheader, jsondata, did, created, nonce, privatekeyBase64);

```


### Issue colored token 
```java
        String privatekeyBase64 = "mKyNuvcWrE5ZtverSYVjxu4LSTDlnLkmF/qvYeq0hU6kEsJKGAZb1CkEFE9qxMytNGPXyIy8gekAdB1rIaVNzQ==";
        String nonce = "nonce";
        String created = "1526613187";

        String strdata = "{\"owner\": \"did:axn:039aff10-b96b-4c76-86d0-73b5a74d2ca2\", \"asset_id\": \"did:axn:6c6743e5-3a62-4c59-b1ab-3385778f5c32\", \"amount\": 1237, \"fees\": {}, \"issuer\": \"did:axn:c015f5a3-6b5d-469e-87ad-183fd137d7c1\"}";
        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);

        JSONObject response = wallet.issueTokens(jsonheader, jsondata, "did:axn:039aff10-b96b-4c76-86d0-73b5a74d2ca2", created, nonce, privatekeyBase64);

```

### Index set 
```java
        String strdata = "{\"id\":\"did:axn:64ec602c-c9e5-4835-8e63-c6f0a619805c\",\"indexs\":{\"combined_index\":[\"first-me\",\"second-me\",\"third-meu\"],\"individual_index\":[\"individual-me-001\",\"individual-me-002\",\"individual-me-003\"]}}";
        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);

        JSONObject response = wallet.indexSet(jsonheader, jsondata);
```

### Index get 
```java
        String strdata ="{\"indexs\":{\"combined_index\":[\"first-me\",\"second-me\",\"third-me\"],\"individual_index\":[\"individual-me-001\",\"individual-me-002\",\"individual-me-003\"]}}";
        JSONObject jsondata = JSON.parseObject(strdata);
        
        JSONObject jsonheader = JSON.parseObject(strheader);

        JSONObject response = wallet.indexGet(jsonheader, jsondata);
```

### Use callback URL to receive blockchain transaction events
Each of the APIs to invoke blockchain has two invoking modes: - `sync` and `async`.

The default invoking mode is asynchronous, it will return without waiting for
blockchain transaction confirmation. In asynchronous mode, you should set
'Callback-Url' in the http header to receive blockchain transaction events.

The blockchain transaction event structure is defined as follows:

```code
import google_protobuf "github.com/golang/protobuf/ptypes/timestamp

// Blockchain transaction event payload
type BcTxEventPayload struct {
    BlockNumber   uint64                     `json:"block_number"`   // Block number
    BlockHash     []byte                     `json:"block_hash"`     // Block hash
    ChannelId     string                     `json:"channel_id"`     // Channel ID
    ChaincodeId   string                     `json:"chaincode_id"`   // Chaincode ID
    TransactionId string                     `json:"transaction_id"` // Transaction ID
    Timestamp     *google_protobuf.Timestamp `json:"timestamp"`      // Transaction timestamp
    IsInvalid     bool                       `json:"is_invalid"`     // Is transaction invalid
    Payload       interface{}                `json:"payload"`        // Transaction Payload
}
```

A blockchain transaction event sample as follows:

```code
{
    "block_number":63,
    "block_hash":"vTRmfHZ3aaecbbw2A5zPcuzekUC42Lid3w+i6dOU5C0=",
    "channel_id":"pubchain",
    "chaincode_id":"pubchain-c4:",
    "transaction_id":"243eaa6e695cc4ce736e765395a64b8b917ff13a6c6500a11558b5e94e02556a",
    "timestamp":{
        "seconds":1521189855,
        "nanos":192203115
    },
    "is_invalid":false,
    "payload":{
        "id":"4debe20b-ca00-49b0-9130-026a1aefcf2d",
        "metadata": '{
            "member_id_value":"3714811988020512",
            "member_mobile":"6666",
            "member_name":"8777896121269017",
            "member_truename":"Tony"
        }'
    }
}
```

If you want to switch to synchronous invoking mode, set the 'Bc-Invoke-Mode' header
to 'sync'. In synchronous mode, it will not return until the blockchain
transaction is confirmed.

```java
    String strheader = "{"Bc-Invoke-Mode": "sync"}";
    String strbody = "{……}"
    
    String response = wallet.register(jsonheader, jsondata);
```

### Transaction procedure

1. Send transfer proposal to get wallet.Tx

2. Sign public key as signature

3. Call ProcessTx to transfer formally


 
