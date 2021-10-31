package com.gmail.scot.sumoplugin.Managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.util.List;

public class LocationManager implements Listener {

    private Location joinLocation;
    private Location winnerLocation;
    private Location loseLocation;

    public void load(FileConfiguration config) {
        for (String key : config.getConfigurationSection("locations_settings").getKeys(false)) {
            String path = "locations_settings." + key + ".";
            List<String> locationsStrings = config.getStringList(path + "locations:");
        }
        joinLocation = parseLocation(config.getString("join_location").replace(" ", "").split(","));
        winnerLocation = parseLocation(config.getString("winner_location").replace(" ", "").split(","));
        loseLocation = parseLocation(config.getString("lose_location").replace(" ", "").split(","));
    }

    private Location parseLocation(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double z = Double.parseDouble(args[2]);
        World world = Bukkit.getWorld(args[3]);
        return new Location(world, x, y, z);
    }

    public Location getJoinLocation() {
        return joinLocation;
    }

    public Location getWinnerLocation() {
        return winnerLocation;
    }

    public Location getLoseLocation() {
        return loseLocation;
    }
}
