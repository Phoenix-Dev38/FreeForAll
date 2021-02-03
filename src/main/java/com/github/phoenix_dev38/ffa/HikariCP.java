package com.github.phoenix_dev38.ffa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariCP {

    public static void connectHikariCP() {
        FreeForAll.getHikari().setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        FreeForAll.getHikari().addDataSourceProperty("serverName", YamlFile.SETTINGYAML.getString("MySQL.host"));
        FreeForAll.getHikari().addDataSourceProperty("port", YamlFile.SETTINGYAML.getString("MySQL.port"));
        FreeForAll.getHikari().addDataSourceProperty("databaseName", YamlFile.SETTINGYAML.getString("MySQL.database"));
        FreeForAll.getHikari().addDataSourceProperty("user", YamlFile.SETTINGYAML.getString("MySQL.user"));
        FreeForAll.getHikari().addDataSourceProperty("password", YamlFile.SETTINGYAML.getString("MySQL.password"));

        createTable();
    }

    public static void createTable() {
        if (YamlFile.SETTINGYAML.getString("MySQL.host") == null
                || YamlFile.SETTINGYAML.getString("MySQL.host").equalsIgnoreCase("")) {
            Bukkit.getLogger().info(ChatColor.RED + "データベースの接続に失敗しました。ファイルの中身を確認して再設定してください。");
            Bukkit.shutdown();
            return;
        }
        try (Connection connection = FreeForAll.getHikari().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ffa_player_block(uuid varchar(64), block text)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ffa_player_inv(uuid varchar(64), kit_type text, kit_num int, inv text, armor text)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ffa_player_prestige(uuid varchar(64), goldenhead text, lightapple text)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ffa_player_stats(uuid varchar(64), kills int, deaths int, coins int)");
            Bukkit.getLogger().info(ChatColor.GREEN + "データベースの接続に成功しました。");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        if (FreeForAll.getHikari() != null)
            FreeForAll.getHikari().close();
    }
}