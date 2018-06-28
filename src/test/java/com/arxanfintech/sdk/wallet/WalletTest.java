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

import org.apache.http.Header;

import com.arxanfintech.common.rest.Client;
import com.arxanfintech.sdk.wallet.Wallet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
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

    private String address = "172.16.12.60:9143";
    private String apikey = "6nXwD-oTp1520565858";
    private String certpath = "/Users/yan/tmp/cert1260";

    /**
     * Register Test
     */
    public void testRegister() {

        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        Wallet wallet = new Wallet(client);

        String strdata = "{\"access\": \"92c62e1c-43ac-11e8-b377-186590cc5d36\", \"secret\": \"Integrate1230\", \"type\": \"Organization\", \"id\": \"\"}";
        JSONObject jsondata = JSON.parseObject(strdata);

        String strheader = "{\"Callback-Url\":\"http://something.com\"}";
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

        String id = "did:axn:8d5c38a9-8ead-468d-b6d3-bec7d62341b0";

        String strheader = "{\"Callback-Url\":\"http://something.com\"}";
        JSONObject jsonheader = JSON.parseObject(strheader);

        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        Wallet wallet = new Wallet(client);

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

        String privatekeyBase64 = "bx0jOwALZ0hLDxwyHyct3xoH4KjFL3wZ6dDYd2O6Bxmh0qnfEFLK9BjiCfwHoUkU/ryNMBbFWYz9HpFGgwKt6Q==";
        String nonce = "nonce";
        String created = "1526613187";
        String did = "did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2";

        String strdata = "{\"hash\": \"\", \"name\": \"name\", \"parent_id\": \"\", \"owner\": \"did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2\", \"id\": \"\", \"metadata\": [123, 34, 112, 104, 111, 110, 101, 34, 58, 32, 34, 49, 56, 50, 48, 49, 51, 57, 49, 56, 48, 57, 34, 125]}";
        JSONObject jsondata = JSON.parseObject(strdata);

        String strheader = "{\"Callback-Url\":\"http://something.com\"}";
        JSONObject jsonheader = JSON.parseObject(strheader);

        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        Wallet wallet = new Wallet(client);

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

        String privatekeyBase64 = "bx0jOwALZ0hLDxwyHyct3xoH4KjFL3wZ6dDYd2O6Bxmh0qnfEFLK9BjiCfwHoUkU/ryNMBbFWYz9HpFGgwKt6Q==";
        String nonce = "nonce";
        String created = "1526613187";
        String did = "did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2";

        String strdata = "{\"hash\": \"\", \"name\": \"name\", \"parent_id\": \"\", \"owner\": \"did:axn:98e90bea-f4c3-4347-9656-d9e3a2b1bfe2\", \"id\": \"\", \"metadata\": [123, 34, 112, 104, 111, 110, 101, 34, 58, 32, 34, 49, 56, 50, 48, 49, 51, 57, 49, 56, 48, 57, 34, 125]}";
        JSONObject jsondata = JSON.parseObject(strdata);

        String strheader = "{\"Callback-Url\":\"http://something.com\"}";
        JSONObject jsonheader = JSON.parseObject(strheader);

        Client client = new Client();
        client.Address = address;
        client.ApiKey = apikey;
        client.CertPath = certpath;
        Wallet wallet = new Wallet(client);

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
