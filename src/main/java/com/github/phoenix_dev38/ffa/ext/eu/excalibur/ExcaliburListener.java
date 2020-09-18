package com.github.phoenix_dev38.ffa.ext.eu.excalibur;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.piapi.API;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ExcaliburListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player defender = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();
            if (GameUtil.checkPlayerInTheFFA(defender) && GameUtil.checkPlayerInTheFFA(attacker)) {
                if (FreeForAll.inGame.contains(defender.getUniqueId())
                        && FreeForAll.inGame.contains(attacker.getUniqueId())) {
                    if (API.checkItemInHandWithItemMeta(attacker, "§6Excalibur")) {
                        if (!FreeForAll.coolDown.contains(attacker.getUniqueId())) {
                            attacker.getWorld().createExplosion(defender.getLocation(), (float) 0.05);
                            FreeForAll.coolDown.add(attacker.getUniqueId());
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    FreeForAll.coolDown.remove(attacker.getUniqueId());
                                }
                            }.runTaskLater(FreeForAll.getInstance(), 100);
                        }
                    }
                }
            }
        }
    }
}
