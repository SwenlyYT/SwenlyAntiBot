package ru.swenly.swenlyantibot.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BotsKickCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("swenlyantibot.bots_kick")) {
            sender.sendMessage(ChatColor.RED + "Ошибка!" + ChatColor.WHITE + " У вас недостаточно прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Ошибка!" + ChatColor.WHITE + " Укажите текст для проверки ника игроков на бота!");
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Ошибка!" + ChatColor.WHITE + " Передано слишком много аргументов!");
            return true;
        }

        String text = args[0];
        for (Player player : sender.getServer().getOnlinePlayers()) {
            if (player.getName().contains(text)) {
                player.kickPlayer(ChatColor.RED + "Защита!" + ChatColor.WHITE + "\nВас кикнул модератор, так как ваш ник содержит ник бота!");
                sender.getServer().broadcastMessage("Игрока " + ChatColor.YELLOW + player.getName() + " кикнули, так как его ник содержит ник бота!");
            }
        }

        return true;
    }
}
