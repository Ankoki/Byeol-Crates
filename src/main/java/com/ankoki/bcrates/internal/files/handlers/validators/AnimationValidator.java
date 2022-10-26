package com.ankoki.bcrates.internal.files.handlers.validators;

import com.ankoki.bcrates.api.animations.CrateAnimation;
import com.ankoki.bcrates.api.animations.CrateAnimationBuilder;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import com.ankoki.bcrates.internal.files.handlers.validators.items.AnimationItemValidator;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class AnimationValidator implements Validator<CrateAnimation> {

	private static final String[] REQUIRED_KEYS = new String[]{"time-between-pages", "gui-rows"};
	private static final AnimationItemValidator ANIMATION_ITEM_VALIDATOR = new AnimationItemValidator();

	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		boolean hasErrors = false;
		for (String key : REQUIRED_KEYS) {
			if (!section.contains(key)) {
				log.error("Required key '" + key + "' was not found. (@" + section.getName() + ":crates.yml)");
				hasErrors = true;
			}
		}
		int rows = section.getInt("gui-rows");
		if (rows < 1 || rows > 6) {
			log.error("You cannot have a gui with '" + rows + "' rows. It must be between 1 and 6. (@" + section.getName() + ":crates.yml)");
			hasErrors = true;
		}
		if (!section.contains("page-1")) {
			log.error("There are no pages in this animation. There has to be atleast 1. (@" + section.getName() + ":crates.yml)");
			hasErrors = true;
		}
		return !hasErrors;
	}

	@Override
	public CrateAnimation buildSection(ConfigurationSection section, ByeolLog log) {
		CrateAnimationBuilder builder = new CrateAnimationBuilder();
		int rows = section.getInt("gui-rows");
		builder.setRowCount(rows);
		builder.setTicksBetween(section.getInt("time-between-keys"));
		for (int i = 1; true; i++) {
			List<String> list = section.getStringList("page-" + i);
			if (list.isEmpty())
				break;
			else if (list.size() != rows * 9) {
				log.error("The amount of rows in an animation page must be the same as the amount of rows in the GUI. " +
						"(@" + section.getName() + ":page-" + i + ":crates.yml)");
				list = new ArrayList<>();
				for (int ii = 0; ii < rows * 9; ii++)
					list.add(".........");
			}
			builder.setShape(i - 1, list.toArray(new String[0]));
		}
		for (int i = 1; true; i++) {
			ConfigurationSection itemSection = section.getConfigurationSection("item-" + i);
			if (itemSection == null)
				break;
			else {
				if (ANIMATION_ITEM_VALIDATOR.validate(itemSection, log))
					builder.setShapeItem(itemSection.getString("character").charAt(0), ANIMATION_ITEM_VALIDATOR.buildSection(itemSection, log));
			}
		}
		return builder.build();
	}
}
