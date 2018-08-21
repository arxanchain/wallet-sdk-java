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

import com.arxanfintech.sdk.wallet.Tools;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Unit test for simple Wallet.
 */
public class ToolsTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public ToolsTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {

        return new TestSuite(WalletTest.class);
    }

    private Logger log = Logger.getLogger("Tools.log");
    private FileHandler fileHandler;

    /**
     * GetFileContent Test
     */
    public void testGetFileContent() {
        try {
            fileHandler = new FileHandler("Tools.log");
            fileHandler.setFormatter(new LogHander());
            log.addHandler(fileHandler);

            String pathFile = "/Users/yan/eclipse-workspace/wallet-sdk-java/LICENSE.txt";
            log.info("start to get file content: " + pathFile);

            String result = Tools.GetFileContent(pathFile);
            if (result == null || result == "") {
                assertTrue(false);
            }
            log.info("GetFileContent:\r\n" + result);

            assertTrue(true);
        } catch (Exception e) {
            log.info("GetFileContent error: " + e.getMessage());
            assertTrue(false);
        }
    }

    /**
     * GetMultipartData Test
     */
    public void testGetMultipartData() {
        try {
            fileHandler = new FileHandler("Tools.log");
            fileHandler.setFormatter(new LogHander());
            log.addHandler(fileHandler);

            String pathFile = "/Users/yan/eclipse-workspace/wallet-sdk-java/LICENSE.txt";
            log.info("start to get file content: " + pathFile);

            String result = Tools.GetMultipartData("boundary", pathFile, "poeID", false);
            if (result == null || result == "") {
                assertTrue(false);
            }

            log.info("GetMultipartData:\r\n" + result);
            assertTrue(true);
        } catch (Exception e) {
            log.info("GetMultipartData error: " + e.getMessage());
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
