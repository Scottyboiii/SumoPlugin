package com.gmail.scot.sumoplugin.SQL;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationSQL {

    private final SQLHandler sqlHandler;

    public LocationSQL(SQLHandler sqlHandler) {
        this.sqlHandler = sqlHandler;
    }

    public void insertLocation(LocationData locationData) {
        String sqlString = "INSERT INTO savedlocations(name, location) VALUES (?, ?);";
        PreparedStatement statement = null;
        Connection connection = null;
        Location location = locationData.getLocation();
        try {
            connection = this.sqlHandler.getConnection();
            statement = this.sqlHandler.getConnection().prepareStatement(sqlString);
            statement.setString(1, locationData.getName());
            statement.setString(2, location.getX() + "-" + location.getBlockY() + "-" + location.getBlockZ() + "-" + location.getWorld().getName());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.sqlHandler.closeAll(statement, null, connection);
        }
    }

    public void removeLocation(String specificName) {
        String sqlString = "DELETE FROM savedlocations WHERE name = ?;";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = this.sqlHandler.getConnection();
            statement = this.sqlHandler.getConnection().prepareStatement(sqlString);
            statement.setString(1, specificName);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.sqlHandler.closeAll(statement, null, connection);
        }
    }

    public boolean locationExist(String specificName) {
        String sqlString = "SELECT name FROM savedlocations WHERE NAME = ?;";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = this.sqlHandler.getConnection();
            statement = this.sqlHandler.getConnection().prepareStatement(sqlString);
            statement.setString(1, specificName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.sqlHandler.closeAll(statement, null, connection);
        }
        return false;
    }
    /*
    public List<Location> loadLocations() {

    }
     */

    public Location locationTeleport(String specificName) {
        String sqlString = "SELECT name, location FROM savedlocations WHERE NAME = ?;";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = this.sqlHandler.getConnection();
            statement = this.sqlHandler.getConnection().prepareStatement(sqlString);
            statement.setString(1, specificName);
            ResultSet rs = statement.executeQuery();
            rs.next();
            String[] locationArgs = rs.getString("location").split("-");
            double x = Double.parseDouble(locationArgs[0]);
            double y = Double.parseDouble(locationArgs[1]);
            double z = Double.parseDouble(locationArgs[2]);
            World world = Bukkit.getWorld(locationArgs[3]);
            return new Location(world, x, y, z);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.sqlHandler.closeAll(statement, null, connection);
        }
        return null;
    }

    public List<LocationData> getLocations() {
        String sqlString = "SELECT * FROM savedlocations;";
        PreparedStatement statement = null;
        Connection connection = null;
        List<LocationData> returnList = new ArrayList<>();
        try {
            connection = this.sqlHandler.getConnection();
            statement = this.sqlHandler.getConnection().prepareStatement(sqlString);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String[] locationArgs = rs.getString("location").split("-");
                double x = Double.parseDouble(locationArgs[0]);
                double y = Double.parseDouble(locationArgs[1]);
                double z = Double.parseDouble(locationArgs[2]);
                World world = Bukkit.getWorld(locationArgs[3]);
                Location loc = new Location(world, x, y, z);
                returnList.add(new LocationData(name, loc));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.sqlHandler.closeAll(statement, null, connection);
        }
        return returnList;
    }

}
