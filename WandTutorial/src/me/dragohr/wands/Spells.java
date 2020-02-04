package me.dragohr.wands;

import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.util.*;

public class Spells
{
    private Main WP;
    public static final String[] helwand;
    
    static {
        helwand = new String[] { "Lightning", "Explosion Wave", "Explosion", "Flowers" };
    }
    
    public Spells(final Main instance) {
        this.setWP(instance);
    }
    
    public int getSpellID(final String name, final Wands.Staffs wand) {
        if (wand == Wands.Staffs.TheFlowerofLife) {
            final String[] list = Spells.helwand;
            for (int i = 0; i <= list.length; ++i) {
                if (list[i].equalsIgnoreCase(name)) {
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }
    
    public int getSpellID(final String name, final String[] wandlist) {
        for (int i = 0; i <= wandlist.length; ++i) {
            if (wandlist[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }
    
    public String getSpell(final Player p) {
        final String name = p.getName();
        if (!this.WP.wand.currentspell.containsKey(name)) {
            return null;
        }
        return this.WP.wand.currentspell.get(name);
    }
    
    public String getSpell(final String playername) {
        if (!this.WP.wand.currentspell.containsKey(playername)) {
            return null;
        }
        return this.WP.wand.currentspell.get(playername);
    }
    
    @SuppressWarnings("deprecation")
	public void AllSpells(final String spellname, final String name) {
        final Player p = Bukkit.getPlayer(name);
        final Location loc = p.getLocation();
        final Vector direction = p.getLocation().getDirection().normalize();
        if (spellname.equalsIgnoreCase("Explosion")) {
            if (p.hasPermission("helwand.spell.explosion")) {
//                final BlockIterator blocksToAdd = new BlockIterator(loc, 0.0, 15);
//                final Location blockToAdd = blocksToAdd.next().getLocation();
                final Fireball f = (Fireball)p.launchProjectile(Fireball.class);
                f.setYield(8.0f);
                f.setVelocity(direction.multiply(3));
//                f.getWorld().playEffect(blockToAdd, Effect.FIREWORKS_SPARK, 1);
            }
            else {
                p.sendMessage("You do not have the correct permission this use this spell");
            }
        }
        else if (spellname.equalsIgnoreCase("Lightning")) {
            if (p.hasPermission("helwand.spell.lightning")) {
                final BlockIterator blocksToAdd = new BlockIterator(loc, 0.0, 400);
                while (blocksToAdd.hasNext()) {
                    final Location blockToAdd = blocksToAdd.next().getLocation();
                    if (blockToAdd.getBlock().getType() != Material.AIR) {
                        for (int i = 0; i <= 3; ++i) {
                            final Location blockloc = blockToAdd.getBlock().getLocation();
                            p.getWorld().strikeLightning(blockloc);
                        }
                        break;
                    }
                }
            }
            else {
                p.sendMessage("You do not have the correct permission this use this spell");
            }
        }
        else if (spellname.equalsIgnoreCase("Flowers")) {
            if (p.hasPermission("helwand.spell.flower")) {
                final BlockIterator blocksToAdd = new BlockIterator(loc, 0.0, 15);
                while (blocksToAdd.hasNext()) {
                    final Location blockToAdd = blocksToAdd.next().getLocation();
                    p.getWorld().playEffect(blockToAdd, Effect.HAPPY_VILLAGER, 1);
                    p.getWorld().playSound(blockToAdd, Sound.BLOCK_GRASS_PLACE, 2.0f, 1.0f);
                    if (blockToAdd.getBlock().getType() == Material.GRASS) {
                        blockToAdd.getBlock().getRelative(BlockFace.UP).setType(Material.YELLOW_FLOWER);
                    }
                    else {
                        if (blockToAdd.getBlock().getType() == Material.AIR || blockToAdd.getBlock().getType() == Material.YELLOW_FLOWER || blockToAdd.getBlock().getType() == Material.DIRT || blockToAdd.getBlock().getType() == Material.STONE || blockToAdd.getBlock().getType() == Material.GRAVEL || blockToAdd.getBlock().getType() == Material.SNOW) {
                            continue;
                        }
                        blockToAdd.getBlock().getRelative(BlockFace.SELF).setType(Material.GRASS);
                    }
                }
            }
            else {
                p.sendMessage("You do not have the correct permission this use this spell");
            }
        }
        else if (spellname.equalsIgnoreCase("Explosion Wave") && p.hasPermission("helwand.spell.explosionwave")) {
            final BlockIterator blocksToAdd = new BlockIterator(loc, 0.0, 15);
            while (blocksToAdd.hasNext()) {
                final Location blockToAdd = blocksToAdd.next().getLocation();
                p.getWorld().playEffect(blockToAdd, Effect.EXPLOSION, 5);
                p.getWorld().playSound(blockToAdd, Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5.0f, 1.0f);
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
