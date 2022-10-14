package com.ankoki.bcrates.internal.files.handlers.validators;

import com.ankoki.bcrates.api.animations.Animations;
import com.ankoki.bcrates.api.animations.CrateAnimation;
import com.ankoki.bcrates.api.crates.CrateItem;
import com.ankoki.bcrates.api.crates.CrateType;
import com.ankoki.bcrates.api.crates.LootTable;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import com.ankoki.bcrates.internal.files.handlers.validators.items.CrateItemValidator;
import com.ankoki.bcrates.internal.files.handlers.validators.items.ItemValidator;
import com.ankoki.bcrates.misc.Misc;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class CratesValidator implements Validator<CrateType> {

	private static final String[] REQUIRED_SHALLOW_KEYS = new String[]{"name", "key", "loot-table", "animation", "block-type"};
	private static final String[] REQUIRED_LOOT_TABLE_KEYS = new String[]{"minimum-reward-amount", "maximum-reward-amount"};

	private static final CrateItemValidator CRATE_ITEM_VALIDATOR = new CrateItemValidator();
	private static final ItemValidator ITEM_VALIDATOR = new ItemValidator();


	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		boolean hasErrors = false;
		for (String key : REQUIRED_SHALLOW_KEYS) {
			if (section.get(key) == null) {
				log.error("Required key '" + key + "' was not found. (@" + section.getName() + ":crates.yml)");
				hasErrors = true;
			}
		}
		for (String key : REQUIRED_LOOT_TABLE_KEYS) {
			final Object lootTable = section.get("loot-table");
			if (lootTable == null) break;
			else if (!(lootTable instanceof ConfigurationSection tableSection)) {
				log.error("Required key 'loot-table' is not a section. (@" + section.getName() + ":crates.yml)");
				hasErrors = true;
			} else if (tableSection.get(key) == null) {
				log.error("Required key '" + key + "' was not found. (@" + section.getName() + ":crates.yml)");
				hasErrors = true;
			}
		}
		final Object lootTable = section.get("loot-table");
		if (lootTable instanceof ConfigurationSection tableSection) {
			int i = 1;
			while (true) {
				final Object item = tableSection.get("item-" + i);
				if (item != null) {
					if (item instanceof ConfigurationSection itemSection) {
						hasErrors = !CRATE_ITEM_VALIDATOR
								.validate(itemSection, log);
					} else {
						log.error("Key 'item-" + i + "' is not a section. (@" + section.getName() + ":crates.yml)");
						hasErrors = true;
					}
					i++;
				} else break;
			}
		}
		return !hasErrors;
	}

	@Override
	public CrateType buildSection(ConfigurationSection section, ByeolLog log) {
		boolean hasErrors = false;
		CrateTypeBuilder builder = new CrateTypeBuilder();
		builder.name = section.getString("name");
		builder.id = section.getName();
		ConfigurationSection keySection = section.getConfigurationSection("key");
		if (keySection != null && ITEM_VALIDATOR.validate(keySection, log))
			builder.key = ITEM_VALIDATOR.buildSection(keySection, log);
		else
			hasErrors = true;
		ConfigurationSection table = section.getConfigurationSection("loot-table");
		for (int i = 0; true; i++) {
			ConfigurationSection itemSection = table.getConfigurationSection("item-" + i);
			if (itemSection == null) {
				if (i == 0) {
					log.error("There are no item's in this crates loot-table. (@" + section.getName() + ":crates.yml)");
					hasErrors = true;
				} else
					break;
			} else {
				if (CRATE_ITEM_VALIDATOR.validate(itemSection, log))
					builder.table.addItems(CRATE_ITEM_VALIDATOR.buildSection(itemSection, log));
			}
		}
		builder.table.setMultipleSame(table.getBoolean("multiple-of-same", true));
		builder.minimumRewards = table.getInt("minimum-reward-amount");
		builder.maximumRewards = table.getInt("maximum-reward-amount");
		String animation = section.getString("animation");
		if (Animations.hasAnimation(animation))
			builder.animation = Animations.getAnimation(animation);
		else {
			log.error("Animation '" + animation + "' was not found. (@" + section.getName() + ":crates.yml)");
			hasErrors = true;
		}
		String type = section.getString("block-type");
		Material material = Misc.adaptMaterial(type);
		if (material != null) {
			if (material.isBlock())
				builder.block = material;
			else {
				log.error("Block type '" + type + "' is not a block. (@" + section.getName() + ":crates.yml)");
				hasErrors = true;
			}
		} else {
			log.error("Block type '" + type + "' is not a valid block. (@" + section.getName() + ":crates.yml)");
			hasErrors = true;
		}

		return hasErrors ? null : builder.build();
	}

	private static class CrateTypeBuilder {
		private String name;
		private String id;
		private ItemStack key;
		private final LootTable table = new LootTable();
		private int minimumRewards, maximumRewards;
		private CrateAnimation animation;
		private Material block;

		public void addTableItem(CrateItem item) {
			this.table.addItems(item);
		}

		public CrateType build() {
			return new CrateType(name, id, key, table, minimumRewards, maximumRewards, animation, block);
		}
	}
}
