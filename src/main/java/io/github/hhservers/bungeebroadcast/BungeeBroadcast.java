package io.github.hhservers.bungeebroadcast;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class BungeeBroadcast extends Plugin {

    private static BungeeBroadcast plugin;
    private File file;
    private Configuration configuration;


    @Override
    public void onEnable() {
        plugin = this;
        getLogger().info("has loaded!");
        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }
        file = new File(getDataFolder(), "config.yml");
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String key : configuration.getSection("broadcast").getKeys()){
            String message = configuration.getString("broadcast."+key+".message");
            long interval = configuration.getInt("broadcast."+key+".interval");
            if(configuration.getString("broadcast."+key+".url").isEmpty()) {
                ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
                    public void run() {
                        new Util().sendBroadcast(message);
                        getLogger().info("task running");
                    }
                }, 1, interval, TimeUnit.MINUTES);
            } else {
                String url = configuration.getString("broadcast."+key+".url");
                ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
                    public void run() {
                        new Util().sendURLBroadcast(message, url);
                        getLogger().info("task running");
                    }
                }, 1, interval, TimeUnit.MINUTES);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
