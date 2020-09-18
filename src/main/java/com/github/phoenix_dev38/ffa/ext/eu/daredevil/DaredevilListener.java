package com.github.phoenix_dev38.ffa.ext.eu.daredevil;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ext.recipes.Daredevil;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class DaredevilListener implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        if (!GameUtil.checkPlayerInTheFFA(player))
            return;
        if (!FreeForAll.inGame.contains(player.getUniqueId()))
            return;
        if (!event.getCurrentItem().equals(Daredevil.monster_Egg))
            return;
        if (!event.isShiftClick()) {
            player.sendMessage("§cシフトクリックでクラフトしてください。");
            event.setCancelled(true);
            return;
        }
        Location loc = player.getLocation();
        Horse horse = loc.getWorld().spawn(loc, Horse.class);

        horse.setVariant(Horse.Variant.SKELETON_HORSE);
        horse.setCustomName("§a" + player.getName() + "'s §cDareDevil");
        horse.setBreed(true);
        horse.setTamed(true);
        horse.setMaxHealth(20.0);
        horse.setHealth(20.0);
        horse.setJumpStrength(0.8);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

        EntityHorse entityHorse = ((CraftHorse) horse).getHandle();
        entityHorse.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5);

        Bukkit.getServer().getScheduler().runTaskLater(FreeForAll.getInstance(), () -> player.getInventory().remove(Daredevil.monster_Egg), 1);

        player.sendMessage("§aDaredevil§eをクラフトしました。");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Horse))
            return;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!GameUtil.checkPlayerInTheFFA(onlinePlayer))
                return;
            if (!FreeForAll.inGame.contains(onlinePlayer.getUniqueId()))
                return;
            Horse horse = (Horse) event.getEntity();
            if (horse.getName().contains(onlinePlayer.getName())) {
                onlinePlayer.getInventory().addItem(new ItemStack(Material.SADDLE));
                onlinePlayer.getInventory().addItem(new ItemStack(Material.BONE, 5));
            }
        }
    }
}
