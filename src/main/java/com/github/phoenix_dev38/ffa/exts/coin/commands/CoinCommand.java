package com.github.phoenix_dev38.ffa.exts.coin.commands;

import com.github.phoenix_dev38.ffa.ScoreType;
import com.github.phoenix_dev38.ffa.utils.ScoreUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinCommand implements CommandExecutor {

    public static String PREFIX = "§e§l[Coin] §r";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cこのコマンドはプレイヤーのみ実行可能です。");
            return true;
        }
        if (command.getName().equalsIgnoreCase("coin")) {
            Player player = (Player) sender;
            player.sendMessage(PREFIX + "§a所持コイン: §e" + ScoreUtil.getScore(player.getUniqueId(), ScoreType.COINS));
        }
        return false;
    }
}
