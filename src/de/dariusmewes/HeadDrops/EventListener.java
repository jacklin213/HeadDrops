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

	private HeadDrops instance;
	private Random rand = new Random();

	public EventListener(HeadDrops instance) {
		this.instance = instance;
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
		if (rand.nextInt(100) < instance.getConfig().getInt("hostile")) {
			if (event.getEntity().getLastDamageCause() == null)
				return;

			EntityType t = event.getEntity().getType();
			DamageCause cause = event.getEntity().getLastDamageCause().getCause();

			if (cause == DamageCause.ENTITY_ATTACK && event.getEntity().getKiller() != null) {
				if (instance.getConfig().getBoolean("permissionCheckMob") && !event.getEntity().getKiller().hasPermission("headdrops.mobhead"))
					return;

				Location loc = event.getEntity().getLocation();

				if (t == EntityType.ZOMBIE && instance.getConfig().getBoolean("zombiedrop")) {
					dropSkull(loc, 2);
				} else if (t == EntityType.SKELETON && instance.getConfig().getBoolean("skeletondrop")) {
					if (((Skeleton) event.getEntity()).getSkeletonType() == SkeletonType.NORMAL)
						dropSkull(loc, 0);
				} else if (t == EntityType.CREEPER && instance.getConfig().getBoolean("creeperdrop")) {
					dropSkull(loc, 4);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (rand.nextInt(100) < instance.getConfig().getInt("player") && instance.getConfig().getBoolean("playerdrop")) {
			if (event.getEntity().getLastDamageCause() == null)
				return;

			Player dead = event.getEntity();
			Player killer = event.getEntity().getKiller();
			DamageCause cause = event.getEntity().getLastDamageCause().getCause();

			if (cause == DamageCause.ENTITY_ATTACK && killer != null) {
				if (instance.getConfig().getBoolean("permissionCheckPlayer") && !event.getEntity().getKiller().hasPermission("headdrops.playerhead"))
					return;

				if (instance.getConfig().getBoolean("ironanddiamond")) {
					if (killer.getItemInHand().getType() == Material.IRON_SWORD || killer.getItemInHand().getType() == Material.DIAMOND_SWORD || (instance.getConfig().getBoolean("axeenabled") && (killer.getItemInHand().getType() == Material.IRON_AXE || killer.getItemInHand().getType() == Material.DIAMOND_AXE))) {
						ItemStack drop = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
						if (!instance.getConfig().getBoolean("dropBlank"))
							drop = HeadDrops.setSkin(drop, dead.getName());

						event.getDrops().add(drop);
					}
				} else {
					ItemStack drop = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
					if (!instance.getConfig().getBoolean("dropBlank"))
						drop = HeadDrops.setSkin(drop, dead.getName());

					event.getDrops().add(drop);
				}
			}
		}
	}

	private void dropSkull(Location loc, int type) {
		loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.SKULL_ITEM, 1, (byte) type));
	}

}