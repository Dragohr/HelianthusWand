package me.dragohr.wands;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
//import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_12_R1.Vec3D;

//import net.minecraft.server.v1_12_R1.Vec3D;

public class Spells
{
    private Main WP;
    public static final String[] helwand;
    
    static {
        helwand = new String[] { "1. Lightning", "2. Explosion Wave", "3. Fireball Explosion", "4. Test Explosion" , "5. Flowers" };
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
        Location loc = p.getLocation();
        final Vector direction = p.getLocation().getDirection().normalize();
        if (spellname.equalsIgnoreCase("3. Fireball Explosion")) {
            if (p.hasPermission("helwand.spell.fbexplosion")) {
//                final BlockIterator blocksToAdd = new BlockIterator(loc, 0.0, 15);
//                final Location blockToAdd = blocksToAdd.next().getLocation();
                final SmallFireball f = (SmallFireball)p.launchProjectile(SmallFireball.class);
//                f.setYield(8.0f);
                f.setVelocity(direction.multiply(6));
//                f.getWorld().playEffect(blockToAdd, Effect.FIREWORKS_SPARK, 1);
                f.getWorld().createExplosion(f.getLocation(), 4);
                
                
            }
            else {
                p.sendMessage("You do not have the correct permission this use this spell");
            }
        } else if(spellname.equalsIgnoreCase("4. Test Explosion")) {
        	final Location loca = p.getPlayer().getEyeLocation();
            final Vector v = loca.getDirection();
//            v.setY(0); // if you want it to go horizontally, if you want it to go the direction the player is looking then remove this line
            double speed = 1;
            v.normalize().multiply(speed); // The speed is how fast it should move, 1 = one block per tick, values over 1 might cause the firework to go through 1 thick block walls
            final SmallFireball f = loca.getWorld().spawn(loca, SmallFireball.class);
         
            /*
             * Do all your firework effect stuff here (e.g. if it should be a star, colors etc)
             */
         
            final boolean ent = true; // Also check if we hit entities
         
            new BukkitRunnable(){
                public void run(){
                    f.setTicksLived(0); // To prevent it from detonating before we want it to
                    loca.add(v);
                    f.teleport(loca);
                    if(f.isOnGround()){ // We have hit a solid block, but are now inside that block
                        f.teleport(loca.subtract(v)); // Go back one step to get outside the block
                        f.getWorld().createExplosion(loca, 4);
                        this.cancel(); // Stops the task as we are now done
                    }
                 
                    if(ent){ // Check if we have hit entities
                        Vec3D v3 = new Vec3D(loca.getX(), loca.getY(), loca.getZ()); // Create a nms vector representing the firework's location
                        for(Entity e : f.getNearbyEntities(2, 2, 2)){ // Scan nearby entities
                            if(((CraftEntity)e).getHandle().getBoundingBox().a(v3) != null){ // Check if the firework is inside the entity's hitbox/bounding box
                                // We have hit an entity, detonate and exit (return prevents the for loop form continuing as we are now done
                            	f.getWorld().createExplosion(loca, 4);
                                this.cancel();
                                return;
                            }
                        }
                     
                    }
                 
                }
            }.runTaskTimer(WP, 0, 1); // Create a task to run every tick (plugin = your plugin instance)
        }
        else if (spellname.equalsIgnoreCase("1. Lightning")) {
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
        else if (spellname.equalsIgnoreCase("5. Flowers")) {
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
                        if (blockToAdd.getBlock().getType() == Material.AIR 
                        		|| blockToAdd.getBlock().getType() == Material.YELLOW_FLOWER 
                        		|| blockToAdd.getBlock().getType() == Material.DIRT 
                        		|| blockToAdd.getBlock().getType() == Material.STONE 
                        		|| blockToAdd.getBlock().getType() == Material.GRAVEL 
                        		|| blockToAdd.getBlock().getType() == Material.SNOW) {
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
        else if (spellname.equalsIgnoreCase("2. Explosion Wave") && p.hasPermission("helwand.spell.explosionwave")) {
        	if (p.hasPermission("helwand.spell.explosionsave")) {
	            final BlockIterator blocksToAdd = new BlockIterator(loc, 0.0, 15);
	            while (blocksToAdd.hasNext()) {
	                final Location blockToAdd = blocksToAdd.next().getLocation();
	                p.getWorld().playEffect(blockToAdd, Effect.EXPLOSION, 5);
	                p.getWorld().playSound(blockToAdd, Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5.0f, 1.0f);
	            }
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
