package com.xpaintall.PracticePlugin.Listeners;

import com.xpaintall.PracticePlugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillingEntities implements Listener, CommandExecutor {

    /*
    In this part of the code we the the instance of our main class
     And get the getters and setters of values (exp, requiredexp, levels and entityExp)
     */
    private Main f;
    Configuration config = f.getConfig();

    public KillingEntities(Main main) {
        this.f = main;
    }

    public int getExp(Player player) {
        return config.getInt("Levels." + player.getUniqueId() + ".Exp");
    }

    public int getLevel(Player player) {
        return config.getInt("Levels." + player.getUniqueId() + ".Level");
    }

    public int getRequiredExp(Player player) {
        return config.getInt("Levels." + player.getUniqueId() + ".RequiredExp");
    }

    public void setExp(int exp, Player player) {
        config.set("Levels." + player.getUniqueId() + ".Exp", exp);
        f.saveConfig();
    }

    public void setLevel(int level, Player player) {
        config.set("Levels." + player.getUniqueId() + ".Level", level);
        f.saveConfig();
    }

    public void setRequiredExp(int requiredExp, Player player) {
        config.set("Levels." + player.getUniqueId() + ".RequiredExp", requiredExp);
        f.saveConfig();
    }

    public int getEntityExp(EntityType entityType) {
        return config.getInt("Entities." + entityType);
    }

    public void setEntityExp(int exp, EntityType entityType) {
        config.set("Entities." + entityType, exp);
        f.saveConfig();
    }

    //this event gets called when an entity dies

    @EventHandler
    public void OnEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();

        //checking if the entity's killer is a player
        //if it is, then searching we are going to search the config and look if the entity we killed is in config

        if(event.getEntity().getKiller() == player) {
            if(event.getEntityType() == config.get("Entities." + event.getEntityType())) {
                EntityType entityType = event.getEntityType();

                //setting exp, levels and requiredExp (i didn't add prestiges)

                setExp(getEntityExp(entityType), player);

                //check if the exp is greater or equal to requiredEXP
                if(getExp(player) >= getRequiredExp(player)) {
                    //subtracting exp from requiredExp to get your remaining exp (you can set it to 0 if you want to make the player start over)
                    setExp(getExp(player) - getRequiredExp(player), player);
                    //setting the player's level
                    setLevel(getLevel(player) + 1, player);
                    //setting the required exp (adding +2 required exp to it)
                    setRequiredExp(getRequiredExp(player) + 2, player);
                }

            }
        }

    }

    /*
    Now this part is tricky
    In the onCommand method, you want to create a new command called "setexp" or whatever you want.
    You will have to check if the arguments are shorter or equal to 2, because we don't want to execute the command if there are more than 2 arguments
    If you don't understand this, i'd recommend checking technovision's video out becuase it's helpful: https://www.youtube.com/watch?v=QVYtjbpD4cE
    You set your entity exp (with commands) with your command arguments, and it gets saved in the config.
    The user can do this in the configuration file, but it will require a server restart in order to see the changes.
    If the arguments are not correct/what we want, it will say the message "Invalid arguments" and won't execute the rest of the code.
    Otherwise, it will just return true.
     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("setexp")) {
            if(args.length >= 2) {
                try {
                    EntityType entity = EntityType.valueOf(args[0]);
                    int expAmount = Integer.parseInt(args[1]);
                    setEntityExp(expAmount, entity);
                    player.sendMessage(ChatColor.GREEN + "The exp amount per kill for the entity " + entity + " has been set to " + expAmount);
                    return true;
                } catch(IllegalArgumentException e) {
                    player.sendMessage(ChatColor.RED + "Invalid arguments");
                }
            }
        }


        return false;
    }
}
