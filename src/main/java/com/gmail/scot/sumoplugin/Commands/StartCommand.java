package com.gmail.scot.sumoplugin.Commands;

import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Managers.SumoManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements SubCommand {

    private final SumoManager sumoManager;

    public StartCommand(SumoManager sumoManager) {
        this.sumoManager = sumoManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        this.sumoManager.start(p);

    }

    @Override
    public void help(CommandSender sender) {

    }
}
