package me.dragohr.wands;

import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;

public class Listeners implements Listener
{
    private Main WP;
    
    public Listeners(final Main instance) {
        this.setWP(instance);
    }
    
    @EventHandler
    public void PlayerInteract(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        final Player p = e.getPlayer();
        final String name = p.getName();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            @SuppressWarnings("deprecation")
			final ItemStack i = p.getItemInHand();
            if (this.WP.wand.isWand(i)) {
                if (this.WP.wand.hasSpell(name)) {
                    this.WP.wand.nextSpell(name);
                    p.sendMessage(String.valueOf(this.WP.title) + ChatColor.GOLD + "You've selected: " + this.WP.spell.getSpell(p));
                }
                else {
                    this.WP.wand.setSpell(name, 0);
                    p.sendMessage(String.valueOf(this.WP.title) + ChatColor.GOLD + "You've selected: " + this.WP.spell.getSpell(p));
                }
            }
        }
        else if ((p.isSneaking() && a == Action.RIGHT_CLICK_AIR) || a == Action.RIGHT_CLICK_BLOCK) {
            @SuppressWarnings("deprecation")
			final ItemStack i = p.getItemInHand();
            if (this.WP.wand.isWand(i) && this.WP.wand.hasSpell(name)) {
                this.WP.wand.prevSpell(name);
                p.sendMessage(String.valueOf(this.WP.title) + ChatColor.GOLD + "You've selected: " + this.WP.spell.getSpell(p));
            }
        }
        else if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) {
            @SuppressWarnings("deprecation")
			final ItemStack i = p.getItemInHand();
            if (this.WP.wand.isWand(i)) {
                this.WP.spell.AllSpells(this.WP.spell.getSpell(p), name);
            }
        }
    }
    
    public Main getWP() {
        return this.WP;
    }
    
    public void setWP(final Main wP) {
        this.WP = wP;
    }
}
