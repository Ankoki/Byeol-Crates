package com.ankoki.bcrates.api.events;

import com.ankoki.bcrates.api.crates.Crate;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class CrateEvent extends PlayerEvent implements Cancellable {
	protected static final HandlerList HANDLER_LIST = new HandlerList();

	private final Crate crate;
	private boolean cancelled;

	public CrateEvent(@NotNull Player who, Crate crate) {
		super(who);
		this.crate = crate;
	}

	public Crate getCrate() {
		return crate;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLER_LIST;
	}
}
