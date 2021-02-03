package com.github.phoenix_dev38.ffa.utils;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ScoreType;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseUtil {

    static int i = 0;

    public static boolean existPlayerStats(UUID uuid) {
        ResultSet resultSet;
        try (Connection connection = FreeForAll.getHikari().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM ffa_player_stats WHERE uuid = '" + uuid + "';");
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void createPlayerStats(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            if (existPlayerStats(uuid)) {
                try (Connection connection = FreeForAll.getHikari().getConnection();
                     PreparedStatement insert = connection.prepareStatement("INSERT INTO ffa_player_stats VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE uuid = ?;")) {
                    insert.setObject(1, uuid);
                    insert.setInt(2, 0);
                    insert.setInt(3, 0);
                    insert.setInt(4, 0);
                    insert.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setScore(UUID uuid, ScoreType scoreType, int score) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            switch (scoreType) {
                case KILLS:
                case DEATHS:
                case COINS:
                    try (Connection connection = FreeForAll.getHikari().getConnection();
                         PreparedStatement update = connection.prepareStatement("UPDATE ffa_player_stats SET " + scoreType.name().toLowerCase() + " = '?' WHERE uuid = '?';")) {
                        update.setObject(1, uuid);
                        update.setInt(2, score);
                        update.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
    }

    public static void addScore(UUID uuid, ScoreType scoreType, int score) {
        setScore(uuid, scoreType, getScore(uuid, scoreType) + score);
    }

    public static void removeScore(UUID uuid, ScoreType scoreType, int score) {
        setScore(uuid, scoreType, getScore(uuid, scoreType) - score);
    }

    public static List<UUID> getUUIDs() {
        List<UUID> uuid = new ArrayList<>();
        try (Connection connection = FreeForAll.getHikari().getConnection();
             PreparedStatement select = connection.prepareStatement("SELECT * FROM ffa_player_stats")) {
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next())
                uuid.add(UUID.fromString(resultSet.getString("uuid")));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    public static int getScore(UUID uuid, ScoreType scoreType) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            try (Connection connection = FreeForAll.getHikari().getConnection();
                 PreparedStatement select = connection.prepareStatement("SELECT * FROM ffa_player_stats WHERE uuid = '?';")) {
                select.setObject(1, uuid);
                switch (scoreType) {
                    case KILLS:
                    case DEATHS:
                    case COINS:
                        ResultSet resultSet = select.executeQuery();
                        if (resultSet.next())
                            i = resultSet.getInt(scoreType.name().toLowerCase());
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return i;
    }
}
