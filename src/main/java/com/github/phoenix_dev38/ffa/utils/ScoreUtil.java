package com.github.phoenix_dev38.ffa.utils;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.ScoreType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ScoreUtil {

    static double kills;
    static double deaths;
    static String returns;

    public static void putScore(UUID uuid, ScoreType scoreType) {
        switch (scoreType) {
            case KILLS:
                if (!FreeForAll.kills.containsKey(uuid)) {
                    FreeForAll.kills.put(uuid, DatabaseUtil.getScore(uuid, ScoreType.KILLS));
                    return;
                }
                FreeForAll.kills.put(uuid, FreeForAll.kills.get(uuid) + 1);
                break;
            case DEATHS:
                if (!FreeForAll.deaths.containsKey(uuid)) {
                    FreeForAll.deaths.put(uuid, DatabaseUtil.getScore(uuid, ScoreType.DEATHS));
                    return;
                }
                FreeForAll.deaths.put(uuid, FreeForAll.deaths.get(uuid) + 1);
                break;
            case COINS:
                if (!FreeForAll.coins.containsKey(uuid)) {
                    FreeForAll.coins.put(uuid, DatabaseUtil.getScore(uuid, ScoreType.COINS));
                    return;
                }
                FreeForAll.coins.put(uuid, FreeForAll.coins.get(uuid) + 10);
                break;
        }
    }

    public static int getScore(UUID uuid, ScoreType scoreType) {
        switch (scoreType) {
            case KILLS:
                if (!FreeForAll.kills.containsKey(uuid))
                    return 0;
                return FreeForAll.kills.get(uuid);
            case DEATHS:
                if (!FreeForAll.deaths.containsKey(uuid))
                    return 0;
                return FreeForAll.deaths.get(uuid);
            case COINS:
                if (!FreeForAll.coins.containsKey(uuid))
                    return 0;
                return FreeForAll.coins.get(uuid);
        }
        return 0;
    }

    public static void putScoreToDatabase(UUID uuid) {
        DatabaseUtil.setScore(uuid, ScoreType.KILLS, getScore(uuid, ScoreType.KILLS));
        DatabaseUtil.setScore(uuid, ScoreType.DEATHS, getScore(uuid, ScoreType.DEATHS));
        DatabaseUtil.setScore(uuid, ScoreType.COINS, getScore(uuid, ScoreType.COINS));
    }

    public static void putScoreKills() {
        for (UUID uuid : DatabaseUtil.getUUIDs())
            FreeForAll.kills.put(uuid, DatabaseUtil.getScore(uuid, ScoreType.KILLS));
    }

    public static String calcKDRate(Player player) {
        UUID uuid = player.getUniqueId();
        kills = getScore(uuid, ScoreType.KILLS);
        deaths = getScore(uuid, ScoreType.DEATHS);
        if (kills == 0 && deaths == 0)
            returns = String.valueOf(0.0);
        else if (kills != 0 && deaths == 0)
            returns = Double.toString(kills);
        else returns = String.format("%.1f", kills / deaths);
        return returns;
    }
}
