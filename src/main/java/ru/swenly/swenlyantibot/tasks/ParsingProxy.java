package ru.swenly.swenlyantibot.tasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;
import org.jsoup.Jsoup;
import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.swenly.swenlyantibot.SwenlyAntiBot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ParsingProxy extends BukkitRunnable {
    String[] ProxyUrls = {"https://raw.githubusercontent.com/jetkai/proxy-list/main/online-proxies/txt/proxies-socks4.txt",
        "https://raw.githubusercontent.com/saschazesiger/Free-Proxies/master/proxies/socks4.txt",
        "https://raw.githubusercontent.com/monosans/proxy-list/main/proxies/socks4.txt",
        "https://raw.githubusercontent.com/ShiftyTR/Proxy-List/master/socks4.txt",
        "https://api.proxyscrape.com/v2/?request=displayproxies&protocol=socks4",
        "https://raw.githubusercontent.com/mmpx12/proxy-list/master/socks4.txt",
        "https://www.proxy-list.download/api/v1/get?type=socks4",
        "https://openproxylist.xyz/socks4.txt",
        "https://proxyspace.pro/socks4.txt",
        //socks5
        "https://raw.githubusercontent.com/jetkai/proxy-list/main/online-proxies/txt/proxies-socks5.txt",
        "https://raw.githubusercontent.com/saschazesiger/Free-Proxies/master/proxies/socks5.txt",
        "https://raw.githubusercontent.com/monosans/proxy-list/main/proxies/socks5.txt",
        "https://raw.githubusercontent.com/ShiftyTR/Proxy-List/master/socks5.txt",
        "https://api.proxyscrape.com/v2/?request=displayproxies&protocol=socks5",
        "https://raw.githubusercontent.com/mmpx12/proxy-list/master/socks5.txt",
        "https://raw.githubusercontent.com/ShiftyTR/Proxy-List/master/socks5.txt",
        "https://raw.githubusercontent.com/monosans/proxy-list/main/proxies/socks5.txt",
        "https://raw.githubusercontent.com/jetkai/proxy-list/main/online-proxies/txt/proxies-socks5.txt",
        "https://raw.githubusercontent.com/hookzof/socks5_list/master/proxy.txt",
	"https://raw.githubusercontent.com/mmpx12/proxy-list/master/socks5.txt",
        "https://www.proxy-list.download/api/v1/get?type=socks5",
        "https://openproxylist.xyz/socks5.txt",
        "https://proxyspace.pro/socks5.txt",
        // "https://cdn.discordapp.com/attachments/1014241422997721249/1014370207524982834/socks4.txt",
        // "https://cdn.discordapp.com/attachments/1014241422997721249/1014370233663893504/socks5.txt",
        "https://proxyx.ru/700.txt"
    };

    public static ArrayList<String> all_proxies = new ArrayList<String>();

    @Override
    public void run() {
        all_proxies.clear();

        for (String url : Arrays.asList(ProxyUrls)) {
            try {
                url = "https:////" + url.replaceAll("https://", "");
                System.out.println(url);

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpsGet = new HttpGet(url);
                httpsGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0");
                try {
                    HttpResponse response = httpClient.execute(httpsGet);
                    String response_text = EntityUtils.toString(response.getEntity());

                    String[] proxies = response_text.split("<pre>", -1)[0].split("\n", -1);

                    for (String proxy : proxies) {
                        if (!all_proxies.contains(proxy)) {
                            all_proxies.add(proxy);
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
