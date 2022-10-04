package com.ankoki.bcrates.internal.files;

import com.ankoki.bcrates.ByeolCrates;

public class Config extends FileConfig {

	public boolean LEFT_CLICK_VIEW;
	public String CRATE_INVENTORY_NAME;

	public Config(ByeolCrates plugin) {
		super(plugin, "config.yml", true);
	}

	@Override
	public void loadFile() {
		LEFT_CLICK_VIEW = this.getConfig().getBoolean("view-on-left-click");
		CRATE_INVENTORY_NAME = this.getConfig().getString("crate-inventory-name");
	}
}
