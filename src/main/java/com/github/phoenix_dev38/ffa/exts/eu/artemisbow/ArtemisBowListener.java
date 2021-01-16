package com.github.phoenix_dev38.ffa.exts.eu.artemisbow;

import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.iapi.API;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.Random;

public class ArtemisBowListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        if (!(event.getEntity().getShooter() instanceof Player))
            return;
        Player player = (Player) event.getEntity().getShooter();
        if (!GameUtil.checkPlayerInTheFFA(player))
            return;
        if (!FreeForAll.inGame.contains(player.getUniqueId()))
            return;
        if (!API.checkItemInHandWithItemMeta(player, "§cArtemis' Bow"))
            return;
        float random = new Random().nextFloat();
        if (random < 0.20 && random < 0.25)
            ItemRunnable.homing(FreeForAll.getInstance(), projectile);
    }
}
