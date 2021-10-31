package com.gmail.scot.sumoplugin.Commands.setcommand;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.Managers.SavedLocManager;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements SubCommand {

    private final LocationSQL locationSQL;
    private final SavedLocManager savedLocManager;

    public SetSpawn(LocationSQL locationSQL, SavedLocManager savedLocManager) {
        this.locationSQL = locationSQL;
        this.savedLocManager = savedLocManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        if (this.locationSQL.locationExist(LocationType.JOIN.getDatabaseName())) {
            p.sendMessage(Language.Message_Set_Spawn_Exist);
            return;
        }
        this.savedLocManager.saveLocation(LocationType.JOIN.getDatabaseName(), p.getLocation());
        p.sendMessage(Language.Message_Set_Spawn_Completed);
    }

    @Override
    public void help(CommandSender sender) {

    }
}
