package net.eduard.api.config.serial;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerSerial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private UUID id;
	public PlayerSerial(OfflinePlayer player) {
		setPlayer(player);
	}
	
	public PlayerSerial(String name) {
		super();
		this.name = name;
	}
	

	public PlayerSerial(UUID id) {
		super();
		this.id = id;
	}
	

	@Override
	public String toString() {
		return "PlayerSerial [name=" + name + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerSerial other = (PlayerSerial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public PlayerSerial(String name, UUID id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public void setPlayer(OfflinePlayer player) {
		setName(player.getName());
		setId(player.getUniqueId());
	}
	public Player getPlayerByName() {
		return Bukkit.getPlayer(name);
	}
	public Player getPlayerById() {
		return Bukkit.getPlayer(id);
	}

}
