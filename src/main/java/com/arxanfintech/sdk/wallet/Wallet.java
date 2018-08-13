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

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.Header;

import com.arxanfintech.common.crypto.Crypto;
import com.arxanfintech.common.rest.*;
import com.arxanfintech.common.structs.RegisterWalletBody;
import com.arxanfintech.common.structs.Headers;
import com.arxanfintech.common.util.JsonUtil;
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
    private Client client;
    private Crypto crypto;

    public Wallet(Client client) {
        if (client.RouteTag == null || client.RouteTag == "") {
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
        request.url = "http://" + request.client.Address + "/v1/wallet/register";

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
        request.url = "http://" + request.client.Address + "/v1/wallet/register/subwallet";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String QueryWalletInfos(JSONObject jsonheader, String id) throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/v1/wallet/info?id=" + id;

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
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
        request.url = "http://" + request.client.Address + "/v1/wallet/balance?id=" + id;

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
        request.url = "http://" + request.client.Address + "/v1/poe/create";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String UpdatePOE(JSONObject jsonheader, JSONObject payload, String did, String created, String nonce,
            String privatekeyBase64, String signToolPath) throws Exception {

        Request request = new Request();
        request.client = this.client;
        request.body = Common.Build_Body(payload, did, created, nonce, privatekeyBase64, signToolPath);
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.Address + "/v1/poe/update";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPut(request);
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
        request.url = "http://" + request.client.Address + "/v1/poe?id=" + id;

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
        request.url = "http://" + request.client.Address + "/v1/transaction/tokens/issue/prepare";

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
        request.url = "http://" + request.client.Address + "/v1/transaction/tokens/transfer/prepare";

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
        request.url = "http://" + request.client.Address + "/v1/transaction/assets/issue/prepare";

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
        request.url = "http://" + request.client.Address + "/v1/transaction/assets/transfer/prepare";

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
        request.url = "http://" + request.client.Address + "/v1/transaction/process";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String QueryTransactionLogs(JSONObject jsonheader, String type, Boolean isEndpoint, String endpointOrId)
            throws Exception {
        Request request = new Request();
        request.client = this.client;
        request.header = jsonheader;
        request.crypto = crypto;
        if (isEndpoint) {
            request.url = "http://" + request.client.Address + "/v1/transaction/logs?type=" + type + "&endpoint="
                    + endpointOrId;
        } else {
            request.url = "http://" + request.client.Address + "/v1/transaction/logs?type=" + type + "&id="
                    + endpointOrId;
        }

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoGet(request);
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
        request.url = "http://" + request.client.Address + "/v1/index/set";
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
        request.url = "http://" + request.client.Address + "/v1/index/get";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * httpclient post file
     *
     * @param request
     *            http post info
     * @return response data error return null
     */
    private String UploadFile(JSONObject jsonheader, String filename, String poeid, Boolean readonly, String apiKey,
            String url) {
        try {
            HttpResponse<String> res = Unirest.post(url).header("API-Key", apiKey)
                    .header("Callback-Url", jsonheader.getString("Callback-Url"))
                    .header("Bc-Invoke-Mode", jsonheader.getString("Bc-Invoke-Mode"))
                    .header("Content-Type", "multipart/form-data").field("poe_id", poeid).field("read_only", readonly)
                    .field("poe_file", filename).field("file", new File(filename)).asString();

            // final InputStream stream = new FileInputStream(new
            // File(getClass().getResource(filename).toURI()));
            // final byte[] bytes = new byte[stream.available()];
            // stream.read(bytes);
            // stream.close();
            // .field("file", bytes, filename)

            // .field("file", new FileInputStream(new
            // File(getClass().getResource(filename).toURI())),
            // ContentType.APPLICATION_OCTET_STREAM, filename)

            String respData = res.getBody();

            System.out.println("Got remote cipher response: " + respData);

            String oriData = this.crypto.decryptAndVerify(respData.getBytes());

            return oriData;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
