package org.nhnacademy.simplecurl;

import org.json.JSONObject;

import java.net.URL;

public class Header {
    URL url;
    String method;
    JSONObject requestObject = new JSONObject();

    public Header(URL url) {
        this.url = url;
        this.method = "GET";
        requestObject.put("HOST",url.getHost());
        requestObject.put("User-Agent","scurl/1.0");
        requestObject.put("Accept", "*/*");
    }

    public void addRequest(String key, String value) {
        requestObject.put(key,value);
    }

    public void printRequestHeader() {

        System.out.println(requestObject.toString(4));
    }



}
