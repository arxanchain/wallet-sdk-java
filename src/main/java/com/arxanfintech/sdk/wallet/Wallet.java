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
import java.util.Map;

import com.arxanfintech.common.crypto.Crypto;
import com.arxanfintech.common.rest.*;
import com.arxanfintech.common.structs.Headers;
import com.arxanfintech.common.util.Utils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
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
    private static int result;
    private Client client;
    private Crypto crypto;

    public Wallet(Client client) {
        if (client.GetRouteTag() == null || client.GetRouteTag() == "") {
            client.SetRouteTag("wallet-ng");
        }
        this.client = client;

        String privateKeyPath = client.GetCertPath() + "/users/" + client.GetApiKey() + "/" + client.GetApiKey()
                + ".key";
        String publicCertPath = client.GetCertPath() + "/tls/tls.cert";

        try {
            this.crypto = new Crypto(new FileInputStream(privateKeyPath), new FileInputStream(publicCertPath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public JSONObject Register(JSONObject jsonheader, JSONObject jsonbody) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/wallet/register";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject RegisterSubWallet(JSONObject jsonheader, JSONObject jsonbody) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/wallet/register/subwallet";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject QueryWalletInfos(JSONObject jsonheader, String id) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/wallet/info?id=" + id;

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject QueryWalletBalance(JSONObject jsonheader, String id) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/wallet/balance?id=" + id;

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject CreatePOE(JSONObject jsonheader, JSONObject payload, String creator, String created, String nonce,
            String privateKeyBase64, String signToolPath) throws Exception {

        Request request = new Request();
        request.client = this.client;

        request.body = Common.BuildBody(payload, creator, created, nonce, privateKeyBase64, signToolPath);

        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/poe/create";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject UpdatePOE(JSONObject jsonheader, JSONObject payload, String creator, String created, String nonce,
            String privateKeyBase64, String signToolPath) throws Exception {

        Request request = new Request();
        request.client = this.client;

        request.body = Common.BuildBody(payload, creator, created, nonce, privateKeyBase64, signToolPath);

        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/poe/update";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPut(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject QueryPOE(JSONObject jsonheader, String id) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/poe?id=" + id;

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject IssueTokens(JSONObject jsonheader, JSONObject payload, String creator, String created,
            String nonce, String privateKeyBase64, String signToolPath) throws Exception {
        Request request = new Request();
        request.client = this.client;

        request.body = Common.BuildBody(payload, creator, created, nonce, privateKeyBase64, signToolPath);

        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/transaction/tokens/issue/prepare";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject issue_pre = JSON.parseObject(response);

            JSONObject issue_pre_resp = issue_pre.getJSONObject("Payload");

            if (!issue_pre_resp.containsKey("txs")) {
                return JSON.parseObject("{\"ErrMessage\":" + "issue ctoken proposal failed: " + response
                        + ",\"ErrCode\":-1,\"Method\":\"\"}");
            }
            JSONArray txs = issue_pre_resp.getJSONArray("txs");

            String issuer = payload.getString("issuer");

            String strParams = "{\"creator\":\"" + creator + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privateKeyBase64 + "\",\"payload\":\""
                    + payload.toString().replace("\"", "\\\"") + "\"}";

            txs = SignTxs(issuer, txs, JSON.parseObject(strParams), signToolPath);

            issue_pre_resp.put("txs", txs);

            String processResp = ProcessTx(jsonheader, issue_pre_resp);

            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            resPayload.put("token_id", issue_pre_resp.getString("token_id"));

            result.put("Payload", resPayload);

            return result;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject TransferTokens(JSONObject jsonheader, JSONObject payload, String creator, String created,
            String nonce, String privateKeyBase64, String signToolPath) throws Exception {
        Request request = new Request();
        request.client = this.client;

        request.body = Common.BuildBody(payload, creator, created, nonce, privateKeyBase64, signToolPath);

        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/transaction/tokens/transfer/prepare";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject issue_pre = JSON.parseObject(response);
            if (issue_pre.getInteger("ErrCode") != 0) {
                return issue_pre;
            }

            JSONArray issue_pre_resp = issue_pre.getJSONArray("Payload");
            String issuer = payload.getString("from");

            String strParams = "{\"creator\":\"" + creator + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privateKeyBase64 + "\"}";

            issue_pre_resp = SignTxs(issuer, issue_pre_resp, JSON.parseObject(strParams), signToolPath);

            JSONObject txs = new JSONObject();
            txs.put("txs", issue_pre_resp);

            String processResp = ProcessTx(jsonheader, txs);
            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            result.put("Payload", resPayload);

            return result;
        } catch (

        Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject IssueAssets(JSONObject jsonheader, JSONObject payload, String creator, String created,
            String nonce, String privateKeyBase64, String signToolPath) throws Exception {
        Request request = new Request();
        request.client = this.client;

        request.body = Common.BuildBody(payload, creator, created, nonce, privateKeyBase64, signToolPath);

        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/transaction/assets/issue/prepare";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject issue_pre = JSON.parseObject(response);
            if (issue_pre.getInteger("ErrCode") != 0) {
                return issue_pre;
            }

            JSONArray issue_pre_resp = issue_pre.getJSONArray("Payload");
            String issuer = payload.getString("from");

            String strParams = "{\"creator\":\"" + creator + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privateKeyBase64 + "\"}";

            issue_pre_resp = SignTxs(issuer, issue_pre_resp, JSON.parseObject(strParams), signToolPath);

            JSONObject txs = new JSONObject();
            txs.put("txs", issue_pre_resp);

            String processResp = ProcessTx(jsonheader, txs);
            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            result.put("Payload", resPayload);

            return result;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject TransferAssets(JSONObject jsonheader, JSONObject payload, String creator, String created,
            String nonce, String privateKeyBase64, String signToolPath) throws Exception {
        Request request = new Request();
        request.client = this.client;

        request.body = Common.BuildBody(payload, creator, created, nonce, privateKeyBase64, signToolPath);

        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/transaction/assets/transfer/prepare";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject issue_pre = JSON.parseObject(response);
            if (issue_pre.getInteger("ErrCode") != 0) {
                return issue_pre;
            }

            JSONArray issue_pre_resp = issue_pre.getJSONArray("Payload");
            String issuer = payload.getString("from");

            String strParams = "{\"creator\":\"" + creator + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privateKeyBase64 + "\"}";

            issue_pre_resp = SignTxs(issuer, issue_pre_resp, JSON.parseObject(strParams), signToolPath);

            JSONObject txs = new JSONObject();
            txs.put("txs", issue_pre_resp);

            String processResp = ProcessTx(jsonheader, txs);
            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            result.put("Payload", resPayload);

            return result;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }

    }

    private JSONArray SignTxs(String did, JSONArray txs, JSONObject params, String signToolPath) {
        for (int i = 0; i < txs.size(); i++) {

            JSONObject job = txs.getJSONObject(i);

            if (!job.getString("founder").equals(did)) {
                params = client.getEntParams();
            }

            job = SignTx(job, params, signToolPath);
            txs.set(i, job);
        }

        return txs;
    }

    private JSONObject SignTx(JSONObject job, JSONObject params, String signToolPath) {
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

            utxo_sig.put("creator", params.getString("creator"));
            utxo_sig.put("nonce", params.getString("nonce"));
            utxo_sig.put("created", params.getInteger("created"));

            String publicKey = utxo_sig.getString("publicKey");

            String signBody = Common.SignString(publicKey.trim(), params.getString("creator"),
                    params.getString("created"), params.getString("nonce"), params.getString("privateB64"),
                    signToolPath);

            utxo_sig.put("signature", signBody);

            tx.put("script", new String(Base64.encode(utxo_sig.toString().getBytes())));
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
        request.url = "http://" + request.client.GetAddress() + "/v1/transaction/process";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public JSONObject QueryTransactionLogs(JSONObject jsonheader, String type, Boolean isEndpoint, String endpointOrId)
            throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        if (isEndpoint) {
            request.url = "http://" + request.client.GetAddress() + "/v1/transaction/logs?type=" + type + "&endpoint="
                    + endpointOrId;
        } else {
            request.url = "http://" + request.client.GetAddress() + "/v1/transaction/logs?type=" + type + "&id="
                    + endpointOrId;
        }

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject UploadFile(JSONObject jsonheader, String pathFile, String poeID, Boolean readOnly) {
        try {
            Map<String, String> mapHeader = Utils.JsonToMap(jsonheader);
            mapHeader.put(Headers.APIKeyHeader, this.client.GetApiKey());

            if (this.client.GetRouteTag() != "") {
                mapHeader.put(Headers.FabioRouteTagHeader, this.client.GetRouteTag());
                mapHeader.put(Headers.RouteTagHeader, this.client.GetRouteTag());
            }
            String url = "http://" + this.client.GetAddress() + "/v1/poe/upload";

            String boundary = "BeginWebKitFormBoundary7MA4YWxkTrZu0gW";
            mapHeader.put("Content-Type", "multipart/form-data; boundary=" + boundary);

            String body = Tools.GetMultipartData(boundary, pathFile, poeID, readOnly);
            if (body == null || body == "") {
                return JSON.parseObject(
                        "{\"ErrMessage\":\"GetMultipartData error. Please make sure correct pathfile.\",\"ErrCode\":-1,\"Method\":\"\"}");
            }
            if (this.client.GetEnableCrypto()) {
                body = new String(crypto.signAndEncrypt(body.getBytes()));
            }
            HttpResponse<String> response = Unirest.post(url).headers(mapHeader).body(body).asString();

            String respData = response.getBody();

            String oriData = this.client.GetEnableCrypto() ? this.crypto.decryptAndVerify(respData.getBytes())
                    : respData;
            JSONObject jsonResponse = JSON.parseObject(oriData);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":\"" + e.getMessage() + "\",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

}
