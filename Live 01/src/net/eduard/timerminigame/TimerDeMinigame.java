package net.eduard.timerminigame;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerDeMinigame extends BukkitRunnable {

	public static int tempo = 60;

	public void run() {

		tempo = tempo - 1;

		if (tempo == 0) {

			Bukkit.broadcastMessage("§a O HG começou!");
			cancel();
			return;

		}
		
		
		if (tempo % 30 == 0) {
			Bukkit.broadcastMessage("A contagem vai terminar em " + tempo + " segundos!");
		} else if (tempo % 15 == 0) {
			Bukkit.broadcastMessage("A contagem vai terminar em " + tempo + " segundos!");
		} else if (tempo % 10 == 0) {
			Bukkit.broadcastMessage("A contagem vai terminar em " + tempo + " segundos!");
		} else  if (tempo < 10) {
			Bukkit.broadcastMessage("A contagem vai terminar em " + tempo + " segundos!");
		}

	}

}
