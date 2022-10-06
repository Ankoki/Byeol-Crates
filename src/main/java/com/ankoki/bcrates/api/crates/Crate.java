package com.ankoki.bcrates.api.crates;

import com.ankoki.bcrates.ByeolCrates;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Crate implements ConfigurationSerializable {

	/**
	 * Creates a crate from a map.
	 * @param map the map to deserialize.
	 * @return the deserialized crate.
	 */
	public static Crate deserialize(Map<String, Object> map) {
		return new Crate(ByeolCrates.getPlugin(ByeolCrates.class).getHandler()
				.getCrateTypeById((String) map.get("type-id")),
				Location.deserialize((Map<String, Object>) map.get("location")));
	}

	/**
	 * Creates a crate from a map.
	 * @param map the map to deserialize.
	 * @return the deserialized crate.
	 */
	public static Crate valueOf(Map<String, Object> map) {
		return Crate.deserialize(map);
	}

	private final CrateType type;
	private final Location location;

	/**
	 * Creates a new crate object and sets the block at the location to the crate.
	 * @param type the crate type.
	 * @param location the location of the crate.
	 */
	public Crate(CrateType type, Location location) {
		this.type = type;
		this.location = location;
		ByeolCrates.getPlugin(ByeolCrates.class).getHandler().placeCrate(this);
	}

	/**
	 * Gets the crates type.
	 * @return the crate type.
	 */
	public CrateType getType() {
		return type;
	}

	/**
	 * Gets the location of the Crate.
	 * @return the location of the crate.
	 */
	public Location getLocation() {
		return location;
	}

	@Override
	public @NotNull Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("type-id", this.getType().getId());
		map.put("location", this.getLocation().serialize());
		return map;
	}
}
