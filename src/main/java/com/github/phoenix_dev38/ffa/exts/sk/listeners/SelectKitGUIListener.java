package com.github.phoenix_dev38.ffa.exts.sk.listeners;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.exts.eu.deaths_scythe.GiveItem;
import com.github.phoenix_dev38.ffa.exts.is.KitType;
import com.github.phoenix_dev38.ffa.exts.is.utils.DatabaseUtil;
import com.github.phoenix_dev38.ffa.exts.sk.NormalKit;
import com.github.phoenix_dev38.ffa.exts.sk.guis.SelectKitGUI;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.iapi.API;
import com.github.phoenix_dev38.pa.ManagementUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.UUID;

public class SelectKitGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws SQLException {
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();

        if (inventory == null)
            return;
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        switch (inventory.getName()) {
            case "§bキット選択 - 種類":
                event.setCancelled(true);
                if (item == null || !item.hasItemMeta())
                    return;
                if (item.getItemMeta().getDisplayName().equals("§aノーマル"))
                    SelectKitGUI.openSelectKitGUI_Normal(player);
                else if (item.getItemMeta().getDisplayName().equals("§cエクストラ(UHC Kit)"))
                    SelectKitGUI.openSelectKitGUI_Extra(player);
                break;
            case "§aキット選択 - ノーマル":
                event.setCancelled(true);
                if (item == null || !item.hasItemMeta())
                    return;
                player.closeInventory();
                switch (item.getItemMeta().getDisplayName()) {
                    case "§aKit - Default":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.NORMAL, 1))
                            DatabaseUtil.givePlayerInv(uuid, KitType.NORMAL, 1);
                        else NormalKit.giveKit_Default(player);
                        player.sendMessage(FreeForAll.PREFIX + "§a§lDefault キット§aを選択しました。");
                        break;
                    case "§aKit - Archer":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.NORMAL, 2))
                            DatabaseUtil.givePlayerInv(uuid, KitType.NORMAL, 2);
                        else NormalKit.giveKit_Archer(player);
                        player.sendMessage(FreeForAll.PREFIX + "§a§lArcher キット§aを選択しました。");
                        break;
                    case "§aKit - Strength":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.NORMAL, 3))
                            DatabaseUtil.givePlayerInv(uuid, KitType.NORMAL, 3);
                        else NormalKit.giveKit_Strength(player);
                        player.sendMessage(FreeForAll.PREFIX + "§a§lStrength キット§aを選択しました。");
                        break;
                    case "§aKit - Teleport":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.NORMAL, 4))
                            DatabaseUtil.givePlayerInv(uuid, KitType.NORMAL, 4);
                        else NormalKit.giveKit_Teleport(player);
                        player.sendMessage(FreeForAll.PREFIX + "§a§lTeleport キット§aを選択しました。");
                        break;
                }
                player.getInventory().remove(Material.BOOK);
                if (ManagementUtil.checkPlayerInTheMainSpawn(player))
                    API.addItemWithItemMeta(Material.BOOK, "§aPvP-GUI（右クリック）", player);
                else if (GameUtil.checkPlayerInTheFFA(player)) GameUtil.setPlayer(player);
                break;
            case "§aキット選択 - エクストラ":
                event.setCancelled(true);
                if (item == null || !item.hasItemMeta())
                    return;
                player.closeInventory();
                switch (item.getItemMeta().getDisplayName()) {
                    case "§cKit - Artemis' Bow":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.EXTRAULTIMATE, 1))
                            DatabaseUtil.givePlayerInv(uuid, KitType.EXTRAULTIMATE, 1);
                        else com.github.phoenix_dev38.ffa.exts.eu.artemisbow.GiveItem.giveKit(player);
                        player.sendMessage(FreeForAll.PREFIX + "§c§lArtemis' Bow キット§aを選択しました。");
                        break;
                    case "§cKit - §6Exodus":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.EXTRAULTIMATE, 2))
                            DatabaseUtil.givePlayerInv(uuid, KitType.EXTRAULTIMATE, 2);
                        else com.github.phoenix_dev38.ffa.exts.eu.exodus.GiveItem.giveKit(player);
                        player.sendMessage(FreeForAll.PREFIX + "§c§lExodus キット§aを選択しました。");
                        break;
                    case "§cKit - §6Axe of Perun":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.EXTRAULTIMATE, 3))
                            DatabaseUtil.givePlayerInv(uuid, KitType.EXTRAULTIMATE, 3);
                        else com.github.phoenix_dev38.ffa.exts.eu.axeofperun.GiveItem.giveKit(player);
                        player.sendMessage(FreeForAll.PREFIX + "§c§lAxe of Perun キット§aを選択しました。");
                        break;
                    case "§cKit - §6Excalibur":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.EXTRAULTIMATE, 4))
                            DatabaseUtil.givePlayerInv(uuid, KitType.EXTRAULTIMATE, 4);
                        else com.github.phoenix_dev38.ffa.exts.eu.excalibur.GiveItem.giveKit(player);
                        player.sendMessage(FreeForAll.PREFIX + "§c§lExcalibur キット§aを選択しました。");
                        break;
                    case "§cKit - §aAndùril":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.EXTRAULTIMATE, 5))
                            DatabaseUtil.givePlayerInv(uuid, KitType.EXTRAULTIMATE, 5);
                        else com.github.phoenix_dev38.ffa.exts.eu.anduril.GiveItem.giveKit(player);
                        player.sendMessage(FreeForAll.PREFIX + "§c§lAndùril キット§aを選択しました。");
                        break;
                    case "§cKit - §dDeath's Scythe":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.EXTRAULTIMATE, 6))
                            DatabaseUtil.givePlayerInv(uuid, KitType.EXTRAULTIMATE, 6);
                        else GiveItem.giveKit(player);
                        player.sendMessage(FreeForAll.PREFIX + "§c§lDeath's Scythe キット§aを選択しました。");
                        break;
                    case "§cKit - §6Cornucopia":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.EXTRAULTIMATE, 7))
                            DatabaseUtil.givePlayerInv(uuid, KitType.EXTRAULTIMATE, 7);
                        else com.github.phoenix_dev38.ffa.exts.eu.cornucopia.GiveItem.giveKit(player);
                        player.sendMessage(FreeForAll.PREFIX + "§c§lCornucopia キット§aを選択しました。");
                        break;
                    case "§cKit - Daredevil":
                        if (DatabaseUtil.hasSavedPlayerInv(uuid, KitType.EXTRAULTIMATE, 8))
                            DatabaseUtil.givePlayerInv(uuid, KitType.EXTRAULTIMATE, 8);
                        else com.github.phoenix_dev38.ffa.exts.eu.daredevil.GiveItem.giveKit(player);
                        player.sendMessage(FreeForAll.PREFIX + "§c§lDaredevil キット§aを選択しました。");
                        break;
                }
                player.getInventory().remove(Material.BOOK);
                if (ManagementUtil.checkPlayerInTheMainSpawn(player))
                    API.addItemWithItemMeta(Material.BOOK, "§aPvP-GUI（右クリック）", player);
                else if (GameUtil.checkPlayerInTheFFA(player)) GameUtil.setPlayer(player);
                break;
        }
    }
}
