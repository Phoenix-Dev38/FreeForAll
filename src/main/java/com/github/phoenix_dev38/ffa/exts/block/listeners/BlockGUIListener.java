package com.github.phoenix_dev38.ffa.exts.block.listeners;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.exts.block.BlockType;
import com.github.phoenix_dev38.ffa.exts.block.DatabaseUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BlockGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory == null)
            return;
        if (!inventory.getName().equals("§aブロック選択"))
            return;
        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta())
            return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        player.closeInventory();
        switch (item.getItemMeta().getDisplayName()) {
            case "§eブロック: 原木":
                DatabaseUtil.setBlock(uuid, BlockType.LOG);
                player.sendMessage(FreeForAll.PREFIX + "§e原木 ブロック§aに設定しました。");
                break;
            case "§7ブロック: 鉄":
                DatabaseUtil.setBlock(uuid, BlockType.IRON_BLOCK);
                player.sendMessage(FreeForAll.PREFIX + "§7鉄 ブロック§aに設定しました。");
                break;
            case "§6ブロック: 金":
                DatabaseUtil.setBlock(uuid, BlockType.GOLD_BLOCK);
                player.sendMessage(FreeForAll.PREFIX + "§6金 ブロック§aに設定しました。");
                break;
            case "§9ブロック: ラピス":
                DatabaseUtil.setBlock(uuid, BlockType.LAPIS_BLOCK);
                player.sendMessage(FreeForAll.PREFIX + "§9ラピス ブロック§aに設定しました。");
                break;
            case "§bブロック: ダイヤモンド":
                DatabaseUtil.setBlock(uuid, BlockType.DIAMOND_BLOCK);
                player.sendMessage(FreeForAll.PREFIX + "§bダイヤモンド ブロック§aに設定しました。");
                break;
            case "§aブロック: エメラルド":
                DatabaseUtil.setBlock(uuid, BlockType.EMERALD_BLOCK);
                player.sendMessage(FreeForAll.PREFIX + "§aエメラルド ブロックに設定しました。");
                break;
            case "§5ブロック: 黒曜石":
                DatabaseUtil.setBlock(uuid, BlockType.OBSIDIAN);
                player.sendMessage(FreeForAll.PREFIX + "§5黒曜石 ブロック§aに設定しました。");
                break;
        }
    }
}
