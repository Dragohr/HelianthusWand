package me.dragohr.wands;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;


public class Wands
{
    private Main WP;
    public static final String[] wandtypes;
    public final HashMap<String, String> currentspell;
    static {
        wandtypes = new String[] { "The Flower of Life" };
    }
    
    public Wands(final Main instance) {
        this.currentspell = new HashMap<String, String>();
        this.setWP(instance);
    }
    
    public boolean isWand(final ItemStack i) {
        final String name = i.getItemMeta().getDisplayName();
        String[] wandtypes;
        for (int length = (wandtypes = Wands.wandtypes).length, j = 0; j < length; ++j) {
            final String s = wandtypes[j];
            if (name.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasSpell(final String name) {
        return this.currentspell.containsKey(name);
    }
    
    public int getWandID(final String spell) {
        for (int i = 0; i <= Wands.wandtypes.length; ++i) {
            if (spell.equalsIgnoreCase(Wands.wandtypes[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public void setSpell(final String name, final String spell) {
        this.currentspell.put(name, spell);
    }
    
    public void setSpell(final String name, final int id) {
        String[] list;
        if (this.hasSpell(name)) {
            final String spell = this.currentspell.get(name);
            list = this.getWandList(spell);
        }
        else {
            @SuppressWarnings("deprecation")
			final String itemname = Bukkit.getPlayer(name).getItemInHand().getItemMeta().getDisplayName();
            @SuppressWarnings("deprecation")
			final ItemStack itemtype = Bukkit.getPlayer(name).getItemInHand();
            list = this.getItemWandList(itemname, itemtype);
        }
        if (list == null) {
            return;
        }
        this.currentspell.put(name, list[id]);
    }
    
    public void nextSpell(final String name) {
        final String spellname = this.WP.spell.getSpell(name);
        final String[] wandlist = this.getWandList(spellname);
        @SuppressWarnings("deprecation")
		final String[] currentwand = this.getItemWandList(Bukkit.getPlayer(name).getItemInHand().getItemMeta().getDisplayName(), Bukkit.getPlayer(name).getItemInHand());
        if (currentwand != wandlist) {
            this.currentspell.put(name, currentwand[0]);
            return;
        }
        int id = this.WP.spell.getSpellID(spellname, wandlist) + 1;
        if (id >= wandlist.length) {
            id = 0;
        }
        this.currentspell.put(name, wandlist[id]);
    }
    
    public void prevSpell(final String name) {
        final String spellname = this.WP.spell.getSpell(name);
        final String[] wandlist = this.getWandList(spellname);
        @SuppressWarnings("deprecation")
		final String[] currentwand = this.getItemWandList(Bukkit.getPlayer(name).getItemInHand().getItemMeta().getDisplayName(), Bukkit.getPlayer(name).getItemInHand());
        if (currentwand != wandlist) {
            this.currentspell.put(name, currentwand[0]);
            return;
        }
        int id = this.WP.spell.getSpellID(spellname, wandlist) - 1;
        if (id >= wandlist.length) {
            id = 0;
        }
        this.currentspell.put(name, wandlist[id]);
    }
    
    private String[] getWandList(final String name) {
        if (Arrays.asList(Spells.helwand).contains(name)) {
            return Spells.helwand;
        }
        return null;
    }
    
//    private String[] getItemWandList(final String itemname, final ItemStack itemtype) {
//        if (itemname.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', WP.getConfig().getString("itemwandname")))) {
//            return Spells.helwand;
//        }
//        return null;
//    }
    
	private String[] getItemWandList(final String itemname, final ItemStack itemtype) {
		if (itemname.equalsIgnoreCase("The Flower of Life")) {
			return Spells.helwand;
		}
		return null;
	}
    
    public Main getWP() {
        return this.WP;
    }
    
    public void setWP(final Main wP) {
        this.WP = wP;
    }
   
    
    enum Staffs
    {
        TheFlowerofLife("TheFlowerofLife", 0);
        
        private Staffs(final String s, final int n) {
        }
    }
}
