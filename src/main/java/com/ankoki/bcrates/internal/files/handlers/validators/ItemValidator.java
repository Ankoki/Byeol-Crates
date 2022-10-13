package com.ankoki.bcrates.internal.files.handlers.validators;

import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import com.ankoki.bcrates.misc.Misc;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NbtApiException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class ItemValidator implements Validator<ItemStack> {

	private static final String[] ALL_KEYS = new String[]{"material", "nbt", "blank", "shiny", "amount", "name", "lore", "enchantments"};
	private static final String[] REQUIRED_KEYS = new String[]{"material"};
	private static final String[] OPTIONAL_KEYS = new String[]{"nbt", "blank", "shiny", "amount", "name", "lore", "enchants"};

	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		boolean hasErrors = false;
		if (!section.contains("material")) {
			log.error("Required key 'material' was not found. (@" + section.getName() + ":crates.yml)");
			hasErrors = true;
		}
		String material = section.getString("material");
		if (!Misc.isNullOrEmpty(material) &&
				!material.toUpperCase().startsWith("SKULL OF ") &&
				!material.toUpperCase().startsWith("SKULL FROM ") &&
				Misc.adaptMaterial(material) == null) {
			log.error("Value of key 'material' (" + material + ") is not valid (@" + section.getName() + ":crates.yml). Must match a material or one of the following patterns: " +
					"\n • skull of <playername>" +
					"\n • skull from <base64>");
			hasErrors = true;
		} else if (material.toUpperCase().startsWith("SKULL OF ") &&
				material.toUpperCase().split("SKULL OF ").length == 1) {
			log.error("You must declare a player name when using the 'skull of <playername>' pattern. (@" + section.getName() + ":crates.yml)");
			hasErrors = true;
		} else if (material.toUpperCase().startsWith("SKULL FROM ") &&
				material.toUpperCase().split("SKULL FROM ").length == 1) {
			log.error("You must declare a base64 key when using the 'skull from <base64>' pattern. (@" + section.getName() + ":crates.yml)");
			hasErrors = true;
		}
		for (String key : section.getKeys(false)) {
			if (!Misc.contains(key, ALL_KEYS)) {
				log.error("Unexpected entry '" + key + "' was found, this will not stop production, but you should remove it (@" + section.getName() + ":crates.yml).");
			}
		}
		return !hasErrors;
	}

	@Override
	public ItemStack buildSection(ConfigurationSection section, ByeolLog log) {
		ItemStack item = null;

		// NBT (We do this first so the other settings will override if necessary)
		if (section.contains("nbt")) {
			try {
				item = NBTItem.convertNBTtoItem(new NBTContainer(section.getString("nbt")));
			} catch (NbtApiException ex) {
				log.error("Could not parse the given NBT (@" + section.getName() + ":crates.yml), NBT: '" + section.getString("nbt") + "'");
			}
		}

		// MATERIAL (The only required option is material, and sets the base of the item)
		String material = section.getString("material");
		if (material.toUpperCase().startsWith("SKULL OF ")) {
			String name = material.split("SKULL OF ")[1];
			if (item == null)
				item = new ItemStack(Material.PLAYER_HEAD);
			else
				item.setType(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
		} else if (material.toUpperCase().startsWith("SKULL FROM "))
			item = ItemValidator.getBase64Skull(material.split("SKULL FROM ")[1], item);
		else {
			if (item == null)
				item = new ItemStack(Misc.adaptMaterial(material));
			else
				item.setType(Misc.adaptMaterial(material));
		}

		// BLANK (We do this now as we can return early, as no other options now matter)
		if (section.contains("blank")) {
			if (section.getBoolean("blank")) {
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§7");
				meta.setLore(null);
				return item;
			}
		}

		// ENCHANTMENTS (Doing this before SHINY makes us able to stop shiny happening if necessary)
		if (section.contains("enchants")) {
			List<String> enchants = section.getStringList("enchants");
			for (String enchant : enchants) {
				String[] split = enchant.split(" ");
				String[] nameArr = new String[split.length - 1];
				System.arraycopy(split, 0, nameArr, 0, split.length - 1);
				String name = String.join("", nameArr);
				Enchantment enchantment = Misc.adaptEnchant(name);
				if (enchantment == null)
					log.error("Enchantment '" + enchant + "' is not valid, and will not be applied. (@" + section.getName() + ":crates.yml)");
				else {
					int level;
					try {
						level = Integer.parseInt(split[split.length - 1]);
					} catch (NumberFormatException ex) {
						level = Misc.fromRoman(split[split.length - 1]);
					}
					if (level == -1)
						log.error("Enchantment level '" + split[split.length - 1] + "' is unable to be parsed. Is it a number or roman numeral? (@" + section.getName() + ":crates.yml)");
					else
						item.addUnsafeEnchantment(enchantment, level);
				}
			}
		}

		// SHINY
		if (section.contains("shiny")) {
			boolean shiny = section.getBoolean("shiny");
			if (shiny) {
				if (!item.getEnchantments().isEmpty())
					log.warn("There are enchantments on this item, so there is no point making it shiny. (@" + section.getName() + ":crates.yml)");
				else {
					item.addUnsafeEnchantment(item.getType() == Material.BOW ?
							Enchantment.RIPTIDE : Enchantment.ARROW_INFINITE, 1);
					ItemMeta meta = item.getItemMeta();
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					item.setItemMeta(meta);
				}
			}
		}

		return item;
	}

	/**
	 * Gets a skull with a base 64 code.
	 * @param base64 the code to get from.
	 * @param item the base item, can be null.
	 * @return the built head.
	 */
	public static ItemStack getBase64Skull(String base64, @Nullable ItemStack item) {
		if (!base64.startsWith("http://textures.minecraft.net/texture/"))
			base64 = "http://textures.minecraft.net/texture/" + base64;
		if (item == null)
			item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
		byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", base64).getBytes());
		profile.setProperty(new ProfileProperty("textures", new String(encodedData)));
		meta.setPlayerProfile(profile);
		item.setItemMeta(meta);
		return item;
	}
}
