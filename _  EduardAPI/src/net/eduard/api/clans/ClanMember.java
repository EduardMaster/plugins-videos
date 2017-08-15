package net.eduard.api.clans;

import java.io.Serializable;

import org.bukkit.OfflinePlayer;

import net.eduard.api.config.serial.PlayerSerial;

public class ClanMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlayerSerial player;
	private Clan clan;

	public ClanMember(OfflinePlayer player) {
		setPlayer(new PlayerSerial(player));
	}

	public PlayerSerial getPlayer() {
		return player;
	}

	public void setPlayer(PlayerSerial player) {
		this.player = player;
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}
}
