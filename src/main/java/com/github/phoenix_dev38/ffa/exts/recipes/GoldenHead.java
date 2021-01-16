package com.github.phoenix_dev38.ffa.exts.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GoldenHead {

    public static ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

    public static void goldenHead() {
        List<String> lore = new ArrayList<>();
        lore.add("§7Absorption II(2:00)");
        lore.add("§7Regeneration III(0:07)");

        ItemMeta skullMeta = skull.getItemMeta();
        skullMeta.setDisplayName("§6Golden Head");
        skull.setItemMeta(skullMeta);

        ShapedRecipe sr = new ShapedRecipe(skull);
        sr.shape("GGG", "GSG", "GGG");
        sr.setIngredient('G', Material.GOLD_INGOT);
        sr.setIngredient('S', Material.SKULL_ITEM, (short) 3);
        Bukkit.addRecipe(sr);
    }
}
