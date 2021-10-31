package com.gmail.scot.sumoplugin.SQL;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public class SQLHandler {

    private final String url;
    private final String username;
    private final String password;

    public SQLHandler(String username, String password, String ip, int port, String database) {
        try {
            new com.mysql.cj.jdbc.Driver();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
        this.username = username;
        this.password = password;
    }

    public SQLHandler(FileConfiguration config) {
        this(config.getString("sql.username"),
                config.getString("sql.password"),
                config.getString("sql.ip"),
                config.getInt("sql.port"),
                config.getString("sql.database"));
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void closeAll(Statement stmt, ResultSet resultSet, Connection con) {
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException ignored) {
        }
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException ignored) {
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException ignored) {
        }
    }
}

/*
    public void insertPerson(String name, int age) {
        String sqlString = "INSERT INTO person(name, age) VALUES(?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = SqlUtil.getConnection(url, username, password);
            statement = SqlUtil.getConnection(url, username, password).prepareStatement(sqlString);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            SqlUtil.closeAll(statement, null, connection);
        }

    }

    public String selectPersonName(int age) {
        String sqlString = "SELECT name FROM person WHERE age = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = SqlUtil.getConnection(url, username, password);
            statement = connection.prepareStatement(sqlString);
            statement.setInt(1, age);
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            SqlUtil.closeAll(statement, rs, connection);
        }
        retur
        n null; //this only happens if an error is thrown
            }
 */
