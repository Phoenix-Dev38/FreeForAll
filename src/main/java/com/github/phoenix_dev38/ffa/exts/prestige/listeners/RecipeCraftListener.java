package com.github.phoenix_dev38.ffa.exts.prestige.listeners;

import com.github.phoenix_dev38.ffa.exts.prestige.DatabaseUtil;
import com.github.phoenix_dev38.ffa.exts.prestige.PrestigeType;
import com.github.phoenix_dev38.ffa.exts.recipes.GoldenHead;
import com.github.phoenix_dev38.ffa.exts.recipes.LightApple;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.sql.SQLException;
import java.util.UUID;

public class RecipeCraftListener implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event) throws SQLException {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        if (event.getCurrentItem().equals(GoldenHead.skull)) {
            if (event.isShiftClick()) {
                player.sendMessage("§cシフトクリックではクラフトできません。");
                event.setCancelled(true);
                return;
            }
            if (PrestigeListener.ghLimit.get(player) == null)
                PrestigeListener.ghLimit.putIfAbsent(player, 1);
            else PrestigeListener.ghLimit.put(player, PrestigeListener.ghLimit.get(player) + 1);
            if ((!DatabaseUtil.isOpenedPrestige(uuid, PrestigeType.GOLDENHEAD)
                    && PrestigeListener.ghLimit.get(player) == 3)
                    || (DatabaseUtil.isOpenedPrestige(uuid, PrestigeType.GOLDENHEAD)
                    && PrestigeListener.ghLimit.get(player) == 4)) {
                player.sendMessage("§cGolden Headのクラフト回数が上限に達したため作ることができません。");
                event.setCancelled(true);
                return;
            }
            if (!DatabaseUtil.isOpenedPrestige(uuid, PrestigeType.GOLDENHEAD))
                player.sendMessage("§aGolden Head§eをクラフトしました。 (" + PrestigeListener.ghLimit.get(player) + "/3)");
            else player.sendMessage("§aGolden Head§eをクラフトしました。 (" + PrestigeListener.ghLimit.get(player) + "/4)");
        } else if (event.getCurrentItem().equals(LightApple.golden_Apple)) {
            if (event.isShiftClick()) {
                player.sendMessage("§cシフトクリックではクラフトできません。");
                event.setCancelled(true);
                return;
            }
            if (PrestigeListener.laLimit.get(player) == null)
                PrestigeListener.laLimit.putIfAbsent(player, 1);
            else PrestigeListener.laLimit.put(player, PrestigeListener.laLimit.get(player) + 1);
            if ((!DatabaseUtil.isOpenedPrestige(uuid, PrestigeType.LIGHTAPPLE)
                    && PrestigeListener.laLimit.get(player) == 1)
                    || (DatabaseUtil.isOpenedPrestige(uuid, PrestigeType.LIGHTAPPLE)
                    && PrestigeListener.laLimit.get(player) == 2)) {
                player.sendMessage("§cLight Appleのクラフト回数が上限に達したため作ることができません。");
                event.setCancelled(true);
                return;
            }
            if (!DatabaseUtil.isOpenedPrestige(uuid, PrestigeType.LIGHTAPPLE))
                player.sendMessage("§aLight Apple§eをクラフトしました。 (" + PrestigeListener.laLimit.get(player) + "/1)");
            else player.sendMessage("§aLight Apple§eをクラフトしました。 (" + PrestigeListener.laLimit.get(player) + "/2)");
        }
    }
}
