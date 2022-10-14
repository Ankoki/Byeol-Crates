package com.ankoki.bcrates.internal.files;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import com.ankoki.bcrates.internal.files.handlers.validators.AnimationValidator;
import com.ankoki.bcrates.internal.files.handlers.validators.CratesValidator;
import org.bukkit.configuration.ConfigurationSection;

public class Crates extends FileConfig {
	private static final CratesValidator CRATES_VALIDATOR = new CratesValidator();
	private static final AnimationValidator ANIMATION_VALIDATOR = new AnimationValidator();

	public Crates(ByeolCrates plugin) {
		super(plugin, "crates.yml", false);
	}

	@Override
	public void loadFile(ByeolLog log) {
		ConfigurationSection animationSection = this.getConfig().getConfigurationSection("animations");
		if (animationSection != null && ANIMATION_VALIDATOR.validate(animationSection, log))
			ANIMATION_VALIDATOR.buildSection(animationSection, log);
		ConfigurationSection crateSection = this.getConfig().getConfigurationSection("crates");
		if (crateSection == null)
			log.error("There was no crates section to validate. No crates are being created.");
		else {
			for (String name : crateSection.getKeys(false)) {
				ConfigurationSection sub = crateSection.getConfigurationSection(name);
				if (CRATES_VALIDATOR.validate(sub, log))
					CRATES_VALIDATOR.buildSection(crateSection, log);
			}
		}
	}
}
