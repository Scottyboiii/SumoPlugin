package com.gmail.scot.sumoplugin.Commands;

import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Managers.SumoManager;
import org.bukkit.command.CommandSender;

public class KickCommand implements SubCommand {

    private final SumoManager sumoManager;

    public KickCommand(SumoManager sumoManager) {
        this.sumoManager = sumoManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        this.sumoManager.kick(args[0]);
    }

    @Override
    public void help(CommandSender sender) {

    }
}
