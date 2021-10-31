package com.gmail.scot.sumoplugin.Interfaces;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CreateCommand implements CommandExecutor {
    public abstract void runCommand(CommandSender sender, Command cmd, String s, String[] args);

    public abstract void printHelp(CommandSender sender);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        runCommand(commandSender, command, s, strings);
        return true;
    }
}

