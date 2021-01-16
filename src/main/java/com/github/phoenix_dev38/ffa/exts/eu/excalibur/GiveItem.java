package com.github.phoenix_dev38.ffa.exts.eu.excalibur;

import com.github.phoenix_dev38.iapi.API;
import com.github.phoenix_dev38.iapi.ArmorType;
import org.bukkit.Material;
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
        API.setItem(player, 8, new ItemStack(Material.ARROW, 32));

        API.setArmor(true, player, ArmorType.HELMET, Material.CHAINMAIL_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.GOLD_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.IRON_BOOTS);
    }

    public static ItemStack setExtraUltimate() {
        List<String> lore = new ArrayList<>();

        lore.add("§9Chaos");
        lore.add("");
        lore.add("§6The Holy Sword");
        lore.add("§7Fools!");

        ItemStack diamond_Sword = API.setItemWithItemMeta(API.addTheSetUnbreakable(Material.DIAMOND_SWORD),
                "§6Excalibur");
        ItemMeta diamond_SwordMeta = diamond_Sword.getItemMeta();

        diamond_SwordMeta.setLore(lore);
        diamond_Sword.setItemMeta(diamond_SwordMeta);
        return diamond_Sword;
    }

    public static void giveExtraUltimate(Player player) {
        API.addItem(player, setExtraUltimate());
    }
}
