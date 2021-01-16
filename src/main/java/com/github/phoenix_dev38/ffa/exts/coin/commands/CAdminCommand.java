package com.github.phoenix_dev38.ffa.exts.coin.commands;

import com.github.phoenix_dev38.ffa.ScoreType;
import com.github.phoenix_dev38.ffa.utils.DatabaseUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cこのコマンドはプレイヤーのみ実行可能です。");
            return true;
        }
        if (command.getName().equalsIgnoreCase("cadmin")) {
            Player player = (Player) sender;
            UUID uuid;
            if (!player.hasPermission("cadmin.use")) {
                player.sendMessage("§cあなたは権限を持っていません。");
                return true;
            }
            switch (args.length) {
                case 0:
                    sendPlayerOfCommandDescription(player);
                    break;
                case 1:
                    if (args[0].equalsIgnoreCase("help")) {
                        sendPlayerOfCommandDescription(player);
                    }
                    break;
                case 2:
                    uuid = player.getUniqueId();
                    int senderNum;
                    try {
                        senderNum = Integer.parseInt(args[1]);
                        if (args[0].equalsIgnoreCase("set")) {
                            DatabaseUtil.setScore(uuid, ScoreType.COINS, senderNum);
                            player.sendMessage(CoinCommand.PREFIX + "§eコイン数§b(" + senderNum + ")§eにセットしました。");
                        } else if (args[0].equalsIgnoreCase("add")) {
                            DatabaseUtil.addScore(uuid, ScoreType.COINS, senderNum);
                            player.sendMessage(CoinCommand.PREFIX + "§eコイン数§b(" + senderNum + ")§eを追加しました。");
                        } else if (args[0].equalsIgnoreCase("remove")) {
                            DatabaseUtil.removeScore(uuid, ScoreType.COINS, senderNum);
                            player.sendMessage(CoinCommand.PREFIX + "§eコイン数§b(" + senderNum + ")§eを削減しました。");
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage("§c数字のみを入力してください。");
                    }
                    break;
                case 3:
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage("§cプレイヤーが見つかりませんでした。");
                        return true;
                    }
                    uuid = target.getUniqueId();
                    int targetNum;
                    try {
                        targetNum = Integer.parseInt(args[2]);
                        if (args[0].equalsIgnoreCase("set")) {
                            DatabaseUtil.setScore(uuid, ScoreType.COINS, targetNum);
                            player.sendMessage(CoinCommand.PREFIX + "§a" + target.getName() + "§eのコイン数§b(" + targetNum + ")§eをセットしました。");
                        } else if (args[0].equalsIgnoreCase("add")) {
                            DatabaseUtil.addScore(uuid, ScoreType.COINS, targetNum);
                            player.sendMessage(CoinCommand.PREFIX + "§a" + target.getName() + "§eのコイン数§b(" + targetNum + ")§eを追加しました。");
                        } else if (args[0].equalsIgnoreCase("remove")) {
                            DatabaseUtil.removeScore(uuid, ScoreType.COINS, targetNum);
                            player.sendMessage(CoinCommand.PREFIX + "§a" + target.getName() + "§eのコイン数§b(" + targetNum + ")§eを削減しました。");
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage("§c数字のみを入力してください。");
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + args.length);
            }
        }
        return false;
    }

    private void sendPlayerOfCommandDescription(Player player) {
        player.sendMessage("§c--- §a§lFreeForAll-Coin §bCommand List §c---");
        player.sendMessage("§b/cadmin help §a<当プラグインのコマンドリストを表示します。>");
        player.sendMessage("§b/cadmin set (amount) §a<プレイヤーの所持コインに(amount)をセットします。>");
        player.sendMessage("§b/cadmin add (amount) §a<プレイヤーの所持コインに(amount)を追加します。>");
        player.sendMessage("§b/cadmin remove (amount) §a<プレイヤーの所持コインに(amount)を削減します。>");
        player.sendMessage("§b/cadmin set (MCID) (amount) §a<(MCID)の所持コインに(amount)をセットします。>");
        player.sendMessage("§b/cadmin add (MCID) (amount) §a<(MCID)の所持コインに(amount)を追加します。>");
        player.sendMessage("§b/cadmin remove (MCID) (amount) §a<(MCID)の所持コインに(amount)を削減します。>");
    }
}
