package com.gmail.scot.sumoplugin.Interfaces;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    void run(CommandSender sender, String[] args);

    void help(CommandSender sender);

}
