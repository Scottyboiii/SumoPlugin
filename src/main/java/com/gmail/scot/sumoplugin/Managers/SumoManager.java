package com.gmail.scot.sumoplugin.Managers;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.Runnable.FindWhoToRemoveRunnable;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import com.gmail.scot.sumoplugin.SumoPlugin;
import com.gmail.scot.sumoplugin.Utils.Runnable;
import com.gmail.scot.sumoplugin.Utils.SelectionManager;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class SumoManager {

    private final LocationManager locationManager;
    private final SelectionManager selectionManager;
    private final SumoPlugin sumoPlugin;
    private final LocationSQL locationSQL;
    private boolean started = false;
    private static final Random r = new Random();
    private final SaveSettings saveSettings;
    private int findWhoToRemoveTaskID = -1;
    private WorldGuardPlugin worldGuard;
    private WorldEditPlugin worldEdit;
    private final List<UUID> joinedPlayers = new ArrayList<>();
    private final Map<UUID, String> playersPlaying = new HashMap<>();
    private final List<UUID> lostPlayers = new ArrayList<>();

    public SumoManager(LocationManager locationManager, SelectionManager selectionManager, SumoPlugin sumoPlugin, LocationSQL locationSQL, SaveSettings saveSettings) {
        this.locationManager = locationManager;
        this.selectionManager = selectionManager;
        this.sumoPlugin = sumoPlugin;
        this.locationSQL = locationSQL;
        this.saveSettings = saveSettings;
    }

    public void start(Player p) {
        if (this.started) {
            p.sendMessage(Language.Message_Sumo_Already_Started);
        } else {
            this.started = true;
            startBroadCast();
        }
    }

    public void join(Player p, String locationType) {
        if (!this.started) {
            p.sendMessage(Language.Message_Sumo_Not_Started);
            return;
        }
        if (this.lostPlayers.contains(p.getUniqueId())) {
            p.sendMessage(Language.Message_Lost);
            return;
        }
        if (this.joinedPlayers.contains(p.getUniqueId())) {
            p.sendMessage(Language.Message_Already_Joined);
            return;
        }
        if (this.locationSQL.locationExist(locationType)) {
            if (this.locationSQL.locationExist(locationType)) {
                Location location = this.locationSQL.locationTeleport(locationType);
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
            this.lostPlayers.add(p.getUniqueId());
            p.teleport(this.locationSQL.locationTeleport(LocationType.JOIN.getDatabaseName()));
            p.sendMessage(Language.Message_Lost);
        }
    }

    private void startBroadCast() {
        Bukkit.broadcast("", Language.Permission_Use);
        Bukkit.broadcast(Language.Message_Sumo_Is_Now_Started, Language.Permission_Use);
        TextComponent textComponent = new TextComponent(Language.Message_Click_To_Join);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sumo join"));
        this.saveSettings.setSaveCommand(true);
        Bukkit.spigot().broadcast(textComponent);
        Bukkit.broadcast("", Language.Permission_Use);
    }

    public void startRunnable(int countdownFrom) {
        new BukkitRunnable() {
            int countdown = countdownFrom;

            @Override
            public void run() {

                if (countdown == 0) {
                    Bukkit.broadcast("§aSumo-Eventet er nu startet!", Language.Permission_Use);
                    started = true;
                    startBattle();
                    cancel();
                } else {
                    if (countdown != 1) {
                        Bukkit.broadcast("§aSumo-Eventet starter om: §6" + countdown + "§a sekunder", Language.Permission_Use);
                    } else {
                        Bukkit.broadcast("§aSumo-Eventet starter om: §6" + countdown + "§a sekund", Language.Permission_Use);
                    }
                    countdown--;
                }

            }
        }.runTaskTimer(sumoPlugin, 0, 20);
    }

    public void startBattle() {
        Player p1 = addRandomPlayer();
        Player p2 = addRandomPlayer();
        fallenOffRunnable("dead");

        p1.teleport(this.locationSQL.locationTeleport(LocationType.POS1.getDatabaseName()));
        p2.teleport(this.locationSQL.locationTeleport(LocationType.POS2.getDatabaseName()));
        Bukkit.broadcast("§aSpillere: §6" + p1.getName() + "§a, §6" + p2.getName(), Language.Permission_Use);
    }

    private Player addRandomPlayer() {
        UUID uuid = joinedPlayers.get(r.nextInt(joinedPlayers.size()));
        Player p = Bukkit.getPlayer(uuid);
        getJoinedPlayers().remove(uuid);
        getPlayersPlaying().put(uuid, p.getName());
        return p;
    }

    private void getWinner() {
        String winner = getPlayersPlaying().entrySet().iterator().next().getValue();
        Player pWinner = Bukkit.getPlayer(winner);
        pWinner.teleport(this.locationSQL.locationTeleport(LocationType.WINNER.getDatabaseName()));
        Bukkit.broadcast(Language.Broadcast_Message_Winner.replace("%player%", pWinner.getName()), Language.Permission_Use);
        getPlayersPlaying().clear();
    }

    public void fallenOffRunnable(String regionName) {
        if (this.started) {
            FindWhoToRemoveRunnable whoToRemove = new FindWhoToRemoveRunnable(() -> {
                Player p = findWhoToRemove(regionName);
                if (p == null) {
                    return false;
                }
                Bukkit.broadcast(Language.Broadcast_Message_Lost.replace("%player%", p.getName()), Language.Permission_Use);
                p.teleport(this.locationSQL.locationTeleport(LocationType.LOST.getDatabaseName()));
                getPlayersPlaying().remove(p.getUniqueId(), p.getName());
                if (getPlayersPlaying().size() == 1) {
                    getWinner();
                }
                findWhoToRemoveTaskID = -1;
                return true;
            });
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(this.sumoPlugin, whoToRemove, 0, 10);
            findWhoToRemoveTaskID = bukkitTask.getTaskId();
            whoToRemove.setId(bukkitTask.getTaskId());
        }
    }

    private Player findWhoToRemove(String regionName) {
        for (UUID uuid : this.playersPlaying.keySet()) {
            Player p = Bukkit.getPlayer(uuid);
            if (this.selectionManager.checkPlayerInRegion(p.getLocation(), regionName)) {
                return Bukkit.getPlayer(uuid);
            }
        }
        return null;
    }

    public List<UUID> getJoinedPlayers() {
        return joinedPlayers;
    }

    public Map<UUID, String> getPlayersPlaying() {
        return playersPlaying;
    }
}
