package net.eduard.login.manager;

import java.util.ArrayList;

import net.eduard.api.lib.modules.FakePlayer;
import net.eduard.api.lib.storage.Storable;

public class PlayerAccountManager implements Storable{

	private ArrayList<PlayerAccount> accounts = new ArrayList<>();
	
	public PlayerAccount getAccount(FakePlayer player	) {
		for (PlayerAccount account : accounts) {
			if (account.getPlayer().equals(player)) {
				return account;
			}
		}
		return null;
	}

	public ArrayList<PlayerAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<PlayerAccount> accounts) {
		this.accounts = accounts;
	}
}
