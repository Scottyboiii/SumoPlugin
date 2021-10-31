package com.gmail.scot.sumoplugin.Commands.removecommand;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveSpawnCommand implements SubCommand {

    private LocationSQL locationSQL;

    public RemoveSpawnCommand(LocationSQL locationSQL) {
        this.locationSQL = locationSQL;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        this.locationSQL.removeLocation(LocationType.JOIN.getDatabaseName());
        p.sendMessage(Language.Message_Removed_Spawn_Location);
    }

    @Override
    public void help(CommandSender sender) {

    }
}
