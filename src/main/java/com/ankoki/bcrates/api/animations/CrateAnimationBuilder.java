package com.ankoki.bcrates.api.animations;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CrateAnimationBuilder {

	private int rowCount, ticksBetween;
	private final Map<Integer, List<Character>> pages = new TreeMap<>();
	private final Map<Character, ItemStack> shapes = new HashMap<>();

	/**
	 * Sets the row count for the animation.
	 * @param rowCount the new row count.
	 * @return the current builder for chaining.
	 */
	public CrateAnimationBuilder setRowCount(int rowCount) {
		this.rowCount = rowCount;
		return this;
	}

	/**
	 * Sets the ticks between each page for the animation.
	 * @param ticksBetween the new ticks between.
	 * @return the current builder for chaining.
	 */
	public CrateAnimationBuilder setTicksBetween(int ticksBetween) {
		this.ticksBetween = ticksBetween;
		return this;
	}

	/**
	 * Sets the shape for the page given.
	 * @param i the page number to set.
	 * @param items the shape. This list should be the amount of items this animation has. <p>
	 *				Each character represents an item; imagine I had an animation with 1 row. <p>
	 *      		I call <code>builder.setShape(1, 'x', 'x', 'x', 'x', 'y', 'x', 'x', 'x', 'x');</code> <p>
	 *      		The shape is now set, now we can use the {@link CrateAnimationBuilder#setShapeItem(char, ItemStack)} (char, ItemStack)} method. <p>
	 *      		Firstly, I want to set all occurrences of 'x' to black stained-glass panes. <p>
	 *      		To do this, I will call <code>builder.setShapeItem('x', new ItemStack(Material.BLACK_STAINED_GLASS_PANE);</code> <p>
	 *      		Now the GUI is black stained-glass panes, however the middle slot where y is is still empty. <p>
	 *      		I want that to be the result filler item; and we can use the same method, just with different parameters. <p>
	 *      		<code>myGui.setShapeItem('y', CrateAnimation.RESULT_FILLER);</code> would do the job. <p>
	 *  			Please note that the shape items are consistent throughout all pages, to save time and space.
	 * @return the current builder for chaining.
	 */
	public CrateAnimationBuilder setShape(int i, Character... items) {
		pages.put(i, List.of(items));
		return this;
	}

	/**
	 * Sets the shape for the page given.
	 * @param i the page number to set.
	 * @param items the shape. This list should be the amount of items this animation has. <p>
	 *				Each character represents an item; imagine I had an animation with 1 row. <p>
	 *      		I call <code>builder.setShape(1, "xxxxyxxxx');</code> <p>
	 *      		The shape is now set, now we can use the {@link CrateAnimationBuilder#setShapeItem(char, ItemStack)} (char, ItemStack)} method. <p>
	 *      		Firstly, I want to set all occurrences of 'x' to black stained-glass panes. <p>
	 *      		To do this, I will call <code>builder.setShapeItem('x', new ItemStack(Material.BLACK_STAINED_GLASS_PANE);</code> <p>
	 *      		Now the GUI is black stained-glass panes, however the middle slot where y is is still empty. <p>
	 *      		I want that to be the result filler item; and we can use the same method, just with different parameters. <p>
	 *      		<code>myGui.setShapeItem('y', CrateAnimation.RESULT_FILLER);</code> would do the job. <p>
	 *  			Please note that the shape items are consistent throughout all pages, to save time and space.
	 * @return the current builder for chaining.
	 */
	public CrateAnimationBuilder setShape(int i, String... items) {
		List<Character> list = new ArrayList<>();
		if (items.length != rowCount)
			throw new IllegalArgumentException("Length of parameter 'items' must be the same as field 'rowCount'.");
		for (String item : items) {
			if (item == null || item.length() != 9)
				throw new IllegalArgumentException("All values of parameter 'items' must have a length of 9.");
			for (Character c : item.toCharArray())
				list.add(c);
		}
		pages.put(i, list);
		return this;
	}

	/**
	 * Sets a shape item for the current builder.
	 * @param c the character to set. See {@link CrateAnimationBuilder#setShape(int, Character...)} for more information.
	 * @param item the item to set it too.
	 * @return the current builder for chaining.
	 */
	public CrateAnimationBuilder setShapeItem(char c, ItemStack item) {
		shapes.put(c, item == null ? new ItemStack(Material.AIR) : item);
		return this;
	}

	/**
	 * Builds the current builder.
	 * @return the complete animation.
	 */
	public CrateAnimation build() {
		final CrateAnimation animation = new CrateAnimation(rowCount, ticksBetween);
		for (Map.Entry<Integer, List<Character>> entry : pages.entrySet()) {
			List<ItemStack> items = new ArrayList<>();
			for (char c : entry.getValue())
				items.add(shapes.get(c));
			animation.addPage(items.toArray(new ItemStack[0]));
		}
		return animation;
	}
}
