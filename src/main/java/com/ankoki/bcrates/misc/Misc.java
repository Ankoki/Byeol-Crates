package com.ankoki.bcrates.misc;

import com.ankoki.bcrates.ByeolCrates;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Misc {

	private static final String STRING_VERSION = (ByeolCrates.getPlugin(ByeolCrates.class).getServer().getClass().getPackage().getName())
			.substring(ByeolCrates.getPlugin(ByeolCrates.class).getServer().getClass().getPackage().getName().lastIndexOf('.') + 1)
			.split("_")[1];
	private static final Pattern HEX_PATTERN = Pattern.compile("<#([a-fA-F\\d]{6})>");

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
	public static String adapt(Location location) {
		if (location == null)
			return "<none>";
		return location.getX() + ", " + location.getY() + ", " + location.getZ() +
				(location.getWorld() != null ? " in world '" + location.getWorld().getName() + "'" : "");
	}
}
