package com.github.phoenix_dev38.ffa.utils;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ScoreType;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseUtil {

    static int i = 0;

    public static boolean existPlayerStats(UUID uuid) throws SQLException {
        ResultSet resultSet;
        try {
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_stats WHERE uuid = '" + uuid + "';");
            return resultSet.next();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            FreeForAll.connectMySQL();
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_stats WHERE uuid = '" + uuid + "';");
            return resultSet.next();
        }
    }

    public static void createPlayerStats(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            try {
                if (!existPlayerStats(uuid))
                    FreeForAll.mysql.update("INSERT INTO ffa_player_stats(uuid, kills, deaths, coins) VALUES('" + uuid + "', '0', '0', '0');");
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public static void setScore(UUID uuid, ScoreType scoreType, int score) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            switch (scoreType) {
                case KILLS:
                case DEATHS:
                case COINS:
                    FreeForAll.mysql.update("UPDATE ffa_player_stats SET " + scoreType.toString().toLowerCase() + " = '" + score + "' WHERE uuid = '" + uuid + "';");
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
        ResultSet resultSet;
        try {
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_stats;");
            while (resultSet.next())
                uuid.add(UUID.fromString(resultSet.getString("uuid")));
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            FreeForAll.connectMySQL();
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_stats;");
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    uuid.add(UUID.fromString(resultSet.getString("uuid")));
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return uuid;
    }

    public static int getScore(UUID uuid, ScoreType scoreType) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            ResultSet resultSet;
            try {
                resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_stats WHERE uuid = '" + uuid + "';");
                switch (scoreType) {
                    case KILLS:
                    case DEATHS:
                    case COINS:
                        String columnLabel = scoreType.toString().toLowerCase();
                        if (resultSet.next())
                            i = resultSet.getInt(columnLabel);
                        break;
                }
            } catch (NullPointerException | SQLException e) {
                e.printStackTrace();
                FreeForAll.connectMySQL();
                resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_stats WHERE uuid = '" + uuid + "';");
                switch (scoreType) {
                    case KILLS:
                    case DEATHS:
                    case COINS:
                        String columnLabel = scoreType.toString().toLowerCase();
                        try {
                            if (resultSet.next())
                                i = resultSet.getInt(columnLabel);
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                        break;
                }
            }
        });
        return i;
    }
}
