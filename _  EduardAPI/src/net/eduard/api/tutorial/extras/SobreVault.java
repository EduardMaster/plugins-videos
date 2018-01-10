package net.eduard.api.tutorial.extras;

import net.eduard.api.setup.VaultAPI;

public class SobreVault {
	public SobreVault() {
		
		// pegar valor da config
		// tem bugs
		VaultAPI.getChat().getGroupInfoDouble("null", "Membro", "price", 20);
	}
}
