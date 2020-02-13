package me.dragohr.wands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.dragohr.wands.commands.HelianthusGetWand;

public class Main extends JavaPlugin
{
	
    public Wands wand;
    public Spells spell;
    public String title;
    
    public Main() {
        this.title = ChatColor.YELLOW + "[" + ChatColor.GRAY + "Helianthus Wand" + ChatColor.YELLOW + "] " + ChatColor.WHITE;
    }
    
    public void loadConfig() {
    	getConfig().options().copyDefaults(true);
    	saveConfig();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
        sender.sendMessage("Only players may execute this command");
        return true;
      }
      final Player p = (Player)sender;
      String itemwandname = getConfig().getString("itemwandname");
      final String itemDN = ChatColor.GOLD + itemwandname;

      switch (cmd.getName().toLowerCase()) {
      case "checkwand":
    	if(p.hasPermission("helwand.checkwand")) {
    		@SuppressWarnings("deprecation") ItemStack inHand = p.getInventory().getItemInHand();
    		p.sendMessage("You are " + ((isValidObject(itemDN, inHand))?"":"not ") + "holding the nifty flower thing");
//    		return Spells.helwand;
    		break;
    	}
     
      case "getwand":
    	if(p.hasPermission("helwand.getwand")) {
    		final ItemStack item = new ItemStack(Material.DOUBLE_PLANT);
            final ItemMeta im = item.getItemMeta();

            im.setDisplayName(itemDN);
            im.setLore(new ArrayList<String>(Arrays.asList("This flowers holds an unknown power")));
            item.setItemMeta(im);

            p.getInventory().addItem(item);
            p.sendMessage(ChatColor.YELLOW + "You have received the Flower of Life!");
            break;
    	}else {
    		p.sendMessage("You do not have permission to execute this command.");
    	}
        
      }
      return true;
    }

    private boolean isValidObject(String sourceName, ItemStack itemToCheck) {
      if (itemToCheck == null || !itemToCheck.hasItemMeta()) return false;
     
      ItemMeta inHandMeta = itemToCheck.getItemMeta();
      if (!inHandMeta.hasDisplayName()) return false;
      if (!inHandMeta.getDisplayName().equals(sourceName)) return false;

      return true;  
    }
    
    public void onEnable() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener)new Listeners(this), (Plugin)this);
        new HelianthusGetWand(this);
        this.wand = new Wands(this);
        this.spell = new Spells(this);
        loadConfig();
        
    }
    
    public void onDisable() {
    }
    
    
}