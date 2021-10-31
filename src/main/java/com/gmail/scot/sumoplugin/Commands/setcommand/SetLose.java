package com.gmail.scot.sumoplugin.Commands.setcommand;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.Managers.SavedLocManager;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLose implements SubCommand {

    private final LocationSQL locationSQL;
    private final SavedLocManager savedLocManager;

    public SetLose(LocationSQL locationSQL, SavedLocManager savedLocManager) {
        this.locationSQL = locationSQL;
        this.savedLocManager = savedLocManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        if (this.locationSQL.locationExist(LocationType.LOST.getDatabaseName())) {
            p.sendMessage(Language.Message_Set_Lose_Exist);
            return;
        }
        this.savedLocManager.saveLocation(LocationType.LOST.getDatabaseName(), p.getLocation());
        p.sendMessage(Language.Message_Set_Lose_Completed);
    }

    @Override
    public void help(CommandSender sender) {

    }
}
