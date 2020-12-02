package com.github.phoenix_dev38.ffa.ext.is.utils;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.ffa.ext.is.KitType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtil {

    public static boolean existPlayerInv(UUID uuid) throws SQLException {
        ResultSet resultSet;
        try {
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_inv WHERE uuid = '" + uuid + "';");
            return resultSet.next();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            FreeForAll.connectMySQL();
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_inv WHERE uuid = '" + uuid + "';");
            return resultSet.next();
        }
    }

    public static void createPlayerInv(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            try {
                if (!existPlayerInv(uuid)) {
                    //キット追加したら忘れるな！
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'Normal', '1', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'Normal', '2', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'Normal', '3', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'Normal', '4', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'ExtraUltimate', '1', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'ExtraUltimate', '2', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'ExtraUltimate', '3', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'ExtraUltimate', '4', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'ExtraUltimate', '5', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'ExtraUltimate', '6', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'ExtraUltimate', '7', '?', '?');");
                    FreeForAll.mysql.update("INSERT INTO ffa_player_inv(uuid, kit_type, kit_num, inv, armor) VALUES('" + uuid + "', 'ExtraUltimate', '8', '?', '?');");
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public static boolean hasSavedPlayerInv(UUID uuid, KitType kitType, int kitNum) throws SQLException {
        ResultSet resultSet;
        try {
            switch (kitType) {
                case NORMAL:
                case EXTRAULTIMATE:
                    String str = kitType.toString().toLowerCase();
                    resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_inv WHERE uuid = '" + uuid + "' AND kit_type = '" + str + "' AND kit_num = " + kitNum + ";");
                    resultSet.next();
                    if (!resultSet.getString("inv").equals("?"))
                        return true;
            }
        } catch (NullPointerException | SQLException e) {
            FreeForAll.connectMySQL();
            switch (kitType) {
                case NORMAL:
                case EXTRAULTIMATE:
                    String str = kitType.toString().toLowerCase();
                    resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_inv WHERE uuid = '" + uuid + "' AND kit_type = '" + str + "' AND kit_num = " + kitNum + ";");
                    if (!resultSet.getString("inv").equals("?"))
                        return true;
            }
        }
        return false;
    }

    public static void savePlayerInv(UUID uuid, KitType kitType, int kitNum, String inv, String armor) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            switch (kitType) {
                case NORMAL:
                case EXTRAULTIMATE:
                    String str = kitType.toString().toLowerCase();
                    FreeForAll.mysql.update("UPDATE ffa_player_inv SET inv = '" + inv + "',armor = '" + armor + "' WHERE uuid = '" + uuid + "' AND kit_type = '" + str + "' AND kit_num = " + kitNum + ";");
                    break;
            }
        });
    }

    private static String[] loadPlayerInv(UUID uuid, KitType kitType, int kitNum) throws SQLException {
        ResultSet resultSet = null;
        switch (kitType) {
            case NORMAL:
            case EXTRAULTIMATE:
                String str = kitType.toString().toLowerCase();
                resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_player_inv WHERE uuid = '" + uuid + "' AND kit_type = '" + str + "' AND kit_num = " + kitNum + ";");
                break;
        }
        String inv = "";
        String armor = "";
        while (true) {
            try {
                if (!resultSet.next()) break;
                inv = resultSet.getString("inv");
                armor = resultSet.getString("armor");
            } catch (NullPointerException | SQLException e) {
                e.printStackTrace();
                FreeForAll.connectMySQL();
                if (!resultSet.next()) break;
                inv = resultSet.getString("inv");
                armor = resultSet.getString("armor");
            }
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
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            ItemStack[] inv = new ItemStack[0];
            ItemStack[] armor = new ItemStack[1];
            try {
                inv = ConverterUtil.itemStackArrayFromBase64(loadPlayerInv[0]);
                armor = ConverterUtil.itemStackArrayFromBase64(loadPlayerInv[1]);
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
                FreeForAll.connectMySQL();
                try {
                    inv = ConverterUtil.itemStackArrayFromBase64(loadPlayerInv[0]);
                    armor = ConverterUtil.itemStackArrayFromBase64(loadPlayerInv[1]);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            player.getInventory().setContents(inv);
            player.getInventory().setArmorContents(armor);
            if (GameUtil.checkPlayerInTheFFA(player))
                GameUtil.setPlayer(player);
        });
    }
}
