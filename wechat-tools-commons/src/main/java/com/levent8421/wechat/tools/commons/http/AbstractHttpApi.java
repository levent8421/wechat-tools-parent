package com.levent8421.wechat.tools.commons.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.levent8421.wechat.tools.commons.context.ContentType;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.commons.utils.http.HttpApiStringResponse;
import com.levent8421.wechat.tools.commons.utils.http.HttpUtils;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.security.KeyStore;
import java.util.Map;

/**
 * Create by 郭文梁 2019/5/18 0018 13:46
 * AbstractHttpApi
 * Http方式的Api基类
 *
 * @author 郭文梁
 */
public abstract class AbstractHttpApi {
    /**
     * 发送Get请求
     *
     * @param url Url
     * @return response string
     */
    public String get(String url) {
        try {
            return HttpUtils.get(url);
        } catch (IOException e) {
            throw new InternalServerErrorException("Http get[" + url + "]:" + ExceptionUtils.getMessage(e), e);
        }
    }

    /**
     * 发送POSt请求 contentType = xml
     *
     * @param url  地址
     * @param body 内容
     * @return 响应
     */
    public String postXml(String url, String body) {
        try {
            return HttpUtils.postXml(url, body);
        } catch (IOException e) {
            throw new InternalServerErrorException("Http post xml [" + url + "],error[" + e.getMessage() + "]", e);
        }
    }

    /**
     * 发送Get请求，并将接管反序列化（json）
     *
     * @param url           请求地址
     * @param responseClass class
     * @param <T>           类型
     * @return 反序列化结果
     */
    public <T> T get(String url, Class<T> responseClass) {
        String jsonString = get(url);
        T res = JSON.parseObject(jsonString, responseClass);
        if (res instanceof SourceJsonAware) {
            ((SourceJsonAware) res).setSourceJson(jsonString);
        }
        return res;
    }

    /**
     * 方get请求，并将响应结果反序列化，
     * 当检查器返回false时抛出InternalServerErrorException异常
     *
     * @param url           Api地址
     * @param responseClass 响应结果class
     * @param checker       检查器
     * @param <T>           反序列化类型
     * @return T
     */
    public <T> T get(String url, Class<T> responseClass, JsonResponseChecker checker) {
        String response = get(url);
        JSONObject jsonObject = JSON.parseObject(response);
        if (checker.isSuccess(jsonObject)) {
            T res = jsonObject.toJavaObject(responseClass);
            if (res instanceof SourceJsonAware) {
                ((SourceJsonAware) res).setSourceJson(response);
            }
            return res;
        }
        throw new InternalServerErrorException("Http get[" + url + "]:response [" + response + "]is unexpect!");
    }

    /**
     * 发送Post请求 contenttype = json
     *
     * @param url  地址
     * @param body 请求体
     * @return 响应
     * @throws IOException IO异常
     */
    public String postJson(String url, String body) throws IOException {
        return HttpUtils.post(url, body, ContentType.JSON_UTF8);
    }

    /**
     * 发送Post请求 contenttype = json
     *
     * @param url           地址
     * @param body          请求体
     * @param responseClass 响应接受类
     * @return 响应
     * @throws IOException IO异常
     */
    public <T> T postJson(String url, String body, Class<T> responseClass) throws IOException {
        final String reasp = postJson(url, body);
        return JSON.parseObject(reasp, responseClass);
    }

    /**
     * Post 提交表单
     *
     * @param url    url
     * @param params params
     * @return resp
     * @throws IOException IOE
     */
    public String postForm(String url, Map<String, Object> params) throws IOException {
        val body = HttpUtils.asFormBody(params);
        return HttpUtils.post(url, body, ContentType.FORM_DATA);
    }

    /**
     * 发送带证书的Http请求
     *
     * @param url      URL
     * @param body     内容
     * @param keyStore 证书文件
     * @param password 密码
     * @return 响应内容
     */
    public static String postXmlWithP12(String url, String body, KeyStore keyStore, String password) {
        return HttpUtils.postXmlWithKeyStore(url, body, keyStore, password);
    }

    /**
     * Exec Http Post Request with json body
     *
     * @param url  url
     * @param body json body
     * @return HttpApiStringReponse
     */
    protected HttpApiStringResponse postJson4Response(String url, String body) {
        return HttpUtils.postJsonForResponse(url, body);
    }

    /**
     * 响应检查器
     */
    @FunctionalInterface
    public interface JsonResponseChecker {
        /**
         * 检查接口调用是否成功
         *
         * @param response 响应数据
         * @return 是否成功
         */
        boolean isSuccess(JSONObject response);
    }
}
