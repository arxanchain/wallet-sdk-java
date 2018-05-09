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

    /**
     * Register Test
     */
    public void testRegister() {

        String strdata = "{\"access\": \"92c62e1c-43ac-11e8-b377-186590cc5d35\", \"secret\": \"Integrate1230\", \"type\": \"Organization\", \"id\": \"\"}";
        JSONObject jsondata = JSON.parseObject(strdata);

        String strheader = "{\"Callback-Url\":\"http://something.com\",\"Bc-Invoke-Mode\":\"sync\"}";
        JSONObject jsonheader = JSON.parseObject(strheader);

        Client client = new Client();
        client.Address = "IP:PORT";
        client.ApiKey = "5zt592jTM1524126512";
        client.CertPath = "/Users/yan/tmp/cert136";

        Wallet wallet = new Wallet(client);

        try {
            wallet.Register(jsonheader, jsondata);
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
        client.Address = "IP:PORT";
        client.ApiKey = "5zt592jTM1524126512";
        client.CertPath = "/Users/yan/tmp/cert136";

        Wallet wallet = new Wallet(client);

        try {
            wallet.Balance(jsonheader, id);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}
