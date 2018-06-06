package net.eduard.tutoriais.lib.nivel_1;

import net.eduard.api.lib.VaultAPI;

public class SobreVault {
	public SobreVault() {
		 
		// pegar valor da config
		// tem bugs
		VaultAPI.getChat().getGroupInfoDouble("null", "Membro", "price", 20);
	}
}
