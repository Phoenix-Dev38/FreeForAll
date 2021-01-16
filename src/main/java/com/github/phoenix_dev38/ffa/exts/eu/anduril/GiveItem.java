package com.github.phoenix_dev38.ffa.exts.eu.anduril;

import com.github.phoenix_dev38.iapi.API;
import com.github.phoenix_dev38.iapi.ArmorType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GiveItem {

    public static void giveKit(Player player) {
        player.getInventory().clear();

        giveExtraUltimate(player);
        API.addItem(player, API.addTheSetUnbreakable(Material.BOW));
        API.addItem(player, API.addTheSetUnbreakable(Material.FISHING_ROD));
        API.setItem(player, 8, Material.ARROW, 16);

        API.setArmor(true, player, ArmorType.HELMET, Material.IRON_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.CHAINMAIL_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.IRON_BOOTS);
    }

    public static ItemStack setExtraUltimate() {
        List<String> lore = new ArrayList<>();

        lore.add("§9Resistance I(While Holding)");
        lore.add("§9Speed I(While Holding)");
        lore.add("§9Justice");

        ItemStack iron_Sword = API.setItemWithItemMeta(API.addTheSetUnbreakable(Material.IRON_SWORD),
                "§aAndúril");
        ItemMeta iron_SwordMeta = iron_Sword.getItemMeta();

        iron_SwordMeta.setLore(lore);
        iron_Sword.setItemMeta(iron_SwordMeta);
        return iron_Sword;
    }

    public static void giveExtraUltimate(Player player) {
        API.addItemWithItemMeta(setExtraUltimate(), Enchantment.DAMAGE_ALL, 2, player);
    }
}
