package com.ankoki.bcrates.internal;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.api.crates.Crate;
import com.ankoki.bcrates.api.crates.CrateType;
import com.ankoki.bcrates.api.crates.animations.CrateAnimation;
import com.ankoki.bcrates.misc.BPlayer;
import de.tr7zw.nbtapi.NBTBlock;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class CrateHandler implements Listener {

	private final List<CrateType> CRATE_TYPE_REGISTRY = new ArrayList<>();
	private final List<Crate> CRATE_CACHE = new ArrayList<>();

	/**
	 * Registers a crate if the ID is not taken.
	 *
	 * @param crate the crate to register.
	 * @return true if successful, false if the ID is in use.
	 */
	public boolean registerCrateType(CrateType crate) {
		if (this.getCrateTypeById(crate.getId()) != null)
			return false;
		CRATE_TYPE_REGISTRY.add(crate);
		return true;
	}

	/**
	 * Places and registers a crate at the given location of the type.
	 *
	 * @param location the location to place the crate.
	 * @param type     the type to make the crate.
	 */
	public void placeCrate(Location location, CrateType type) {
		location.getBlock().setType(type.getBlock().getType(), false);
		NBTBlock block = new NBTBlock(location.getBlock());
		block.getData().setString("bcrate", type.getId());
	}

	/**
	 * Breaks a crate a location.
	 *
	 * @param location the location to break a crate at.
	 * @param drop     true if the crate block should be dropped.
	 */
	public void breakCrate(Location location, boolean drop) {
		NBTBlock block = new NBTBlock(location.getBlock());
		if (!block.getData().hasKey("bcrate"))
			return;
		if (drop) {
			CrateType type = this.getCrateTypeById(block.getData().getString("bcrate"));
			if (type == null) {
				block.getData().removeKey("bcrate");
				return;
			}
			location.getWorld().dropItem(location, type.getBlock());
		}
		location.getBlock().setType(Material.AIR);
		block.getData().removeKey("bcrate");
	}

	/**
	 * Gets a crate by its name.
	 *
	 * @param name the name look for.
	 * @return the crate if it exists, else null.
	 */
	@Nullable
	public CrateType getCrateType(String name) {
		for (CrateType crate : CRATE_TYPE_REGISTRY) {
			if (crate.getName().equalsIgnoreCase(name))
				return crate;
		}
		return null;
	}

	/**
	 * Gets a crate by its unique ID.
	 *
	 * @param id the id look for.
	 * @return the crate if it exists, else null.
	 */
	@Nullable
	public CrateType getCrateTypeById(String id) {
		for (CrateType crate : CRATE_TYPE_REGISTRY) {
			if (crate.getId().equalsIgnoreCase(id))
				return crate;
		}
		return null;
	}

	/**
	 * Checks if an item is a crate key.
	 *
	 * @param item  the item to check.
	 * @param crate the crate to check for.
	 * @return true if the item is a key.
	 */
	public static boolean isKey(ItemStack item, CrateType crate) {
		NBTItem nbt = new NBTItem(item);
		return nbt.hasKey("bcrates") && nbt.getString("bcrates").equalsIgnoreCase(crate.getId());
	}

	/**
	 * Checks if the block at the location is a crate.
	 *
	 * @param location the location to check.
	 * @return true if there is a crate.
	 */
	public boolean isCrate(Location location) {
		return this.isCrate(location.getBlock());
	}

	/**
	 * Checks if the block is a crate.
	 *
	 * @param block the block to check.
	 * @return true if there is a crate.
	 */
	public boolean isCrate(Block block) {
		NBTBlock nbt = new NBTBlock(block);
		return nbt.getData().getString("bcrate") != null;
	}

	/**
	 * Checks if the block at the location is of the given crate type.
	 *
	 * @param location the location to check.
	 * @param type     the type to check.
	 * @return true if there is a crate of that type.
	 */
	public boolean isCrate(Location location, CrateType type) {
		return this.isCrate(location.getBlock(), type);
	}

	/**
	 * Checks if the block at the location is of the given crate type.
	 *
	 * @param block the block to check.
	 * @param type  the type to check.
	 * @return true if there is a crate of that type.
	 */
	public boolean isCrate(Block block, CrateType type) {
		if (!this.isCrate(block)) return false;
		NBTBlock nbt = new NBTBlock(block);
		return nbt.getData().getString("bcrate").equalsIgnoreCase(type.getId());
	}

	/**
	 * Gets a crate at a location, null if CrateHandler#isCrate(Location) is false.
	 *
	 * @param location the location to look for a crate.
	 * @return the crate or null.
	 */
	@Nullable
	public Crate getCrate(Location location) {
		return this.getCrate(location.getBlock());
	}

	/**
	 * Gets a crate at a block, null if CrateHandler#isCrate(Block) is false.
	 *
	 * @param block the block to look for a crate.
	 * @return the crate or null.
	 */
	@Nullable
	public Crate getCrate(Block block) {
		for (Crate crate : CRATE_CACHE) {
			if (crate.getLocation() == block.getLocation())
				return crate;
		}
		return null;
	}

	/**
	 * Opens a crate for the given player.
	 *
	 * @param crate  the crate to open.
	 * @param player the player to open it for.
	 */
	public void openCrate(CrateType crate, Player player) {
		CrateAnimation animation = crate.getAnimation();
		Inventory inventory = Bukkit.createInventory(player, crate.getRows() * 9,
				ByeolCrates.getPlugin(ByeolCrates.class)
						.getConfiguration().CRATE_INVENTORY_NAME
						.replace("<crate>", crate.getId())
						.replace("<crate-name>", crate.getName()));
		BPlayer bplayer = BPlayer.get(player);
		player.openInventory(inventory);
		bplayer.setOpenCrate(crate);
		bplayer.setOpenCrateInventory(inventory);
		animation.play(inventory);
	}

	@EventHandler
	private void onCrateView(PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.LEFT_CLICK_BLOCK) return;

	}

	@EventHandler
	private void onCrateOpen(PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		Block block = event.getClickedBlock();
		if (!this.isCrate(block)) return;
		event.setCancelled(true);
		final Crate crate = this.getCrate(block);
		final Player p = event.getPlayer();
		final ItemStack item = p.getInventory().getItemInMainHand();
		if (item == crate.getType().getKey())
			this.openCrate(crate.getType(), p);
	}

	@EventHandler
	private void onCrateClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player p) {
			BPlayer player = BPlayer.get(p);
			if (player.isOpening() && player.getOpenCrateInventory() == event.getInventory()) {
				event.setCancelled(true);
				if (event.getCurrentItem() == CrateAnimation.RESULT_FILLER) {
					CrateType crate = player.getOpenCrate();
					ItemStack[] items = crate.getLoot().generateRewards(ThreadLocalRandom.current(), crate.getMinimumRewards(), crate.getMaximumRewards());
					if (player.canHold(items))
						p.getInventory().addItem(items);
					else {
						player.sendMessage(ByeolCrates.getPlugin(ByeolCrates.class).getMessages().get("no-space"));
						p.getInventory().addItem(player.getOpenCrate().getKey());
					}
					p.closeInventory();
					player.setOpenCrate(null);
					player.setOpenCrateInventory(null);
				}
			} else if (player.isViewing() && player.getViewedCrateInventory() == event.getInventory()) {
				event.setCancelled(true);
				p.closeInventory();
				player.setViewedCrate(null);
				player.setViewedCrateInventory(null);
			}
		}
	}

	@EventHandler
	private void onCrateDrag(InventoryDragEvent event) {
		if (event.getWhoClicked() instanceof Player p) {
			BPlayer player = BPlayer.get(p);
			if (player.isOpening() && player.getOpenCrateInventory() == event.getInventory())
				event.setCancelled(true);
			else if (player.isViewing() && player.getViewedCrateInventory() == event.getInventory()) {
				event.setCancelled(true);
				p.closeInventory();
				player.setViewedCrate(null);
				player.setViewedCrateInventory(null);
			}
		}
	}
}