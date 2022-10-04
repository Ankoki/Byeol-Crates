package com.ankoki.bcrates.commands;

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

	private static final Messages MESSAGES = ByeolCrates.getPlugin(ByeolCrates.class).getMessages();

	@CommandHook(name = "bkey")
	private void bkey(CommandSender sender, Player p, CrateType crate, int amount) {
		if (sender.hasPermission("bcrates.command.key")) {
			BPlayer player = BPlayer.get(p);
			ItemStack key = crate.getKey();
			if (amount > key.getMaxStackSize()) {
				String message = MESSAGES.get("too-many-keys")
						.replace("<max-stack-size>", String.valueOf(key.getMaxStackSize()));
				sender.sendMessage(message);
			} if (player.canHold(key)) {
				String message = MESSAGES.get("given-key")
						.replace("<crate-name>", crate.getName());
				sender.sendMessage(message);
				player.giveKey(crate, amount);
			} else {
				sender.sendMessage(MESSAGES.get("no-space"));
			}
		} else sender.sendMessage(MESSAGES.get("no-permission-command-error"));
	}

	@CommandHook(name = "setbcrate")
	private void setbcrate(Player p, CrateType crate) {
		if (p == null) {
			Bukkit.getConsoleSender().sendMessage(MESSAGES.get("must-be-player-error"));
			return;
		} else if (!p.hasPermission("bcrates.command.setcrate")) {
			p.sendMessage(MESSAGES.get("no-permission-command-error"));
		}
		BPlayer player = BPlayer.get(p);
		if (crate == null) {
			player.sendMessage(MESSAGES.get("invalid-crate-id-error"));
			return;
		}
		Block target = p.getTargetBlock(20);
		if (target == null)
			player.sendMessage(MESSAGES.get("set-block-too-far-error"));
		else {
			ByeolCrates.getPlugin(ByeolCrates.class).getHandler()
					.placeCrate(target.getLocation(), crate);
			player.sendMessage(MESSAGES.get("placed-crate")
					.replace("<crate>", crate.getId())
					.replace("<location>", Misc.adapt(target.getLocation())));
		}
	}

	@CommandHook(name = "openbcrate")
	private void openbcrate(CommandSender sender, Player p, CrateType crate) {
		if (!sender.hasPermission("bcrates.command.openbcrate"))
			sender.sendMessage(MESSAGES.get("no-permission-command-error"));
		else if (p == null)
			sender.sendMessage(MESSAGES.get("invalid-player-error"));
		else if (crate == null)
			sender.sendMessage(MESSAGES.get("invalid-crate-id-error"));
		else {
			ByeolCrates.getPlugin(ByeolCrates.class).getHandler()
							.openCrate(crate, p);
			sender.sendMessage(MESSAGES.get("made-player-open")
					.replace("<player>", p.getName())
					.replace("<crate-name>", crate.getName()));
		}
	}
}
