package com.slzh.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    public static String getHttpEntityContent(HttpResponse response) throws UnsupportedOperationException, IOException{
        String result = "";
        HttpEntity entity = response.getEntity();
        if(entity != null){
            InputStream in = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder strber= new StringBuilder();
            String line = null;
            while((line = br.readLine())!=null){
                strber.append(line+'\n');
            }
            br.close();
            in.close();
            result = strber.toString();
        }

        return result;

    }

    public static String doGet(Map<String, String> headers,String url){
        String result = "";
        HttpGet get = new HttpGet(url);
        try{
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                get.addHeader(entry.getKey(), entry.getValue());
            }
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpResponse response = httpClient.execute(get);
            result = getHttpEntityContent(response);

            if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
                result = "???????????????";
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally{
            get.abort();
        }
        return result;
    }
    public static String doPost(Map<String, String> params, Map<String, String> headers, String url){
        String result = null;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        for(Map.Entry<String, String> entry : params.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        // ??????httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            //??????post??????
            HttpPost httpPost = new HttpPost(url);
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
            // ?????????????????????????????????
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpPost.setConfig(requestConfig);


            // ????????????????????????
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps);
            httpPost.setEntity(urlEncodedFormEntity);
            response = httpclient.execute(httpPost);
            // ??????????????????
            int statusCode = response.getStatusLine().getStatusCode();
            // ??????????????????????????????
            if (statusCode != HttpStatus.SC_OK) {
                // ?????????????????????
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //result = EntityUtils.toString(entity);//?????????????????????
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //????????????????????????
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }
}
