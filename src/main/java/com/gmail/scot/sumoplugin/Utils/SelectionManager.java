package com.gmail.scot.sumoplugin.Utils;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.Managers.LocationManager;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SelectionManager {

    private ArrayList<Player> entered = new ArrayList<>();
    private ArrayList<Player> left = new ArrayList<>();
    private final LocationManager locationManager;
    private final LocationSQL locationSQL;

    private List<Location> playerLocList = new ArrayList<>();

    public SelectionManager(LocationManager locationManager, LocationSQL locationSQL) {
        this.locationManager = locationManager;
        this.locationSQL = locationSQL;
    }

    public Selection getPlayerSelection(Player p) {
        return getWorldEdit().getSelection(p);
    }

    public void createRegion(Player p, String name) {
        Selection sel = getPlayerSelection(p);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name,
                new BlockVector(sel.getNativeMinimumPoint()),
                new BlockVector(sel.getNativeMaximumPoint()));
        this.getWorldGuard().getRegionManager(p.getWorld()).addRegion(region);
        p.sendMessage("Du har oprettet regionen: " + region.getId());
    }

    public void getLocation(Player p) {
        p.getLocation().getBlock().getLocation();
    }

    public void checkPlayerInRegion(Player p, String regionName, LocationType locationType, String pMessage) {
        if (checkPlayerInRegion(p.getLocation(), regionName)) {
            if (!this.locationSQL.locationExist(LocationType.LOST.getDatabaseName())) {
                return;
            }
            p.teleport(this.locationSQL.locationTeleport(locationType.getDatabaseName()));
            p.sendMessage(pMessage);
        }
    }

    public boolean checkPlayerInRegion(Location loc, String regionName) {
        if (regionName == null) {
            return true;
        }
        ApplicableRegionSet set = getWGSet(loc);
        if (set == null) {
            return false;
        }
        for (ProtectedRegion region : set) {
            if (region.getId().equalsIgnoreCase(regionName)) {
                return true;
            }
        }
        return false;
    }

    private ApplicableRegionSet getWGSet(Location loc) {
        WorldGuardPlugin wg = getWorldGuard();
        if (wg == null) {
            return null;
        }
        RegionManager rm = wg.getRegionManager(loc.getWorld());
        if (rm == null) {
            return null;
        }
        return rm.getApplicableRegions(com.sk89q.worldguard.bukkit.BukkitUtil.toVector(loc));
    }

    public void removeRegion(Player p, String name) {
        RegionManager removeRegion = getWorldGuard().getRegionManager(p.getWorld());
        ProtectedRegion region = getWorldGuard().getRegionManager(p.getWorld()).getRegion(name.toLowerCase());
        if (removeRegion == null) {
            return;
        }
        if (region == null) {
            p.sendMessage(Language.Message_Region_Does_Not_Exist.replace("%regionName%", name));
            return;
        }

        removeRegion.removeRegion(name);
        p.sendMessage(Language.Message_Region_Deleted.replace("%regionName%", name.toLowerCase()));
    }

    public WorldEditPlugin getWorldEdit() {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (p instanceof WorldEditPlugin) {
            return (WorldEditPlugin) p;
        } else {
            return null;
        }
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (p instanceof WorldGuardPlugin) {
            return (WorldGuardPlugin) p;
        } else {
            return null;
        }
    }

}
