package com.github.phoenix_dev38.ffa.listeners;

import com.github.phoenix_dev38.ffa.FreeForAll;
import com.github.phoenix_dev38.ffa.GameScoreboard;
import com.github.phoenix_dev38.ffa.Ranking;
import com.github.phoenix_dev38.ffa.ScoreType;
import com.github.phoenix_dev38.ffa.utils.DatabaseUtil;
import com.github.phoenix_dev38.ffa.utils.GameUtil;
import com.github.phoenix_dev38.ffa.utils.ScoreUtil;
import com.github.phoenix_dev38.pa.ManagementUtil;
import com.github.phoenix_dev38.iapi.API;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameListener implements Listener {

    UUID uuid;

    List<String> list = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        this.uuid = event.getPlayer().getUniqueId();
        if (!DatabaseUtil.existPlayerStats(this.uuid))
            DatabaseUtil.createPlayerStats(this.uuid);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.uuid = player.getUniqueId();
        if (!GameUtil.checkPlayerInTheFFA(player))
            return;
        if (!FreeForAll.inGame.contains(this.uuid))
            return;
        FreeForAll.inGame.remove(this.uuid);
        String entityName = "§a" + player.getName() + "'s §cZombie";
        if (list.contains(entityName))
            return;
        list.add(entityName);

        ScoreUtil.putScore(this.uuid, ScoreType.DEATHS);

        Zombie zombie = player.getLocation().getWorld().spawn(player.getLocation(), Zombie.class);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skull.setDurability((short) 3);
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName("§a" + player.getName() + "'s §cHead.");
        skull.setItemMeta(skullMeta);

        zombie.setCustomName(entityName);
        zombie.setBaby(false);

        zombie.getEquipment().setHelmet(skull);
        zombie.getEquipment().setChestplate(new ItemStack(Material.AIR));
        zombie.getEquipment().setLeggings(new ItemStack(Material.AIR));
        zombie.getEquipment().setBoots(new ItemStack(Material.AIR));

        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 100));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.getDrops().clear();

        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (event.getEntity() != null && event.getEntity().getKiller() != null) {
            Player entity = event.getEntity();
            Player killer = entity.getKiller();
            if (!(GameUtil.checkPlayerInTheFFA(entity) && GameUtil.checkPlayerInTheFFA(killer)))
                return;
            UUID entity_uuid = entity.getUniqueId();
            UUID killer_uuid = killer.getUniqueId();
            if (!(FreeForAll.inGame.contains(entity_uuid)
                    && FreeForAll.inGame.contains(killer_uuid)))
                return;
            ScoreUtil.putScore(killer_uuid, ScoreType.KILLS);
            ScoreUtil.putScore(entity_uuid, ScoreType.DEATHS);
            ScoreUtil.putScore(killer_uuid, ScoreType.COINS);
            ScoreUtil.putScoreToDatabase(killer_uuid);
            ScoreUtil.putScoreToDatabase(entity_uuid);

            FreeForAll.kills.put(killer_uuid, ScoreUtil.getScore(killer_uuid, ScoreType.KILLS));
            Ranking.updateRanking();

            GameScoreboard.updateGameScoreboard(entity);
            GameScoreboard.updateGameScoreboard(killer);

            String killMsg;
            killMsg = FreeForAll.PREFIX + "§c" + entity.getName() + "§bを倒しました。";
            String deathMsg;
            deathMsg = FreeForAll.PREFIX + "§c" + killer.getName() + "§bによって倒されました。";

            killer.sendMessage(killMsg);
            entity.sendMessage(deathMsg);

            killer.getWorld().strikeLightningEffect(entity.getLocation());

            killer.setLevel(killer.getLevel() + 1);

            killer.getInventory().addItem(new ItemStack(Material.APPLE, 1));
            killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 4));

            killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0));
            killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));

            GameUtil.giveHead(entity, killer);
            GameUtil.giveArrow(killer);

            GameUtil.setSpectator(entity);

            killer.playSound(killer.getLocation(), Sound.IRONGOLEM_DEATH, 50.0F, 1.0F);
            killer.playSound(killer.getLocation(), Sound.ANVIL_USE, 10.0F, 1.0F);
            killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 100.0F, 1.0F);

            skull.setDurability((short) 3);
            skullMeta.setOwner(entity.getName());
            skullMeta.setDisplayName("§a" + entity.getName() + "'s §cHead.");
            skull.setItemMeta(skullMeta);

            entity.getWorld().dropItem(entity.getLocation(), skull);
        } else {
            Player entity = event.getEntity();
            if (!(GameUtil.checkPlayerInTheFFA(entity) && FreeForAll.inGame.contains(entity.getUniqueId())))
                return;
            UUID entity_uuid = entity.getUniqueId();

            ScoreUtil.putScore(entity_uuid, ScoreType.DEATHS);
            ScoreUtil.putScoreToDatabase(entity_uuid);

            GameUtil.setSpectator(entity);

            skull.setDurability((short) 3);
            skullMeta.setOwner(entity.getName());
            skullMeta.setDisplayName("§a" + entity.getName() + "'s §cHead.");
            skull.setItemMeta(skullMeta);

            entity.getWorld().dropItem(entity.getLocation(), skull);

            entity.getWorld().strikeLightningEffect(entity.getLocation());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        /*if (!ManagementUtil.checkPlayerInTheMain(player))
            return;*/
        if (event.getFrom().getBlockX() != event.getTo().getBlockX() ||
                event.getFrom().getBlockY() != event.getTo().getBlockY() ||
                event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            if (!GameUtil.teleportCoolTime.containsKey(player)) {
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                if (block.getType().equals(Material.WOOL) && block.getData() == 5) {
                    GameUtil.teleportCoolTime.put(player, Boolean.FALSE);
                    GameUtil.teleport(player, 3);
                }
            } else {
                GameUtil.teleportCoolTime.remove(player);
                FreeForAll.ab.sendActionBar(player, "");
                player.sendMessage("§c動いた為、テレポートをキャンセルしました。");
            }
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        this.uuid = player.getUniqueId();
        if (GameUtil.checkPlayerInTheFFA(player)) {
            if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
                return;
            GameUtil.setPlayer(player);
        } else {
            if (player.getDisplayName().contains("☆"))
                player.setDisplayName(player.getDisplayName().substring(6));
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(false);
            player.setMaxHealth(20);
            player.setHealth(20);
            player.setLevel(0);
            player.setExp(0);

            player.getInventory().clear();
            API.setItemWithItemMeta(Material.BOOK, "§aPvP-GUI（右クリック）", player, 4);
            API.setArmorAir(player);

            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());

            FreeForAll.inGame.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        Player player = event.getPlayer();
        if (player.getItemInHand() == null)
            return;
        ItemStack itemInHand = player.getItemInHand();
        if (!(itemInHand.getType().equals(Material.INK_SACK) && itemInHand.getDurability() == 15))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof Horse) {
            if (player.getAllowFlight())
                event.setCancelled(true);
        } else if (event.getRightClicked() instanceof Player) {
            if (player.getAllowFlight()) {
                Player target = (Player) event.getRightClicked();
                player.openInventory(target.getInventory());
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (ManagementUtil.checkPlayerInTheMainSpawn(player)) {
            if (!player.hasPermission("can.dropItem"))
                event.setCancelled(true);
        } else if (GameUtil.checkPlayerInTheFFA(player)) {
            if (FreeForAll.inGame.contains(event.getPlayer().getUniqueId()))
                event.setCancelled(true);
            if (player.getAllowFlight())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (ManagementUtil.checkPlayerInTheMainSpawn(player)) {
            if (!player.hasPermission("can.pickupItem"))
                event.setCancelled(true);
        } else if (GameUtil.checkPlayerInTheFFA(player)) {
            if (!FreeForAll.inGame.contains(player.getUniqueId()))
                return;
            ItemStack itemStack = event.getItem().getItemStack();
            if (!itemStack.getType().equals(Material.ARROW) || !itemStack.getType().equals(Material.SKULL_ITEM))
                player.setCanPickupItems(false);
            if (API.itemCheck(player, "§aArcher Bow") && itemStack.getType().equals(Material.ARROW))
                player.setCanPickupItems(false);
            if (player.getAllowFlight())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        if (!FreeForAll.inGame.contains(player.getUniqueId()))
            return;
        Block block = event.getBlockClicked();
        GameUtil.removeWaterAndGiveWaterBucketFromRelative(BlockFace.UP, block, player);
        GameUtil.removeWaterAndGiveWaterBucketFromRelative(BlockFace.DOWN, block, player);
        GameUtil.removeWaterAndGiveWaterBucketFromRelative(BlockFace.NORTH, block, player);
        GameUtil.removeWaterAndGiveWaterBucketFromRelative(BlockFace.SOUTH, block, player);
        GameUtil.removeWaterAndGiveWaterBucketFromRelative(BlockFace.EAST, block, player);
        GameUtil.removeWaterAndGiveWaterBucketFromRelative(BlockFace.WEST, block, player);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        if (!GameUtil.checkPlayerInTheFFA(player))
            return;
        if (displayName.contains("☆"))
            displayName = player.getDisplayName().substring(6);
        int score = ScoreUtil.getScore(player.getUniqueId(), ScoreType.KILLS);
        if (score >= 10210)
            displayName = player.getDisplayName().substring(7);
        if (score < 10) player.setDisplayName("§e[1☆]" + displayName);
        else if (score < 60) player.setDisplayName("§e[2☆]" + displayName);
        else if (score < 210) player.setDisplayName("§e[3☆]" + displayName);
        else if (score < 460) player.setDisplayName("§e[4☆]" + displayName);
        else if (score < 960) player.setDisplayName("§e[5☆]" + displayName);
        else if (score < 1710) player.setDisplayName("§e[6☆]" + displayName);
        else if (score < 2710) player.setDisplayName("§e[7☆]" + displayName);
        else if (score < 5210) player.setDisplayName("§e[8☆]" + displayName);
        else if (score < 10210) player.setDisplayName("§e[9☆]" + displayName);
        else player.setDisplayName("§e[10☆]" + displayName);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player))
            return;
        if (((Player) event.getTarget()).getAllowFlight())
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player || event.getEntity() instanceof Zombie || event.getEntity() instanceof Horse))
            return;
        if (!event.getCause().equals(DamageCause.FALL))
            return;
        if (ManagementUtil.checkPlayerInTheMainSpawn((Player) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player entity = (Player) event.getEntity();
            if (!GameUtil.checkPlayerInTheFFA(entity))
                return;
            if (entity == event.getDamager() || entity.getAllowFlight())
                event.setCancelled(true);
        } else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
            Player entity = (Player) event.getEntity();
            if (!GameUtil.checkPlayerInTheFFA(entity))
                return;
            if (entity == event.getDamager() || entity.getAllowFlight())
                event.setCancelled(true);
        }

        if (event.getDamager() instanceof Arrow) {
            Arrow damager = (Arrow) event.getDamager();
            if (!(damager.getShooter() instanceof Player))
                return;
            Player shooter = (Player) damager.getShooter();
            if (!(event.getEntity() instanceof Player))
                return;
            Damageable damageable = (Damageable) event.getEntity();
            Player entity = (Player) damageable;
            double health = damageable.getHealth();
            int damage = (int) event.getFinalDamage();
            int realHealth = (int) (health - damage);
            String realHealthMsg = String.format("%.1f", (double) realHealth);
            if (realHealth > 0)
                shooter.sendMessage("§b§l" + entity.getName() + "§aの残り体力は§c§l" + realHealthMsg + "§aです。");
            double distance = entity.getLocation().distance(shooter.getLocation());
            String distanceMsg = String.format("%.2f", distance);
            if (distance >= 30.0D && distance <= 150.0D) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (GameUtil.checkPlayerInTheFFA(onlinePlayer))
                        onlinePlayer.sendMessage(FreeForAll.PREFIX + "§a" + shooter.getName() + "§eは§a" +
                                entity.getName() + "§eに§c" + distanceMsg + "§eの距離から矢を当てました。");
                }
            }
        } else if (event.getDamager() instanceof FishHook) {
            FishHook hook = (FishHook) event.getDamager();
            if (!(hook.getShooter() instanceof Player))
                return;
            if (!(event.getEntity() instanceof Player))
                return;
            Player attacker = (Player) hook.getShooter();
            Damageable damageable = (Damageable) event.getEntity();
            Player defender = (Player) damageable;
            double health = damageable.getHealth();
            int damage = (int) event.getFinalDamage();
            int realHealth = (int) (health - damage);
            String realHealthMsg = String.format("%.1f", (double) realHealth);
            if (realHealth > 0)
                attacker.sendMessage("§b§l" + defender.getName() + "§aの残り体力は§c§l" + realHealthMsg + "§aです。");
        } else if (event.getDamager() instanceof Zombie) event.setDamage(0);
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        if (!(GameUtil.checkPlayerInTheFFA(player)
                && FreeForAll.inGame.contains(player.getUniqueId())
                && event.getRegainReason().equals(RegainReason.SATIATED)))
            return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(EntityDeathEvent event) {
        event.getDrops().clear();
        event.setDroppedExp(0);
        if (!(event.getEntity() instanceof Zombie))
            return;
        if (!list.contains(event.getEntity().getName()))
            return;
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        String playerName = entity.getName().replace("§a", "").replace("'s §cZombie", "");

        ScoreUtil.putScore(killer.getUniqueId(), ScoreType.KILLS);
        ScoreUtil.putScore(killer.getUniqueId(), ScoreType.COINS);

        GameScoreboard.updateGameScoreboard(killer);

        killer.sendMessage(FreeForAll.PREFIX + "§c" + entity.getName() + "§bを倒しました。");

        killer.getWorld().strikeLightningEffect(entity.getLocation());

        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skull.setDurability((short) 3);
        skullMeta.setOwner(playerName);
        skullMeta.setDisplayName("§c" + playerName + "'s Head.");
        skull.setItemMeta(skullMeta);

        entity.getWorld().dropItem(entity.getLocation(), skull);

        list.remove(entity.getName());
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        if (player.getAllowFlight() && GameUtil.checkPlayerInTheFFA(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        BlockState blockState = event.getBlockReplacedState();
        Material material = event.getBlock().getType();

        if (!FreeForAll.inGame.contains(player.getUniqueId()))
            return;
        if (block.getType().equals(Material.SKULL) || blockState.getType().equals(Material.GRASS)
                || blockState.getType().equals(Material.LONG_GRASS) ||
                blockState.getType().equals(Material.YELLOW_FLOWER)) event.setCancelled(true);
        else if (blockState.getType().equals(Material.WATER_LILY)) {
            Bukkit.getServer().getScheduler().runTaskLater(FreeForAll.getInstance(), () -> {
                block.setType(Material.AIR);
                if (GameUtil.checkPlayerInTheFFA(player)) player.getInventory().addItem(new ItemStack(material));
                else if (ManagementUtil.checkPlayerInTheMainSpawn(player)) player.getInventory().remove(material);
            }, 20);
        } else {
            Bukkit.getServer().getScheduler().runTaskLater(FreeForAll.getInstance(), () -> {
                block.setType(Material.AIR);
                if (GameUtil.checkPlayerInTheFFA(player)) player.getInventory().addItem(new ItemStack(material));
                else if (ManagementUtil.checkPlayerInTheMainSpawn(player)) player.getInventory().remove(material);
            }, 100);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
