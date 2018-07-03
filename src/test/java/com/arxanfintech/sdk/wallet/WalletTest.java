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

    private String address = "IP:PORT";
    private String apikey = "pgZ2CzcTp1530257507";
    private String certpath = "/usr/local/lib/python2.7/site-packages/py_common-2.0.1-py2.7.egg/cryption/ecc/certs";
    private String sign_params_creator = "did:axn:cdf96536-751c-4266-8c6f-8e55f14dc976";
    private String sign_params_nonce = "nonce";
    private String sign_params_privatekeyBase64 = "AH6+jOCP1bbKHfTqR9fPlQxKmaY0/ZYRCG9+XnNGIaexc1cgeJWmBLh4NipMLXoum4ibtRkBYBgGlqDpTDO/Qg==";
    // Each of the APIs to invoke blockchain has two invoking modes: - `sync` and
    // `async`. You can set it in http header.
    // header = {"Bc-Invoke-Mode:"sync"} for sync mode.
    // default or header = {"Bc-Invoke-Mode:"async"} for async mode.In asynchronous
    // mode, you should set 'Callback-Url'.
    private String strheader = "{\"Callback-Url\":\"http://something.com\"}";

    /**
     * IssuePOE Test
     */
    public void testIssuePOE() {
        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        client.Creator = sign_params_creator;
        client.Nonce = sign_params_nonce;
        client.PrivateB64 = sign_params_privatekeyBase64;

        Wallet wallet = new Wallet(client);

        String privatekeyBase64 = "mKyNuvcWrE5ZtverSYVjxu4LSTDlnLkmF/qvYeq0hU6kEsJKGAZb1CkEFE9qxMytNGPXyIy8gekAdB1rIaVNzQ==";
        String nonce = "nonce";
        String created = "1526613187";

        String strdata = "{\"owner\": \"did:axn:039aff10-b96b-4c76-86d0-73b5a74d2ca2\", \"asset_id\": \"did:axn:6c6743e5-3a62-4c59-b1ab-3385778f5c32\", \"amount\": 1237, \"fees\": {}, \"issuer\": \"did:axn:c015f5a3-6b5d-469e-87ad-183fd137d7c1\"}";
        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);

        try {
            String response = wallet.IssueTokens(jsonheader, jsondata, "did:axn:039aff10-b96b-4c76-86d0-73b5a74d2ca2",
                    created, nonce, privatekeyBase64);
            System.out.print(response);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    /**
     * Register Test
     */
    public void testRegister() {
        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        client.Creator = sign_params_creator;
        client.Nonce = sign_params_nonce;
        client.PrivateB64 = sign_params_privatekeyBase64;

        Wallet wallet = new Wallet(client);

        String strdata = "{\"access\": \"92c62e1c-43ac-11e8-b377-186590cc5d36\",\"secret\": \"Integrate1230\", \"type\": \"Organization\", \"id\": \"\"}";
        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);

        try {
            String response = wallet.Register(jsonheader, jsondata);
            System.out.print(response);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    /**
     * Balance Test
     */
    public void testBalance() {
        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        client.Creator = sign_params_creator;
        client.Nonce = sign_params_nonce;
        client.PrivateB64 = sign_params_privatekeyBase64;

        Wallet wallet = new Wallet(client);

        JSONObject jsonheader = JSON.parseObject(strheader);
        String id = "did:axn:039aff10-b96b-4c76-86d0-73b5a74d2ca2";

        try {
            String response = wallet.QueryWalletBalance(jsonheader, id);
            System.out.print(response);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    /**
     * CreatePOE Test
     */
    public void testCreatePOE() {
        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        client.Creator = sign_params_creator;
        client.Nonce = sign_params_nonce;
        client.PrivateB64 = sign_params_privatekeyBase64;

        Wallet wallet = new Wallet(client);

        String privatekeyBase64 = "bx0jOwALZ0hLDxwyHyct3xoH4KjFL3wZ6dDYd2O6Bxmh0qnfEFLK9BjiCfwHoUkU/ryNMBbFWYz9HpFGgwKt6Q==";
        String nonce = "nonce";
        String created = "1526613187";
        String did = "did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2";

        String strdata = "{\"hash\": \"\", \"name\": \"name\", \"parent_id\": \"\",\"owner\": \"did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2\", \"id\": \"\",\"metadata\": [123, 34, 112, 104, 111, 110, 101, 34, 58, 32, 34, 49, 56, 50,48, 49, 51, 57, 49, 56, 48, 57, 34, 125]}";
        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);

        try {
            String response = wallet.CreatePOE(jsonheader, jsondata, did, created, nonce, privatekeyBase64);
            System.out.print(response);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    /**
     * TransferTokens Test
     */
    public void TransferTokens() {
        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        client.Creator = sign_params_creator;
        client.Nonce = sign_params_nonce;
        client.PrivateB64 = sign_params_privatekeyBase64;

        Wallet wallet = new Wallet(client);

        String privatekeyBase64 = "bx0jOwALZ0hLDxwyHyct3xoH4KjFL3wZ6dDYd2O6Bxmh0qnfEFLK9BjiCfwHoUkU/ryNMBbFWYz9HpFGgwKt6Q==";
        String nonce = "nonce";
        String created = "1526613187";
        String did = "did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2";

        String strdata = "{\"hash\": \"\", \"name\": \"name\", \"parent_id\": \"\",\"owner\": \"did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2\", \"id\": \"\",\"metadata\": [123, 34, 112, 104, 111, 110, 101, 34, 58, 32, 34, 49, 56, 50,48, 49, 51, 57, 49, 56, 48, 57, 34, 125]}";
        JSONObject jsondata = JSON.parseObject(strdata);

        JSONObject jsonheader = JSON.parseObject(strheader);

        try {
            String response = wallet.CreatePOE(jsonheader, jsondata, did, created, nonce, privatekeyBase64);
            System.out.print(response);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}
