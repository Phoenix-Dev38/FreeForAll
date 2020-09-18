package com.github.phoenix_dev38.ffa.ext.eu.cornucopia;

import com.github.phoenix_dev38.piapi.API;
import com.github.phoenix_dev38.piapi.ArmorType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GiveItem {

    public static void giveKit(Player player) {
        player.getInventory().clear();

        API.addItem(player, API.addTheSetUnbreakble(Material.IRON_SWORD));
        API.addItem(player, API.addTheSetUnbreakble(Material.BOW));
        API.addItem(player, API.addTheSetUnbreakble(Material.FISHING_ROD));
        giveExtraUltimate(player);
        API.setItem(player, 8, new ItemStack(Material.ARROW, 16));

        API.setArmor(true, player, ArmorType.HELMET, Material.CHAINMAIL_HELMET);
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.IRON_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.IRON_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.CHAINMAIL_BOOTS);
    }

    public static ItemStack setExtraUltimate() {
        List<String> lore = new ArrayList<>();

        lore.add("§9Saturation I(10 Min)");
        lore.add("§9Regeneration I(10 Sec)");

        ItemStack golden_Carrot = API.setItemWithItemMeta(Material.GOLDEN_CARROT, 3, "§6Cornucopia");
        ItemMeta golden_CarrotMeta = golden_Carrot.getItemMeta();

        golden_CarrotMeta.setLore(lore);
        golden_Carrot.setItemMeta(golden_CarrotMeta);
        return golden_Carrot;
    }

    public static void giveExtraUltimate(Player player) {
        API.addItem(player, setExtraUltimate());
    }
}
