package ru.swenly.swenlyantibot.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import ru.swenly.swenlyantibot.events.OnMessage;

public class ClearMessages extends BukkitRunnable {
    @Override
    public void run() {
        OnMessage.messages.clear();
    }
}
