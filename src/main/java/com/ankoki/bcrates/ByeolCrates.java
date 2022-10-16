package com.ankoki.bcrates;

import com.ankoki.bcrates.internal.files.Crates;
import com.ankoki.bcrates.internal.files.handlers.ByeolLog;
import com.ankoki.bcrates.misc.Misc;
import com.ankoki.byeol.Byeol;
import com.ankoki.byeol.commands.CommandHandler;
import com.ankoki.bcrates.api.crates.CrateItem;
import com.ankoki.bcrates.api.crates.CrateType;
import com.ankoki.bcrates.api.crates.LootTable;
import com.ankoki.bcrates.api.animations.Animations;
import com.ankoki.bcrates.commands.CratesCommand;
import com.ankoki.bcrates.commands.conveters.CrateTypeConverter;
import com.ankoki.bcrates.internal.CrateHandler;
import com.ankoki.bcrates.internal.files.Config;
import com.ankoki.bcrates.internal.files.CrateStorage;
import com.ankoki.bcrates.internal.files.Messages;
import com.ankoki.bcrates.misc.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ByeolCrates extends JavaPlugin implements Listener {

	private CrateHandler handler;
	private Messages messages;
	private Config config;
	private CrateStorage storage;
	private Crates crates;
	private ByeolLog log;

	@Override
	public void onEnable() {
		Byeol.get().setup(this);
		CommandHandler.get().registerCommand(CratesCommand.class);
		CommandHandler.get().registerConverters(new CrateTypeConverter());
		this.handler = new CrateHandler();
		this.messages = new Messages(this);
		this.config = new Config(this);
		this.storage = new CrateStorage(this);
		this.crates = new Crates(this);
		this.getServer().getPluginManager().registerEvents(handler, this);
		this.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> this.getStorage().saveFile(false),
				36000L, (long) this.getConfiguration().BACKUP_INTERVAL.getTicks());
		if (this.config.DEV_MODE) {
			LootTable table = new LootTable(
					new CrateItem(ItemUtils.getItem(Material.SUNFLOWER, "Test 1"), 95),
					new CrateItem(ItemUtils.getItem(Material.PLAYER_HEAD, "Slay"), 45),
					new CrateItem(ItemUtils.getItem(Material.BEEF, "BEEEEF"), 25),
					new CrateItem(ItemUtils.getItem(Material.DEAD_BRAIN_CORAL, "sea"), 54),
					new CrateItem(ItemUtils.getItem(Material.GILDED_BLACKSTONE, "blackstone"), 12));
			CrateType type = new CrateType("Test", "test", ItemUtils.getKey("test", Material.TRIPWIRE_HOOK, "Test Crate Key"), table, 1, 3,
					Animations.SWIPE, Material.LIME_STAINED_GLASS);
			// Crate crate = new Crate(type, new Location(Bukkit.getWorld("world"), 100, 100, 100));
		}
	}

	@Override
	public void onDisable() {
		this.getStorage().saveFile(false);
	}

	public CrateHandler getHandler() {
		return handler;
	}

	public Messages getMessages() {
		return messages;
	}

	public Config getConfiguration() {
		return config;
	}

	public CrateStorage getStorage() {
		return storage;
	}

	public void setErrorLog(ByeolLog log) {
		this.log = log;
	}

	/**
	 * Reloads the given file name (messages or config). If file name is null or empty, both files will be reloaded.
	 * @param string the file to reload, or null/empty.
	 */
	public void reload(String string) {
		if (Misc.isNullOrEmpty(string)) {
			this.messages.reload();
			this.config.reload();
		}
		else if (string.equalsIgnoreCase("messages"))
			this.messages.reload();
		else if (string.equalsIgnoreCase("config"))
			this.config.reload();
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (player.hasPermission("bcrates.errors") && log.hasOutput())
			player.sendMessage(messages.get("on-join-error-message"));
	}
}
