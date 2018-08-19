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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLContext;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

import com.arxanfintech.common.crypto.Crypto;
import com.arxanfintech.common.rest.*;
import com.arxanfintech.common.structs.RegisterWalletBody;
import com.arxanfintech.common.structs.Headers;
import com.arxanfintech.common.util.JsonUtil;
import com.arxanfintech.common.util.Utils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.util.encoders.Base64;
import org.spongycastle.crypto.InvalidCipherTextException;

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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
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
            System.out.println(issue_pre);

            JSONArray issue_pre_resp = issue_pre.getJSONArray("Payload");
            System.out.println("transfer response");
            System.out.println(issue_pre_resp);
            System.out.println("end transfer response");

            String issuer = payload.getString("from");

            String strParams = "{\"creator\":\"" + creator + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privateKeyBase64 + "\"}";

            issue_pre_resp = SignTxs(issuer, issue_pre_resp, JSON.parseObject(strParams), signToolPath);

            JSONObject txs = new JSONObject();
            txs.put("txs", issue_pre_resp);

            System.out.println("txs");
            System.out.println(txs);
            System.out.println("txs");

            String processResp = ProcessTx(jsonheader, txs);
            JSONObject result = JSON.parseObject(processResp);

            JSONObject resPayload = result.getJSONObject("Payload");

            result.put("Payload", resPayload);

            return result;
        } catch (

        Exception e) {
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public String IssueAssets(JSONObject jsonheader, JSONObject payload, String creator, String created, String nonce,
            String privateKeyBase64, String signToolPath) throws Exception {
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

            JSONObject issue_pre_resp = issue_pre.getJSONObject("Payload");

            if (!issue_pre_resp.containsKey("txs")) {
                return "issue ctoken proposal failed: " + response;
            }
            JSONArray txs = issue_pre_resp.getJSONArray("txs");

            String issuer = payload.getString("issuer");

            String strParams = "{\"creator\":\"" + creator + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privateKeyBase64 + "\"}";

            txs = SignTxs(issuer, txs, JSON.parseObject(strParams), signToolPath);

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

    public String TransferAssets(JSONObject jsonheader, JSONObject payload, String creator, String created,
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

            JSONObject issue_pre_resp = issue_pre.getJSONObject("Payload");

            if (!issue_pre_resp.containsKey("txs")) {
                return "issue ctoken proposal failed: " + response;
            }
            JSONArray txs = issue_pre_resp.getJSONArray("txs");

            String issuer = payload.getString("issuer");

            String strParams = "{\"creator\":\"" + creator + "\",\"created\":\"" + created + "\",\"nonce\":\"" + nonce
                    + "\",\"privateB64\":\"" + privateKeyBase64 + "\"}";

            txs = SignTxs(issuer, txs, JSON.parseObject(strParams), signToolPath);

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

    private JSONArray SignTxs(String did, JSONArray txs, JSONObject params, String signToolPath) {
        System.out.println("SignTxs input params" + params.toString());
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
        System.out.println("SignTx input params" + params.toString());
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
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject IndexSet(JSONObject jsonheader, JSONObject jsonbody) {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/index/set";
        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    public JSONObject IndexGet(JSONObject jsonheader, JSONObject jsonbody) {
        Request request = new Request();
        request.client = this.client;
        request.body = jsonbody;
        request.header = jsonheader;
        request.crypto = crypto;
        request.url = "http://" + request.client.GetAddress() + "/v1/index/get";

        Api api = new Api();
        try {
            api.NewHttpClient();
            String response = api.DoPost(request);
            JSONObject jsonResponse = JSON.parseObject(response);
            return jsonResponse;
        } catch (Exception e) {
            return JSON.parseObject("{\"ErrMessage\":" + e.getMessage() + ",\"ErrCode\":-1,\"Method\":\"\"}");
        }
    }

    /**
     * httpclient post file
     *
     * @param request
     *            http post info
     * @return response data error return null
     */
    public String UploadFile(JSONObject jsonheader, String filename, String poeid, Boolean readonly, String apiKey,
            String url) {
        try {

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("poe_id", poeid);
            builder.addTextBody("read_only", readonly.toString());
            builder.addBinaryBody("poe_file", new File(filename), ContentType.APPLICATION_OCTET_STREAM, "file.ext");

            HttpEntity multipart = builder.build();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("poe_file", new File(filename));
            map.put("poe_id", poeid);
            map.put("read_only", readonly);

            String test = "--4OMdVgP5x3Mu_WCKE_vPIJjs85Kb8cZierkNV\n"
                    + "Content-Disposition: form-data; name=\"poe_id\"\n"
                    + "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\n"
                    + "Content-Transfer-Encoding: 8bit\n" + "\n" + "did:axn:bf09ccc0-d3dc-47f0-9709-4140945fc782\n"
                    + "--4OMdVgP5x3Mu_WCKE_vPIJjs85Kb8cZierkNV\n"
                    + "Content-Disposition: form-data; name=\"read_only\"\n"
                    + "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\n"
                    + "Content-Transfer-Encoding: 8bit\n" + "\n" + "false\n"
                    + "--4OMdVgP5x3Mu_WCKE_vPIJjs85Kb8cZierkNV\n"
                    + "Content-Disposition: form-data; name=\"poe_file\"; filename=\"a.csv\"\n"
                    + "Content-Type: application/octet-stream\n" + "Content-Transfer-Encoding:binary\n" + "\n"
                    + "test file content \n" + "--4OMdVgP5x3Mu_WCKE_vPIJjs85Kb8cZierkNV--";

            String buf = "";
            if (this.client.GetEnableCrypto()) {
                buf = crypto.signAndEncrypt(test.toString().getBytes());

            } else {
                buf = test.toString();
            }

            Map<String, String> mapHeader = Utils.JsonToMap(jsonheader);
            mapHeader.put(Headers.APIKeyHeader, apiKey);
            mapHeader.put("Route-Tag", "wallet-ng");
            mapHeader.put("Content-Type", multipart.getContentType().getValue());

            mapHeader.put("Content-Length", String.valueOf(buf.length()));
            HttpResponse<String> res = Unirest.post(url).headers(mapHeader).body(buf).asString();
            // .field("poe_id", poeid).field("read_only", readonly).field("poe_file", new
            // File(filename)).asString();

            String respData = res.getBody();

            System.out.println("Got remote cipher response: " + respData);

            String oriData = respData;
            if (this.client.GetEnableCrypto()) {
                oriData = this.crypto.decryptAndVerify(respData.getBytes());
            }

            return oriData;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static void uploadFile(JSONObject jsonheader, String filename, String poeid, Boolean readonly, String apiKey,
            String url) {
        try {

            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";

            // 定义数据分隔线
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("poe_id", poeid);
            builder.addTextBody("read_only", readonly.toString());
            builder.addBinaryBody("poe_file", new File(filename), ContentType.APPLICATION_OCTET_STREAM, "file.ext");

            HttpEntity multipart = builder.build();

            String BOUNDARY = multipart.getContentType().getValue();

            // 服务器的域名
            URL httpurl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) httpurl.openConnection();

            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty(Headers.APIKeyHeader, apiKey);
            conn.setRequestProperty(Headers.RouteTagHeader, "wallet-ng");
            // conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" +
            // BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // 上传文件
            File file = new File(filename);
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"photo\";filename=\"" + filename + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }

            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();

            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应
            result = conn.getContentLength();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
    }
}
