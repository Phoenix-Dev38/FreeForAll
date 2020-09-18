package com.github.phoenix_dev38.ffa;

import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.ffa.utils.ScoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public class GameScoreboard {

    public static void createGameScoreboard(Player player) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();

        player.setScoreboard(sb);

        Objective obj = sb.getObjective("freeforall") != null ? sb.getObjective("freeforall")
                : sb.registerNewObjective("freeforall", "sb");

        obj.setDisplayName("§a§l§oUHC-FreeForAll");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Objective obj_health = sb.getObjective("health") != null ? sb.getObjective("health")
                : sb.registerNewObjective("health", "sb");

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (GameUtil.checkPlayerInTheFFA(onlinePlayer)) {
                obj_health.setDisplayName("§c❤");
                obj_health.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
        }

        UUID uuid = player.getUniqueId();

        Score titleKills = obj.getScore("§a§lキル:");
        titleKills.setScore(20);

        Score kills = obj.getScore("§7➤ §a§b" + ScoreUtil.getScore(uuid, ScoreType.KILLS));
        kills.setScore(19);

        Score space1 = obj.getScore("");
        space1.setScore(18);

        Score titleDeaths = obj.getScore("§c§lデス:");
        titleDeaths.setScore(17);

        Score deaths = obj.getScore("§7➤ §b§b" + ScoreUtil.getScore(uuid, ScoreType.DEATHS));
        deaths.setScore(16);

        Score space2 = obj.getScore(" ");
        space2.setScore(15);

        Score titleScores = obj.getScore("§e§lスコア:");
        titleScores.setScore(14);

        Score scores = obj.getScore("§7➤ §c§b" + ScoreUtil.getScore(uuid, ScoreType.KILLS));
        scores.setScore(13);

        Score space3 = obj.getScore("  ");
        space3.setScore(12);

        Score titleKDa = obj.getScore("§a§lキ§r/§c§lデ:");
        titleKDa.setScore(11);

        Score kda = obj.getScore("§7➤ §d§b" + ScoreUtil.calcKDRate(player));
        kda.setScore(10);

        Score space4 = obj.getScore("   ");
        space4.setScore(9);

        Score titleCoins = obj.getScore("§6§lコイン:");
        titleCoins.setScore(8);

        Score coins = obj.getScore("§7➤ §e§b" + ScoreUtil.getScore(uuid, ScoreType.COINS));
        coins.setScore(7);
    }

    public static void updateGameScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        createGameScoreboard(player);
    }
}
