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

import com.arxanfintech.common.rest.Config;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

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
     * Rigourous Test :-)
     */
    public void testRegister() {
        String jsondata = "{ \"callback_url\": \"http://callback-url\",\"id\": \"did:axn:21tDAKCERh95uGgKbJNHYp\",\"type\": \"Organization\",\"access\": \"xxxxx\", \"secret\": \"xxxx\",\"public_key\":  {\"usage\": \"SignVerify\",\"key_type\": \"EdDsaPublicKey\",\"public_key_data\": \"dGhpcyBpcyBhIHB1YmxpYyBrZXl0aGlzIGlzIGEgcHVibGljIGtleXRoaXMgaXMgYSBwdWJsaWMga2V5dGhpcyBpcyBhIHB1YmxpYyBrZXl0aGlzIGlzIGEgcHVibGljIGtleXRoaXMgaXMgYSBwdWJsaWMga2V5dGhpcyBpcyBhIHB1YmxpYyBrZXk=\"}}";

        Config config = new Config();
        config.Address = "http://172.16.13.2:9143";
        config.ApiKey = "Jj9beKQpM1516793756";
        Wallet wallet = new Wallet(config,
                "/Users/yan/eclipse-workspace/java-common/src/main/java/com/arxanfintech/common/example/keys/client/client.key",
                "/Users/yan/eclipse-workspace/java-common/src/main/java/com/arxanfintech/common/example/keys/client/server.crt");

        String jsonheader = "{\"Content-Type\":\"application/cipher\",\"API-Key\":\"" + config.ApiKey
                + "\",\"Accept\":\"application/json\"}";

        try {
            wallet.Register(jsonheader, jsondata);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}
