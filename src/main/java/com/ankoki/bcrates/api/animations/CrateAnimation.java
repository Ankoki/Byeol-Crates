package com.ankoki.bcrates.api.animations;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.misc.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CrateAnimation {

	private static final ByeolCrates plugin = ByeolCrates.getPlugin(ByeolCrates.class);
	public static ItemStack RESULT_FILLER = ItemUtils.getItem(Material.SUNFLOWER, "§eReward", "§aClick to redeem your rewards!");
	// { x, x, x, x, x, x, x, x, x }
	// { x, x, x, x, x, x, x, x, x }
	// { x, x, x, x, x, x, x, x, x }
	// { x, x, x, x, x, x, x, x, x }
	// { x, x, x, x, x, x, x, x, x }
	private ItemStack[][] rows = new ItemStack[0][0];
	private int rowCount;
	private int ticksBetween;

	/**
	 * Creates a new empty animation.
	 * @param rowCount the rows this animation will have.
	 * @param ticksBetween the ticks between each page.
	 */
	public CrateAnimation(int rowCount, int ticksBetween) {
		this.rowCount = rowCount;
		this.ticksBetween = ticksBetween;
	}

	/**
	 * Sets the rows this animation requires.
	 * @param rowCount the new count.
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * Gets the amount of rows this animation has.
	 * @return the rows of this animation.
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Sets the ticks between each page of the animation.
	 * @param ticksBetweenEach the ticks between each page.
	 */
	public void setTicksBetween(int ticksBetweenEach) {
		this.ticksBetween = ticksBetweenEach;
	}

	/**
	 * Adds a new page to this animation.
	 * @param items the items the next page should show. The length of this array should be equal to CrateItem#getRowCount() * 9.
	 * @return the current animation for chaining.
	 */
	public CrateAnimation addPage(ItemStack... items) {
		if (items.length > 9 * rowCount)
			throw new IllegalArgumentException("Cannot fit that many items (" + 9 * rowCount + ") in a page with this size.");
		if (rows.length > 0) {
			ItemStack[][] copy = new ItemStack[rows.length + 1][items.length];
			System.arraycopy(rows, 0, copy, 0, rows.length);
			copy[rows.length] = items;
			rows = copy;
		} else {
			ItemStack[][] array = new ItemStack[1][items.length];
			array[0] = items;
			rows = array;
		}
		return this;
	}

	/**
	 * Plays the current animation for an inventory.
	 * @param inventory the inventory to play it for.
	 */
	public void play(Inventory inventory) {
		if (inventory.getSize() / 9 != this.rowCount)
			throw new IllegalArgumentException("The given inventory must be the same size as CrateAnimation#rowCount.");
		new BukkitRunnable() {
			private int page = -1;
			@Override
			public void run() {
				if (page == rows.length - 1) {
					this.cancel();
				} else {
					page++;
					for (int i = 0; i < inventory.getSize(); i++) {
						ItemStack item = rows[page][i];
						if (item != null)
							inventory.setItem(i, item);
					}
				}
			}
		}.runTaskTimer(plugin, 0L, this.ticksBetween);
	}
}