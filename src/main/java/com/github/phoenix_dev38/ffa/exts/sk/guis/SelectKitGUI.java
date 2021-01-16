package com.github.phoenix_dev38.ffa.exts.sk.guis;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.iapi.API;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectKitGUI {

    public static void openSelectKitGUI_Normal(Player player) {
        Inventory inventory = FreeForAll.getInstance().getServer().createInventory(null, 45, "§aキット選択 - ノーマル");

        ItemStack iron_Sword1 = API.setItemWithItemMeta(Material.IRON_SWORD, "§aKit - Default");
        ItemStack bow = API.setItemWithItemMeta(Material.BOW, "§aKit - Archer");
        ItemStack iron_Sword2 = API.setItemWithItemMeta(Material.IRON_SWORD, "§aKit - Strength");
        ItemStack eye_Of_Ender = API.setItemWithItemMeta(Material.EYE_OF_ENDER, "§aKit - Teleport");

        inventory.setItem(10, iron_Sword1);
        inventory.setItem(11, bow);
        inventory.setItem(12, iron_Sword2);
        //inventory.setItem(13, eye_Of_Ender);

        player.openInventory(inventory);
    }

    public static void openSelectKitGUI_Extra(Player player) {
        Inventory inventory = FreeForAll.getInstance().getServer().createInventory(null, 45, "§aキット選択 - エクストラ");

        ItemStack bow = API.setItemWithItemMetaAndHideEnchants(Material.BOW,
                "§cKit - Artemis' Bow");
        ItemStack diamond_Helmet = API.setItemWithItemMetaAndHideEnchants(Material.DIAMOND_HELMET,
                "§cKit - §6Exodus");
        ItemStack diamond_Axe = API.setItemWithItemMetaAndHideEnchants(Material.DIAMOND_AXE,
                "§cKit - §6Axe of Perun");
        ItemStack diamond_Sword = API.setItemWithItemMetaAndHideEnchants(Material.DIAMOND_SWORD,
                "§cKit - §6Excalibur");
        ItemStack iron_Sword = API.setItemWithItemMetaAndHideEnchants(Material.IRON_SWORD,
                "§cKit - §aAndùril");
        ItemStack iron_Hoe = API.setItemWithItemMetaAndHideEnchants(Material.IRON_HOE,
                "§cKit - §dDeath's Scythe");
        ItemStack golden_Carrot = API.setItemWithItemMeta(Material.GOLDEN_CARROT, "§cKit - §6Cornucopia");
        ItemStack monster_Egg = API.setItemWithItemMeta(new ItemStack(Material.MONSTER_EGG, 1, (short) 100),
                "§cKit - Daredevil");

        inventory.setItem(10, bow);
        inventory.setItem(11, diamond_Helmet);
        inventory.setItem(12, diamond_Axe);
        inventory.setItem(13, diamond_Sword);
        inventory.setItem(14, iron_Sword);
        inventory.setItem(15, iron_Hoe);
        inventory.setItem(16, golden_Carrot);
        inventory.setItem(19, monster_Egg);

        player.openInventory(inventory);
    }
}
