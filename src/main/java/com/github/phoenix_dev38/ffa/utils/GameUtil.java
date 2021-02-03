package com.github.phoenix_dev38.ffa.utils;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.GameScoreboard;
import com.github.phoenix_dev38.ffa.YamlFile;
import com.github.phoenix_dev38.ffa.exts.block.DatabaseUtil;
import com.github.phoenix_dev38.ffa.exts.sk.NormalKit;
import com.github.phoenix_dev38.gs.GlobalScoreboard;
import com.github.phoenix_dev38.iapi.API;
import com.github.phoenix_dev38.pa.ManagementUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameUtil {

    public static Map<Player, Boolean> teleportCoolTime = new HashMap<>();

    public static void setPlayerSpawns(Player player, String locType) {
        String world = player.getWorld().getName();
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        float pitch = player.getLocation().getPitch();
        float yaw = player.getLocation().getYaw();

        YamlConfiguration yml = YamlFile.LOCATIONYAML;
        ArrayList<String> list = new ArrayList<>();
        String loc = world + ", " + x + ", " + y + ", " + z + ", " + pitch + ", " + yaw;
        list.add(loc);
        list.addAll(yml.getStringList("PlayerSpawns-" + locType));
        yml.set("PlayerSpawns-" + locType, list);
        try {
            yml.save(new File(YamlFile.LOCATIONFILEPATH));
            player.sendMessage(FreeForAll.PREFIX + "§aプレイヤーのスポーン地点を設定しました。");
        } catch (IOException e) {
            e.printStackTrace();
        }
        YamlFile.loadSetting();
        YamlFile.SETTINGYAML.set("Stage.world." + locType, world);
        YamlFile.saveSetting();
    }

    public static boolean checkPlayerInTheFFA(Player player) {
        YamlFile.loadSetting();
        return player.getWorld().getName().equals(YamlFile.SETTINGYAML.getString("Stage.world.Midium"));
    }

    public static void teleportPlayerToFFA(Player player) {
        List<String> list = YamlFile.LOCATIONYAML.getStringList("PlayerSpawns-Midium");
        Collections.shuffle(list);

        String targetLoc = list.get(0);

        String world = Bukkit.getWorld(targetLoc.split(", ")[0]).getName();
        double x = Double.parseDouble(targetLoc.split(", ")[1]);
        double y = Double.parseDouble(targetLoc.split(", ")[2]);
        double z = Double.parseDouble(targetLoc.split(", ")[3]);
        Location loc = new Location(Bukkit.getWorld(world), x, y, z);

        player.teleport(loc);
    }

    public static void teleport(Player player, int seconds) {
        if (!teleportCoolTime.containsKey(player))
            return;
        FreeForAll.ab.sendActionBar(player, "§6テレポートまで" + seconds + " 秒");
        player.playSound(player.getLocation(), Sound.CLICK, 100.0F, 1.0F);
        if (seconds > 1)
            Bukkit.getServer().getScheduler().runTaskLater(FreeForAll.getInstance(), () -> teleport(player, seconds - 1), 20);
        else {
            teleportCoolTime.remove(player);
            teleportCoolTime.put(player, true);
            Bukkit.getServer().getScheduler().runTaskLater(FreeForAll.getInstance(), () -> {
                if (teleportCoolTime.containsKey(player)) {
                    teleportPlayerToFFA(player);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 99));
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 100.0F, 1.0F);
                    FreeForAll.ab.sendActionBar(player, "");
                    teleportCoolTime.remove(player);
                }
            }, 20);
        }
    }

    public static void setPlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setMaxHealth(40);
        player.setHealth(40);
        player.spigot().setCollidesWithEntities(true);

        if (player.getInventory().contains(Material.BOOK))
            player.getInventory().remove(Material.BOOK);

        if (!player.getInventory().contains(Material.BOW))
            NormalKit.giveKit_Default(player);

        if (GameUtil.checkPlayerInTheFFA(player))
            giveBlockAndWaterBucket(player);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 99));

        FreeForAll.inGame.add(player.getUniqueId());
        GameScoreboard.updateGameScoreboard(player);

        teleportPlayerToFFA(player);
    }

    public static void setSpectator(Player player) {
        UUID uuid = player.getUniqueId();

        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setLevel(0);
        player.setExp(0);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.spigot().setCollidesWithEntities(false);

        player.getInventory().clear();
        Bukkit.getServer().getScheduler().runTaskLater(FreeForAll.getInstance(), () -> {
            API.setItemWithItemMeta(Material.BOOK, "§aキット選択(右クリック)", player, 2);
            API.setItemWithItemMeta(Material.BED, "§bロビーに戻る(右クリック)", player, 6);
        }, 10);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, (int) 0.5));

        FreeForAll.inGame.remove(uuid);
        GameScoreboard.updateGameScoreboard(player);
    }

    public static void setLoser(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(false);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setLevel(0);
        player.setExp(0);
        player.spigot().setCollidesWithEntities(true);

        player.getInventory().clear();
        API.setItemWithItemMeta(Material.BOOK, "§aPvP-GUI（右クリック）", player, 4);
        API.setArmorAir(player);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        FreeForAll.inGame.remove(player.getUniqueId());
        GlobalScoreboard.sendGlobalScoreboard(player);

        ManagementUtil.teleportPlayerToTheMainSpawn(player);
    }

    public static void giveBlockAndWaterBucket(Player player) {
        if (GameUtil.checkPlayerInTheFFA(player))
            DatabaseUtil.givePlayerBlock(player);
        player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
    }

    public static void removeWaterAndGiveWaterBucketFromRelative(BlockFace blockFace, Block block, Player player) {
        if (!player.getInventory().contains(new ItemStack(Material.WATER_BUCKET)))
            return;
        switch (blockFace) {
            case UP:
                block = block.getLocation().add(0, 1, 0).getBlock();
                removeWaterAndGiveWaterBucket(block, player);
                break;
            case DOWN:
                block = block.getLocation().add(0, -1, 0).getBlock();
                removeWaterAndGiveWaterBucket(block, player);
                break;
            case NORTH:
                block = block.getLocation().add(0, 0, -1).getBlock();
                removeWaterAndGiveWaterBucket(block, player);
                break;
            case SOUTH:
                block = block.getLocation().add(0, 0, 1).getBlock();
                removeWaterAndGiveWaterBucket(block, player);
                break;
            case EAST:
                block = block.getLocation().add(1, 0, 0).getBlock();
                removeWaterAndGiveWaterBucket(block, player);
                break;
            case WEST:
                block = block.getLocation().add(-1, 0, 0).getBlock();
                removeWaterAndGiveWaterBucket(block, player);
                break;
        }
    }

    public static void removeWaterAndGiveWaterBucket(Block block, Player player) {
        switch (block.getType()) {
            case WATER:
            case STATIONARY_WATER:
                Bukkit.getServer().getScheduler().runTaskLater(FreeForAll.getInstance(), () -> {
                    block.setType(Material.AIR);
                    if (player.getGameMode().equals(GameMode.SURVIVAL) && checkPlayerInTheFFA(player)) {
                        if (!player.getInventory().contains(new ItemStack(Material.WATER_BUCKET))) {
                            player.getInventory().remove(new ItemStack(Material.BUCKET));
                            player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                        }
                    }
                }, 60);
        }
    }

    public static void giveHead(Player entity, Player killer) {
        for (ItemStack item : entity.getInventory().getContents()) {
            if (item != null && item.getType().equals(Material.SKULL_ITEM)) {
                killer.getInventory().addItem(item);
                return;
            }
        }
    }

    public static void giveArrow(Player player) {
        if (API.itemCheck(player, "§aArcher Bow"))
            return;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType().equals(Material.ARROW)) {
                player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
                return;
            }
        }
        player.getInventory().setItem(8, new ItemStack(Material.ARROW, 16));
    }
}
