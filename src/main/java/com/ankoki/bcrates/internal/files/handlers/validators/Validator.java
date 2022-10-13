package com.ankoki.bcrates.internal.files.handlers.validators;

import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import org.bukkit.configuration.ConfigurationSection;

public interface Validator<T> {

	/**
	 * Validates a section.
	 * @param section the section to validate.
	 * @param log the log to write errors to if any.
	 * @return true if correct.
	 */
	boolean validate(ConfigurationSection section, ByeolLog log);

	/**
	 * Builds the validated section into T.
	 * Must have {@link Validator#validate(ConfigurationSection, ByeolLog)} called first, and it must return true, else errors are thrown.
	 * @param section the same section that was validated.
	 * @param log the log to write errors to if any.
	 * @return the built section.
	 */
	T buildSection(ConfigurationSection section, ByeolLog log);
}
