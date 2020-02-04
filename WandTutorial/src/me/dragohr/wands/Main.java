package me.dragohr.wands;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.dragohr.wands.commands.*;

public class Main extends JavaPlugin
{
    public Wands wand;
    public Spells spell;
    public String title;
    
    public Main() {
        this.title = ChatColor.YELLOW + "[" + ChatColor.GRAY + "Helianthus Wand" + ChatColor.YELLOW + "] " + ChatColor.WHITE;
    }
    
    public void onEnable() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener)new Listeners(this), (Plugin)this);
        new HelianthusGetWand(this);
        this.wand = new Wands(this);
        this.spell = new Spells(this);
    }
    
    public void onDisable() {
    }
}