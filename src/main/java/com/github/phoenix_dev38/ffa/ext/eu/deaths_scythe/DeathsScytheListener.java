package com.github.phoenix_dev38.ffa.ext.eu.deaths_scythe;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.piapi.API;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathsScytheListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        if (!API.checkItemInHandWithItemMeta(event.getPlayer(), "§dDeath's Scythe"))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
            return;
        Player defender = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();
        if (!(GameUtil.checkPlayerInTheFFA(defender) && GameUtil.checkPlayerInTheFFA(attacker)))
            return;
        if (!(FreeForAll.inGame.contains(defender.getUniqueId())
                && FreeForAll.inGame.contains(attacker.getUniqueId())))
            event.setCancelled(true);
        if (!API.checkItemInHandWithItemMeta(attacker, "§dDeath's Scythe"))
            return;
        if (FreeForAll.coolDown.contains(attacker.getUniqueId()))
            return;
        defender.setHealth(defender.getHealth() * 0.8);
        FreeForAll.coolDown.add(attacker.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                FreeForAll.coolDown.remove(attacker.getUniqueId());
            }
        }.runTaskLater(FreeForAll.getInstance(), 80);
    }
}
