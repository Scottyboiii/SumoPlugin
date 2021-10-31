package com.gmail.scot.sumoplugin.SQL;

import org.bukkit.Location;

public class LocationData {

    private final String name;
    private final Location location;

    public LocationData(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
