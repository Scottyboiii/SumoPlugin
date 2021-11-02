package com.gmail.scot.sumoplugin.Commands.teleportcommands;

import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TeleportCommand implements SubCommand {

    private final LocationSQL locationSQL;

    private final Map<String, SubCommand> commandMap = new HashMap<>();

    public TeleportCommand(LocationSQL locationSQL) {
        this.locationSQL = locationSQL;
        setupCommandMap(new TeleportSpawn(this.locationSQL),
                new TeleportLose(locationSQL), new TeleportPos1(locationSQL),
                new TeleportPos2(locationSQL), new TeleportWinner(locationSQL));
    }

    private void setupCommandMap(TeleportSpawn teleportSpawn, TeleportLose teleportLose, TeleportPos1 teleportPos1, TeleportPos2 teleportPos2, TeleportWinner teleportWinner) {
        this.commandMap.put("spawn", teleportSpawn);
        this.commandMap.put("lose", teleportLose);
        this.commandMap.put("pos1", teleportPos1);
        this.commandMap.put("pos2", teleportPos2);
        this.commandMap.put("winner", teleportWinner);
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
