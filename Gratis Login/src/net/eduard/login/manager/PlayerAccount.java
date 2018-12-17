package net.eduard.login.manager;

import java.sql.Timestamp;

import net.eduard.api.lib.modules.FakePlayer;
import net.eduard.api.lib.storage.Storable;

public class PlayerAccount implements Storable {

	private FakePlayer player;

	private String password;

	private Timestamp registerTime;

	private Timestamp lastLogin;

	public String getPassword() {
		return password;
	}

	public boolean isRegistred() {
		return registerTime != null;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public FakePlayer getPlayer() {
		return player;
	}

	public void setPlayer(FakePlayer player) {
		this.player = player;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

}
