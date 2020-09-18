package com.github.phoenix_dev38.ffa.ext.block.listeners;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ext.block.DatabaseUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.UUID;

public class BlockListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        UUID uuid = event.getPlayer().getUniqueId();
        if (!DatabaseUtil.existPlayerBlock(uuid))
            DatabaseUtil.createPlayerBlock(uuid);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!FreeForAll.inGame.contains(event.getPlayer().getUniqueId()))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPrepareCraftItem(PrepareItemCraftEvent event) {
        ItemStack result = event.getRecipe().getResult();
        if (!(result.getType().equals(Material.WOOD)
                || result.getType().equals(Material.IRON_INGOT)
                || result.getType().equals(Material.GOLD_INGOT)
                || (result.getType().equals(Material.INK_SACK) && result.getData().getData() == 4)
                || result.getType().equals(Material.DIAMOND)
                || result.getType().equals(Material.EMERALD)
                || (result.getType().equals(Material.GOLDEN_APPLE) && result.getData().getData() == 1)))
            return;
        event.getInventory().setResult(new ItemStack(Material.AIR));
    }
}
