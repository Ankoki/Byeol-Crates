package com.ankoki.bcrates.api.crates;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.api.animations.CrateAnimation;
import com.ankoki.bcrates.api.animations.Animations;
import com.ankoki.bcrates.misc.ItemUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CrateType {

	private final String name, id;
	private final LootTable loot;
	private final int minimumRewards, maximumRewards;
	private final CrateAnimation animation;
	private final ItemStack block;
	private final ItemStack key;

	/**
	 * Creates a new crate type. Is registered on creation.
	 * @param name the name of the crate.
	 * @param id the id of the crate. Must be unique.
	 * @param key the ItemStack base the key for this crate is.
	 * @param loot the loot table of the crate.
	 * @param minimumRewards the minimum rewards you can earn from the crate.
	 * @param maximumRewards the maximum rewards you can earn from the crate.
	 * @param animation the animation to play. Take a look at {@link Animations}
	 * @param block the block to set the crate too. Must have {@link Material#isBlock()} return true.
	 */
	public CrateType(String name,
					 String id,
					 ItemStack key,
					 LootTable loot,
					 int minimumRewards,
					 int maximumRewards,
					 CrateAnimation animation,
					 Material block) {
		this.name = name;
		this.id = id;
		this.key = ItemUtils.getKey(id, key);
		this.loot = loot;
		this.minimumRewards = minimumRewards;
		this.maximumRewards = maximumRewards;
		this.animation = animation != null ? animation : Animations.SWIPE;
		this.block = this.getBlockFrom(block);
		ByeolCrates.getPlugin(ByeolCrates.class).getHandler().registerCrateType(this);
	}

	/**
	 * Gets the name of the crate type.
	 * @return the name of the crate type.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the unique id of the crate type.
	 * @return the unique id of the crate type.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the loot table of the crate type.
	 * @return the loot table of the crate type.
	 */
	public LootTable getLoot() {
		return loot;
	}

	/**
	 * The minimum amount of rewards this crate should give.
	 * @return the minimum amount.
	 */
	public int getMinimumRewards() {
		return minimumRewards;
	}

	/**
	 * The maximum amount of rewards this crate should give.
	 * @return the maximum amount.
	 */
	public int getMaximumRewards() {
		return maximumRewards;
	}

	/**
	 * Gets the crates animation.
	 * @return the animation of the crate.
	 */
	public CrateAnimation getAnimation() {
		return animation;
	}

	/**
	 * The key of this crate type.
	 * @return the key.
	 */
	public ItemStack getKey() {
		return key;
	}

	/**
	 * Gets the item of the block of this crate should be.
	 * @return the item of the block.
	 */
	public ItemStack getBlock() {
		return block;
	}

	/**
	 * Gets the ItemStack from material of the block this crate should be. Material#isBlock must return true.
	 * @param block the new material of the block.
	 */
	private ItemStack getBlockFrom(Material block) {
		if (!block.isBlock())
			throw new IllegalArgumentException("Parameter 'block' return true with Material#isBlock().");
		ItemStack item = new ItemStack(block);
		NBTItem nbt = new NBTItem(item);
		nbt.setString("bcrate", id);
		return nbt.getItem();
	}
}
