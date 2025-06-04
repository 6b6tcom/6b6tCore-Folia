package com.blbilink.blbilogin.modules;


import com.blbilink.blbilogin.load.LoadFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sqlite {

    private Connection connection;

    public static Sqlite getSqlite() {
        return LoadFunction.sqlite;
    }
    public Sqlite() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/blbiLogin/players.db");
            try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, username TEXT, password TEXT)")) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS playerdata (" +
                            "uuid TEXT PRIMARY KEY," +
                            "firstJoin INTEGER," +
                            "joinCount INTEGER," +
                            "logoutWorld TEXT," +
                            "logoutX REAL," +
                            "logoutY REAL," +
                            "logoutZ REAL," +
                            "logoutYaw REAL," +
                            "logoutPitch REAL," +
                            "blacklist INTEGER DEFAULT 0" +
                            ")")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean playerExists(String uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet results = statement.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void registerPlayer(String uuid, String username, String password) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO players (uuid, username, password) VALUES (?, ?, ?)")) {
            statement.setString(1, uuid);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPassword(String uuid, String password) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT password FROM players WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return results.getString("password").equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resetPassword(String uuid, String newPassword) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE players SET password = ? WHERE uuid = ?")) {
            statement.setString(1, newPassword);
            statement.setString(2, uuid);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void recordJoin(String uuid) {
        try (PreparedStatement select = connection.prepareStatement("SELECT joinCount FROM playerdata WHERE uuid = ?")) {
            select.setString(1, uuid);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("joinCount") + 1;
                try (PreparedStatement update = connection.prepareStatement("UPDATE playerdata SET joinCount = ? WHERE uuid = ?")) {
                    update.setInt(1, count);
                    update.setString(2, uuid);
                    update.executeUpdate();
                }
            } else {
                try (PreparedStatement insert = connection.prepareStatement("INSERT INTO playerdata (uuid, firstJoin, joinCount) VALUES (?, ?, 1)")) {
                    insert.setString(1, uuid);
                    insert.setLong(2, System.currentTimeMillis());
                    insert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getJoinCount(String uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT joinCount FROM playerdata WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("joinCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Long getFirstJoin(String uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT firstJoin FROM playerdata WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong("firstJoin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLogoutLocation(String uuid, org.bukkit.Location loc) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO playerdata (uuid, logoutWorld, logoutX, logoutY, logoutZ, logoutYaw, logoutPitch) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                        "ON CONFLICT(uuid) DO UPDATE SET logoutWorld=excluded.logoutWorld, logoutX=excluded.logoutX, " +
                        "logoutY=excluded.logoutY, logoutZ=excluded.logoutZ, logoutYaw=excluded.logoutYaw, logoutPitch=excluded.logoutPitch")) {
            statement.setString(1, uuid);
            statement.setString(2, loc.getWorld().getName());
            statement.setDouble(3, loc.getX());
            statement.setDouble(4, loc.getY());
            statement.setDouble(5, loc.getZ());
            statement.setFloat(6, loc.getYaw());
            statement.setFloat(7, loc.getPitch());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public org.bukkit.Location getLogoutLocation(String uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT logoutWorld, logoutX, logoutY, logoutZ, logoutYaw, logoutPitch FROM playerdata WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (rs.next() && rs.getString("logoutWorld") != null) {
                org.bukkit.World world = org.bukkit.Bukkit.getWorld(rs.getString("logoutWorld"));
                if (world != null) {
                    return new org.bukkit.Location(world,
                            rs.getDouble("logoutX"),
                            rs.getDouble("logoutY"),
                            rs.getDouble("logoutZ"),
                            rs.getFloat("logoutYaw"),
                            rs.getFloat("logoutPitch"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isBlacklisted(String uuid) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT blacklist FROM playerdata WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("blacklist") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setBlacklisted(String uuid, boolean value) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO playerdata (uuid, blacklist) VALUES (?, ?) " +
                        "ON CONFLICT(uuid) DO UPDATE SET blacklist=excluded.blacklist")) {
            statement.setString(1, uuid);
            statement.setInt(2, value ? 1 : 0);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public java.util.List<String> listUsernames() {
        java.util.List<String> list = new java.util.ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT username FROM players ORDER BY username ASC")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
