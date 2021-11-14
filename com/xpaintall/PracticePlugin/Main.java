package com.xpaintall.PracticePlugin;

import com.xpaintall.PracticePlugin.Listeners.JoinEvent;
import com.xpaintall.PracticePlugin.Listeners.KillingEntities;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new KillingEntities(this), this);
        getCommand("setexp").setExecutor(new KillingEntities(this));
    }

    public void onDisable() {

    }


}
