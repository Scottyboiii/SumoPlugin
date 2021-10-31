package com.gmail.scot.sumoplugin.Managers;

import com.gmail.scot.sumoplugin.SQL.LocationData;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SavedLocManager {

    private final LocationSQL locationSQL;
    private final List<LocationData> locationCache;

    public SavedLocManager(LocationSQL locationSQL) {
        this.locationSQL = locationSQL;
        this.locationCache = locationSQL.getLocations();
    }

    public List<LocationData> getAllLocations() {
        return locationCache;
    }

    public void saveLocation(String name, Location location) {
        CompletableFuture.runAsync(() -> {
            LocationData locationData = new LocationData(name, location);
            this.locationSQL.insertLocation(locationData);
            locationCache.add(locationData);
            System.out.println("Data er blevet tilføjet!");
        });
    }

}
