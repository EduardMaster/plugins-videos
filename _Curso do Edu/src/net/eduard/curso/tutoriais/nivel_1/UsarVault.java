package net.eduard.curso.tutoriais.nivel_1;

import net.eduard.api.lib.VaultAPI;

public class UsarVault {
	public UsarVault() {
		 
		// pegar valor da config
		// tem bugs
		VaultAPI.getChat().getGroupInfoDouble("null", "Membro", "price", 20);
	}
}
