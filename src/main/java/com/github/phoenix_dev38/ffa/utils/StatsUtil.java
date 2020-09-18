package com.github.phoenix_dev38.ffa.utils;

import com.github.phoenix_dev38.ffa.ScoreType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StatsUtil {

    public static void sendPlayerStats(Player player) {
        UUID uuid = player.getUniqueId();
        player.sendMessage("§b--- §a§l" + player.getName() + "のUHC-FFAの統計情報 §b---");
        player.sendMessage("§a§lキル: §b" + ScoreUtil.getScore(uuid, ScoreType.KILLS));
        player.sendMessage("§c§lデス: §b" + ScoreUtil.getScore(uuid, ScoreType.DEATHS));
        player.sendMessage("§e§lスコア: §b" + ScoreUtil.getScore(uuid, ScoreType.KILLS));
        player.sendMessage("§a§lキ§r/§c§lデ: §b" + ScoreUtil.calcKDRate(player));
        player.sendMessage("§6§lコイン: §b" + ScoreUtil.getScore(uuid, ScoreType.COINS));
        player.sendMessage("");
    }
}
