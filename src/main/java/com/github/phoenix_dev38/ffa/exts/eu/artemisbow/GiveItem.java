package com.github.phoenix_dev38.ffa.exts.eu.artemisbow;

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

        API.addItem(player, API.addTheSetUnbreakable(Material.STONE_SWORD));
        giveExtraUltimate(player);
        API.addItem(player, API.addTheSetUnbreakable(Material.FISHING_ROD));
        API.setItem(player, 8, new ItemStack(Material.ARROW, 16));

        API.setArmor(true, player, ArmorType.HELMET, Material.GOLD_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.GOLD_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.IRON_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.CHAINMAIL_BOOTS);
    }

    public static ItemStack setExtraUltimate() {
        List<String> lore = new ArrayList<>();

        lore.add("§cHunting");

        ItemStack bow = API.setItemWithItemMeta(API.addTheSetUnbreakable(Material.BOW),
                "§cArtemis' Bow");
        ItemMeta bowMeta = bow.getItemMeta();

        bowMeta.setLore(lore);
        bow.setItemMeta(bowMeta);
        return bow;
    }

    public static void giveExtraUltimate(Player player) {
        API.addItemWithItemMeta(setExtraUltimate(), Enchantment.ARROW_DAMAGE, 3, player);
    }
}
