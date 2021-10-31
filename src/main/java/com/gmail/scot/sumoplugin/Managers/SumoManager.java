package com.gmail.scot.sumoplugin.Managers;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.Runnable.FindWhoToRemoveRunnable;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class SumoManager {

    private final LocationManager locationManager;
    private final LocationSQL locationSQL;
    private boolean started = false;
    private final SaveSettings saveSettings;
    private WorldGuardPlugin worldGuard;
    private WorldEditPlugin worldEdit;
    private List<UUID> joinedPlayers = new ArrayList<>();
    private Set<UUID> playersPlaying = new HashSet<>();

    public SumoManager(LocationManager locationManager, LocationSQL locationSQL, SaveSettings saveSettings) {
        this.locationManager = locationManager;
        this.locationSQL = locationSQL;
        this.saveSettings = saveSettings;
    }

    public void start(Player p) {
        if (this.started) {
            p.sendMessage(Language.Message_Sumo_Already_Started);
        } else {
            this.started = true;
            startBroadCast(p);
        }
    }

    public void join(Player p) {
        if (this.joinedPlayers.contains(p.getUniqueId())) {
            p.sendMessage(Language.Message_Already_Joined);
            return;
        }
        if (this.locationSQL.locationExist(LocationType.JOIN.getDatabaseName())) {
            if (this.locationSQL.getLocations().get(0).getName().equalsIgnoreCase(LocationType.JOIN.getDatabaseName())) {
                Location location = this.locationSQL.getLocations().get(0).getLocation();
                p.teleport(location);
                Bukkit.broadcast(Language.Message_Player_Joined.replace("%player%", p.getName()), Language.Permission_Use);
                this.joinedPlayers.add(p.getUniqueId());
            }
        }
    }

    public void kick(String name) {
        Player p = Bukkit.getPlayer(name);
        if (!this.joinedPlayers.contains(p.getUniqueId())) {
            p.sendMessage(Language.Message_Player_Not_Playing);
        } else {
            this.joinedPlayers.remove(p.getUniqueId());
            p.teleport(this.locationSQL.locationTeleport(LocationType.JOIN.getDatabaseName()));
            p.sendMessage(Language.Message_Lost);
        }
    }

    private void startBroadCast(Player p) {
        Bukkit.broadcast("", Language.Permission_Use);
        Bukkit.broadcast(Language.Message_Sumo_Is_Now_Started, Language.Permission_Use);
        TextComponent textComponent = new TextComponent(Language.Message_Click_To_Join);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sumo join"));
        this.saveSettings.setSaveCommand(true);
        p.spigot().sendMessage(textComponent);
        Bukkit.broadcast("", Language.Permission_Use);
    }

    /*
    public void fallenOff() {
        if (this.started) {
            FindWhoToRemoveRunnable whotoRemove = new FindWhoToRemoveRunnable(() -> {
                Player p = findWhoToRemove();
            })
        }
    }

    public void findWhoToRemove() {

    }

     */

    public void disqualified() {
        if (this.started) {
            FindWhoToRemoveRunnable whoToRemove = new FindWhoToRemoveRunnable(() -> {
                Player p;
                return true;
            });
        }

    }

}
