package com.gmail.scot.sumoplugin;

import com.gmail.scot.sumoplugin.Commands.SumoCommand;
import com.gmail.scot.sumoplugin.Listeners.SumoListener;
import com.gmail.scot.sumoplugin.Managers.LocationManager;
import com.gmail.scot.sumoplugin.Managers.SaveSettings;
import com.gmail.scot.sumoplugin.Managers.SavedLocManager;
import com.gmail.scot.sumoplugin.Managers.SumoManager;
import com.gmail.scot.sumoplugin.SQL.LocationSQL;
import com.gmail.scot.sumoplugin.SQL.SQLHandler;
import com.gmail.scot.sumoplugin.Utils.SelectionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SumoPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Pluginnet loadet");
        saveDefaultConfig();

        SaveSettings saveSettings = new SaveSettings();
        LocationSQL locationSQL = new LocationSQL(new SQLHandler(getConfig()));
        SavedLocManager savedLocManager = new SavedLocManager(locationSQL);
        LocationManager locationManager = new LocationManager();
        SelectionManager selectionManager = new SelectionManager(locationManager, locationSQL);
        SumoManager sumoManager = new SumoManager(locationManager, selectionManager, this, locationSQL, saveSettings);


        SumoListener sumoListener = new SumoListener(selectionManager, locationSQL, sumoManager);
        Bukkit.getServer().getPluginManager().registerEvents(sumoListener, this);

        SumoCommand sumoCommand = new SumoCommand(selectionManager, sumoManager, savedLocManager, locationSQL, this);
        getCommand("sumo").setExecutor(sumoCommand);

    }

    @Override
    public void onDisable() {

    }

}
