package com.ankoki.bcrates.internal.files;

import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;

public class Crates extends FileConfig {

	public Crates(ByeolCrates plugin) {
		super(plugin, "crates.yml", false);
	}

	@Override
	public void loadFile(ByeolLog log) {
		// TODO parse all animations and crate types.
	}
}
