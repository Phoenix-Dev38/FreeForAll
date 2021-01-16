package com.github.phoenix_dev38.ffa.exts.eu;

import com.github.phoenix_dev38.ffa.exts.eu.anduril.GiveItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cこのコマンドはプレイヤーのみ実行可能です。");
            return true;
        }
        if (command.getName().equalsIgnoreCase("eu")) {
            Player player = (Player) sender;
            if (!player.hasPermission("eu.use")) {
                player.sendMessage("§cあなたは権限を持っていません。");
                return true;
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (args[1].equalsIgnoreCase("anduril")) GiveItem.giveExtraUltimate(player);
                    else if (args[1].equalsIgnoreCase("artemisbow"))
                        com.github.phoenix_dev38.ffa.exts.eu.artemisbow.GiveItem.giveExtraUltimate(player);
                    else if (args[1].equalsIgnoreCase("axeofperun"))
                        com.github.phoenix_dev38.ffa.exts.eu.axeofperun.GiveItem.giveExtraUltimate(player);
                    else if (args[1].equalsIgnoreCase("cornucopia"))
                        com.github.phoenix_dev38.ffa.exts.eu.cornucopia.GiveItem.giveExtraUltimate(player);
                    else if (args[1].equalsIgnoreCase("daredevil")) {
                        player.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
                        player.getInventory().addItem(new ItemStack(Material.SADDLE));
                        player.getInventory().addItem(new ItemStack(Material.BONE, 5));
                    } else if (args[1].equalsIgnoreCase("deathsscythe"))
                        com.github.phoenix_dev38.ffa.exts.eu.deaths_scythe.GiveItem.giveExtraUltimate(player);
                    else if (args[1].equalsIgnoreCase("excalibur"))
                        com.github.phoenix_dev38.ffa.exts.eu.excalibur.GiveItem.giveExtraUltimate(player);
                    else if (args[1].equalsIgnoreCase("exodus"))
                        com.github.phoenix_dev38.ffa.exts.eu.exodus.GiveItem.giveExtraUltimate(player);
                }
            }
        }
        return false;
    }
}
