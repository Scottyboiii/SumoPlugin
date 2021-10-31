package com.gmail.scot.sumoplugin.Listeners;

import com.gmail.scot.sumoplugin.Enum.LocationType;
import com.gmail.scot.sumoplugin.Language;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import com.gmail.scot.sumoplugin.Utils.SelectionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SumoListener implements Listener {

    private final SelectionManager selectionManager;
    private final LocationSQL locationSQL;

    public SumoListener(SelectionManager selectionManager, LocationSQL locationSQL) {
        this.selectionManager = selectionManager;
        this.locationSQL = locationSQL;
    }

    @EventHandler
    public void moveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        this.selectionManager.checkPlayerInRegion(p, "dead", LocationType.LOST, Language.Message_Lost);
    }

}
