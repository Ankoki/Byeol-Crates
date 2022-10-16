package com.ankoki.bcrates.internal.files;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class FileConfig {

	private final ByeolCrates plugin;
	private final File file;
	private YamlConfiguration configuration;

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
		ByeolLog log = new ByeolLog(plugin);
		loadFile(log);
		if (log.hasOutput())
			log.printLog();
	}

	public void reload() {
		this.configuration = YamlConfiguration.loadConfiguration(file);
	}

	public void loadFile(ByeolLog log){}

	public YamlConfiguration getConfig() {
		return configuration;
	}

	private void matchConfigFile() {
		try {
			boolean hasUpdated = false;
			InputStream stream = plugin.getResource(file.getName());
			InputStreamReader is = new InputStreamReader(stream);
			YamlConfiguration jarConfig = YamlConfiguration.loadConfiguration(is);
			for (String key : jarConfig.getConfigurationSection("").getKeys(true)) {
				if (!configuration.contains(key)) {
					configuration.set(key, jarConfig.get(key));
					hasUpdated = true;
				}
			}
			for (String key : configuration.getConfigurationSection("").getKeys(true)) {
				if (!jarConfig.contains(key)) {
					if (file.getName().equalsIgnoreCase("config.yml") &&
							key.equalsIgnoreCase("dev-mode")) continue;
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
