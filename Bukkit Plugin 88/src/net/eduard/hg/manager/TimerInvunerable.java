
package net.eduard.hg.manager;

import net.eduard.hg.HGState;
import net.eduard.hg.Main;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerInvunerable {

	public static int time;

	public TimerInvunerable() {
		Main.state = HGState.INVUNERABLE;
		time = 10;
		Bukkit.broadcastMessage("§aInvunerabilidade Ativada!");
		new BukkitRunnable() {

			public void run() {

				time--;
				boolean message = false;
				if (time == 0) {
					cancel();
					Bukkit.broadcastMessage("§cInvunerabilidade acabou!");
					Main.state = HGState.GAME;
				} else if (time < 10) {
					message = true;
				} else if (time % 20 == 0) {
					message = true;
				}
				if (message) {
					Bukkit.broadcastMessage(
						"§6Invunerabilidade vai acabar em " + time + " segundos!");
				}
			}
		}.runTaskTimer(Main.plugin, 20, 20);
	}
}
