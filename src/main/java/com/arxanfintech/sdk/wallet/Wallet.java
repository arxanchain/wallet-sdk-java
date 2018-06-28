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

import java.io.FileInputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.Header;

import com.arxanfintech.common.crypto.Crypto;
import com.arxanfintech.common.rest.*;
import com.arxanfintech.common.structs.RegisterWalletBody;
import com.arxanfintech.common.structs.Headers;
import com.arxanfintech.common.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Wallet Api Sdk
 *
 */
public class Wallet {
    private Client client;
    private Crypto crypto;

    public Wallet(Client client) {
        this.client = client;
        String privateKeyPath = client.CertPath + "/users/" + client.ApiKey + "/" + client.ApiKey + ".key";
        String publicCertPath = client.CertPath + "/tls/tls.cert";
        ;
        try {
            this.crypto = new Crypto(new FileInputStream(privateKeyPath), new FileInputStream(publicCertPath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String Register(JSONObject jsonheader, JSONObject jsonbody) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/wallet/register";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String RegisterSubWallet(JSONObject jsonheader, JSONObject jsonbody) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/wallet/register/subwallet";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String QueryWalletBalance(JSONObject jsonheader, String id) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/wallet/balance?id=" + id;

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String CreatePOE(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/poe/create";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String QueryPOE(JSONObject jsonheader, String id) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/poe?id=" + id;

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String IssueTokens(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/transaction/tokens/issue";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String TransferTokens(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/transaction/tokens/transfer";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String IssueAssets(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/transaction/tokens/transfer";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String TransferAssets(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/wallet-ng/v1/transaction/assets/transfer";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
