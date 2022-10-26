package com.ankoki.bcrates.internal.files.handlers.validators.items;

import com.ankoki.bcrates.api.crates.CrateItem;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import com.ankoki.bcrates.internal.files.handlers.validators.Validator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class CrateItemValidator implements Validator<CrateItem> {

	private static final String[] REQUIRED_KEYS = new String[]{"chance"};
	private static final ItemValidator ITEM_VALIDATOR = new ItemValidator();

	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		boolean hasErrors = !ITEM_VALIDATOR.validate(section, log);
		for (String key : REQUIRED_KEYS) {
			if (!section.contains(key)) {
				log.error("Required key '" + key + "' was not found. (@" + section.getName() + ":crates.yml)");
				hasErrors = true;
			}
		} return !hasErrors;
	}

	@Override
	public CrateItem buildSection(ConfigurationSection section, ByeolLog log) {
		ItemStack item = ITEM_VALIDATOR.buildSection(section, log);
		int chance = Math.min(100, section.getInt("chance"));
		return new CrateItem(item, chance);
	}
}
