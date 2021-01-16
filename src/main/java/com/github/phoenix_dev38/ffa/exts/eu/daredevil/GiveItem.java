package com.github.phoenix_dev38.ffa.exts.eu.daredevil;

import com.github.phoenix_dev38.iapi.API;
import com.github.phoenix_dev38.iapi.ArmorType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItem {

    public static void giveKit(Player player) {
        player.getInventory().clear();

        API.addItem(player, API.addTheSetUnbreakable(Material.IRON_SWORD));
        API.addItemWithItemMeta(API.addTheSetUnbreakable(Material.BOW), Enchantment.ARROW_DAMAGE, 1,
                player);
        API.addItem(player, API.addTheSetUnbreakable(Material.FISHING_ROD));
        API.addItem(player, Material.SADDLE);
        API.addItem(player, new ItemStack(Material.BONE, 5));
        API.setItem(player, 8, Material.ARROW, 16);

        API.setArmor(true, player, ArmorType.HELMET, Material.GOLD_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.GOLD_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.GOLD_BOOTS);
    }
}
