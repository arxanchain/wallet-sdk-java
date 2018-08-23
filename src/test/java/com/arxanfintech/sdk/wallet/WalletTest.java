/*******************************************************************************
Copyright ArxanFintech Technology Ltd. 2018 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

                 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*******************************************************************************/

package com.arxanfintech.sdk.wallet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.arxanfintech.common.rest.Client;
import com.arxanfintech.sdk.wallet.Wallet;
import java.sql.Timestamp;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Unit test for simple Wallet.
 */
public class WalletTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public WalletTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {

        return new TestSuite(WalletTest.class);
    }

    private Logger log = Logger.getLogger("wallet.log");
    private FileHandler fileHandler;

    private String address = "172.16.12.21:9143";
    private String apikey = "2z98QwxHM1534146706";
    private String certpath = "/Users/yan/tmp/cert1221";
    private String sign_params_creator = "did:axn:aede0319-17dd-40df-99f4e-ddfdfa6f1814";
    private String sign_params_nonce = "nonce";
    private String sign_params_created = "1534323232";
    private String sign_params_privatekeyBase64 = "R8OyUeRU2csQIDsXLa/anmCplffEgy0K9if/355ROKaDwGaZMIGyXDkd23ORXuJjxMIxdbH60Ei3NLb+umVGjg==";
    // Each of the APIs to invoke blockchain has two invoking modes: - `sync` and
    // `async`. You can set it in http header.
    // header = {"Bc-Invoke-Mode:"sync"} for sync mode.
    // default or header = {"Bc-Invoke-Mode:"async"} for async mode.In asynchronous
    // mode, you should set 'Callback-Url'.
    private String strheader = "{\"Callback-Url\":\"http://something.com\",\"Crypto-Mode\":\"0\"}";
    private Boolean enableCrypto = true;

    private String walletID = "did:axn:63946328-58a9-43cf-b370-a6da3a9059b9";
    private String privateKeyBase64 = "uegxVd0IWKPJfNMUBhUm45JLeICLjJGGs2dqU1CLRZ0zCUayQ+UBp0FTesK31Xew/ivPEA3GtwvcijB8yLuVlQ==";

    private String poeID = "did:axn:686468d0-2f2d-4498-98b8-28df200ee8f1";
    private String tokenID = "7c99c321a868b6ea53d9cdf25f4eb0f60ea3762e891677cab3244a1e7c4c96c3";
    private String toWalletID = "did:axn:147253cc-c39a-4206-9418-0bd33d9ccac2";

    // /**
    // * Register Test
    // */
    // public void testRegister() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, enableCrypto);
    // Wallet wallet = new Wallet(client);
    //
    // String access = UUID.randomUUID().toString();
    //
    // String strdata = "{\"access\":\"" + access +
    // "\",\"secret\":\"Integrate1230\",\"type\":"
    // + WalletType.ORGANIZATION.getIndex() + ", \"id\": \"\"}";
    //
    // JSONObject jsondata = JSON.parseObject(strdata);
    //
    // JSONObject jsonheader = JSON.parseObject(strheader);
    //
    // try {
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    // log.info("Register wallet body: " + strdata);
    //
    // JSONObject jsonResponse = wallet.register(jsonheader, jsondata);
    //
    // log.info("Register wallet response: " + jsonResponse.toString());
    //
    // // if (jsonResponse.getInteger("ErrCode") != 0) {
    // // assertTrue(false);
    // // }
    // // // eg: how to get wallet did and private_key
    // // this.walletID = jsonResponse.getJSONObject("Payload").getString("id");
    // // String walletPrivateKeyBase64 =
    // // jsonResponse.getJSONObject("Payload").getJSONObject("key_pair")
    // // .getString("private_key");
    // // log.info("Register wallet did: " + walletID);
    // // log.info("Register wallet private_key: " + walletPrivateKeyBase64);
    //
    // assertTrue(true);
    // } catch (Exception e) {
    //
    // log.info("Register wallet error: " + e.getMessage());
    // assertTrue(false);
    // }
    // }
    //
    // /**
    // * IndexSet Test
    // */
    // public void testIndexSet() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, enableCrypto);
    // Wallet wallet = new Wallet(client);
    //
    // String strdata = "{\"id\":\"" + walletID
    // +
    // "\",\"indexs\":{\"combined_index\":[\"first-me\",\"second-me\",\"third-me\"],\"individual_index\":[\"individual-me-001\",\"individual-me-002\",\"individual-me-003\"]}}";
    //
    // JSONObject jsondata = JSON.parseObject(strdata);
    //
    // JSONObject jsonheader = JSON.parseObject(strheader);
    // try {
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    // log.info("Index set body: " + strdata);
    //
    // JSONObject response = wallet.indexSet(jsonheader, jsondata);
    // log.info("Index set response: " + response.toString());
    //
    // // if (response.getInteger("ErrCode") != 0) {
    // // assertTrue(false);
    // // }
    //
    // assertTrue(true);
    // } catch (Exception e) {
    // log.info("Index set error:" + e.getMessage());
    // assertTrue(false);
    // }
    // }
    //
    // /**
    // * IndexSet Test
    // */
    // public void testIndexGet() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, enableCrypto);
    // Wallet wallet = new Wallet(client);
    //
    // String strdata =
    // "{\"indexs\":{\"combined_index\":[\"first-me\",\"second-me\",\"third-me\"],\"individual_index\":[\"individual-me-001\",\"individual-me-002\",\"individual-me-003\"]}}";
    //
    // JSONObject jsondata = JSON.parseObject(strdata);
    //
    // JSONObject jsonheader = JSON.parseObject(strheader);
    // try {
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    // log.info("Index set body: " + strdata);
    //
    // JSONObject response = wallet.indexGet(jsonheader, jsondata);
    // log.info("Index get response: " + response.toString());
    //
    // // if (response.getInteger("ErrCode") != 0) {
    // // assertTrue(false);
    // // }
    //
    // assertTrue(true);
    // } catch (Exception e) {
    // log.info("Index get error:" + e.getMessage());
    // assertTrue(false);
    // }
    // }
    //
    /**
     * CreatePOE Test
     */
    public void testCreatePOE() {
        Client client = new Client(apikey, certpath, sign_params_creator, sign_params_created, sign_params_nonce,
                sign_params_privatekeyBase64, address, enableCrypto);
        Wallet wallet = new Wallet(client);

        String strdata = "{\"hash\": \"\", \"name\":\"name\",\"parent_id\":\"\",\"owner\":\"" + walletID
                + "\", \"id\":\"\",\"metadata\":[123, 34, 112, 104, 111, 110, 101, 34, 58,32,34, 49, 56,50,48, 49, 51, 57,49, 56, 48, 57, 34, 125]}";

        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);

        String created = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime() / 1000);
        String nonce = "test-nonce";

        try {
            fileHandler = new FileHandler("wallet.log");
            fileHandler.setFormatter(new LogHander());
            log.addHandler(fileHandler);
            log.info("Create poe body: " + strdata);

            JSONObject response = wallet.createPOE(jsonheader, jsondata, walletID, created, nonce, privateKeyBase64,
                    "/Users/yan/eclipse-workspace/java-common/src/main/resources/sign-util");
            log.info("Create poe response: " + response);

            // if (response.getInteger("ErrCode") != 0) {
            // assertTrue(false);
            // }
            // this.poeID = response.getJSONObject("Payload").getString("id");
            // log.info("Create poe did: " + this.poeID);

            assertTrue(true);
        } catch (Exception e) {
            log.info("Create poe error: " + e.getMessage());
            assertTrue(false);
        }
    }

    // /**
    // * IssuePOE Test
    // */
    // public void testIssuePOE() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, enableCrypto);
    // Wallet wallet = new Wallet(client);
    //
    // String created = String.valueOf(new
    // Timestamp(System.currentTimeMillis()).getTime() / 1000);
    // String nonce = "test-nonce";
    // JSONObject jsonheader = JSON.parseObject(strheader);
    // try {
    //
    // String createData = "{\"hash\": \"\",
    // \"name\":\"name\",\"parent_id\":\"\",\"owner\":\"" + walletID
    // + "\", \"id\":\"\",\"metadata\":[123, 34, 112, 104, 111, 110, 101, 34, 58,32,
    // 34, 49, 56,50,48, 49, 51, 57,49, 56, 48, 57, 34, 125]}";
    // log.info("Issue poe first try create poe: " + createData);
    // JSONObject jsonCreate = JSON.parseObject(createData);
    // JSONObject createResponse = wallet.createPOE(jsonheader, jsonCreate,
    // walletID, created, nonce,
    // privateKeyBase64,
    // "/Users/yan/eclipse-workspace/java-common/src/main/resources/sign-util");
    // this.poeID = createResponse.getJSONObject("Payload").getString("id");
    //
    // String strdata = "{\"owner\":\"" + walletID + "\",\"asset_id\":\"" + poeID
    // + "\", \"amount\":1230, \"fees\":{}, \"issuer\":\"" + walletID + "\"}";
    // JSONObject jsondata = JSON.parseObject(strdata);
    //
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    // log.info("Issue poe body: " + strdata);
    //
    // JSONObject response = wallet.issueTokens(jsonheader, jsondata, walletID,
    // created, nonce, privateKeyBase64,
    // "/Users/yan/eclipse-workspace/java-common/src/main/resources/sign-util");
    // log.info("Issue poe response: " + response.toString());
    //
    // // if (response.getInteger("ErrCode") != 0) {
    // // assertTrue(false);
    // // }
    //
    // assertTrue(true);
    // } catch (
    //
    // Exception ex) {
    // log.info("Issue poe error: " + ex.getMessage());
    // assertTrue(false);
    // }
    // }
    //
    // /**
    // * Balance Test
    // */
    // public void testBalance() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, enableCrypto);
    // Wallet wallet = new Wallet(client);
    //
    // JSONObject jsonheader = JSON.parseObject(strheader);
    //
    // try {
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    // log.info("Query wallet balance walletID: " + walletID);
    //
    // JSONObject response = wallet.queryWalletBalance(jsonheader, walletID);
    // log.info("Query wallet balance response: " + response.toString());
    // //
    // // if (response.getInteger("ErrCode") != 0) {
    // // assertTrue(false);
    // // }
    //
    // assertTrue(true);
    // } catch (Exception e) {
    // log.info("Query wallet balance error: " + e.getMessage());
    // assertTrue(false);
    // }
    // }
    //
    // /**
    // * TransferTokens Test
    // */
    // public void TransferTokens() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, enableCrypto);
    // Wallet wallet = new Wallet(client);
    //
    // JSONObject jsonheader = JSON.parseObject(strheader);
    //
    // try {
    // String strdata = "{\"from\":\"" + walletID + "\",\"to\":\"" + toWalletID
    // + "\",\"asset_id\":\"\",\"tokens\":[{\"token_id\":\"" + tokenID
    // + "\",\"amount\":3}],\"fee\":{\"amount\":1}}";
    //
    // JSONObject jsondata = JSON.parseObject(strdata);
    //
    // String created = String.valueOf(new
    // Timestamp(System.currentTimeMillis()).getTime() / 1000);
    // String nonce = "nonce";
    //
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    // log.info("Transfer tokens body: " + strdata);
    //
    // JSONObject response = wallet.transferTokens(jsonheader, jsondata, walletID,
    // created, nonce,
    // privateKeyBase64,
    // "/Users/yan/work/gospace/src/github.com/arxanchain/sdk-go-common/crypto/tools/build/bin/sign-util");
    // log.info("Transfer tokens response: " + response.toString());
    //
    // // if (response.getInteger("ErrCode") != 0) {
    // // assertTrue(false);
    // // }
    //
    // assertTrue(true);
    // } catch (Exception e) {
    // log.info("Transfer tokens error: " + e.getMessage());
    // assertTrue(false);
    // }
    // }
    //
    // /**
    // * IssueAssets Test
    // */
    // public void testIssueAssets() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, enableCrypto);
    // Wallet wallet = new Wallet(client);
    //
    // String created = String.valueOf(new
    // Timestamp(System.currentTimeMillis()).getTime() / 1000);
    // String nonce = "test-nonce";
    // JSONObject jsonheader = JSON.parseObject(strheader);
    // try {
    //
    // String createData = "{\"hash\": \"\",
    // \"name\":\"name\",\"parent_id\":\"\",\"owner\":\"" + walletID
    // + "\", \"id\":\"\",\"metadata\":[123, 34, 112, 104, 111, 110, 101, 34, 58,32,
    // 34, 49, 56,50,48, 49, 51, 57,49, 56, 48, 57, 34, 125]}";
    // log.info("Issue poe first try create poe: " + createData);
    // JSONObject jsonCreate = JSON.parseObject(createData);
    // JSONObject createResponse = wallet.createPOE(jsonheader, jsonCreate,
    // walletID, created, nonce,
    // privateKeyBase64,
    // "/Users/yan/eclipse-workspace/java-common/src/main/resources/sign-util");
    // this.poeID = createResponse.getJSONObject("Payload").getString("id");
    //
    // String strdata = "{\"owner\":\"" + walletID + "\",\"asset_id\":\"" + poeID
    // + "\", \"fees\":{}, \"issuer\":\"" + walletID + "\"}";
    // JSONObject jsondata = JSON.parseObject(strdata);
    //
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    // log.info("Issue poe body: " + strdata);
    //
    // JSONObject response = wallet.issueAssets(jsonheader, jsondata, walletID,
    // created, nonce, privateKeyBase64,
    // "/Users/yan/eclipse-workspace/java-common/src/main/resources/sign-util");
    // log.info("Issue poe response: " + response.toString());
    //
    // // if (response.getInteger("ErrCode") != 0) {
    // // assertTrue(false);
    // // }
    //
    // assertTrue(true);
    // } catch (
    //
    // Exception ex) {
    // log.info("Issue poe error: " + ex.getMessage());
    // assertTrue(false);
    // }
    // }
    //
    // /**
    // * TransferAssets Test
    // */
    // public void TransferAssets() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, enableCrypto);
    // Wallet wallet = new Wallet(client);
    //
    // JSONObject jsonheader = JSON.parseObject(strheader);
    //
    // try {
    // String strdata = "{\"from\":\"" + walletID + "\",\"to\":\"" + toWalletID
    // +
    // "\",\"assets\":[\"did:axn:f6e741e1-4c45-4052-b8c2-6cd0873e1be2\"],\"fee\":{\"amount\":1}}";
    //
    // JSONObject jsondata = JSON.parseObject(strdata);
    //
    // String created = String.valueOf(new
    // Timestamp(System.currentTimeMillis()).getTime() / 1000);
    // String nonce = "nonce";
    //
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    // log.info("Transfer tokens body: " + strdata);
    //
    // JSONObject response = wallet.transferAssets(jsonheader, jsondata, walletID,
    // created, nonce,
    // privateKeyBase64,
    // "/Users/yan/work/gospace/src/github.com/arxanchain/sdk-go-common/crypto/tools/build/bin/sign-util");
    // log.info("Transfer tokens response: " + response.toString());
    //
    // // if (response.getInteger("ErrCode") != 0) {
    // // assertTrue(false);
    // // }
    //
    // assertTrue(true);
    // } catch (Exception e) {
    // log.info("Transfer tokens error: " + e.getMessage());
    // assertTrue(false);
    // }
    // }
    //
    // /**
    // * UploadPOETest Test
    // */
    // public void UploadPOETest() {
    // Client client = new Client(apikey, certpath, sign_params_creator,
    // sign_params_created, sign_params_nonce,
    // sign_params_privatekeyBase64, address, true);
    // Wallet wallet = new Wallet(client);
    //
    // JSONObject jsonheader = JSON.parseObject(strheader);
    //
    // try {
    // fileHandler = new FileHandler("wallet.log");
    // fileHandler.setFormatter(new LogHander());
    // log.addHandler(fileHandler);
    //
    // String response = wallet.uploadFile(jsonheader, "/Users/yan/a.csv",
    // "did:axn:bf09ccc0-d3dc-47f0-9709-4140945fc782", false, apikey,
    // "http://172.16.12.21:8006/v1/poe/upload");
    // log.info("UploadPOETest response: " + response.toString());
    //
    // assertTrue(true);
    // } catch (Exception e) {
    // log.info("UploadPOETest error: " + e.getMessage());
    // assertTrue(false);
    // }
    // }
    /**
     * UploadPOETest Test
     */
    public void UploadPOETest() {
        Client client = new Client(apikey, certpath, sign_params_creator, sign_params_created, sign_params_nonce,
                sign_params_privatekeyBase64, address, true);
        Wallet wallet = new Wallet(client);

        JSONObject jsonheader = JSON.parseObject(strheader);

        try {
            fileHandler = new FileHandler("wallet.log");
            fileHandler.setFormatter(new LogHander());
            log.addHandler(fileHandler);

            JSONObject response = wallet.uploadFile(jsonheader, "/Users/yan/upload_poe_file.a",
                    "did:axn:39621915-6735-4bb4-b710-49295181d5ba", false);
            log.info("UploadPOETest response: " + response.toString());

            assertTrue(true);
        } catch (Exception e) {
            log.info("UploadPOETest error: " + e.getMessage());
            assertTrue(false);
        }
    }

    class LogHander extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getLevel() + ":" + record.getMessage() + "\n";
        }
    }
}
