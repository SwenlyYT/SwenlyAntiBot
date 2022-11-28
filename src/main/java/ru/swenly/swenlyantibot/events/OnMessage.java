package ru.swenly.swenlyantibot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.swenly.swenlyantibot.SwenlyAntiBot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OnMessage implements Listener {
    File path = SwenlyAntiBot.getPlugin(SwenlyAntiBot.class).getDataFolder();
    String full_path = path + "/who_resolved.json";
    JSONObject main_obj = new JSONObject();
    JSONParser jsonParser = new JSONParser();
    private static BufferedWriter bw;
    JSONObject obj = new JSONObject();
    File file;
    FileReader fileReader;
    FileWriter fileWriter;
    JSONArray who_used;
    public static Map<Player, String> messages = new HashMap<Player, String>();

    @EventHandler
    public void onChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        Boolean have_captcha = false;

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                have_captcha = true;
                String otvet_in_captcha_replaced = captcha.replace(" ", "");
                String otvet_in_captcha = otvet_in_captcha_replaced.split("=", -1)[1];

                if (!message.equals(otvet_in_captcha)) {
                    player.kickPlayer(SwenlyAntiBot.kick_message);
                }

                else {
                    File who_resolved = new File(full_path);
                    try {
                        who_resolved.getParentFile().mkdirs();

                        if (who_resolved.createNewFile()) {
                            main_obj = new JSONObject();
                            obj = new JSONObject();
                            JSONArray who_used = new JSONArray();
                            obj.put("Who Resolved", who_used);
                        }


                        Object read_obj = jsonParser.parse(new FileReader(full_path));
                        main_obj = (JSONObject) read_obj;
                        obj = (JSONObject) main_obj.get("Main");
                        who_used = (JSONArray) obj.get("Who Resolved");

                        Boolean inList = false;
                        for (Object nickname_obj : who_used) {
                            String nickname = (String) nickname_obj;

                            if (nickname.equals(player.getName())) {
                                inList = true;
                            }
                        }

                        if (!inList) {
                            who_used.add(player.getName());
                        }

                        main_obj.put("Main", obj);
                        fileWriter = new FileWriter(full_path);
                        bw = new BufferedWriter(fileWriter);
                        bw.write(main_obj.toJSONString());
                        bw.flush();
                        bw.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    player.sendMessage(SwenlyAntiBot.sucsess);
                    player.setInvulnerable(false);
                    player.resetTitle();
                }

                SwenlyAntiBot.captchas.remove(captcha);
                event.setCancelled(true);
                break;
            }
        }

        if (!have_captcha) {
            messages.put(player, message);
        }
        
        return;
    }
}
