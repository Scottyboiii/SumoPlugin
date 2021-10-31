package com.gmail.scot.sumoplugin.Commands;

import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Managers.SumoManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements SubCommand {

    private final SumoManager sumoManager;

    public JoinCommand(SumoManager sumoManager) {
        this.sumoManager = sumoManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        this.sumoManager.join(p);
    }

    @Override
    public void help(CommandSender sender) {

    }
}
