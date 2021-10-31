package com.gmail.scot.sumoplugin.Commands.setcommand;

import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Managers.SavedLocManager;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SetCommand implements SubCommand {

    private final LocationSQL locationSQL;
    private SavedLocManager savedLocManager;
    private final Map<String, SubCommand> commandMap = new HashMap<>();

    public SetCommand(LocationSQL locationSQL, SavedLocManager savedLocManager) {
        this.locationSQL = locationSQL;
        this.savedLocManager = savedLocManager;
        setupCommandMap(new SetSpawn(locationSQL, savedLocManager), new SetLose(locationSQL, savedLocManager));
    }

    private void setupCommandMap(SetSpawn setSpawn, SetLose setLose) {
        this.commandMap.put("spawn", setSpawn);
        this.commandMap.put("lose", setLose);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (commandMap.containsKey(args[0])) {
            commandMap.get(args[0]).run(sender, Arrays.copyOfRange(args, 1, args.length));
        } else {
            for (SubCommand value : commandMap.values()) {
                value.help(sender);
            }
        }
    }

    @Override
    public void help(CommandSender sender) {

    }
}
