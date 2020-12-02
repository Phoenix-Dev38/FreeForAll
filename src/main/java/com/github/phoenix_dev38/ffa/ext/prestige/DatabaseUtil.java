package com.github.phoenix_dev38.ffa.ext.prestige;

import com.github.phoenix_dev38.ffa.FreeForAll;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtil {

    public static boolean existPlayerPrestige(UUID uuid) throws SQLException {
        ResultSet resultSet;
        try {
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_prestige WHERE uuid = '" + uuid + "';");
            return resultSet.next();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            FreeForAll.connectMySQL();
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_prestige WHERE uuid = '" + uuid + "';");
            return resultSet.next();
        }
    }

    public static void createPlayerPrestige(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            try {
                if (!existPlayerPrestige(uuid))
                    FreeForAll.mysql.update("INSERT INTO ffa_player_prestige(uuid, goldenhead, lightapple) VALUES('" + uuid + "', 'false', 'false');");
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public static boolean isOpenedPrestige(UUID uuid, PrestigeType prestigeType) throws SQLException {
        ResultSet resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_prestige WHERE uuid = '" + uuid + "';");
        try {
            resultSet.next();
            switch (prestigeType) {
                case GOLDENHEAD:
                case LIGHTAPPLE:
                    String str = prestigeType.toString().toLowerCase();
                    return resultSet.getBoolean(str);
            }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            FreeForAll.connectMySQL();
            resultSet.next();
            switch (prestigeType) {
                case GOLDENHEAD:
                case LIGHTAPPLE:
                    String str = prestigeType.toString().toLowerCase();
                    return resultSet.getBoolean(str);
            }
        }
        return false;
    }

    public static void openPrestige(UUID uuid, PrestigeType prestigeType) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            switch (prestigeType) {
                case GOLDENHEAD:
                case LIGHTAPPLE:
                    String str = prestigeType.toString().toLowerCase();
                    FreeForAll.mysql.update("UPDATE ffa_player_prestige SET " + str + " = 'true' WHERE uuid = '" + uuid + "';");
                    break;
            }
        });
    }
}
