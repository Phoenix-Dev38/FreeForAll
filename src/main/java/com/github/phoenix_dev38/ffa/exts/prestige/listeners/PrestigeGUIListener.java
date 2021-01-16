package com.github.phoenix_dev38.ffa.exts.prestige.listeners;

import com.github.phoenix_dev38.ffa.utils.ScoreUtil;
import com.github.phoenix_dev38.ffa.ScoreType;
import com.github.phoenix_dev38.ffa.exts.prestige.DatabaseUtil;
import com.github.phoenix_dev38.ffa.exts.prestige.PrestigeType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;
import java.util.UUID;

public class PrestigeGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws SQLException {
        if (!event.getInventory().getName().equals("§aプレステージ"))
            return;
        event.setCancelled(true);
        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        switch (slot) {
            case 11:
                if (DatabaseUtil.isOpenedPrestige(uuid, PrestigeType.LIGHTAPPLE)) {
                    player.sendMessage("§a既に開放しています。");
                    return;
                }
                if (ScoreUtil.getScore(uuid, ScoreType.COINS) >= 700) {
                    DatabaseUtil.openPrestige(uuid, PrestigeType.LIGHTAPPLE);
                    com.github.phoenix_dev38.ffa.utils.DatabaseUtil.removeScore(uuid, ScoreType.COINS, 700);
                    player.sendMessage("§aLightAppleのプレステージを開放しました。");
                    player.closeInventory();
                } else player.sendMessage("§cコインが足りません。");
                break;
            case 16:
                if (DatabaseUtil.isOpenedPrestige(uuid, PrestigeType.GOLDENHEAD)) {
                    player.sendMessage("§a既に開放しています。");
                    return;
                }
                if (ScoreUtil.getScore(uuid, ScoreType.COINS) >= 500) {
                    DatabaseUtil.openPrestige(uuid, PrestigeType.GOLDENHEAD);
                    com.github.phoenix_dev38.ffa.utils.DatabaseUtil.removeScore(uuid, ScoreType.COINS, 500);
                    player.sendMessage("§aGoldenHeadのプレステージを開放しました。");
                    player.closeInventory();
                } else player.sendMessage("§cコインが足りません。");
                break;
        }
    }
}
