package com.github.phoenix_dev38.ffa.ext.sk.listeners;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ext.sk.ItemRunnable;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.piapi.API;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() != null && event.getEntity().getKiller() != null) {
            Player entity = event.getEntity();
            Player killer = entity.getKiller();
            if (GameUtil.checkPlayerInTheFFA(entity) && GameUtil.checkPlayerInTheFFA(killer)) {
                if (FreeForAll.inGame.contains(entity.getUniqueId()) && FreeForAll.inGame.contains(killer.getUniqueId())) {
                    if (API.checkItemInHandWithItemMeta(killer, "§aStrength Sword")) {
                        ItemStack itemInHand = killer.getItemInHand();
                        if (itemInHand.getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) {
                            if (Math.random() < 0.15) {
                                itemInHand.addEnchantment(Enchantment.DAMAGE_ALL,
                                        itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1);
                                itemInHand.setItemMeta(itemInHand.getItemMeta());
                                killer.sendMessage("§aダメージ増加が１レベル上がりました。");
                                killer.sendMessage("§e現在のレベル:" + itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (!API.checkItemInHand(player, Material.BOW))
            new ItemRunnable(player, player.getInventory().getItem(event.getNewSlot())).runTaskTimer(
                    FreeForAll.getInstance(), 0, 1);
    }
}
