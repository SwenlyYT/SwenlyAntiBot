package ru.swenly.swenlyantibot.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.swenly.swenlyantibot.not_my.MinecraftPing;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PingCMD implements CommandExecutor {
    private static Class<?> craftPlayer;
    private static Method playerGetPing;
    private static Field pingField;
    private static Method handle;
    int ping;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!sender.hasPermission("swenlyantibot.ping")) {
            sender.sendMessage(ChatColor.RED + "Ошибка!" + ChatColor.WHITE + " У вас недостаточно прав!");
            return true;
        }

        if (args.length > 0) {
            player = Bukkit.getPlayer(args[0]);
        }

        if (player != null) {
            try {
                sender.sendMessage("Ping: " + MinecraftPing.getReflectionPing(Bukkit.getPlayer(player.getName())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
