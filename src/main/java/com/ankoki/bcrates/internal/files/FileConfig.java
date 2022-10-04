package com.ankoki.bcrates.internal.files;

import com.ankoki.bcrates.ByeolCrates;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class FileConfig {

	private final ByeolCrates plugin;
	private final File file;
	private FileConfiguration configuration;

	public FileConfig(ByeolCrates plugin, String string, boolean match) {
		this.plugin = plugin;
		this.file = new File(plugin.getDataFolder(), string);
		if (!file.exists()) {
			if (string.equalsIgnoreCase("storage.yml")) {
				try {
					file.createNewFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else
				plugin.saveResource(string, false);
		}
		this.configuration = YamlConfiguration.loadConfiguration(file);
		if (match)
			matchConfigFile();
		loadFile();
	}

	public void reload() {
		this.configuration = YamlConfiguration.loadConfiguration(file);
	}

	public void loadFile(){}

	public FileConfiguration getConfig() {
		return configuration;
	}

	private void matchConfigFile() {
		try {
			boolean hasUpdated = false;
			InputStream stream = plugin.getResource(file.getName());
			InputStreamReader is = new InputStreamReader(stream);
			YamlConfiguration defLand = YamlConfiguration.loadConfiguration(is);
			for (String key : defLand.getConfigurationSection("").getKeys(true)) {
				if (!configuration.contains(key)) {
					configuration.set(key, defLand.get(key));
					hasUpdated = true;
				}
			}
			for (String key : configuration.getConfigurationSection("").getKeys(true)) {
				if (!defLand.contains(key)) {
					configuration.set(key, null);
					hasUpdated = true;
				}
			}
			if (hasUpdated)
				configuration.save(file);
			is.close();
			stream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
