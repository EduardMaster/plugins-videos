package net.eduard.api.time;

import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.PlayerEffect;
import net.eduard.api.util.Save;

public class Delay implements Save {

	private int seconds = 3;

	private String bypass = "teleport.bypass";

	private String message = "Por favor espera $seconds segundos!";


	@Override
	public Object get(ConfigSection section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ConfigSection section, Object value) {
		// TODO Auto-generated method stub

	}
	public void effect(Player p, PlayerEffect effect) {
		if (p.hasPermission(bypass)) {
			
			effect.effect(p);
		} else {
			ConfigSection.chat(p, message.replace("$seconds", ""+seconds));
			API.TIME.delay(20 * seconds, new Runnable() {

				@Override
				public void run() {
					effect.effect(p);
				}
			});
		}
	}

}
