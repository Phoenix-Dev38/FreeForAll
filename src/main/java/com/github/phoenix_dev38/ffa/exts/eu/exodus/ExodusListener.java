package com.github.phoenix_dev38.ffa.exts.eu.exodus;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.iapi.API;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ExodusListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player defender = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();
            if (GameUtil.checkPlayerInTheFFA(defender) && GameUtil.checkPlayerInTheFFA(attacker)) {
                if (FreeForAll.inGame.contains(defender.getUniqueId())
                        && FreeForAll.inGame.contains(attacker.getUniqueId())) {
                    if (API.checkHelmetWithItemMeta(attacker, "§6Exodus")) {
                        if (!FreeForAll.coolDown.contains(attacker.getUniqueId())) {
                            attacker.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));

                            FreeForAll.coolDown.add(attacker.getUniqueId());
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    FreeForAll.coolDown.remove(attacker.getUniqueId());
                                }
                            }.runTaskLater(FreeForAll.getInstance(), 40);
                        }
                    }
                }
            }
        } else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
            Player defender = (Player) event.getEntity();
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player attacker = (Player) arrow.getShooter();
                if (GameUtil.checkPlayerInTheFFA(defender) && GameUtil.checkPlayerInTheFFA(attacker)) {
                    if (FreeForAll.inGame.contains(defender.getUniqueId())
                            && FreeForAll.inGame.contains(attacker.getUniqueId())) {
                        if (defender == attacker)
                            return;
                        if (API.checkHelmetWithItemMeta(attacker, "§6Exodus")) {
                            if (!FreeForAll.coolDown.contains(attacker.getUniqueId())) {
                                attacker.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));

                                FreeForAll.coolDown.add(attacker.getUniqueId());
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        FreeForAll.coolDown.remove(attacker.getUniqueId());
                                    }
                                }.runTaskLater(FreeForAll.getInstance(), 40);
                            }
                        }
                    }
                }
            }
        }
    }
}
