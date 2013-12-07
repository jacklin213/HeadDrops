/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.timolia.headdrops.cmds.headinfo;

public class EventListener implements Listener {

	private HeadDrops instance;
	private Random rand = new Random();

	public EventListener(HeadDrops instance) {
		this.instance = instance;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockDamage(BlockDamageEvent event) {
		if (event.getBlock().getType() == Material.SKULL && headinfo.isActive(event.getPlayer())) {
			Skull skull = (Skull) event.getBlock().getState();
			if (skull.getSkullType() == SkullType.PLAYER && skull.hasOwner() && !SkullManager.isSkullCustom(skull.getOwner())) {
				event.getPlayer().sendMessage(HeadDrops.PREFIX + (skull.getOwner() != null ? ("This is " + skull.getOwner() + (skull.getOwner().endsWith("s") || skull.getOwner().endsWith("S") ? "'" : "'s") + " head.") : "This head is unknown..."));
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.SKULL && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			Skull skull = (Skull) event.getBlock().getState();
			if (skull.getSkullType() == SkullType.PLAYER) {
				String owner = skull.getOwner();
				if (SkullManager.isSkullCustom(owner)) {
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), SkullManager.getCustomSkull(CustomSkullType.forSkullOwnerString(owner)));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (instance.updateAvailable && (event.getPlayer().isOp() || event.getPlayer().hasPermission("headdrops.update"))) {
			event.getPlayer().sendMessage(HeadDrops.PREFIX + "A new version is available!");
			event.getPlayer().sendMessage(HeadDrops.PREFIX + "Get it at http://dev.bukkit.org/server-mods/head-drops");
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity().getLastDamageCause() == null)
			return;

		if (event.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK && event.getEntity().getKiller() != null) {
			if (instance.getConfig().getBoolean("permissionCheckMob") && !event.getEntity().getKiller().hasPermission("headdrops.mobhead"))
				return;

			if (event.getEntity().getType() == EntityType.SKELETON && ((Skeleton) event.getEntity()).getSkeletonType() == SkeletonType.NORMAL && chance("skeleton"))
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 0));
			else if (event.getEntity().getType() == EntityType.ZOMBIE && chance("zombie"))
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
			else if (event.getEntity().getType() == EntityType.CREEPER && chance("creeper"))
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 4));
			else {
				String entityType = event.getEntity().getType().toString();
				for (CustomSkullType skullType : CustomSkullType.values())
					if (entityType.equals(skullType.toString())) {
						if (chance(skullType.toString().toLowerCase()))
							event.getDrops().add(SkullManager.getCustomSkull(skullType));
						break;
					}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if (chance("player") && event.getEntity().getLastDamageCause() != null && event.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK && killer != null) {
			if ((instance.getConfig().getBoolean("permissionCheckPlayer") && !event.getEntity().getKiller().hasPermission("headdrops.playerhead")))
				return;

			event.getDrops().add(instance.getConfig().getBoolean("dropBlank") ? new ItemStack(Material.SKULL_ITEM, 1, (byte) 3) : SkullManager.getSkinnedHead(event.getEntity().getName()));
		}
	}

	private boolean chance(String mobType) {
		return rand.nextInt(100) < instance.getConfig().getInt("chance." + mobType);
	}

}