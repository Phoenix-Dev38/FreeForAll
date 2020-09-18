package com.github.phoenix_dev38.ffa.commands;

import com.github.phoenix_dev38.ffa.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("stats")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cこのコマンドはプレイヤーのみ実行可能です。");
                return true;
            }
            Player player = (Player) sender;
            switch (args.length) {
                case 1:
                    if (args[0].equalsIgnoreCase("freeforall") || args[0].equalsIgnoreCase("ffa"))
                        StatsUtil.sendPlayerStats(player);
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("freeforall") || args[0].equalsIgnoreCase("ffa")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage("§cプレイヤーが見つかりません。");
                            return true;
                        }
                        StatsUtil.sendPlayerStats(target);
                    }
                    break;
            }
        }
        return false;
    }
}
