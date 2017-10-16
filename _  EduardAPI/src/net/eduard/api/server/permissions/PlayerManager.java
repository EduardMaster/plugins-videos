package net.eduard.api.server.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.eduard.api.setup.StorageAPI.Reference;

public class PlayerManager {
	
	private UUID playerId;

	private List<String> permissions = new ArrayList<>();
	@Reference
	private List<Group> groups = new ArrayList<>();
	
	
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public List<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	public UUID getPlayerId() {
		return playerId;
	}
	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}
	
}
