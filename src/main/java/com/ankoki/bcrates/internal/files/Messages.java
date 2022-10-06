package com.ankoki.bcrates.internal.files;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.misc.Misc;
import org.jetbrains.annotations.Nullable;

public class Messages extends FileConfig {

	private final String prefix;

	public Messages(ByeolCrates plugin) {
		super(plugin, "messages.yml", true);
		prefix = this.get("prefix") + " ";
	}

	@Nullable
	public String get(String path) {
		return Misc.colour(prefix + this.getConfig().getString(path));
	}

	/**
	 * Gets a message without a prefix.
	 * @param path the path to get.
	 * @return the semi-raw content.
	 */
	public String getRaw(String path) {
		return Misc.colour(this.getConfig().getString(path));
	}
}
