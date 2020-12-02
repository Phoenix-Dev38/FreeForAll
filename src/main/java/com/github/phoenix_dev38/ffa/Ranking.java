package com.github.phoenix_dev38.ffa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Ranking {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    private static final String nameURL = "https://api.mojang.com/user/profiles/%s/names";

    private static final Map<String, String> username = new HashMap<>();

    private static Map<String, Integer> getPlayerRank() {
        LinkedHashMap<String, Integer> ranking = new LinkedHashMap<>();
        FreeForAll.kills.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(player -> ranking.put(String.valueOf(player.getKey()), player.getValue()));
        return ranking;
    }

    public static void showRanking(Player player, int number) {
        int rank = 1;
        player.sendMessage("");
        player.sendMessage("§c--- §a§lFreeForAll §9§lRanking-Top§b§l" + number + " §a§lKillers §c---");
        player.sendMessage("");
        for (Map.Entry<String, Integer> ranking : getPlayerRank().entrySet()) {
            if (rank > number)
                break;
            player.sendMessage("§b§l#" + rank + " §a§l" + getName(ranking.getKey()) + " §7§l➤ §6"
                    + ranking.getValue() + "§cキル");
            rank++;
        }
        player.sendMessage("");
    }

    public static void updateRanking() {
        int rank = 1;
        World world = Bukkit.getWorld("lobby");
        Sign[] signs = new Sign[5];
        signs[0] = (Sign) new Location(world, -8, 10, -56).getBlock().getState();
        signs[1] = (Sign) new Location(world, -8, 9, -55).getBlock().getState();
        signs[2] = (Sign) new Location(world, -8, 9, -57).getBlock().getState();
        signs[3] = (Sign) new Location(world, -8, 8, -54).getBlock().getState();
        signs[4] = (Sign) new Location(world, -8, 8, -58).getBlock().getState();
        Skull[] skulls = new Skull[5];
        skulls[0] = (Skull) new Location(world, -9, 11, -56).getBlock().getState();
        skulls[1] = (Skull) new Location(world, -9, 10, -55).getBlock().getState();
        skulls[2] = (Skull) new Location(world, -9, 10, -57).getBlock().getState();
        skulls[3] = (Skull) new Location(world, -9, 9, -54).getBlock().getState();
        skulls[4] = (Skull) new Location(world, -9, 9, -58).getBlock().getState();
        for (Map.Entry<String, Integer> ranking : getPlayerRank().entrySet()) {
            if (rank > 5)
                break;
            updateSign(signs[rank - 1], rank, getName(ranking.getKey()), ranking.getValue());
            updateSkull(skulls[rank - 1], getName(ranking.getKey()));
            rank++;
        }
    }

    private static void updateSign(Sign sign, int rank, String name, int kills) {
        sign.setLine(0, "§dランキング");
        sign.setLine(1, "§b#" + rank);
        sign.setLine(2, "§a" + name);
        sign.setLine(3, "§9" + kills + " §cキル");
        sign.update();
    }

    private static void updateSkull(Skull skull, String name) {
        skull.setOwner(name);
        skull.update();
    }

    private static String getName(String uuid) {
        if (username.containsKey(uuid))
            return username.get(uuid);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    String.format(nameURL, UUIDTypeAdapter.fromUUID(UUID.fromString(uuid)))).openConnection();
            connection.setReadTimeout(5000);
            PlayerUUID[] allUserNames = gson.fromJson(
                    new BufferedReader(new InputStreamReader(connection.getInputStream())), PlayerUUID[].class);
            PlayerUUID currentName = allUserNames[allUserNames.length - 1];
            username.put(uuid, currentName.getName());
            return currentName.getName();
        } catch (Exception e) {
            username.put(uuid, Bukkit.getOfflinePlayer(uuid).getName());
            return Bukkit.getOfflinePlayer(uuid).getName();
        }
    }

    static class PlayerUUID {
        private final String name;

        PlayerUUID(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
