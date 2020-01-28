package me.perry1900.warzoneoreveins;

import me.perry1900.warzoneoreveins.commands.AddItemDrop;
import me.perry1900.warzoneoreveins.commands.Giveoreveinblock;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class WarzoneOreVeins extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;
    public static WarzoneOreVeins instance;

    @Override
    public void onEnable() {
        instance = JavaPlugin.getPlugin(WarzoneOreVeins.class);
        loadMethod();
        registerConfig();
        createCustomConfig();
        getLogger().info("Registered config");
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getLogger().info("Registered events");
        regenerateOreVeinBlocks();
        getLogger().info("The plugin has been enabled!");

    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled");
    }


    public void loadMethod() {
        getCommand("GiveOreVeinBlock").setExecutor(new Giveoreveinblock());
        getCommand("AddItemDrop").setExecutor(new AddItemDrop());
    }


    //    private void createCustomConfig() {
//        customConfigFile = new File(getDataFolder(), "oreVeinBlocks.yml");
//        if (!customConfigFile.exists()) {
//            customConfigFile.getParentFile().mkdirs();
//            saveResource("oreVeinBlocks.yml", false);
//         }
//        customConfig= new YamlConfiguration();
//        try {
//            customConfig.load(customConfigFile);
//        }
//        catch (IOException | InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//    }
    public void registerConfig() {
        FileConfiguration config = this.getConfig();
        saveDefaultConfig();
        //config.options().copyDefaults(true);
        saveConfig();

    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "oreveinblocks.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("oreveinblocks.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public File getCustomConfigFile() {
        return this.customConfigFile;
    }

    public void regenerateOreVeinBlocks() {
        List<Location> locations = (List<Location>) getCustomConfig().getList("Locations");
        HashMap<String, Object> blockList = new HashMap<String, Object>(WarzoneOreVeins.instance.getConfig().getConfigurationSection("BlockChances").getValues(false));
        for (int i = 0; locations != null && i < locations.size(); i++) {
            BlockRegenerator.blockRegen.blockRegeneratorInsant(blockList, locations.get(i).getBlock().getLocation());
        }
    }
}
