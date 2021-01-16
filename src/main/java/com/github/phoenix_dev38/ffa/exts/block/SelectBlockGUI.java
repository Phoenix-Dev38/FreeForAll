package com.github.phoenix_dev38.ffa.exts.block;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.iapi.API;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectBlockGUI {

    public static void openSelectBlockGUI(Player player) {
        Inventory inventory = FreeForAll.getInstance().getServer().createInventory(null, 27, "§aブロック選択");

        ItemStack block1 = API.setItemWithItemMeta(Material.LOG, "§eブロック: 原木");
        ItemStack block2 = API.setItemWithItemMeta(Material.IRON_BLOCK, "§7ブロック: 鉄");
        ItemStack block3 = API.setItemWithItemMeta(Material.GOLD_BLOCK, "§6ブロック: 金");
        ItemStack block4 = API.setItemWithItemMeta(Material.LAPIS_BLOCK, "§9ブロック: ラピス");
        ItemStack block5 = API.setItemWithItemMeta(Material.DIAMOND_BLOCK, "§bブロック: ダイヤモンド");
        ItemStack block6 = API.setItemWithItemMeta(Material.EMERALD_BLOCK, "§aブロック: エメラルド");
        ItemStack block7 = API.setItemWithItemMeta(Material.OBSIDIAN, "§5ブロック: 黒曜石");

        inventory.setItem(10, block1);
        inventory.setItem(11, block2);
        inventory.setItem(12, block3);
        inventory.setItem(13, block4);
        inventory.setItem(14, block5);
        inventory.setItem(15, block6);
        inventory.setItem(16, block7);

        player.openInventory(inventory);
    }
}
