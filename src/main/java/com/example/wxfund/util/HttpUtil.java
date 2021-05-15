package com.example.wxfund.util;
import com.alibaba.fastjson.JSON;
import com.example.wxfund.entity.Fund;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class HttpUtil {

    private static String sessionID = null;

    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if(StringUtils.isBlank(sessionID)){
                httpGet.addHeader("sessionid",sessionID);
            }
            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("exception during invoke HttpClientUtils.doGet",e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                log.error("exception during invoke HttpClientUtils.doGet",e);
            }
        }
        return resultString;
    }
    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            if(StringUtils.isBlank(sessionID)){
                httpPost.addHeader("sessionid",sessionID);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("exception during invoke HttpClientUtils.doPost",e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                log.error("exception during invoke HttpClientUtils.doPost",e);
            }
        }
        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json){
        return doPostJson(url,json,null);
    }

    public static String doPostJson(String url, String json, String sessionID) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            if(!StringUtils.isBlank(sessionID)){
                httpPost.addHeader("sessionid",sessionID);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("exception during invoke HttpClientUtils.doPostJson",e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                log.error("exception during invoke HttpClientUtils.doPostJson",e);
            }
        }
        return resultString;
    }

    public static String doPostForm(String url, Map<String, Object> paramMap, String sessionID){
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("sessionid",sessionID);
        MultipartEntityBuilder mutiEntity = MultipartEntityBuilder.create();
        mutiEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//请设置Mode为BROWSER_COMPATIBLE
        ContentType strContent=ContentType.create("text/plain",Charset.forName("UTF-8"));
        for(String param : paramMap.keySet()){
            if(param.equals("img")){
                FileSystemResource fileSystemResource = (FileSystemResource)paramMap.get(param);
                mutiEntity.addBinaryBody(param,fileSystemResource.getFile());
            }
            else{
                mutiEntity.addTextBody(param, paramMap.get(param).toString(), strContent);
            }
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpPost.setEntity(mutiEntity.build());
        String resultString = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }catch (IOException e){
            log.error("exception during invoke doPostForm",e);
        }finally{
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                }
            }
        }
        return resultString;
    }

    public static void main(String[] args) {
        String res = doGet("http://fundgz.1234567.com.cn/js/001186.js");
        String regex = "^jsonpgz(.*)$";
        String nu = "jsonpgz({fund})";
        Pattern r = Pattern.compile(regex);
        String resJson = res.substring(8, res.length() - 2);
        Fund fund = JSON.parseObject(resJson, Fund.class);
        System.out.println(res);
    }

}
