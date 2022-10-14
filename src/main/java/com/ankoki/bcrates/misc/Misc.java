package com.ankoki.bcrates.misc;

import com.ankoki.bcrates.ByeolCrates;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Misc {

	private static final String STRING_VERSION = (ByeolCrates.getPlugin(ByeolCrates.class).getServer().getClass().getPackage().getName())
			.substring(ByeolCrates.getPlugin(ByeolCrates.class).getServer().getClass().getPackage().getName().lastIndexOf('.') + 1)
			.split("_")[1];
	private static final Pattern HEX_PATTERN = Pattern.compile("<#([a-fA-F\\d]{6})>");
	private static final Map<Character, Integer> ROMAN_VALUES = new LinkedHashMap<>();

	static {
		ROMAN_VALUES.put('I', 1);
		ROMAN_VALUES.put('V', 5);
		ROMAN_VALUES.put('X', 10);
		ROMAN_VALUES.put('L', 50);
		ROMAN_VALUES.put('C', 100);
		ROMAN_VALUES.put('D', 500);
		ROMAN_VALUES.put('M', 1000);
	}

	/**
	 * Returns a coloured version of the parameter. <p/>
	 * If the server is 1.16+, hex colours will be transformed in the format <code><#hexcode></code>
	 * @param message the message to colour.
	 * @return the coloured text.
	 */
	public static String colour(String message) {
		if (Misc.getServerMinor() >= 16) {
			Matcher matcher = HEX_PATTERN.matcher(message);
			if (matcher.find()) {
				String matched = matcher.group(1);
				message = message.replace("<#" + matched + ">", net.md_5.bungee.api.ChatColor.of("#" + matched).toString());
			}
		} return ChatColor.translateAlternateColorCodes('&', message);
	}

	/**
	 * Gets the minor version (x.>X<.x) of the server.
	 * @return the server minor.
	 */
	public static int getServerMinor() {
		return Integer.parseInt(STRING_VERSION);
	}

	/**
	 * Gets a readable string from a location.
	 * @param location the location to adapt.
	 * @return the adapted location.
	 */
	public static String adaptLocation(Location location) {
		if (location == null)
			return "<none>";
		return location.getX() + ", " + location.getY() + ", " + location.getZ() +
				(location.getWorld() != null ? " in world '" + location.getWorld().getName() + "'" : "");
	}

	/**
	 * Gets a material from a string, or null.
	 * @param unparsed the material to parse.
	 * @return the material or null if not found.
	 */
	@Nullable
	public static Material adaptMaterial(String unparsed) {
		try {
			return Material.valueOf(unparsed.toUpperCase().replace(" ", "_"));
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	/**
	 * Gets an enchantment from a string, or null.
	 * @param unparsed the enchantment to parse.
	 * @return the enchantment or null if not found.
	 */
	@Nullable
	public static Enchantment adaptEnchant(String unparsed) {
		String key = unparsed.toUpperCase().replace(" ", "_");
		return Enchantment.getByName(key) == null ?
				Enchantment.getByKey(NamespacedKey.minecraft(key.toLowerCase())) :
				Enchantment.getByName(key);
	}

	/**
	 * Converts a string to title case.
	 * For example, "hello world" to "Hello World",
	 *
	 * @param text text to be in title case.
	 * @return string in title case.
	 */
	public static String toTitleCase(String text) {
		if (text == null || text.isEmpty() || text.isBlank()) return "";
		if (text.length() == 1) return text.toUpperCase();
		text = text.replace("_", " ");

		StringBuilder builder = new StringBuilder(text.length());

		Stream.of(text.split(" ")).forEach(stringPart -> {
			char[] charArray = stringPart.toLowerCase().toCharArray();
			charArray[0] = Character.toUpperCase(charArray[0]);
			builder.append(new String(charArray)).append(" ");
		});
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}

	/**
	 * Checks to see if a string is null or empty.
	 * @param s the string to check.
	 * @return true if null or empty.
	 */
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	/**
	 * Checks if an array contains an item.
	 * @param needle the item to look for.
	 * @param haystack the array to look in.
	 * @param <T> the type of array/needle.
	 * @return true if contains, else false.
	 */
	@SafeVarargs
	public static <T> boolean contains(T needle, T... haystack) {
		for (T item : haystack) {
			if (item == needle)
				return true;
		} return false;
	}

	/**
	 * Converts a roman numeral to its int form (III -> 3).
	 * @param roman the numeral to translate.
	 * @return the roman numeral's number. Will be -1 if unable to be parsed.
	 */
	public static int fromRoman(String roman) {
		int number = -1;
		for (int i = 0; i < roman.length(); i++) {
			if (i + 1 == roman.length() ||
					ROMAN_VALUES.get(roman.charAt(i)) >= ROMAN_VALUES.get(roman.charAt(i + 1)))
				number += ROMAN_VALUES.get(roman.charAt(i));
			else
				number -= ROMAN_VALUES.get(roman.charAt(i));
		}
		return number;
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
