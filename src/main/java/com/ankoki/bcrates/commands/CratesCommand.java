package com.ankoki.bcrates.commands;

import com.ankoki.bcrates.api.crates.Crate;
import com.ankoki.byeol.commands.CommandHook;
import com.ankoki.bcrates.ByeolCrates;
import com.ankoki.bcrates.api.crates.CrateType;
import com.ankoki.bcrates.internal.files.Messages;
import com.ankoki.bcrates.misc.BPlayer;
import com.ankoki.bcrates.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CratesCommand {

	@CommandHook(name = "bkey")
	private void bkey(CommandSender sender, Player p, CrateType crate, int amount) {
		final Messages messages = ByeolCrates.getPlugin(ByeolCrates.class).getMessages();
		if (sender.hasPermission("bcrates.command.key")) {
			BPlayer player = BPlayer.get(p);
			ItemStack key = crate.getKey();
			if (amount > key.getMaxStackSize()) {
				String message = messages.get("too-many-keys")
						.replace("<max-stack-size>", String.valueOf(key.getMaxStackSize()));
				sender.sendMessage(message);
			} if (player.canHold(key)) {
				String message = messages.get("given-key")
						.replace("<crate-name>", crate.getName());
				sender.sendMessage(message);
				player.giveKey(crate, amount);
			} else {
				sender.sendMessage(messages.get("no-space"));
			}
		} else sender.sendMessage(messages.get("no-permission-command-error"));
	}

	@CommandHook(name = "setbcrate")
	private void setbcrate(Player p, CrateType crate) {
		final Messages messages = ByeolCrates.getPlugin(ByeolCrates.class).getMessages();
		if (p == null) {
			Bukkit.getConsoleSender().sendMessage(messages.get("must-be-player-error"));
			return;
		} else if (!p.hasPermission("bcrates.command.setcrate")) {
			p.sendMessage(messages.get("no-permission-command-error"));
		}
		BPlayer player = BPlayer.get(p);
		if (crate == null) {
			player.sendMessage(messages.get("invalid-crate-id-error"));
			return;
		}
		Block target = p.getTargetBlock(20);
		if (target == null)
			player.sendMessage(messages.get("set-block-too-far-error"));
		else {
			ByeolCrates.getPlugin(ByeolCrates.class).getHandler()
					.placeCrate(new Crate(crate, target.getLocation()));
			player.sendMessage(messages.get("placed-crate")
					.replace("<crate>", crate.getId())
					.replace("<location>", Misc.adapt(target.getLocation())));
		}
	}

	@CommandHook(name = "openbcrate")
	private void openbcrate(CommandSender sender, Player p, CrateType crate) {
		final Messages messages = ByeolCrates.getPlugin(ByeolCrates.class).getMessages();
		if (!sender.hasPermission("bcrates.command.openbcrate"))
			sender.sendMessage(messages.get("no-permission-command-error"));
		else if (p == null)
			sender.sendMessage(messages.get("invalid-player-error"));
		else if (crate == null)
			sender.sendMessage(messages.get("invalid-crate-id-error"));
		else {
			ByeolCrates.getPlugin(ByeolCrates.class).getHandler()
							.openCrate(crate, p);
			sender.sendMessage(messages.get("made-player-open")
					.replace("<player>", p.getName())
					.replace("<crate-name>", crate.getName()));
		}
	}

	@CommandHook(name = "breload")
	private void breload(CommandSender sender, String file) {
		if (sender.hasPermission("bcrates.command.breload")) {
			if (file != null &&
					(file.equalsIgnoreCase("config") || file.equalsIgnoreCase("messages"))) {
				ByeolCrates.getPlugin(ByeolCrates.class).reload(file);
				sender.sendMessage(ByeolCrates.getPlugin(ByeolCrates.class)
						.getMessages()
						.get("reload-command-success")
						.replace("<file>", file));
			} else if (file != null) {
				sender.sendMessage(ByeolCrates.getPlugin(ByeolCrates.class)
						.getMessages()
						.get("reload-command-no-file-found")
						.replace("<file>", file));
			} else {
				ByeolCrates.getPlugin(ByeolCrates.class).reload(null);
				sender.sendMessage(ByeolCrates.getPlugin(ByeolCrates.class)
						.getMessages()
						.get("reload-command-success")
						.replace("<file>", "all files"));
			}
		} else
			sender.sendMessage(ByeolCrates.getPlugin(ByeolCrates.class)
					.getMessages()
					.get("no-permission-command-error"));
	}

	@CommandHook(name = "bbackup")
	private void bbackup(CommandSender sender) {
		if (!sender.hasPermission("bcrates.command.bbackup"))
			sender.sendMessage(ByeolCrates.getPlugin(ByeolCrates.class)
					.getMessages()
					.get("no-permission-command-error"));
		else {
			ByeolCrates.getPlugin(ByeolCrates.class).getStorage().saveFile(true);
			sender.sendMessage(ByeolCrates.getPlugin(ByeolCrates.class)
					.getMessages()
					.get("backup-success"));
		}
	}
}
