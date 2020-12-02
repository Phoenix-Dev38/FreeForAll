package com.github.phoenix_dev38.ffa.commands;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.Ranking;
import com.github.phoenix_dev38.ffa.YamlFile;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.piapi.API;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FreeForAllCommand implements CommandExecutor {

    UUID uuid;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("freeforall")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cこのコマンドはプレイヤーのみ実行可能です。");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("freeforall.use")) {
                player.sendMessage("§cあなたは権限を持っていません。");
                return true;
            }
            switch (args.length) {
                case 0:
                    sendPlayerOfCommandDescription(player);
                    break;
                case 1:
                    this.uuid = player.getUniqueId();
                    if (args[0].equalsIgnoreCase("help")) {
                        sendPlayerOfCommandDescription(player);
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        YamlFile.loadLocation();
                        YamlFile.loadSetting();
                        player.sendMessage(FreeForAll.PREFIX + "§aFreeForAll内のファイルを再読み込みしました。");
                    } else if (args[0].equalsIgnoreCase("join")) {
                        if (!FreeForAll.inGame.contains(uuid)) {
                            FreeForAll.inGame.add(this.uuid);
                            player.sendMessage(FreeForAll.PREFIX + "§aFFAに参加しました。");
                        } else player.sendMessage(FreeForAll.PREFIX + "§cFFAには既に参加しています。");
                    } else if (args[0].equalsIgnoreCase("leave")) {
                        if (FreeForAll.inGame.contains(uuid)) {
                            FreeForAll.inGame.remove(this.uuid);
                            player.sendMessage(FreeForAll.PREFIX + "§aFFAから抜けました。");
                        } else player.sendMessage(FreeForAll.PREFIX + "§cFFAには参加していません。");
                    } else if (args[0].equalsIgnoreCase("dt")) {
                        API.setItemWithItemMeta(Material.BOOK, "§aキット選択(右クリック)", player, 2);
                        API.setItemWithItemMeta(Material.BED, "§bロビーに戻る(右クリック)", player, 6);
                    }
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("tp")) {
                        if (args[1].equalsIgnoreCase("ps"))
                            GameUtil.teleportPlayerToFFA(player);
                    } else if (args[0].equalsIgnoreCase("rt")) {
                        int number;
                        try {
                            number = Integer.parseInt(args[1]);
                            Ranking.showRanking(player, number);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            player.sendMessage("§c数字のみを入力してください。");
                            return true;
                        }
                    }
                    break;
                case 3:
                    if (args[0].equalsIgnoreCase("set")) {
                        if (args[1].equalsIgnoreCase("ps")) {
                            String locType = args[2];
                            switch (locType) {
                                case "Small":
                                case "Midium":
                                case "Large":
                                    GameUtil.setPlayerSpawns(player, locType);
                                    break;
                            }
                        }
                    }
            }
        }
        return false;
    }

    private void sendPlayerOfCommandDescription(Player player) {
        player.sendMessage("§c--- §a§lFreeForAll §bCommand List §c---");
        player.sendMessage("§b/ffa help §a<当プラグインのコマンドリストを表示します。>");
        player.sendMessage("§b/ffa reload §a<FreeForAll内のファイルを再読み込みします。>");
        player.sendMessage("§b/ffa join §a<FreeForAllに参加します。>");
        player.sendMessage("§b/ffa leave §a<FreeForAllから抜けます。>");
        player.sendMessage("§b/ffa dt §a<死んだときのアイテムを配布します。>");
        player.sendMessage("§b/ffa tp ps §a<FreeForAllのStageにテレポートします。>");
        player.sendMessage("§b/ffa rt <Number> §a<<Number>で指定したランキングを表示します。>");
        player.sendMessage("§b/ffa set ps <LocationType> §a<FreeForAllのプレイヤーのテレポート地点<LocationType>を設定します。>");
    }
}
