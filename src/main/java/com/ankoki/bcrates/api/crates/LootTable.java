package com.ankoki.bcrates.api.crates;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LootTable {

	private CrateItem[] loot;

	/**
	 * Creates a new LootTable for a crate.
	 * @param loot the probability loot.
	 */
	public LootTable(CrateItem... loot) {
		this.loot = loot;
	}

	/**
	 * Gets an array of the CrateItems.
	 * @return the loot.
	 */
	public CrateItem[] getLoot() {
		return loot;
	}

	/**
	 * Adds items to the current loot table.
	 * @param items the items to add.
	 */
	public void addItems(CrateItem... items) {
		CrateItem[] clone = new CrateItem[loot.length + items.length];
		System.arraycopy(loot, 0, clone, 0, loot.length);
		System.arraycopy(items, loot.length, clone, loot.length, clone.length - loot.length);
		loot = clone;
	}

	/**
	 * Gets the rewards of a crate.
	 * @param random the random object to act with.
	 * @param minimum the minimum amount of items to get from this reward.
	 * @param maximum the maximum amount of items to get from this reward. -1 if infinite.
	 * @return the generated items.
	 */
	public ItemStack[] generateRewards(ThreadLocalRandom random, int minimum, int maximum) {
		if (minimum > maximum)
			throw new IllegalArgumentException("You cannot have a minimum (" + minimum + ") which is larger than the maximum (" + maximum + ")");
		for (int i = 0; i < loot.length; i++) {
			int randomIndexToSwap = random.nextInt(loot.length);
			CrateItem temp = loot[randomIndexToSwap];
			loot[randomIndexToSwap] = loot[i];
			loot[i] = temp;
		}
		final List<ItemStack> items = new ArrayList<>();
		while (items.size() < minimum) {
			for (CrateItem item : loot) {
				if (random.nextDouble(0, 100) < item.getChance())
					items.add(item.getItem());
			}
		}
		while (items.size() > maximum)
			items.remove(items.size() - 2);
		return items.toArray(new ItemStack[0]);
	}
}
