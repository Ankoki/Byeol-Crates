package com.ankoki.bcrates.misc;

import com.ankoki.bcrates.api.crates.CrateType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BPlayer {

	private static final Map<Player, BPlayer> BPLAYER_CACHE = new HashMap<>();

	/**
	 * Gets a BPlayer from a player object.
	 * @param player the player to wrap.
	 * @return the wrapped BPlayer.
	 */
	public static BPlayer get(Player player) {
		return BPLAYER_CACHE.getOrDefault(player, new BPlayer(player));
	}

	private final Player player;
	private CrateType openCrate;
	private CrateType viewCrate;
	private Inventory openCrateInventory;
	private Inventory viewCrateInventory;

	private BPlayer(Player player) {
		this.player = player;
		this.update();
	}

	/**
	 * Gets the Bukkit player.
	 * @return the player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Checks if a player is viewing a crate.
	 * @return true if the player is viewing a crate.
	 */
	public boolean isViewing() {
		return viewCrateInventory != null;
	}

	/**
	 * Gets the crate type the player is viewing, or null if BPlayer#isViewing is false.
	 * @return the viewed crate type or null.
	 */
	public CrateType getViewedCrate() {
		return viewCrate;
	}

	/**
	 * Sets the type of the viewed crate.
	 * @param viewCrate the crate type the player is viewing.
	 */
	public void setViewedCrate(CrateType viewCrate) {
		this.viewCrate = viewCrate;
	}

	/**
	 * Gets the inventory of the crate player is viewing.
	 * @return the crate inventory.
	 */
	public Inventory getViewedCrateInventory() {
		return viewCrateInventory;
	}

	/**
	 * Sets the inventory of the crate player is viewing.
	 * @param crate the inventory of the crate.
	 */
	public void setViewedCrateInventory(Inventory crate) {
		this.viewCrateInventory = crate;
	}

	/**
	 * Checks if a player is opening a crate.
	 * @return true if they are.
	 */
	public boolean isOpening() {
		return openCrateInventory != null;
	}

	/**
	 * Gets the crate the player is opening or null if BPlayer#isOpening is false.
	 * @return the open crate or null.
	 */
	@Nullable
	public CrateType getOpenCrate() {
		return openCrate;
	}

	/**
	 * Sets the player's current crate.
	 * @param openCrate the crate that is being opened.
	 */
	public void setOpenCrate(CrateType openCrate) {
		this.openCrate = openCrate;
	}

	/**
	 * Gets the inventory of the crate player is opening.
	 * @return the crate inventory.
	 */
	public Inventory getOpenCrateInventory() {
		return openCrateInventory;
	}

	/**
	 * Sets the inventory of the crate player is opening.
	 * @param crate the inventory of the crate.
	 */
	public void setOpenCrateInventory(Inventory crate) {
		this.openCrateInventory = crate;
	}

	/**
	 * Checks to see if the player can hold the given items.
	 * @param items the items to check.
	 * @return true if they can hold the items.
	 */
	public boolean canHold(ItemStack... items) {
		final Inventory inventory = player.getInventory();
		final Inventory clone = Bukkit.createInventory(null, 36);
		for (int i = 0; i < 36; i++) {
			ItemStack item = inventory.getItem(i);
			if (item != null) clone.addItem(item);
		} return clone.addItem(items).isEmpty();
	}

	/**
	 * Gives the player a key.
	 * @param type they key type to give.
	 * @return true if they successfully received it.
	 */
	public boolean giveKey(CrateType type) {
		return this.giveKey(type, 1);
	}

	/**
	 * Gives the player a key.
	 * @param type they key type to give.
	 * @param amount the amount of keys to give.
	 * @return true if they successfully received it.
	 */
	public boolean giveKey(CrateType type, int amount) {
		ItemStack key = type.getKey();
		key.setAmount(amount);
		if (this.canHold(key))
			this.player.getInventory().addItem(key);
		else return false;
		return true;
	}

	/**
	 * Sends coloured messages to the player.
	 * @param messages the messages to send.
	 */
	public void sendMessage(String... messages) {
		for (String message : messages)
			player.sendMessage(Misc.colour(message));
	}

	/**
	 * Updates the cache to the current instance.
	 * Used primarily internally however can be called externally if needed.
	 */
	public void update() {
		BPLAYER_CACHE.put(player, this);
	}
}
