package com.gmail.scot.sumoplugin.Listeners;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.Managers.SumoManager;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import com.gmail.scot.sumoplugin.Utils.SelectionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SumoListener implements Listener {

    private final SelectionManager selectionManager;
    private final LocationSQL locationSQL;
    private final SumoManager sumoManager;

    public SumoListener(SelectionManager selectionManager, LocationSQL locationSQL, SumoManager sumoManager) {
        this.selectionManager = selectionManager;
        this.locationSQL = locationSQL;
        this.sumoManager = sumoManager;
    }

    @EventHandler
    public void moveEvent(PlayerMoveEvent e) {
        /*
        Player p = e.getPlayer();
        if (!this.sumoManager.getJoinedPlayers().contains(p.getUniqueId())) {
            return;
        }
        if (this.selectionManager.checkPlayerInRegion(p.getLocation(), "dead")) {
            this.sumoManager.kick(p.getName());
        }
                 */
    }


}
