package com.ankoki.bcrates.api.crates;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CrateItem {

	/**
	 * Serializes a CrateItem into a Map.
	 * @param item the item to serialize.
	 * @return the created map.
	 */
	public static Map<String, Object> serialize(CrateItem item) {
		Map<String, Object> map = new HashMap<>();
		map.put("item", item.getItem().serialize());
		map.put("chance", item.getChance());
		return map;
	}

	/**
	 * Gets a new CrateItem from a map.
	 * @param map the map to deserialize.
	 * @return the CrateItem.
	 */
	public static CrateItem deserialize(Map<String, Object> map) {
		if (map.containsKey("item") && map.containsKey("chance"))
			return new CrateItem(ItemStack.deserialize((Map<String, Object>) map.get("item")), (Double) map.get("key"));
		throw new IllegalArgumentException("Map must contain 'item' and 'chance' key.");
	}

	private final ItemStack item;
	private final double chance;

	/**
	 * Creates a new CrateItem which can be used in LootTables.
	 * @param item the item the CrateItem should be, including amount.
	 * @param chance the chance to get this item.
	 */
	public CrateItem(ItemStack item, double chance) {
		this.item = item;
		this.chance = chance;
	}

	/**
	 * The wrapped item.
	 * @return the item.
	 */
	public ItemStack getItem() {
		return item;
	}

	/**
	 * The wrapped chance.
	 * @return the chance.
	 */
	public double getChance() {
		return chance;
	}
}
