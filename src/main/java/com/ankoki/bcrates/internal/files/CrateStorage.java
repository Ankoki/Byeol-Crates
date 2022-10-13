package com.ankoki.bcrates.internal.files;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.api.crates.Crate;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

public class CrateStorage extends FileConfig {

	public CrateStorage(ByeolCrates plugin) {
		super(plugin, "storage.yml", false);
	}

	@Override
	public void loadFile(ByeolLog log) {
		ConfigurationSection section = this.getConfig().getConfigurationSection("crates");
		if (section == null)
			return;
		for (int i = 0; i < section.getKeys(false).size(); i++) {
			if (!section.contains("crates-" + i))
				return;
			Crate crate = section.getSerializable("crates-" + i, Crate.class);
		}
	}

	public void saveFile(boolean command) {
		ConfigurationSection section = this.getConfig().createSection("crates");
		Crate[] crates = ByeolCrates.getPlugin(ByeolCrates.class).getHandler().getCrates();
		for (int i = 0; i < crates.length; i++)
			section.set("crates-" + i, crates[i]);
		if (!command)
			Bukkit.getConsoleSender().sendMessage(ByeolCrates.getPlugin(ByeolCrates.class)
					.getMessages()
					.getRaw("backup-success"));
	}
}
