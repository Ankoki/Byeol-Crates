package com.ankoki.bcrates.api.crates.events;

import com.ankoki.bcrates.api.crates.Crate;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class CrateViewEvent extends CrateEvent {

	public CrateViewEvent(Player who, Crate crate) {
		super(who, crate);
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
