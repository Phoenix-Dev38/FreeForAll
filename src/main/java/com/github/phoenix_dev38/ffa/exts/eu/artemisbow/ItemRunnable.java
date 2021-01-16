package com.github.phoenix_dev38.ffa.exts.eu.artemisbow;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

public class ItemRunnable {

    private final Projectile projectile;
    private LivingEntity target;

    private ItemRunnable(Plugin plugin, Projectile projectile) {
        this.projectile = projectile;
        this.target = null;
        control(plugin);
    }

    public static void homing(Plugin plugin, Projectile projectile) {
        new ItemRunnable(plugin, projectile);
    }

    private void control(Plugin plugin) {
        new BukkitRunnable() {
            public void run() {
                if (ItemRunnable.this.projectile.isDead() || ItemRunnable.this.projectile.isOnGround())
                    cancel();
                else if (ItemRunnable.this.target == null || ItemRunnable.this.target.isDead())
                    ItemRunnable.this.target();
                else {
                    ItemRunnable.this.projectile
                            .setVelocity(ItemRunnable.getVelocity(ItemRunnable.this.projectile.getLocation(),
                                    ItemRunnable.this.target.getEyeLocation()));
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    private void target() {
        Location loc = this.projectile.getLocation();
        LivingEntity shooter = (LivingEntity) this.projectile.getShooter();
        UUID uuid = shooter.getUniqueId();
        double lowestDistance = 7.0D;
        for (Entity entity : this.projectile.getNearbyEntities(50.0D, 50.0D, 50.0D)) {
            if ((entity instanceof LivingEntity)
                    && (!(entity instanceof Player) || ((Player) entity).getGameMode().equals(GameMode.SURVIVAL))
                    && entity.getUniqueId() != uuid && ((shooter instanceof Player) || (entity instanceof Player))) {
                double distance = loc.distance(entity.getLocation());
                if (distance < lowestDistance) {
                    lowestDistance = distance;
                    this.target = ((LivingEntity) entity);
                }
            }
        }
    }

    private static Vector getVelocity(Location from, Location to) {
        double dX = from.getX() - to.getX();
        double dY = from.getY() - to.getY();
        double dZ = from.getZ() - to.getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + 3.141592653589793D;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);
        return new Vector(x, z, y);
    }
}
