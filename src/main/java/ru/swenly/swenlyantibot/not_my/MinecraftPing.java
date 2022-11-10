package ru.swenly.swenlyantibot.not_my;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class MinecraftPing {
    private static Method getHandleMethod;
    private static Field pingField;

    //this value updates every 40 Ticks => 2 Seconds. So you proparly want to add a scheduled task for it.
    public static int getReflectionPing(Player player) {
        try {
            if (getHandleMethod == null) {
                getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
                //disable java security check. This will speed it a little
                getHandleMethod.setAccessible(true);
            }

            Object entityPlayer = getHandleMethod.invoke(player);

            if (isModdedServer()) {
                //MCPC has a remapper, but it doesn't work if we get the class dynamic
                setMCPCPing(entityPlayer);
            } else {
                pingField = entityPlayer.getClass().getDeclaredField("ping");
                //disable java security check. This will speed it a little
                pingField.setAccessible(true);
            }

            //returns the found int value
            return pingField.getInt(entityPlayer);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static boolean isModdedServer() {
        //aggressive checking for modded servers
        List<String> versionNames = Arrays.asList(Bukkit.getVersion(), Bukkit.getName(), Bukkit.getServer().toString());
        return versionNames.stream().anyMatch((version) -> (version.contains("MCPC") || version.contains("Cauldron")));
    }

    private static void setMCPCPing(Object entityPlayer) {
        //this isn't secure, because it detects the ping variable by the order of the fields
        Class<?> lastType = null;
        Field lastIntField = null;
        for (Field field : entityPlayer.getClass().getDeclaredFields()) {
            if (field.getType() == Integer.TYPE
                    && Modifier.isPublic(field.getModifiers())
                    && lastType == Boolean.TYPE) {
                lastIntField = field;
                continue;
            }

            if (field.getType() == Boolean.TYPE && lastIntField != null) {
                pingField = lastIntField;
                //disable java security check. This will speed it a little
                pingField.setAccessible(true);
                break;
            }

            lastIntField = null;
            lastType = field.getType();
        }
    }
}