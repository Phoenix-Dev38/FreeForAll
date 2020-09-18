package com.github.phoenix_dev38.ffa.ext.eu.axeofperun;

import com.github.phoenix_dev38.piapi.API;
import com.github.phoenix_dev38.piapi.ArmorType;
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

        API.addItem(player, API.addTheSetUnbreakble(Material.STONE_SWORD));
        giveExtraUltimate(player);
        API.addItem(player, API.addTheSetUnbreakble(Material.BOW));
        API.addItem(player, API.addTheSetUnbreakble(Material.FISHING_ROD));
        API.setItem(player, 8, new ItemStack(Material.ARROW, 32));

        API.setArmor(true, player, ArmorType.HELMET, Material.GOLD_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.GOLD_BOOTS);
    }

    public static ItemStack setExtraUltimate() {
        List<String> lore = new ArrayList<>();

        lore.add("§9Thunder");
        lore.add("");
        lore.add("§fStrikes Lightning, dealing");
        lore.add("§f1.5 hearts!(8 seconds");
        lore.add("§fcooldown)");

        ItemStack diamond_Axe = API.setItemWithItemMeta(API.addTheSetUnbreakble(Material.DIAMOND_AXE),
                "§6Axe of Perun");
        ItemMeta diamond_AxeMeta = diamond_Axe.getItemMeta();

        diamond_AxeMeta.setLore(lore);
        diamond_Axe.setItemMeta(diamond_AxeMeta);
        return diamond_Axe;
    }

    public static void giveExtraUltimate(Player player) {
        API.addItemWithItemMeta(setExtraUltimate(), Enchantment.DURABILITY, 1, player);
    }
}
