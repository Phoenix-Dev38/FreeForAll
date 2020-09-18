package com.github.phoenix_dev38.ffa.ext.eu.exodus;

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
        API.addItem(player, API.addTheSetUnbreakble(Material.BOW));
        API.addItem(player, API.addTheSetUnbreakble(Material.FISHING_ROD));
        API.setItem(player, 8, new ItemStack(Material.ARROW, 32));

        player.getInventory().setHelmet(setExtraUltimate());
        API.setArmor(true, player, ArmorType.CHESTPLATE, Material.CHAINMAIL_CHESTPLATE);
        API.setArmor(true, player, ArmorType.LEGGINGS, Material.IRON_LEGGINGS);
        API.setArmor(true, player, ArmorType.BOOTS, Material.CHAINMAIL_BOOTS);
    }

    public static ItemStack setExtraUltimate() {
        List<String> lore = new ArrayList<>();

        lore.add("§9Aeon");
        lore.add("");
        lore.add("§fRegenerates a small portion");
        lore.add("§fof health when hit!(2");
        lore.add("§fseconds cooldown)");

        ItemStack diamond_Helmet = API.setItemWithItemMeta(Material.DIAMOND_HELMET, "§6Exodus");
        ItemMeta diamond_HelmetMeta = diamond_Helmet.getItemMeta();

        diamond_HelmetMeta.setLore(lore);
        diamond_Helmet.setItemMeta(diamond_HelmetMeta);
        return diamond_Helmet;
    }

    public static void giveExtraUltimate(Player player) {
        API.addItemWithItemMeta(setExtraUltimate(), Enchantment.DURABILITY, 3, player);
    }
}
