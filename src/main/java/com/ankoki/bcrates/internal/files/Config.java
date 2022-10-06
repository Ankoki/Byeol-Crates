package com.ankoki.bcrates.internal.files;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.internal.files.parsers.ErrorLog;
import com.ankoki.bcrates.misc.Timespan;

public class Config extends FileConfig {

	public boolean LEFT_CLICK_VIEW, DEV_MODE;
	public Timespan BACKUP_INTERVAL;
	public String CRATE_INVENTORY_NAME;

	public Config(ByeolCrates plugin) {
		super(plugin, "config.yml", true);
	}

	@Override
	public void loadFile(ErrorLog log) {
		LEFT_CLICK_VIEW = this.getConfig().getBoolean("view-on-left-click");
		BACKUP_INTERVAL = Timespan.of(this.getConfig().getString("backup-interval"));
		if (BACKUP_INTERVAL == null)
			log.log("'backup-interval' value '" +
					this.getConfig().getString("backup-interval") +
					"' is not a valid timespan (config.yml, line " + "TODO, LINE NUMBER)");
		CRATE_INVENTORY_NAME = this.getConfig().getString("crate-inventory-name");
		if (this.getConfig().contains("dev-mode"))
			DEV_MODE = this.getConfig().getBoolean("dev-mode");
	}
}
