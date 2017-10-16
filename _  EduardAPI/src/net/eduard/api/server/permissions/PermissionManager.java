package net.eduard.api.server.permissions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.eduard.api.setup.StorageAPI.Storable;

/**
 * Controlador de Permissões e Grupos dos Jogadores
 * @author Eduard-PC
 *
 */
public class PermissionManager implements Storable{
	
	private Map<String,  Group> groups = new HashMap<>();

	private Map<UUID, PlayerManager> players = new HashMap<>();

	@Override
	public Object restore(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Map<String, Object> map, Object object) {
		// TODO Auto-generated method stub
		
	}

	public Map<String,  Group> getGroups() {
		return groups;
	}

	public void setGroups(Map<String,  Group> groups) {
		this.groups = groups;
	}

	public Map<UUID, PlayerManager> getPlayers() {
		return players;
	}

	public void setPlayers(Map<UUID, PlayerManager> players) {
		this.players = players;
	}

}
