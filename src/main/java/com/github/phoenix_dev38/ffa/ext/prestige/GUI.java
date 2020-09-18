package com.github.phoenix_dev38.ffa.ext.prestige;

import com.github.phoenix_dev38.piapi.API;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUI {

    public static void openPrestige(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§aプレステージ");
        ItemStack lightApple = API.setItemWithItemMeta(Material.WORKBENCH, "§aLightApple プレステージ 700Coins");
        ItemStack goldenHead = API.setItemWithItemMeta(Material.WORKBENCH, "§aGoldenHead プレステージ 500Coins");

        inv.setItem(11, lightApple);
        inv.setItem(16, goldenHead);

        player.openInventory(inv);
    }
}
