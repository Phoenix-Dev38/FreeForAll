package com.github.phoenix_dev38.ffa.ext.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class LightApple {

    public static ItemStack golden_Apple = new ItemStack(Material.GOLDEN_APPLE);

    public static void lightApple() {
        ItemMeta golden_AppleMeta = golden_Apple.getItemMeta();
        golden_AppleMeta.setDisplayName("§bLight Apple");
        golden_Apple.setItemMeta(golden_AppleMeta);

        ShapedRecipe sr = new ShapedRecipe(golden_Apple);
        sr.shape(" G ", "GAG", " G ");
        sr.setIngredient('G', Material.GOLD_INGOT);
        sr.setIngredient('A', Material.APPLE);
        Bukkit.addRecipe(sr);
    }
}
