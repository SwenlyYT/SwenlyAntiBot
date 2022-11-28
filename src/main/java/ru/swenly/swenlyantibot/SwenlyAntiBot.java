package ru.swenly.swenlyantibot;

import org.bukkit.scheduler.BukkitTask;
import ru.swenly.swenlyantibot.commands.BotsKickCMD;
import ru.swenly.swenlyantibot.commands.PingCMD;
import ru.swenly.swenlyantibot.events.AntiActions;
import ru.swenly.swenlyantibot.events.OnMessage;
import ru.swenly.swenlyantibot.events.OnPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.swenly.swenlyantibot.tasks.ParsingProxy;

import java.util.ArrayList;

public final class SwenlyAntiBot extends JavaPlugin {
    public static ArrayList<String> captchas = new ArrayList<String>();
    public static String kick_message;
    public static Long kick_delay;
    public static String ping_message;
    public static Integer max_ping;
    public static String antiproxy_message;
    public static Integer antiproxy;

    public static String sucsess;
    public static String Antibot_title_message;
    public static String Antibot_subtitle_message;
    BukkitTask parsingTask;

    @Override
    public void onEnable() {
        // Commands
        getCommand("ping").setExecutor(new PingCMD());
        getCommand("bots_kick").setExecutor(new BotsKickCMD());
        getCommand("antibot").setExecutor((CommandExecutor)this);

        // Events
        Bukkit.getPluginManager().registerEvents(new AntiActions(), this);
        Bukkit.getPluginManager().registerEvents(new OnMessage(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayer(), this);

        // Tasks
        parsingTask = new ParsingProxy().runTaskTimer(this, 100L, 20L * 180);

        this.saveDefaultConfig();
        FileConfiguration configload = this.getConfig();
        kick_message = configload.getString("kick_message");
        kick_delay = 20L * configload.getInt("kick_delay");
        ping_message = configload.getString("ping_message");
        max_ping = configload.getInt("max_ping");
        antiproxy_message = configload.getString("antiproxy_message");
        antiproxy = configload.getInt("antiproxy");
        sucsess = configload.getString("sucsess");
        Antibot_title_message = configload.getString("Antibot_title_message");
        Antibot_subtitle_message = configload.getString("Antibot_subtitle_message");
    }

    @Override
    public void onDisable() {
        parsingTask.cancel();
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("antibot")) {
            sender.sendMessage(ChatColor.WHITE+"Coded by "+ChatColor.GREEN+"Swenly_YT");
            sender.sendMessage(ChatColor.GREEN+"Discord: "+ChatColor.WHITE+"Swenly_YT#8002");
            sender.sendMessage(ChatColor.GREEN+"GitHub: "+ChatColor.WHITE+"github.com/SwenlyYT");
            }
        return false;
        }
}
