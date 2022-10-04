package com.ankoki.bcrates;

import com.ankoki.byeol.Byeol;
import com.ankoki.byeol.commands.CommandHandler;
import com.ankoki.bcrates.api.crates.CrateItem;
import com.ankoki.bcrates.api.crates.CrateType;
import com.ankoki.bcrates.api.crates.LootTable;
import com.ankoki.bcrates.api.crates.animations.DefaultAnimations;
import com.ankoki.bcrates.commands.CratesCommand;
import com.ankoki.bcrates.commands.conveters.CrateTypeConverter;
import com.ankoki.bcrates.internal.CrateHandler;
import com.ankoki.bcrates.internal.files.Config;
import com.ankoki.bcrates.internal.files.CrateStorage;
import com.ankoki.bcrates.internal.files.Messages;
import com.ankoki.bcrates.misc.ItemUtils;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class ByeolCrates extends JavaPlugin {

	private CrateHandler handler;
	private Messages messages;
	private Config config;
	private CrateStorage storage;

	private boolean test = true;

	@Override
	public void onEnable() {
		Byeol.get().setup(this);
		CommandHandler.get().registerCommand(CratesCommand.class);
		CommandHandler.get().registerConverters(new CrateTypeConverter());
		this.handler = new CrateHandler();
		this.messages = new Messages(this);
		this.config = new Config(this);
		this.storage = new CrateStorage(this);
		this.getServer().getPluginManager().registerEvents(handler, this);
		if (test) {
			LootTable table = new LootTable(
					new CrateItem(ItemUtils.getItem(Material.SUNFLOWER, "Test 1"), 95),
					new CrateItem(ItemUtils.getItem(Material.PLAYER_HEAD, "Slay"), 45),
					new CrateItem(ItemUtils.getItem(Material.BEEF, "BEEEEF"), 25),
					new CrateItem(ItemUtils.getItem(Material.DEAD_BRAIN_CORAL, "sea"), 54),
					new CrateItem(ItemUtils.getItem(Material.GILDED_BLACKSTONE, "blackstone"), 12));
			CrateType type = new CrateType("Test", "test", table, 3, 1, 3,
					DefaultAnimations.SWIPE, Material.LIME_STAINED_GLASS, ItemUtils.getKey("test", Material.TRIPWIRE_HOOK, "Test Key"));
			// Crate crate = new Crate(type, new Location(Bukkit.getWorld("world"), 100, 100, 100));
		}
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
}
