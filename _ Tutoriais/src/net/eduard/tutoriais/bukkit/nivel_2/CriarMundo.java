package net.eduard.tutoriais.bukkit.nivel_2;

import org.bukkit.WorldCreator;

public class CriarMundo {
	public CriarMundo(String nome) {
		WorldCreator m = new WorldCreator(nome);
		m.createWorld();
	}
}
