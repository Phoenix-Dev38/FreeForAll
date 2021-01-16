package com.github.phoenix_dev38.ffa.exts.sk;

import com.github.phoenix_dev38.iapi.API;
import com.github.phoenix_dev38.iapi.ArmorType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NormalKit {

    public static void giveKit_Default(Player player) {
        player.getInventory().clear();

        ItemStack iron_Sword = API.setItemWithItemMeta(API.addTheSetUnbreakable(Material.IRON_SWORD),
                "§aDefault Sword");
        API.addItemWithItemMeta(iron_Sword, Enchantment.DAMAGE_ALL, 2, player);
        API.addItem(player, API.addTheSetUnbreakable(Material.BOW));
        API.addItem(player, API.addTheSetUnbreakable(Material.FISHING_ROD));
        API.setItem(player, 8, Material.ARROW, 32);

        API.setArmor(true, player, ArmorType.HELMET, Material.IRON_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.IRON_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.IRON_BOOTS);
    }

    public static void giveKit_Archer(Player player) {
        player.getInventory().clear();

        ItemStack bow = API.setItemWithItemMeta(API.addTheSetUnbreakable(Material.BOW),
                "§aArcher Bow");
        ItemMeta bowMeta = bow.getItemMeta();

        bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bow.setItemMeta(bowMeta);

        API.addItem(player, bow);
        API.addItem(player, API.addTheSetUnbreakable(Material.FISHING_ROD));
        API.setItem(player, 8, Material.ARROW, 1);

        API.setArmor(true, player, ArmorType.HELMET, Material.CHAINMAIL_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.IRON_BOOTS);
    }

    public static void giveKit_Strength(Player player) {
        player.getInventory().clear();

        ItemStack iron_Sword = API.setItemWithItemMeta(API.addTheSetUnbreakable(Material.IRON_SWORD),
                "§aStrength Sword");
        API.addItemWithItemMeta(iron_Sword, Enchantment.DAMAGE_ALL, 1, player);
        API.addItem(player, API.addTheSetUnbreakable(Material.BOW));
        API.addItem(player, API.addTheSetUnbreakable(Material.FISHING_ROD));
        API.setItem(player, 8, Material.ARROW, 32);

        API.setArmor(true, player, ArmorType.HELMET, Material.CHAINMAIL_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.IRON_BOOTS);
    }

    public static void giveKit_Teleport(Player player) {
        player.getInventory().clear();

        API.addItemWithItemMeta(API.addTheSetUnbreakable(Material.IRON_SWORD), "§aTeleport Sword", player);
        API.addItem(player, API.addTheSetUnbreakable(Material.BOW));
        API.addItem(player, API.addTheSetUnbreakable(Material.FISHING_ROD));
        API.setItem(player, 8, Material.ARROW, 32);

        API.setArmor(true, player, ArmorType.HELMET, Material.GOLD_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.IRON_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.IRON_BOOTS);
    }
}
