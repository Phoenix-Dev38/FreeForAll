package com.github.phoenix_dev38.ffa.exts.sk.guis;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.iapi.API;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectKitTypeGUI {

    public static void openSelectKitTypeGUI(Player player) {
        Inventory inventory = FreeForAll.getInstance().getServer().createInventory(null, 27, "§bキット選択 - 種類");

        ItemStack normal = API.setItemWithItemMeta(Material.IRON_SWORD, "§aノーマル");
        ItemStack extra = API.setItemWithItemMeta(Material.DIAMOND_SWORD, "§cエクストラ(UHC Kit)");

        inventory.setItem(11, normal);
        inventory.setItem(15, extra);

        player.openInventory(inventory);
    }
}
