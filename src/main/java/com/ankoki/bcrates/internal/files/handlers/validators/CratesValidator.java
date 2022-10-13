package com.ankoki.bcrates.internal.files.handlers.validators;

import com.ankoki.bcrates.api.crates.CrateItem;
import com.ankoki.bcrates.api.crates.CrateType;
import com.ankoki.bcrates.api.crates.LootTable;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import org.bukkit.configuration.ConfigurationSection;

public class CratesValidator implements Validator<CrateType> {

	private static final String[] REQUIRED_SHALLOW_KEYS = new String[]{"name", "loot-table", "animation", "block-type"};
	private static final String[] REQUIRED_LOOT_TABLE_KEYS = new String[]{"minimum-reward-amount", "maximum-reward-amount"};
	private static final String[] REQUIRED_LOOT_ITEM_KEYS = new String[]{"chance", "material"};
	// private static final String[] REQUIRED_ANIMATION_KEYS = new String[]{"time-between-pages", "gui-rows"};

	private static final String[] OPTIONAL_LOOT_TABLE_KEYS = new String[]{"multiple-of-same"};
	private static final String[] OPTIONAL_LOOT_ITEM_KEYS = new String[]{"chance", "name", "lore", "nbt", "enchantments", "shiny", "blank", "amount"};
	// private static final String[] OPTIONAL_ANIMATION_ITEM_KEYS = new String[]{"result-item"};


	@Override
	public boolean validate(ConfigurationSection section, ByeolLog log) {
		boolean hasErrors = false;
		for (String key : REQUIRED_SHALLOW_KEYS) {
			if (section.get(key) == null) {
				log.error("Required key '" + key + "' was not found. (crates.yml)");
				hasErrors = true;
			}
		}
		for (String key : REQUIRED_LOOT_TABLE_KEYS) {
			final Object lootTable = section.get("loot-table");
			if (lootTable == null) break;
			else if (!(lootTable instanceof ConfigurationSection tableSection)) {
				log.error("Required key 'loot-table' is not a section. (crates.yml)");
				hasErrors = true;
			} else if (tableSection.get(key) == null) {
				log.error("Required key '" + key + "' was not found. (crates.yml)");
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
						hasErrors = !new CrateItemValidator()
								.validate(itemSection, log);
					} else {
						log.error("Key 'item-" + i + "' is not a section. (crates.yml)");
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
		CrateTypeBuilder builder = new CrateTypeBuilder();
		return null;
	}

	private static class CrateTypeBuilder {
		private LootTable table = new LootTable();

		public void addTableItem(CrateItem item) {
			this.table.addItems(item);
		}

		public CrateType build() {
			return null;
		}
	}
}
