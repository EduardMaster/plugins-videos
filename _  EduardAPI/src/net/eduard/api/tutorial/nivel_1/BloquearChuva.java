package net.eduard.api.tutorial.nivel_1;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class BloquearChuva implements Listener{

	@EventHandler
	public void RemoveChuva(WeatherChangeEvent e) {

		if (e.toWeatherState()) {
			e.setCancelled(true);
		}
	}
}
