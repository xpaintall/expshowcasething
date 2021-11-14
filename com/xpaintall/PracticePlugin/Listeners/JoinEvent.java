package com.xpaintall.PracticePlugin.Listeners;

import com.xpaintall.PracticePlugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    Main f = Main.getPlugin(Main.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(!f.getConfig().contains("Levels." + player.getUniqueId() + ".Level")) {
            f.getConfig().set("Levels." + player.getUniqueId() + ".Level", 0);
            f.getConfig().set("Levels." + player.getUniqueId() + ".Exp", 0);
            f.getConfig().set("Levels." + player.getUniqueId() + ".RequiredExp", 5);
            f.saveConfig();
        }

    }



}
