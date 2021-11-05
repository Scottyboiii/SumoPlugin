package com.gmail.scot.sumoplugin.Utils;

import com.gmail.scot.sumoplugin.SumoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnable {

    private static SumoPlugin sumoPlugin;

    public static void countdownRunnable(int countdownFrom, String finalBroadCastMessage,
                                         String countdownMessage, String lastCountdownBroadCast, String MessagePermission, int delay, int period) {
        new BukkitRunnable() {
            int countdown = countdownFrom;

            @Override
            public void run() {
                if (countdown == 0) {
                    Bukkit.broadcast(finalBroadCastMessage, MessagePermission);
                } else {
                    if (countdown != 1) {
                        Bukkit.broadcast(countdownMessage + countdown + "§a sekunder", MessagePermission);
                    } else {
                        Bukkit.broadcast(lastCountdownBroadCast + countdown + "§a sekund", MessagePermission);
                    }
                    countdown--;
                }
            }
        }.runTaskTimer(sumoPlugin, delay, period);
    }

}
