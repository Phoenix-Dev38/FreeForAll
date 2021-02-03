package com.github.phoenix_dev38.ffa.exts.is.utils;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.exts.is.KitType;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class DatabaseUtil {

    public static boolean existPlayerInv(UUID uuid) {
        ResultSet resultSet;
        try (Connection connection = FreeForAll.getHikari().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM ffa_player_inv WHERE uuid = '" + uuid + "';");
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean hasSavedPlayerInv(UUID uuid, KitType kitType, int kitNum) {
        ResultSet resultSet;
        switch (kitType) {
            case NORMAL:
            case EXTRAULTIMATE:
                try (Connection connection = FreeForAll.getHikari().getConnection();
                     PreparedStatement select = connection.prepareStatement("SELECT * FROM ffa_player_inv WHERE uuid = '?';")) {
                    select.setObject(1, uuid);
                    select.setString(2, kitType.name().toLowerCase());
                    select.setInt(3, kitNum);
                    resultSet = select.executeQuery();
                    return resultSet.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    public static void savePlayerInv(UUID uuid, KitType kitType, int kitNum, String inv, String armor) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            switch (kitType) {
                case NORMAL:
                case EXTRAULTIMATE:
                    try (Connection connection = FreeForAll.getHikari().getConnection();
                         PreparedStatement insert = connection.prepareStatement("INSERT INTO ffa_player_inv VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE uuid = '?';")) {
                        insert.setObject(1, uuid);
                        insert.setString(2, kitType.name().toLowerCase());
                        insert.setInt(3, kitNum);
                        insert.setString(4, inv);
                        insert.setString(5, armor);
                        insert.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    private static String[] loadPlayerInv(UUID uuid, KitType kitType, int kitNum) throws SQLException {
        ResultSet resultSet;
        String inv = "";
        String armor = "";
        switch (kitType) {
            case NORMAL:
            case EXTRAULTIMATE:
                try (Connection connection = FreeForAll.getHikari().getConnection();
                     PreparedStatement select = connection.prepareStatement("SELECT * FROM ffa_player_inv WHERE uuid = '?';")) {
                    select.setObject(1, uuid);
                    select.setString(2, kitType.name().toLowerCase());
                    select.setInt(3, kitNum);
                    resultSet = select.executeQuery();
                    if (resultSet.next()) {
                        inv = resultSet.getString("inv");
                        armor = resultSet.getString("armor");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return new String[]{inv, armor};
    }

    public static void givePlayerInv(UUID uuid, KitType kitType, int kitNum) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            String[] loadPlayerInv = new String[0];
            try {
                loadPlayerInv = loadPlayerInv(uuid, kitType, kitNum);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ItemStack[] inv = new ItemStack[0];
            ItemStack[] armor = new ItemStack[1];
            try {
                inv = ConverterUtil.itemStackArrayFromBase64(loadPlayerInv[0]);
                armor = ConverterUtil.itemStackArrayFromBase64(loadPlayerInv[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.getInventory().setContents(inv);
            player.getInventory().setArmorContents(armor);
            if (GameUtil.checkPlayerInTheFFA(player))
                GameUtil.setPlayer(player);
        });
    }
}
