package com.gmail.scot.sumoplugin.Commands.teleportcommands;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportLose implements SubCommand {

    private final LocationSQL locationSQL;

    public TeleportLose(LocationSQL locationSQL) {
        this.locationSQL = locationSQL;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        if (this.locationSQL.locationExist(LocationType.LOST.getDatabaseName())) {
            return;
        }
        if (this.locationSQL.getLocations().size() < 1) {
            p.sendMessage(Language.Message_Lost_Most_Be_Set);
            return;
        }
        Location location = this.locationSQL.locationTeleport(LocationType.LOST.getDatabaseName());
        p.teleport(location);
        p.sendMessage(Language.Message_Player_Tp_Lose);
    }

    @Override
    public void help(CommandSender sender) {

    }
}
