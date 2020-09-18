package com.github.phoenix_dev38.ffa.ext.eu.cornucopia;

import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.piapi.API;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CornucopiaListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        Player player = event.getPlayer();
        if (!GameUtil.checkPlayerInTheFFA(player))
            return;
        if (!FreeForAll.inGame.contains(player.getUniqueId()))
            return;
        if (!API.checkItemInHandWithItemMeta(player, "§6Cornucopia"))
            return;
        ItemStack itemInHand = player.getItemInHand();
        if (player.hasPotionEffect(PotionEffectType.REGENERATION)
                && player.hasPotionEffect(PotionEffectType.ABSORPTION)) {
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.removePotionEffect(PotionEffectType.ABSORPTION);

            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 140, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 600, 1));

            if (itemInHand.getAmount() >= 2) itemInHand.setAmount(itemInHand.getAmount() - 1);
            else player.getInventory().remove(itemInHand);
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 140, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 600, 1));
        if (itemInHand.getAmount() >= 2) itemInHand.setAmount(itemInHand.getAmount() - 1);
        else player.getInventory().remove(itemInHand);
    }
}
