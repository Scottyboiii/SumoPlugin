package com.gmail.scot.sumoplugin.Commands;

import com.gmail.scot.sumoplugin.Commands.removecommand.RemoveCommand;
import com.gmail.scot.sumoplugin.Commands.setcommand.SetCommand;
import com.gmail.scot.sumoplugin.Commands.teleportcommands.TeleportCommand;
import com.gmail.scot.sumoplugin.Interfaces.CreateCommand;
import com.gmail.scot.sumoplugin.Interfaces.SubCommand;
import com.gmail.scot.sumoplugin.Managers.SavedLocManager;
import com.gmail.scot.sumoplugin.Managers.SumoManager;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import com.gmail.scot.sumoplugin.SumoPlugin;
import com.gmail.scot.sumoplugin.Utils.SelectionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SumoCommand extends CreateCommand {

    private final SelectionManager selection;
    private final SumoManager sumoManager;
    private final SavedLocManager savedLocManager;
    private final LocationSQL locationSQL;
    private final SumoPlugin sumoPlugin;
    private final Map<String, SubCommand> commandMap = new HashMap<>();

    public SumoCommand(SelectionManager selection, SumoManager sumoManager, SavedLocManager savedLocManager, LocationSQL locationSQL, SumoPlugin sumoPlugin) {
        this.selection = selection;
        this.sumoManager = sumoManager;
        this.savedLocManager = savedLocManager;
        this.locationSQL = locationSQL;
        this.sumoPlugin = sumoPlugin;
        setupCommandMap(new JoinCommand(sumoManager, this.sumoPlugin),
                new StartCommand(sumoManager),
                new KickCommand(sumoManager),
                new SetCommand(locationSQL, savedLocManager),
                new TeleportCommand(locationSQL),
                new RemoveCommand(locationSQL));
    }

    private void setupCommandMap(JoinCommand joinCommand, StartCommand startCommand, KickCommand kickCommand,
                                 SetCommand setCommand, TeleportCommand teleportCommand, RemoveCommand removeCommand) {
        this.commandMap.put("join", joinCommand);
        this.commandMap.put("start", startCommand);
        this.commandMap.put("kick", kickCommand);
        this.commandMap.put("set", setCommand);
        this.commandMap.put("tp", teleportCommand);
        this.commandMap.put("remove", removeCommand);
    }

    public void runCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (commandMap.containsKey(args[0])) {
            commandMap.get(args[0]).run(sender, Arrays.copyOfRange(args, 1, args.length));
        } else {
            for (SubCommand value : commandMap.values()) {
                value.help(sender);
            }
        }
    }

    public void printHelp(CommandSender sender) {
        sender.sendMessage("§bCommands:");
        sender.sendMessage("§7/sumo start");
        sender.sendMessage("§7/sumo join");
        sender.sendMessage("§7/sumo kick");
        sender.sendMessage("§7/sumo set (location)");
        sender.sendMessage("§7/sump remove (location)");
        sender.sendMessage("§7/sumo tp (location)");
    }

}
