package com.ankoki.bcrates.api.crates.events;

import com.ankoki.bcrates.api.crates.Crate;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class CrateOpenEvent extends CrateEvent {

	public CrateOpenEvent(Player player, Crate crate) {
		super(player, crate);
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
