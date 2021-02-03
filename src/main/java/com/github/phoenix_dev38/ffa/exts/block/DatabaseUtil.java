package com.github.phoenix_dev38.ffa.exts.block;

import com.github.phoenix_dev38.ffa.FreeForAll;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.UUID;

public class DatabaseUtil {

    public static boolean existPlayerBlock(UUID uuid) {
        ResultSet resultSet;
        try (Connection connection = FreeForAll.getHikari().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM ffa_player_block WHERE uuid = '" + uuid + "';");
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void createPlayerBlock(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            if (existPlayerBlock(uuid)) {
                try (Connection connection = FreeForAll.getHikari().getConnection();
                     PreparedStatement insert = connection.prepareStatement("INSERT INTO ffa_player_block VALUES(?, ?) ON DUPLICATE KEY UPDATE uuid = '?';")) {
                    insert.setObject(1, uuid);
                    insert.setString(2, "?");
                    insert.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setBlock(UUID uuid, BlockType blockType) {
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            switch (blockType) {
                case LOG:
                case IRON_BLOCK:
                case GOLD_BLOCK:
                case LAPIS_BLOCK:
                case DIAMOND_BLOCK:
                case EMERALD_BLOCK:
                case OBSIDIAN:
                default:
                    try (Connection connection = FreeForAll.getHikari().getConnection();
                         PreparedStatement update = connection.prepareStatement("UPDATE ffa_player_prestige SET " + blockType.name().toLowerCase() + " = '?' WHERE uuid = '?';")) {
                        update.setObject(1, uuid);
                        update.setString(2, blockType.name().toLowerCase());
                        update.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
    }

    public static void givePlayerBlock(Player player) {
        UUID uuid = player.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(FreeForAll.getInstance(), () -> {
            String blockName = "?";
            try (Connection connection = FreeForAll.getHikari().getConnection();
                 PreparedStatement select = connection.prepareStatement("SELECT * FROM ffa_player_block WHERE uuid = '?';")) {
                select.setObject(1, uuid);
                ResultSet resultSet = select.executeQuery();
                if (resultSet.next())
                    blockName = resultSet.getString("block");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (blockName.equals("?")) player.getInventory().addItem(new ItemStack(Material.LOG, 64));
            else player.getInventory().addItem(new ItemStack(Material.getMaterial(blockName.toUpperCase()), 64));
        });
    }
}
