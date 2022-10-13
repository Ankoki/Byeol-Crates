package com.ankoki.bcrates.internal.files.handlers.validators;

import com.ankoki.bcrates.api.crates.CrateItem;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import org.bukkit.configuration.ConfigurationSection;

public class CrateItemValidator implements Validator<CrateItem> {

	private static final String[] REQUIRED_KEYS = new String[]{"chance"};

	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		boolean hasErrors = new ItemValidator().validate(section, log);
		for (String key : REQUIRED_KEYS) {
			if (!section.contains(key)) {
				log.error("Required key '" + key + "' was not found.");
				hasErrors = true;
			}
		} return !hasErrors;
	}

	@Override
	public CrateItem buildSection(ConfigurationSection section, ByeolLog log) {
		return null;
	}
}
