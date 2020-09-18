package com.github.phoenix_dev38.ffa.ext.prestige.listeners;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ext.prestige.DatabaseUtil;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class PrestigeListener implements Listener {

    public static HashMap<Player, Integer> laLimit = new HashMap<>();
    public static HashMap<Player, Integer> ghLimit = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!DatabaseUtil.existPlayerPrestige(uuid))
            DatabaseUtil.createPlayerPrestige(uuid);
        if (laLimit.containsKey(player))
            laLimit.clear();
        if (ghLimit.containsKey(player))
            ghLimit.clear();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() != null && event.getEntity().getKiller() != null) {
            Player entity = event.getEntity();
            Player killer = entity.getKiller();
            if (GameUtil.checkPlayerInTheFFA(entity) && GameUtil.checkPlayerInTheFFA(killer)) {
                if (FreeForAll.inGame.contains(entity.getUniqueId()) && FreeForAll.inGame.contains(killer.getUniqueId())) {
                    if (laLimit.containsKey(entity))
                        laLimit.clear();
                    if (ghLimit.containsKey(entity))
                        ghLimit.clear();
                }
            }
        }
    }
}
