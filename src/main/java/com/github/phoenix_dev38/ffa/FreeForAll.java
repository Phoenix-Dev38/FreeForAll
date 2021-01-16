package com.github.phoenix_dev38.ffa;

import com.github.phoenix_dev38.ffa.commands.FreeForAllCommand;
import com.github.phoenix_dev38.ffa.commands.StatsCommand;
import com.github.phoenix_dev38.ffa.exts.block.listeners.BlockGUIListener;
import com.github.phoenix_dev38.ffa.exts.block.listeners.BlockListener;
import com.github.phoenix_dev38.ffa.exts.buyitem.BuyItemGUIListener;
import com.github.phoenix_dev38.ffa.exts.coin.commands.CAdminCommand;
import com.github.phoenix_dev38.ffa.exts.coin.commands.CoinCommand;
import com.github.phoenix_dev38.ffa.exts.eu.GiveCommand;
import com.github.phoenix_dev38.ffa.exts.eu.anduril.AndurilListener;
import com.github.phoenix_dev38.ffa.exts.eu.artemisbow.ArtemisBowListener;
import com.github.phoenix_dev38.ffa.exts.eu.axeofperun.AxeofPerunListener;
import com.github.phoenix_dev38.ffa.exts.eu.cornucopia.CornucopiaListener;
import com.github.phoenix_dev38.ffa.exts.eu.daredevil.DaredevilListener;
import com.github.phoenix_dev38.ffa.exts.eu.deaths_scythe.DeathsScytheListener;
import com.github.phoenix_dev38.ffa.exts.eu.excalibur.ExcaliburListener;
import com.github.phoenix_dev38.ffa.exts.eu.exodus.ExodusListener;
import com.github.phoenix_dev38.ffa.exts.is.KitSaveListener;
import com.github.phoenix_dev38.ffa.exts.prestige.listeners.PrestigeGUIListener;
import com.github.phoenix_dev38.ffa.exts.prestige.listeners.PrestigeListener;
import com.github.phoenix_dev38.ffa.exts.prestige.listeners.RecipeCraftListener;
import com.github.phoenix_dev38.ffa.exts.recipes.Daredevil;
import com.github.phoenix_dev38.ffa.exts.recipes.GoldenHead;
import com.github.phoenix_dev38.ffa.exts.recipes.LightApple;
import com.github.phoenix_dev38.ffa.exts.sk.listeners.ItemListener;
import com.github.phoenix_dev38.ffa.exts.sk.listeners.SelectKitGUIListener;
import com.github.phoenix_dev38.ffa.listeners.GameListener;
import com.github.phoenix_dev38.ffa.utils.ScoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class FreeForAll extends JavaPlugin {

    private static FreeForAll instance;
    public static ActionBar ab;
    public static MySQL mysql;

    public static String PREFIX = "§a§l[FFA] §r";

    public static Set<UUID> coolDown = new HashSet<>();
    public static Set<UUID> inGame = new HashSet<>();

    public static Map<UUID, Integer> kills = new HashMap<>();
    public static Map<UUID, Integer> deaths = new HashMap<>();
    public static Map<UUID, Integer> coins = new HashMap<>();

    public void onEnable() {
        instance = this;
        ab = new ActionBar();

        YamlFile.loadLocation();
        YamlFile.loadSetting();
        if (YamlFile.SETTINGYAML.getString("MySQL") == null) {
            YamlFile.SETTINGYAML.set("MySQL.host", "");
            YamlFile.SETTINGYAML.set("MySQL.database", "");
            YamlFile.SETTINGYAML.set("MySQL.user", "");
            YamlFile.SETTINGYAML.set("MySQL.password", "");
            YamlFile.saveSetting();
        }
        YamlFile.saveLocation();
        YamlFile.saveSetting();

        connectMySQL();

        setExecutor();
        registerEvents();
        registerRecipes();

        ScoreUtil.putScoreKills();
        getServer().getScheduler().runTaskLater(this, Ranking::updateRanking, 100);
    }

    public void onDisable() {
        MySQL.close();

        YamlFile.saveLocation();
        YamlFile.saveSetting();
    }

    public static FreeForAll getInstance() {
        return instance;
    }

    private void setExecutor() {
        getCommand("freeforall").setExecutor(new FreeForAllCommand());
        getCommand("stats").setExecutor(new StatsCommand());

        getCommand("cadmin").setExecutor(new CAdminCommand());
        getCommand("coin").setExecutor(new CoinCommand());

        getCommand("eu").setExecutor(new GiveCommand());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new BlockGUIListener(), this);

        getServer().getPluginManager().registerEvents(new BuyItemGUIListener(), this);

        getServer().getPluginManager().registerEvents(new AndurilListener(), this);
        getServer().getPluginManager().registerEvents(new ArtemisBowListener(), this);
        getServer().getPluginManager().registerEvents(new AxeofPerunListener(), this);
        getServer().getPluginManager().registerEvents(new CornucopiaListener(), this);
        getServer().getPluginManager().registerEvents(new DaredevilListener(), this);
        getServer().getPluginManager().registerEvents(new DeathsScytheListener(), this);
        getServer().getPluginManager().registerEvents(new ExcaliburListener(), this);
        getServer().getPluginManager().registerEvents(new ExodusListener(), this);

        getServer().getPluginManager().registerEvents(new KitSaveListener(), this);

        getServer().getPluginManager().registerEvents(new PrestigeGUIListener(), this);
        getServer().getPluginManager().registerEvents(new PrestigeListener(), this);
        getServer().getPluginManager().registerEvents(new RecipeCraftListener(), this);

        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new SelectKitGUIListener(), this);

        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new com.github.phoenix_dev38.ffa.listeners.ItemListener(), this);
    }

    private void registerRecipes() {
        Daredevil.daredevil();
        GoldenHead.goldenHead();
        LightApple.lightApple();
    }

    public static void connectMySQL() {
        if (YamlFile.SETTINGYAML.getString("MySQL.host") == null
                || YamlFile.SETTINGYAML.getString("MySQL.host").equalsIgnoreCase("")) {
            Bukkit.getLogger().info(ChatColor.RED + "データベースの接続に失敗しました。ファイルの中身を確認して再設定してください。");
            Bukkit.shutdown();
            return;
        }
        mysql = new MySQL(YamlFile.SETTINGYAML.getString("MySQL.host"), YamlFile.SETTINGYAML.getString("MySQL.database"), YamlFile.SETTINGYAML.getString("MySQL.user"), YamlFile.SETTINGYAML.getString("MySQL.password"));
        mysql.update("CREATE TABLE IF NOT EXISTS ffa_player_block(uuid varchar(64), block text)");
        mysql.update("CREATE TABLE IF NOT EXISTS ffa_player_inv(uuid varchar(64), kit_type text, kit_num int, inv text, armor text)");
        mysql.update("CREATE TABLE IF NOT EXISTS ffa_player_prestige(uuid varchar(64), goldenhead text, lightapple text)");
        mysql.update("CREATE TABLE IF NOT EXISTS ffa_player_stats(uuid varchar(64), kills int, deaths int, coins int)");
        Bukkit.getLogger().info(ChatColor.GREEN + "データベースの接続に成功しました。");
    }
}
