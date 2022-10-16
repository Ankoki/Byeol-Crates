package com.ankoki.bcrates.internal.files.handlers.validators;

import com.ankoki.bcrates.api.animations.CrateAnimation;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import org.bukkit.configuration.ConfigurationSection;

public class AnimationValidator implements Validator<CrateAnimation> {

	private static final String[] REQUIRED_KEYS = new String[]{"time-between-pages", "gui-rows"};

	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		boolean hasErrors = false;
		for (String key : REQUIRED_KEYS) {
			if (!section.contains(key)) {
				log.error("Required key '" + key + "' was not found. (@" + section.getName() + ":crates.yml)");
				hasErrors = true;
			}
		}
		if (!section.contains("page-1")) {
			log.error("There are no pages in this animation. There has to be atleast 1. (@" + section.getName() + ":crates.yml)");
			hasErrors = true;
		}
		return !hasErrors;
	}

	@Override
	public CrateAnimation buildSection(ConfigurationSection section, ByeolLog log) {
		return null;
	}
}
