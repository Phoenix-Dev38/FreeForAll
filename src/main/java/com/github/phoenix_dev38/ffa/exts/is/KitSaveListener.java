package com.github.phoenix_dev38.ffa.exts.is;

import com.github.phoenix_dev38.ffa.exts.is.utils.ConverterUtil;
import com.github.phoenix_dev38.ffa.exts.is.utils.DatabaseUtil;
import com.github.phoenix_dev38.iapi.API;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.UUID;

public class KitSaveListener implements Listener {

    UUID uuid;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        this.uuid = event.getPlayer().getUniqueId();
        if (!DatabaseUtil.existPlayerInv(this.uuid))
            DatabaseUtil.createPlayerInv(this.uuid);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        if (!event.getClickedBlock().getType().equals(Material.WALL_SIGN))
            return;
        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!(sign.getLine(0).equalsIgnoreCase("§a[InventorySave]")
                && sign.getLine(1).equalsIgnoreCase("§cClick here!")))
            return;
        Player player = event.getPlayer();
        this.uuid = player.getUniqueId();
        String[] base64 = ConverterUtil.playerInventoryToBase64(player.getInventory());
        String inv = base64[0];
        String armor = base64[1];
        if (API.itemCheck(player, "§aDefault Sword"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.NORMAL, 1, inv, armor);
        else if (API.itemCheck(player, "§aArcher Bow"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.NORMAL, 2, inv, armor);
        else if (API.itemCheck(player, "§aStrength Sword"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.NORMAL, 3, inv, armor);
        else if (API.itemCheck(player, "§aTeleport Sword"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.NORMAL, 4, inv, armor);
        else if (API.itemCheck(player, "§cArtemis' Bow"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.EXTRAULTIMATE, 1, inv, armor);
        else if (API.itemCheck(player, "§6Exodus"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.EXTRAULTIMATE, 2, inv, armor);
        else if (API.itemCheck(player, "§6Axe of Perun"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.EXTRAULTIMATE, 3, inv, armor);
        else if (API.itemCheck(player, "§6Excalibur"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.EXTRAULTIMATE, 4, inv, armor);
        else if (API.itemCheck(player, "§aAndúril"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.EXTRAULTIMATE, 5, inv, armor);
        else if (API.itemCheck(player, "§dDeath's Scythe"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.EXTRAULTIMATE, 6, inv, armor);
        else if (API.itemCheck(player, "§6Cornucopia"))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.EXTRAULTIMATE, 7, inv, armor);
        else if (API.itemCheck(player, Material.SADDLE))
            DatabaseUtil.savePlayerInv(this.uuid, KitType.EXTRAULTIMATE, 8, inv, armor);
        else {
            player.sendMessage("§cあなたはFFA Kitを持っていません。");
            return;
        }
        player.sendMessage("§aFFA Kitのインベントリの保存が完了しました。");
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("si") || event.getLine(0).equalsIgnoreCase("save inventory")) {
            event.setLine(0, "§a[InventorySave]");
            event.setLine(1, "§cClick here!");
        }
    }
}
