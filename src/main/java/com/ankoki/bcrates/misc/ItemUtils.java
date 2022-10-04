package com.ankoki.bcrates.misc;

import com.ankoki.bcrates.api.crates.CrateType;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemUtils {

	/**
	 * Gets a blank item with no lore or name.
	 * @param material the material.
	 * @return the blank item.
	 */
	public static ItemStack getBlank(@NotNull Material material) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§e");
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Gets an ItemStack with modified ItemMeta.
	 * @param material the material.
	 * @param name the name.
	 * @param lore the lore.
	 * @return the built item.
	 */
	public static ItemStack getItem(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.stream(lore).map(Misc::colour).collect(Collectors.toList()));
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Creates an ItemStack from the given options to become a crate key for the given key.
	 * @param crate the crate the key can open.
	 * @param material the material it should be.
	 * @param name the name of the key.
	 * @param lore the lore of the key.
	 * @return the built key.
	 */
	public static ItemStack getKey(CrateType crate, Material material, String name, String... lore) {
		return ItemUtils.getKey(crate.getId(), material, name, lore);
	}

	/**
	 * Creates an ItemStack from the given options to become a crate key for the given key.
	 * @param id the id of the crate it should be.
	 * @param material the material it should be.
	 * @param name the name of the key.
	 * @param lore the lore of the key.
	 * @return the built key.
	 */
	public static ItemStack getKey(String id, Material material, String name, String... lore) {
		ItemStack item = ItemUtils.getItem(material, name, lore);
		NBTItem nbt = new NBTItem(item);
		nbt.setString("bcrates", id);
		return nbt.getItem();
	}
}
