package ru.swenly.swenlyantibot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import ru.swenly.swenlyantibot.SwenlyAntiBot;

public class AntiActions implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSpawHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDropEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        for (String captcha : SwenlyAntiBot.captchas) {
            if (captcha.contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }
}
