package com.github.phoenix_dev38.ffa.exts.recipes;

import com.github.phoenix_dev38.iapi.API;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Daredevil {

    public static ItemStack monster_Egg = API
            .setItemWithItemMeta(new ItemStack(Material.MONSTER_EGG, 1, (short) 100), "§cDaredevil");

    public static void daredevil() {
        List<String> lore = new ArrayList<>();

        lore.add("§aType: §7Skeleton Horse");
        lore.add("§aHealth: §725 Hearts");
        lore.add("§aSpped: §7Max");
        lore.add("§aJump: §73 Blocks");
        lore.add("§eComes with free saddle.");

        ItemMeta monster_EggMeta = monster_Egg.getItemMeta();
        monster_Egg.setItemMeta(monster_EggMeta);

        ShapedRecipe sr = new ShapedRecipe(monster_Egg);
        sr.shape("HS ", "BBB", "B B");
        sr.setIngredient('H', Material.SKULL_ITEM, (short) 3);
        sr.setIngredient('S', Material.SADDLE);
        sr.setIngredient('B', Material.BONE);
        Bukkit.addRecipe(sr);
    }
}
