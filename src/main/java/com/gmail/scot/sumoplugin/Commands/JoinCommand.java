package com.gmail.scot.sumoplugin.Commands;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.Managers.SumoManager;
import com.gmail.scot.sumoplugin.SumoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements SubCommand {

    private final SumoManager sumoManager;
    private final SumoPlugin sumoPlugin;

    public JoinCommand(SumoManager sumoManager, SumoPlugin sumoPlugin) {
        this.sumoManager = sumoManager;
        this.sumoPlugin = sumoPlugin;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player p = (Player) sender;
        this.sumoManager.join(p, LocationType.JOIN.getDatabaseName());
        if (this.sumoManager.getJoinedPlayers().size() == 2) {
            this.sumoManager.startRunnable(10);
        }
    }

    @Override
    public void help(CommandSender sender) {

    }
}
