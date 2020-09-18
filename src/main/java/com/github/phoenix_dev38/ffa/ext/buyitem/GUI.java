package com.github.phoenix_dev38.ffa.ext.buyitem;

import com.github.phoenix_dev38.piapi.API;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class GUI {

    public static void openShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, "§aUHC-FFA's Shop");
        ItemStack gold_Ingot = API.setItemWithItemMeta(new ItemStack(Material.GOLD_INGOT, 4), "§d金インゴット(×4) 30Coins");
        ItemStack golden_Apple = API.setItemWithItemMeta(Material.GOLDEN_APPLE, "§d金りんご 50Coins");
        ItemStack skull_Item = API.setItemWithItemMeta(Material.SKULL_ITEM, "§dヘッド 70Coins");
        ItemStack potion_InstantHealth = API.setItemWithItemMeta(new ItemStack(Material.POTION, 1, (short) 8229),
                "§d即時回復のポーション 40Coins");

        ItemStack book = API.setItemWithItemMeta(Material.BOOK, "§a本 10Coins");

        SkullMeta skull_ItemMeta = (SkullMeta) skull_Item.getItemMeta();

        skull_Item.setDurability((short) 3);
        skull_ItemMeta.setOwner(player.getName());
        skull_Item.setItemMeta(skull_ItemMeta);

        inv.setItem(10, gold_Ingot);
        inv.setItem(11, golden_Apple);
        inv.setItem(12, skull_Item);
        inv.setItem(13, potion_InstantHealth);
        inv.setItem(19, book);

        player.openInventory(inv);
    }
}
