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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.util.encoders.Base64;

/**
 * 
 * Wallet Api Sdk
 *
 */
public class Wallet {
    private Client client;
    private Crypto crypto;

    public Wallet(Client client) {
        // set default routeTag
        if (client.RouteTag == "") {
            client.RouteTag = "wallet-ng";
        }
        this.client = client;
        
        String privateKeyPath = client.CertPath + "/users/" + client.ApiKey + "/" + client.ApiKey + ".key";
        String publicCertPath = client.CertPath + "/tls/tls.cert";

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
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag + "/v1/wallet/register";

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
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag
                + "/v1/wallet/register/subwallet";

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
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag + "/v1/wallet/balance?id="
                + id;

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
            String privatekeyBase64, String signToolPath) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64, signToolPath);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag + "/v1/poe/create";

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
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag + "/v1/poe?id=" + id;

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
            String privatekeyBase64, String signToolPath) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64, signToolPath);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag
                + "/v1/transaction/tokens/issue/prepare";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject issue_pre = JSON.parseObject(response);

            JSONObject issue_pre_resp = issue_pre.getJSONObject("Payload");

            if (!issue_pre_resp.containsKey("txs")) {
                return "issue ctoken proposal failed: " + response;
            }
            JSONArray txs = issue_pre_resp.getJSONArray("txs");

            String issuer = payload.getString("issuer");

            String strParams = "{\"creator\":\"" + did + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privatekeyBase64 + "\",\"payload\":\""
                    + payload.toString().replace("\"", "\\\"") + "\"}";

            txs = SignTxs(issuer, txs, JSON.parseObject(strParams));

            issue_pre_resp.put("txs", txs);

            String processResp = ProcessTx(jsonheader, issue_pre_resp);

            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            resPayload.put("token_id", issue_pre_resp.getString("token_id"));

            result.put("Payload", resPayload);

            return result.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String TransferTokens(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64, String signToolPath) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64, signToolPath);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag
                + "/v1/transaction/tokens/transfer/prepare";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject issue_pre = JSON.parseObject(response);

            JSONObject issue_pre_resp = issue_pre.getJSONObject("Payload");

            if (!issue_pre_resp.containsKey("txs")) {
                return "issue ctoken proposal failed: " + response;
            }
            JSONArray txs = issue_pre_resp.getJSONArray("txs");

            String issuer = payload.getString("issuer");

            String strParams = "{\"creator\":\"" + did + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privatekeyBase64 + "\",\"payload\":\""
                    + payload.toString().replace("\"", "\\\"") + "\"}";

            txs = SignTxs(issuer, txs, JSON.parseObject(strParams));

            issue_pre_resp.put("txs", txs);

            String processResp = ProcessTx(jsonheader, issue_pre_resp);

            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            resPayload.put("token_id", issue_pre_resp.getString("token_id"));

            result.put("Payload", resPayload);

            return result.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String IssueAssets(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64, String signToolPath) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64, signToolPath);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag
                + "/v1/transaction/assets/issue/prepare";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject issue_pre = JSON.parseObject(response);

            JSONObject issue_pre_resp = issue_pre.getJSONObject("Payload");

            if (!issue_pre_resp.containsKey("txs")) {
                return "issue ctoken proposal failed: " + response;
            }
            JSONArray txs = issue_pre_resp.getJSONArray("txs");

            String issuer = payload.getString("issuer");

            String strParams = "{\"creator\":\"" + did + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privatekeyBase64 + "\",\"payload\":\""
                    + payload.toString().replace("\"", "\\\"") + "\"}";

            txs = SignTxs(issuer, txs, JSON.parseObject(strParams));

            issue_pre_resp.put("txs", txs);

            String processResp = ProcessTx(jsonheader, issue_pre_resp);

            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            resPayload.put("token_id", issue_pre_resp.getString("token_id"));

            result.put("Payload", resPayload);

            return result.toString();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String TransferAssets(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64, String signToolPath) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64, signToolPath);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag
                + "/v1/transaction/assets/transfer/prepare";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject issue_pre = JSON.parseObject(response);

            JSONObject issue_pre_resp = issue_pre.getJSONObject("Payload");

            if (!issue_pre_resp.containsKey("txs")) {
                return "issue ctoken proposal failed: " + response;
            }
            JSONArray txs = issue_pre_resp.getJSONArray("txs");

            String issuer = payload.getString("issuer");

            String strParams = "{\"creator\":\"" + did + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privatekeyBase64 + "\",\"payload\":\""
                    + payload.toString().replace("\"", "\\\"") + "\"}";

            txs = SignTxs(issuer, txs, JSON.parseObject(strParams));

            issue_pre_resp.put("txs", txs);

            String processResp = ProcessTx(jsonheader, issue_pre_resp);

            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            resPayload.put("token_id", issue_pre_resp.getString("token_id"));

            result.put("Payload", resPayload);

            return result.toString();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private JSONArray SignTxs(String did, JSONArray txs, JSONObject params) {
        for (int i = 0; i < txs.size(); i++) {
            JSONObject job = txs.getJSONObject(i);

            if (job.getString("founder") != did) {
                params = client.getEntParams();
            }

            job = SignTx(job, params);
            txs.set(i, job);
        }

        return txs;
    }

    private JSONObject SignTx(JSONObject job, JSONObject params) {
        if (!job.containsKey("txout")) {
            return null;
        }
        JSONArray txoutArray = job.getJSONArray("txout");
        for (int i = 0; i < txoutArray.size(); i++) {
            JSONObject tx = txoutArray.getJSONObject(i);
            if (!tx.containsKey("script")) {
                return null;
            }

            JSONObject utxo_sig = JSON.parseObject(new String(Base64.decode(tx.getString("script"))));
            utxo_sig.put("creator", params.get("creator"));
            utxo_sig.put("nonce", params.get("nonce"));
            utxo_sig.put("signature", params.get("privateB64"));
            tx.put("txout", utxo_sig);
            txoutArray.set(i, tx);
        }
        job.put("txout", txoutArray);
        return job;
    }

    private String ProcessTx(JSONObject jsonheader, JSONObject jsonbody) {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag + "/v1/transaction/process";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String IndexSet(JSONObject jsonheader, JSONObject jsonbody) {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag + "/v1/index/set";
        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String IndexGet(JSONObject jsonheader, JSONObject jsonbody) {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/" + request.client.RouteTag + "/v1/index/get";

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
