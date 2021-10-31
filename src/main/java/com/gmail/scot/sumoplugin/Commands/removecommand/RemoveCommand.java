package com.gmail.scot.sumoplugin.Commands.removecommand;

import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RemoveCommand implements SubCommand {

    private final LocationSQL locationSQL;
    private final Map<String, SubCommand> commandMap = new HashMap<>();

    public RemoveCommand(LocationSQL locationSQL) {
        this.locationSQL = locationSQL;
        setupCommandMap(new RemoveSpawnCommand(locationSQL), new RemoveLoseCommand(locationSQL));
    }

    private void setupCommandMap(RemoveSpawnCommand removeSpawnCommand, RemoveLoseCommand removeLoseCommand) {
        this.commandMap.put("spawn", removeSpawnCommand);
        this.commandMap.put("lose", removeLoseCommand);
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
