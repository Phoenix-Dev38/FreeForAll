package com.github.phoenix_dev38.ffa;

import com.github.phoenix_dev38.ffa.ext.coin.commands.CAdminCommand;
import com.github.phoenix_dev38.ffa.ext.coin.commands.CoinCommand;
import com.github.phoenix_dev38.ffa.ext.eu.anduril.AndurilListener;
import com.github.phoenix_dev38.ffa.ext.eu.artemisbow.ArtemisBowListener;
import com.github.phoenix_dev38.ffa.ext.eu.axeofperun.AxeofPerunListener;
import com.github.phoenix_dev38.ffa.ext.eu.cornucopia.CornucopiaListener;
import com.github.phoenix_dev38.ffa.ext.eu.daredevil.DaredevilListener;
import com.github.phoenix_dev38.ffa.ext.eu.excalibur.ExcaliburListener;
import com.github.phoenix_dev38.ffa.ext.eu.exodus.ExodusListener;
import com.github.phoenix_dev38.ffa.ext.prestige.listeners.PrestigeGUIListener;
import com.github.phoenix_dev38.ffa.ext.prestige.listeners.PrestigeListener;
import com.github.phoenix_dev38.ffa.ext.prestige.listeners.RecipeCraftListener;
import com.github.phoenix_dev38.ffa.ext.recipes.Daredevil;
import com.github.phoenix_dev38.ffa.ext.recipes.GoldenHead;
import com.github.phoenix_dev38.ffa.ext.recipes.LightApple;
import com.github.phoenix_dev38.ffa.ext.sk.listeners.ItemListener;
import com.github.phoenix_dev38.ffa.ext.sk.listeners.SelectKitGUIListener;
import com.github.phoenix_dev38.ffa.listeners.GameListener;
import com.github.phoenix_dev38.ffa.utils.ScoreUtil;
import com.github.phoenix_dev38.ffa.commands.FreeForAllCommand;
import com.github.phoenix_dev38.ffa.commands.StatsCommand;
import com.github.phoenix_dev38.ffa.ext.block.listeners.BlockGUIListener;
import com.github.phoenix_dev38.ffa.ext.block.listeners.BlockListener;
import com.github.phoenix_dev38.ffa.ext.buyitem.BuyItemGUIListener;
import com.github.phoenix_dev38.ffa.ext.eu.GiveCommand;
import com.github.phoenix_dev38.ffa.ext.eu.deaths_scythe.DeathsScytheListener;
import com.github.phoenix_dev38.ffa.ext.is.KitSaveListener;
import org.bukkit.Bukkit;
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

        YamlFile.loadSettings();
        YamlFile.loadLocations();
        if (YamlFile.SettingsYaml.getString("MySQL") == null) {
            YamlFile.SettingsYaml.set("MySQL.host", "");
            YamlFile.SettingsYaml.set("MySQL.database", "");
            YamlFile.SettingsYaml.set("MySQL.user", "");
            YamlFile.SettingsYaml.set("MySQL.password", "");
            YamlFile.saveSettings();
        }
        YamlFile.saveSettings();
        YamlFile.saveLocations();

        connectMySQL();

        setExecutor();
        registerEvents();
        registerRecipes();

        ScoreUtil.putScoreKills();
        Ranking.updateRanking();
        for (Map.Entry<UUID, Integer> uuid : FreeForAll.kills.entrySet())
            System.out.println(uuid.getKey());
    }

    public void onDisable() {
        MySQL.close();

        YamlFile.saveSettings();
        YamlFile.saveLocations();
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
        if (YamlFile.SettingsYaml.getString("MySQL.host") == null
                || YamlFile.SettingsYaml.getString("MySQL.host").equalsIgnoreCase("")) {
            System.out.println("データベースの接続に失敗しました。ファイルの中身を確認して再設定してください。");
            Bukkit.shutdown();
            return;
        }
        mysql = new MySQL(YamlFile.SettingsYaml.getString("MySQL.host"), YamlFile.SettingsYaml.getString("MySQL.database"), YamlFile.SettingsYaml.getString("MySQL.user"), YamlFile.SettingsYaml.getString("MySQL.password"));
        mysql.update("CREATE TABLE IF NOT EXISTS ffa_block(uuid varchar(64), block text)");
        mysql.update("CREATE TABLE IF NOT EXISTS ffa_inv(uuid varchar(64), kit_type text, kit_num int, inv text, armor text)");
        mysql.update("CREATE TABLE IF NOT EXISTS ffa_prestige(uuid varchar(64), goldenhead text, lightapple text)");
        mysql.update("CREATE TABLE IF NOT EXISTS ffa_stats(uuid varchar(64), kills int, deaths int, coins int)");
    }
}
