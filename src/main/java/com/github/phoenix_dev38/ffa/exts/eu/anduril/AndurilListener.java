package com.github.phoenix_dev38.ffa.exts.eu.anduril;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.iapi.API;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class AndurilListener implements Listener {

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (!API.checkItemInHand(player, Material.IRON_SWORD))
            new ItemRunnable(player, player.getInventory().getItem(event.getNewSlot())).runTaskTimer(
                    FreeForAll.getInstance(), 0, 1);
    }
}
