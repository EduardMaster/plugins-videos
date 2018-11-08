package net.eduard.login;

import java.util.ArrayList;

import net.eduard.api.lib.storage.Storable;

public class LoginSystem implements Storable {

	private static LoginSystem system;

	private ArrayList<PlayerAccount> accounts = new ArrayList<>();

	public static LoginSystem getSystem() {
		return system;
	}

	public static void setSystem(LoginSystem system) {
		LoginSystem.system = system;
	}

	public ArrayList<PlayerAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<PlayerAccount> accounts) {
		this.accounts = accounts;
	}

}
