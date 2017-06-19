package net.eduard.api.net.nametagedit;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NameTagChangeEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private String oldPlayer;
	private String oldPrefix;
	private String oldSuffix;
	private String newPrefix;
	private String newSuffix;
	private NametagChangeType changeType;
	private NametagChangeReason changeReason;
	private boolean cancelled = false;
	
	

	public NameTagChangeEvent(String oldPlayer, String oldPrefix,
			String oldSuffix, String newPrefix, String newSuffix,
			NametagChangeType changeType, NametagChangeReason changeReason) {
		super();
		this.oldPlayer = oldPlayer;
		this.oldPrefix = oldPrefix;
		this.oldSuffix = oldSuffix;
		this.newPrefix = newPrefix;
		this.newSuffix = newSuffix;
		this.changeType = changeType;
		this.changeReason = changeReason;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public String getOldPlayer() {
		return oldPlayer;
	}

	public void setOldPlayer(String oldPlayer) {
		this.oldPlayer = oldPlayer;
	}

	public String getOldPrefix() {
		return oldPrefix;
	}

	public void setOldPrefix(String oldPrefix) {
		this.oldPrefix = oldPrefix;
	}

	public String getOldSuffix() {
		return oldSuffix;
	}

	public void setOldSuffix(String oldSuffix) {
		this.oldSuffix = oldSuffix;
	}

	public String getNewPrefix() {
		return newPrefix;
	}

	public void setNewPrefix(String newPrefix) {
		this.newPrefix = newPrefix;
	}

	public String getNewSuffix() {
		return newSuffix;
	}

	public void setNewSuffix(String newSuffix) {
		this.newSuffix = newSuffix;
	}

	public NametagChangeType getChangeType() {
		return changeType;
	}

	public void setChangeType(NametagChangeType changeType) {
		this.changeType = changeType;
	}

	public NametagChangeReason getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(NametagChangeReason changeReason) {
		this.changeReason = changeReason;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
		
	}
}