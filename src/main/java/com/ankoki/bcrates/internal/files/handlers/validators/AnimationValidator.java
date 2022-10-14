package com.ankoki.bcrates.internal.files.handlers.validators;

import com.ankoki.bcrates.api.animations.CrateAnimation;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import org.bukkit.configuration.ConfigurationSection;

public class AnimationValidator implements Validator<CrateAnimation> {

	private static final String[] REQUIRED_KEYS = new String[]{"time-between-pages", "gui-rows"};

	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		return false;
	}

	@Override
	public CrateAnimation buildSection(ConfigurationSection section, ByeolLog log) {
		return null;
	}
}
