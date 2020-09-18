package com.github.phoenix_dev38.ffa.ext.block;

import com.github.phoenix_dev38.ffa.FreeForAll;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtil {

    public static boolean existPlayerBlock(UUID uuid) throws SQLException {
        ResultSet resultSet;
        try {
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_block WHERE UUID= '" + uuid + "';");
            return resultSet.next();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            FreeForAll.connectMySQL();
            resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_block WHERE UUID= '" + uuid + "';");
            return resultSet.next();
        }
    }

    public static void createPlayerBlock(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            try {
                if (!existPlayerBlock(uuid))
                    FreeForAll.mysql.update("INSERT INTO ffa_block(uuid, block) VALUES('" + uuid + "', '?');");
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public static void setBlock(UUID uuid, BlockType blockType) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            switch (blockType) {
                case LOG:
                case IRON:
                case GOLD:
                case LAPIS:
                case DIAMOND:
                case EMERALD:
                case OBSIDIAN:
                default:
                    FreeForAll.mysql.update("UPDATE ffa_block SET Block = '" + blockType.toString().toLowerCase() + "' WHERE uuid = '" + uuid + "';");
                    break;
            }
        });
    }

    public static void givePlayerBlock(Player player) {
        UUID uuid = player.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            ResultSet resultSet = FreeForAll.mysql.query("SELECT * FROM ffa_block WHERE uuid = '" + uuid + "';");
            String blockName = "?";
            try {
                resultSet.next();
                blockName = resultSet.getString("block");
            } catch (NullPointerException | SQLException e) {
                e.printStackTrace();
                FreeForAll.connectMySQL();
                try {
                    resultSet.next();
                    blockName = resultSet.getString("block");
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
            if (blockName.equals("?"))
                player.getInventory().addItem(new ItemStack(Material.LOG, 64));
            player.getInventory().addItem(new ItemStack(Material.LOG, 64));
        });
    }
}
