package com.william.toolkit.net;

/**
 * Time:2024/1/19 9:46
 * Author:陈保鲁
 * Description:
 */
public interface DecryptCallBack {
    public String responseBodyDecrypt(String body);
    public String requestBodyDecrypt(String body);
}
