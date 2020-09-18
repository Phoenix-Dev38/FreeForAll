package com.github.phoenix_dev38.ffa.ext.buyitem;

import com.github.phoenix_dev38.ffa.GameScoreboard;
import com.github.phoenix_dev38.ffa.ScoreType;
import com.github.phoenix_dev38.ffa.utils.DatabaseUtil;
import com.github.phoenix_dev38.ffa.utils.ScoreUtil;
import com.github.phoenix_dev38.piapi.API;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class BuyItemGUIListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
            if (villager.getName().equals("UHC-FFA's Shop"))
                GUI.openShop(event.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getName().equals("§aUHC-FFA's Shop"))
            return;
        event.setCancelled(true);
        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        switch (slot) {
            case 10:
                if (ScoreUtil.getScore(uuid, ScoreType.COINS) >= 30) {
                    DatabaseUtil.removeScore(uuid, ScoreType.COINS, 30);
                    API.addItem(player, Material.GOLD_INGOT, 4);
                } else player.sendMessage("§cコインが足りません。");
                break;
            case 11:
                if (ScoreUtil.getScore(uuid, ScoreType.COINS) >= 50) {
                    DatabaseUtil.removeScore(uuid, ScoreType.COINS, 50);
                    API.addItem(player, Material.GOLDEN_APPLE);
                } else player.sendMessage("§cコインが足りません。");
                break;
            case 12:
                if (ScoreUtil.getScore(uuid, ScoreType.COINS) >= 70) {
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM);
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

                    skull.setDurability((short) 3);
                    skullMeta.setOwner(player.getName());
                    skullMeta.setDisplayName("§a" + player.getName() + "'s §cHead.");
                    skull.setItemMeta(skullMeta);

                    DatabaseUtil.removeScore(uuid, ScoreType.COINS, 70);
                    API.addItem(player, skull);
                } else player.sendMessage("§cコインが足りません。");
                break;
            case 13:
                if (ScoreUtil.getScore(uuid, ScoreType.COINS) >= 40) {
                    DatabaseUtil.removeScore(uuid, ScoreType.COINS, 40);
                    player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 8229));
                } else player.sendMessage("§cコインが足りません。");
                break;
            case 19:
                if (ScoreUtil.getScore(uuid, ScoreType.COINS) >= 10) {
                    DatabaseUtil.removeScore(uuid, ScoreType.COINS, 10);
                    API.addItem(player, Material.BOOK);
                } else player.sendMessage("§cコインが足りません。");
                break;
        }
        GameScoreboard.updateGameScoreboard(player);
    }
}
