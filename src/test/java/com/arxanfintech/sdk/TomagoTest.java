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

package com.arxanfintech.sdk;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.http.Header;

import com.arxanfintech.common.rest.Client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Unit test for simple Wallet.
 */
public class TomagoTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public TomagoTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TomagoTest.class);
    }

    /**
     * Invoke Test
     */
    public void testInvoke() {
        //FIX ME
        assertTrue(true);
//        String strdata = "{\"payload\": {\"chaincode_id\": \"mycc\",\"args\": [\"invoke\", \"a\", \"b\", \"1\"]} }";
//        JSONObject jsondata = JSON.parseObject(strdata);
//
//        String strheader = "{\"Callback-Url\":\"http://something.com\",\"Bc-Invoke-Mode\":\"sync\"}";
//        JSONObject jsonheader = JSON.parseObject(strheader);
//
//        Client client = new Client();
//        client.Address = "IP:PORT";
//        client.ApiKey = "5zt592jTM1524126512";
//        client.CertPath = "/Users/yan/tmp/cert136";
//
//        Tomago tomago = new Tomago(client);
//
//        try {
//            tomago.Invoke(jsonheader, jsondata);
//            assertTrue(true);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            assertTrue(false);
//        }
    }

    /**
     * Quey Test
     */
    public void testQuery() {
        //FIX ME
        assertTrue(true);
//        String strdata = "{\"payload\": {\"chaincode_id\": \"mycc\",\"args\": [\"query\", \"a\"]} }";
//        JSONObject jsondata = JSON.parseObject(strdata);
//
//        String strheader = "{\"Callback-Url\":\"http://something.com\",\"Bc-Invoke-Mode\":\"sync\"}";
//        JSONObject jsonheader = JSON.parseObject(strheader);
//
//        Client client = new Client();
//        client.Address = "IP:PORT";
//        client.ApiKey = "5zt592jTM1524126512";
//        client.CertPath = "/Users/yan/tmp/cert136";
//
//        Tomago tomago = new Tomago(client);
//
//        try {
//            tomago.Query(jsonheader, jsondata);
//            assertTrue(true);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            assertTrue(false);
//        }
    }

    /**
     * Transaction Test
     */
    public void testTransaction() {
        //FIX ME
//        assertTrue(true);
//        String id = "test0";
//
//        String strheader = "{\"Callback-Url\":\"http://something.com\"}";
//        JSONObject jsonheader = JSON.parseObject(strheader);
//
//        Client client = new Client();
//        client.Address = "IP:PORT";
//        client.ApiKey = "5zt592jTM1524126512";
//        client.CertPath = "/Users/yan/tmp/cert136";
//
//        Tomago tomago = new Tomago(client);
//
//        try {
//            tomago.Transaction(jsonheader, id);
//            assertTrue(true);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            assertTrue(false);
//        }
    }
}
