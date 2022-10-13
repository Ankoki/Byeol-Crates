package com.ankoki.bcrates.internal.files.handlers;

import com.ankoki.bcrates.ByeolCrates;

import java.util.ArrayList;
import java.util.List;

public class ByeolLog {

	private final ByeolCrates plugin;
	private final List<String> errors = new ArrayList<>(), warnings = new ArrayList<>(), infos = new ArrayList<>();

	/**
	 * Creates a new error logger.
	 * @param plugin the plugin.
	 */
	public ByeolLog(ByeolCrates plugin) {
		this.plugin = plugin;
	}

	/**
	 * Adds an error to the current logger.
	 * @param error the error to add.
	 */
	public void error(String error) {
		this.errors.add(error);
	}

	/**
	 * Adds a warning to the current logger.
	 * @param warning the warning to add.
	 */
	public void warn(String warning) {
		this.warnings.add(warning);
	}

	/**
	 * Adds an informative message to the current logger.
	 * @param info the informative message to add.
	 */
	public void info(String info) {
		this.infos.add(info);
	}

	/**
	 * Checks if the current logger has any errors.
	 * @return true if there are errors.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Prints the current errors if any.
	 */
	public void printLog() {
		for (String error : this.errors) {
			for (String line : error.split("\n"))
				plugin.getLogger().severe(line);
		}
		for (String warning : this.warnings) {
			for (String line : warning.split("\n"))
				plugin.getLogger().warning(line);
		}
		for (String info : this.infos) {
			for (String line : info.split("\n"))
				plugin.getLogger().info(line);
		}
	}
}
