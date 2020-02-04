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
        if (p.hasPermission("helwand.get")) {
            final ItemStack item = new ItemStack(Material.DOUBLE_PLANT);
            final ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6The Flower of Life"));
            final List<String> lore = new ArrayList<String>();
            lore.add("This flowers holds an unknown power");
            im.setLore(lore);
            item.setItemMeta(im);
            p.getInventory().addItem(new ItemStack[] { item });
            p.sendMessage(ChatColor.YELLOW + "You have received the Flower of Life!");
            return true;
        }
        p.sendMessage("You do not have the correct permission this use this command");
        return false;
    }
}
