package com.github.phoenix_dev38.ffa.ext.is.utils;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ext.is.KitType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class KitUtil {

    public static void addListPlayerItem(UUID uuid, KitType kitType, int kitNum) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null)
            return;
        String[] base64 = ConverterUtil.playerInventoryToBase64(player.getInventory());
        String inv = base64[0];
        String armor = base64[1];
        DatabaseUtil.savePlayerInv(uuid, kitType, kitNum, inv, armor);
    }

    public static ItemStack[] getPlayerItem(UUID uuid, KitType kitType, int kitNum) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            ItemStack[] contents = player.getInventory().getContents();
            ResultSet resultSet = FreeForAll.mysql.query("SELECT * FROM FFA_PlayerInv WHERE UUID= '" + uuid + "'");
            try {
                if (resultSet.next()) {
                    List<?> list = Collections.singletonList(resultSet.getObject(kitType.toString().substring(0, 1).toUpperCase() + kitType.toString().substring(1).toLowerCase() + "Kit." + kitNum + ".Item"));
                    for (int slot = 0; slot < list.size(); slot++)
                        contents[slot] = (ItemStack) list.get(slot);
                    return contents;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ItemStack[] getPlayerArmor(UUID uuid, KitType kitType, int kitNum) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            ItemStack[] contents = player.getInventory().getContents();
            ResultSet resultSet = FreeForAll.mysql.query("SELECT * FROM FFA_PlayerInv WHERE UUID= '" + uuid + "'");
            try {
                if (resultSet.next()) {
                    List<?> list = Collections.singletonList(resultSet.getObject(kitType.toString().substring(0, 1).toUpperCase() + kitType.toString().substring(1).toLowerCase() + "Kit." + kitNum + ".Armor"));
                    for (int slot = 0; slot < list.size(); slot++)
                        contents[slot] = (ItemStack) list.get(slot);
                    return contents;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
