package com.github.phoenix_dev38.ffa;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlFile {

    public static final String FileBaseDirPath = "plugins/FreeForAll/";

    public static final String SettingsFilePath = FileBaseDirPath + "settings.yml";
    public static final String LocationsFilePath = FileBaseDirPath + "locations.yml";

    public static final YamlConfiguration SettingsYaml = new YamlConfiguration();
    public static final YamlConfiguration LocationsYaml = new YamlConfiguration();

    public static void loadLocations() {
        if (!(new File(FileBaseDirPath)).exists())
            (new File(FileBaseDirPath)).mkdir();
        if (!(new File(LocationsFilePath)).exists()) {
            try {
                (new File(LocationsFilePath)).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            LocationsYaml.load((new File(LocationsFilePath)));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        if (!(new File(FileBaseDirPath)).exists())
            (new File(FileBaseDirPath)).mkdir();
        if (!(new File(SettingsFilePath)).exists()) {
            try {
                (new File(SettingsFilePath)).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            SettingsYaml.load((new File(SettingsFilePath)));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveLocations() {
        if (!(new File(FileBaseDirPath)).exists())
            (new File(FileBaseDirPath)).mkdir();
        if (!(new File(LocationsFilePath)).exists()) {
            try {
                (new File(LocationsFilePath)).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            LocationsYaml.save(LocationsFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSettings() {
        if (!(new File(FileBaseDirPath)).exists())
            (new File(FileBaseDirPath)).mkdir();
        if (!(new File(SettingsFilePath)).exists()) {
            try {
                (new File(SettingsFilePath)).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            SettingsYaml.save(SettingsFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
