package ru.swenly.swenlyantibot.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import ru.swenly.swenlyantibot.SwenlyAntiBot;
import ru.swenly.swenlyantibot.tasks.ParsingProxy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class CheckProxy {
    private static String url = "https://detector.tools/api/v1/whois";

    public static boolean isProxy(String ip) {
        if (ip.contains(":")) {
            ip = ip.replace(":", "");
        }

        if (ip.contains("127.0.0.1")) {
            return false;
        }

        if (ParsingProxy.all_proxies.contains(ip)) {
            return true;
        }


        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0");
        JSONObject obj = new JSONObject();
        obj.put("address", ip);
        String json = obj.toJSONString();
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(stringEntity);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String response_text = EntityUtils.toString(response.getEntity());
            JsonElement jsonElement = new JsonParser().parse(response_text);
            Boolean isProxy = Boolean.parseBoolean(jsonElement.getAsJsonObject().get("ip_details").getAsJsonObject().get("proxy").toString());
            if (isProxy) {
                return true;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://funkemunky.cc/vpn?ip=" + ip + "&license=");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0");

        try {
            HttpResponse response = httpClient.execute(httpGet);
            String response_text = EntityUtils.toString(response.getEntity());
            JsonElement jsonElement = new JsonParser().parse(response_text);
            Boolean isProxy = Boolean.parseBoolean(jsonElement.getAsJsonObject().get("proxy").toString());
            if (isProxy) {
                return true;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File kingdos_proxies = new File(SwenlyAntiBot.class.getResource("KingDOS_proxies.txt").getPath());
        try {
            Scanner scanner = new Scanner(kingdos_proxies);
            return scanner.toString().contains(ip);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
