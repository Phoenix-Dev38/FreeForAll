package com.github.phoenix_dev38.ffa.exts.prestige;

import com.github.phoenix_dev38.ffa.FreeForAll;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseUtil {

    public static boolean existPlayerPrestige(UUID uuid) {
        ResultSet resultSet;
        try (Connection connection = FreeForAll.getHikari().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM ffa_player_prestige WHERE uuid = '" + uuid + "';");
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void createPlayerPrestige(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            if (existPlayerPrestige(uuid)) {
                try (Connection connection = FreeForAll.getHikari().getConnection();
                     PreparedStatement insert = connection.prepareStatement("INSERT INTO ffa_player_prestige VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE uuid = '?';")) {
                    insert.setObject(1, uuid);
                    insert.setBoolean(2, false);
                    insert.setBoolean(3, false);
                    insert.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean isOpenedPrestige(UUID uuid, PrestigeType prestigeType) {
        AtomicBoolean bool = new AtomicBoolean(false);
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            try (Connection connection = FreeForAll.getHikari().getConnection();
                 PreparedStatement select = connection.prepareStatement("SELECT * FROM ffa_player_prestige WHERE uuid = '?';")) {
                select.setObject(1, uuid);
                switch (prestigeType) {
                    case GOLDENHEAD:
                    case LIGHTAPPLE:
                        ResultSet resultSet = select.executeQuery();
                        if (resultSet.next())
                            bool.set(resultSet.getBoolean(prestigeType.name().toLowerCase()));
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return bool.get();
    }

    public static void openPrestige(UUID uuid, PrestigeType prestigeType) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            switch (prestigeType) {
                case GOLDENHEAD:
                case LIGHTAPPLE:
                    try (Connection connection = FreeForAll.getHikari().getConnection();
                         PreparedStatement update = connection.prepareStatement("UPDATE ffa_player_prestige SET " + prestigeType.name().toLowerCase() + " = '?' WHERE uuid = '?';")) {
                        update.setObject(1, uuid);
                        update.setBoolean(2, true);
                        update.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        });
    }
}
