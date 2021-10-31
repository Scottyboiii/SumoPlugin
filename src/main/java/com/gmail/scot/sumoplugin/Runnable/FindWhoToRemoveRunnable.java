package com.gmail.scot.sumoplugin.Runnable;

import org.bukkit.Bukkit;

import java.util.function.Supplier;

public class FindWhoToRemoveRunnable implements Runnable {

    private final Supplier<Boolean> supplier;
    private int id;

    public FindWhoToRemoveRunnable(Supplier<Boolean> supplier) {
        this.supplier = supplier;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        if (supplier.get()) {
            cancel();
        }
    }
}
