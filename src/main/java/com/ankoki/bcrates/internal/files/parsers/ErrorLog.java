package com.ankoki.bcrates.internal.files.parsers;

import com.ankoki.bcrates.ByeolCrates;

import java.util.ArrayList;
import java.util.List;

public class ErrorLog {

	private final ByeolCrates plugin;
	private final List<String> errors = new ArrayList<>();

	/**
	 * Creates a new error logger.
	 * @param plugin the plugin.
	 */
	public ErrorLog(ByeolCrates plugin) {
		this.plugin = plugin;
	}

	/**
	 * Adds an error to the current logger.
	 * @param error the error to add.
	 */
	public void log(String error) {
		this.errors.add(error);
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
		for (String error : this.errors)
			plugin.getLogger().severe(error);
	}
}
