package net.eduard.api.tutorial.internet;

import org.bukkit.entity.Player;

import net.eduard.api.central.player.Particle;

public class ExemploParticleAPI {

	public static void fazerEfeito(Player player) {
		Particle.displayBlockCrack(player.getLocation(), 1, (byte) 0, 0, 0, 0, 10);
	}
	public static void fazerEfeito2(Player player) {
		Particle.displayBlockCrack(player.getLocation(), 2, (byte) 0, 2, 3, 3, 10);
	}
	public static void fazerEfeito3(Player player) {
		Particle.ANGRY_VILLAGER.display(player.getLocation(), 1, 1, 1, 0.5F, 15);
	}
}
