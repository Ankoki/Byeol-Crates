package com.ankoki.bcrates.api.events;

import com.ankoki.bcrates.api.crates.Crate;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class CratePlaceEvent extends CrateEvent {

	public CratePlaceEvent(Player player, Crate crate) {
		super(player, crate);
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
