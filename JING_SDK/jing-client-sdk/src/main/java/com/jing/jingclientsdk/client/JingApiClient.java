package com.jing.jingclientsdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.jing.jingclientsdk.utils.SignUtils;
import com.jing.project.jingcommon.model.entity.InterfaceInfoInvoke;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class JingApiClient {

    private String accessKey;
    private String secretKey;

    public JingApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> headerMap = new HashMap<>();
        Long timestamp = System.currentTimeMillis() / 1000L;
        String s = Long.toString(timestamp);
        headerMap.put("timestamp",s);
        headerMap.put("accessKey", accessKey);
        headerMap.put("body", body);
        headerMap.put("sign", SignUtils.genSign(s, secretKey));
        return headerMap;
    }

    public String interfaceInvokeByPost(InterfaceInfoInvoke interfaceInfoInvoke) {
        String params = interfaceInfoInvoke.getParams();
        String url = interfaceInfoInvoke.getUrl();
        HttpResponse httpResponse = HttpRequest.post(url)
                .addHeaders(getHeaderMap(params))
                .body(params).execute();
        String result =null;
        try{
            result =httpResponse.body();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }
    public String interfaceInvokeByGet(InterfaceInfoInvoke interfaceInfoInvoke) {
        String params = interfaceInfoInvoke.getParams();
        String url = interfaceInfoInvoke.getUrl();
        HttpResponse httpResponse = HttpRequest.get(url)
                .addHeaders(getHeaderMap(params))
                .body(params).execute();
        String result = httpResponse.body();
        return result;

    }
}
