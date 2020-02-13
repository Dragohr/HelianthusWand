package me.dragohr.wands.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.meta.*;

import me.dragohr.wands.*;

public class HelianthusGetWand implements CommandExecutor
{
    
    public HelianthusGetWand(final Main plugin) {
        plugin.getCommand("helwand").setExecutor((CommandExecutor)this);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
    	if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may execute this command");
            return true;
        }
    	final Player p = (Player)sender;
    	final String itemDN = ChatColor.GOLD + "The Flower of Life";
    	if(p.hasPermission("helwand.getwand")) {
    		final ItemStack item = new ItemStack(Material.DOUBLE_PLANT);
            final ItemMeta im = item.getItemMeta();

            im.setDisplayName(itemDN);
            im.setLore(new ArrayList<String>(Arrays.asList("This flowers holds an unknown power")));
            item.setItemMeta(im);

            p.getInventory().addItem(item);
            p.sendMessage(ChatColor.YELLOW + "You have received the Flower of Life!");
            return true;
    	}else {
    		p.sendMessage("You do not have permission to execute this command.");
    		return false;
    	}
    }
    
    
}
