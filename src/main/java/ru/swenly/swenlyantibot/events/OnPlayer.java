package ru.swenly.swenlyantibot.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.swenly.swenlyantibot.not_my.MinecraftPing;
import ru.swenly.swenlyantibot.SwenlyAntiBot;
import ru.swenly.swenlyantibot.utils.CheckProxy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class OnPlayer implements Listener {
    private final JavaPlugin plugin = SwenlyAntiBot.getPlugin(SwenlyAntiBot.class);
    File path = plugin.getDataFolder();
    String full_path = path + "/who_resolved.json";
    JSONObject main_obj = new JSONObject();
    JSONParser jsonParser = new JSONParser();
    private static BufferedWriter bw;
    JSONObject obj = new JSONObject();
    File file;
    FileReader fileReader;
    Boolean inList = false;
    Integer ping = 1;

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                SwenlyAntiBot.captchas.remove(captcha);
                break;
            }
        }

        System.out.println(SwenlyAntiBot.antiproxy);
        if (SwenlyAntiBot.antiproxy == 1) {
            if (CheckProxy.isProxy(player.getAddress().getAddress().getHostAddress())) {
                player.kickPlayer(SwenlyAntiBot.antiproxy_message);
                return;
            }
        }

        File who_resolved = new File(full_path);
        try {
            who_resolved.getParentFile().mkdirs();

            if (who_resolved.createNewFile()) {
                main_obj = new JSONObject();
                obj = new JSONObject();
                JSONArray who_used = new JSONArray();
                obj.put("Who Resolved", who_used);
                main_obj.put("Main", obj);

                FileWriter fileWriter = new FileWriter(full_path);
                bw = new BufferedWriter(fileWriter);
                bw.write(main_obj.toJSONString());
                bw.flush();
                bw.close();
            } else {
                Object read_obj = jsonParser.parse(new FileReader(full_path));
                main_obj = (JSONObject) read_obj;
                obj = (JSONObject) main_obj.get("Main");
                JSONArray who_used = (JSONArray) obj.get("Who Resolved");

                inList = false;
                for (Object nickname_obj : who_used) {
                    String nickname = (String) nickname_obj;

                    if (nickname.equals(player.getName())) {
                        inList = true;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (!inList) {
            Random random = new Random();
            Integer first_int = random.nextInt(14) + 1;
            Integer second_int = random.nextInt(9) + 1;
            Integer otvet = first_int + second_int;
            SwenlyAntiBot.captchas.add(player.getName() + ":" + first_int + " + " + second_int + " = " + otvet);
            player.sendTitle(SwenlyAntiBot.Antibot_title_message, SwenlyAntiBot.Antibot_subtitle_message.replace("%first_int%",first_int.toString()).replace("%second_int%",second_int.toString()), 1, (int) ((long) SwenlyAntiBot.kick_delay), 1);
            player.setInvulnerable(true);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                try {
                    ping = MinecraftPing.getReflectionPing(Bukkit.getPlayer(player.getName()));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                player.sendMessage("Ping: " + ping);
                player.sendMessage("Max Ping: " + SwenlyAntiBot.max_ping);
                if ((ping > SwenlyAntiBot.max_ping) && (SwenlyAntiBot.max_ping > 0)) {
                    player.kickPlayer(SwenlyAntiBot.ping_message);
                    return;
                }

                Bukkit.getScheduler().runTaskLater(SwenlyAntiBot.getPlugin(SwenlyAntiBot.class), () -> {
                    if (player == null) {
                        return;
                    }

                    for (String captcha : SwenlyAntiBot.captchas) {
                        if (captcha.contains(player.getName())) {
                            player.kickPlayer(SwenlyAntiBot.kick_message);
                            break;
                        }
                    }
                }, SwenlyAntiBot.kick_delay - 100L); // amount to wait in ticks , 20 ticks = 1 second
            }, 100L);
        }
    }
}
