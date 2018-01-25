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

import java.io.FileInputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.Header;

import com.arxanfintech.common.crypto.Crypto;
import com.arxanfintech.common.rest.*;
import com.arxanfintech.common.structs.RegisterWalletBody;
import com.arxanfintech.common.structs.Headers;
import com.arxanfintech.common.util.JsonUtil;

/**
 * 
 * Wallet Api Sdk
 *
 */
public class Wallet {
    public Config config;
    public Crypto crypto;

    public Wallet(Config config, String privateKeyPath, String publicCertPath) {
        this.config = config;
        try {
            this.crypto = new Crypto(new FileInputStream(privateKeyPath), new FileInputStream(publicCertPath));
        } catch (Exception e) {

        }
    }

    public void Register(String jsonheader, String jsonbody) throws Exception {
        RegisterWalletBody register = JsonUtil.parseJsonToClass(jsonbody, RegisterWalletBody.class);
        List<NameValuePair> body = register.ToListNameValuePair();

        Headers head = JsonUtil.parseJsonToClass(jsonheader, Headers.class);
        Header[] headers = head.ToHeaderArray();
        Request request = new Request();
        request.config = this.config;
        request.body = body;
        request.headers = headers;
        request.crypto = crypto;
        request.url = request.config.Address + "/wallet-ng/v1/wallet/register";

        Api api = new Api();
        api.NewHttpClient();
        try {
            api.DoPost(request);
        } catch (Exception e) {

        }
    }
}
