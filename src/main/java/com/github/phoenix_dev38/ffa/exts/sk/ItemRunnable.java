package com.github.phoenix_dev38.ffa.exts.sk;

import com.github.phoenix_dev38.iapi.API;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemRunnable extends BukkitRunnable {

    Player player;
    ItemStack itemStack;

    public ItemRunnable(Player player, ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack;
    }

    public void run() {
        if (API.checkItemInHandWithItemMeta(this.player, "§aArcher Bow")) {
            this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
            return;
        }
        cancel();
    }
}
