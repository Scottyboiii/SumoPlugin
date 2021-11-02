package com.gmail.scot.sumoplugin.Runnable;

import com.gmail.scot.sumoplugin.Managers.SumoManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Jukebox;

public class CheckIfPlayingRunnable implements Runnable {

    private final SumoManager sumoManager;
    private final Runnable runnable;
    private int id;

    public CheckIfPlayingRunnable(Runnable runnable, SumoManager sumoManager) {
        this.runnable = runnable;
        this.sumoManager = sumoManager;
    }

    @Override
    public void run() {
        int size = this.sumoManager.getJoinedPlayers().size();
        if (size == 1) {
            runnable.run();
            cancel();
        }
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(id);
    }

    public void setId(int id) {
        this.id = id;
    }
}