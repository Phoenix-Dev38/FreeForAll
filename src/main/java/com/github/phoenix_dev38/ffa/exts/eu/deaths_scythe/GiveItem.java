package com.github.phoenix_dev38.ffa.exts.eu.deaths_scythe;

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

        API.addItem(player, API.addTheSetUnbreakable(Material.STONE_SWORD));
        giveExtraUltimate(player);
        API.addItem(player, API.addTheSetUnbreakable(Material.BOW));
        API.addItem(player, API.addTheSetUnbreakable(Material.FISHING_ROD));
        API.setItem(player, 8, new ItemStack(Material.ARROW, 32));

        API.setArmor(true, player, ArmorType.HELMET, Material.IRON_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.CHAINMAIL_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.CHAINMAIL_BOOTS);
    }

    public static ItemStack setExtraUltimate() {
        List<String> lore = new ArrayList<>();

        lore.add("§9Oblivion");
        lore.add("§fDeals more damage the");
        lore.add("§fhigher your opponents");
        lore.add("§fhealth is, and lifesteals a");
        lore.add("§fchunk of the damage dealt!");

        ItemStack iron_Hoe = API.setItemWithItemMeta(Material.IRON_HOE, "§dDeath's Scythe");
        ItemMeta iron_HoeMeta = iron_Hoe.getItemMeta();

        iron_HoeMeta.setLore(lore);
        iron_Hoe.setItemMeta(iron_HoeMeta);
        return iron_Hoe;
    }

    public static void giveExtraUltimate(Player player) {
        API.addItem(player, setExtraUltimate());
    }
}
