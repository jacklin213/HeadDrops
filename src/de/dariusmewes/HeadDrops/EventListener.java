/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {

	private HeadDrops plugin;
	private Random rand = new Random();

	public EventListener(HeadDrops plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (HeadDrops.updateAvailable && (event.getPlayer().isOp() || event.getPlayer().hasPermission("headdrops.update"))) {
			event.getPlayer().sendMessage(HeadDrops.PREFIX + "A new version is available!");
			event.getPlayer().sendMessage(HeadDrops.PREFIX + "Get it at http://dev.bukkit.org/server-mods/head-drops");
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		if (rand.nextInt(100) < plugin.getConfig().getInt("hostile")) {
			if (event.getEntity().getLastDamageCause() == null) {
				return;
			}

			EntityType t = event.getEntity().getType();
			DamageCause cause = event.getEntity().getLastDamageCause().getCause();

			if (cause == DamageCause.ENTITY_ATTACK && event.getEntity().getKiller() != null) {
				Location loc = event.getEntity().getLocation();

				if (t == EntityType.ZOMBIE && plugin.getConfig().getBoolean("zombiedrop")) {
					dropSkull(loc, 2);
				} else if (t == EntityType.SKELETON && plugin.getConfig().getBoolean("skeletondrop")) {
					if (((Skeleton) event.getEntity()).getSkeletonType() == SkeletonType.NORMAL)
						dropSkull(loc, 0);
				} else if (t == EntityType.CREEPER && plugin.getConfig().getBoolean("creeperdrop")) {
					dropSkull(loc, 4);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (rand.nextInt(100) < plugin.getConfig().getInt("player") && plugin.getConfig().getBoolean("playerdrop")) {
			if (event.getEntity().getLastDamageCause() == null) {
				return;
			}

			Player dead = event.getEntity();
			Player killer = event.getEntity().getKiller();
			DamageCause cause = event.getEntity().getLastDamageCause().getCause();

			if (cause == DamageCause.ENTITY_ATTACK && killer != null) {
				if (plugin.getConfig().getBoolean("ironanddiamond")) {
					if (killer.getItemInHand().getType() == Material.IRON_SWORD || killer.getItemInHand().getType() == Material.DIAMOND_SWORD || (plugin.getConfig().getBoolean("axeenabled") && (killer.getItemInHand().getType() == Material.IRON_AXE || killer.getItemInHand().getType() == Material.DIAMOND_AXE))) {
						ItemStack drop = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
						if (!plugin.getConfig().getBoolean("dropBlank"))
							drop = HeadDrops.setSkin(drop, dead.getName());

						event.getDrops().add(drop);
					}

				} else {
					ItemStack drop = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
					if (!plugin.getConfig().getBoolean("dropBlank"))
						drop = HeadDrops.setSkin(drop, dead.getName());

					event.getDrops().add(drop);
				}
			}
		}
	}

	// @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	// public void onBlockDamage(BlockDamageEvent event) {
	// Bukkit.broadcastMessage("test" + event.getBlock().getData());
	// if (event.getBlock().getType() == Material.SKULL && event.getBlock().get
	// == 3 && headinfo.enabled.contains(event.getPlayer().getName())) {
	// event.getPlayer().sendMessage(HeadDrops.PREFIX + "Name: " + ((Skull)
	// event.getBlock()).getOwner());
	// event.setCancelled(true);
	// }
	// }

	private void dropSkull(Location loc, int type) {
		loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.SKULL_ITEM, 1, (byte) type));
	}

}