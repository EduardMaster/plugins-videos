package net.eduard.eduardapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.dev.Tag;
import net.eduard.eduardapi.manager.Info;

public class Manager extends Config implements Listener {

	public Manager() {

		add("noCustomQuitMessage", true);
		add("noCustomJoinMessage", true);
		add("noQuitMessage", false);
		add("noJoinMessage", true);
		add("noDeathMessage", false);
		add("autoRespawn", true);
		add("gamemodeTarget", "&aVoce mudou o gamemode do &2$player&a para &2$gamemode");
		add("gamemode", "&6Voce mudou seu Modo de Jogo para $gamemode");
		add("quitMessage", "&6O jogador $player saiu do Servidor");
		add("joinMessage", "&6O jogador $player entrou no Servidor");
		add("Test", false);
		saveConfig();
		API.event(this);

	}

	public static void setSeralizable(Object object, File file) {
		try {
			FileOutputStream saveStream = new FileOutputStream(file);
			ObjectOutputStream save = new ObjectOutputStream(saveStream);
			if (object instanceof Serializable) {
				save.writeObject(object);
				System.out.println("Merda");
			}
			System.out.println("Merda2");
			save.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static Object getSerializable(File file) {
		if (!file.exists()) {
			return null;
		}
		try {

			FileInputStream getStream = new FileInputStream(file);
			ObjectInputStream get = new ObjectInputStream(getStream);
			Object object = get.readObject();
			get.close();
			return object;
		} catch (Exception ex) {
		}

		return null;
	}

	@EventHandler
	private void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!Tag.getTags().containsKey(p)) {
			API.removeTag(p);
		}
		Info.saveObject("Players/" + p.getName() + " " + p.getUniqueId(), p);
	}



	
	
}
