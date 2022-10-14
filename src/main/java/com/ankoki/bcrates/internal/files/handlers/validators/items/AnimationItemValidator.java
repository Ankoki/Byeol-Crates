package com.ankoki.bcrates.internal.files.handlers.validators.items;

import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import com.ankoki.bcrates.internal.files.handlers.validators.Validator;
import com.ankoki.bcrates.misc.ItemUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class AnimationItemValidator implements Validator<ItemStack> {

	private static final ItemValidator ITEM_VALIDATOR = new ItemValidator();

	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		return ITEM_VALIDATOR.validate(section, log);
	}

	@Override
	public ItemStack buildSection(ConfigurationSection section, ByeolLog log) {
		ItemStack item = ITEM_VALIDATOR.buildSection(section, log);
		if (section.getBoolean("result-item"))
			item = ItemUtils.getResultItem(item);
		return item;
	}
}
