package com.github.phoenix_dev38.ffa.listeners;

import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ext.sk.guis.SelectKitTypeGUI;
import com.github.phoenix_dev38.piapi.API;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (GameUtil.checkPlayerInTheFFA(player)) {
                if (FreeForAll.inGame.contains(player.getUniqueId())) {
                    ItemStack itemInHand = player.getItemInHand();
                    if (itemInHand.getType().equals(Material.SKULL_ITEM)) {
                        if (itemInHand.getItemMeta().getDisplayName() != null &&
                                itemInHand.getItemMeta().getDisplayName().equals("§6Golden Head")) {
                            player.removePotionEffect(PotionEffectType.SPEED);
                            player.removePotionEffect(PotionEffectType.REGENERATION);

                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1));

                            player.sendMessage(FreeForAll.PREFIX + "§aスピード2, 再生能力4, 衝撃吸収2を与えました。");
                            if (itemInHand.getAmount() >= 2) itemInHand.setAmount(itemInHand.getAmount() - 1);
                            else player.getInventory().remove(itemInHand);
                            return;
                        }
                        player.removePotionEffect(PotionEffectType.SPEED);
                        player.removePotionEffect(PotionEffectType.REGENERATION);

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 3));
                        player.sendMessage(FreeForAll.PREFIX + "§aスピード2, 再生能力4を与えました。");
                        if (itemInHand.getAmount() >= 2) itemInHand.setAmount(itemInHand.getAmount() - 1);
                        else player.getInventory().remove(itemInHand);
                    } else if (itemInHand.getType().equals(Material.MONSTER_EGG)) {
                        if (!player.hasPermission("ffa.spawnMonster"))
                            event.setCancelled(true);
                    }
                } else {
                    if (API.checkItemInHandWithItemMeta(player, "§aキット選択(右クリック)"))
                        SelectKitTypeGUI.openSelectKitTypeGUI(player);
                    else if (API.checkItemInHandWithItemMeta(player, "§bロビーに戻る(右クリック)")) {
                        GameUtil.setLoser(player);
                        player.sendMessage("§a初期スポーン地点にテレポートしました。");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            Player player = event.getPlayer();
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 140, 2));
        }
    }
}
