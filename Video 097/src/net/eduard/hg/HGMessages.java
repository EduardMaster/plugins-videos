
package net.eduard.hg;

import org.bukkit.entity.Player;

public class HGMessages {

	public static String onJoining = "§6O jogador $player entrou no HG $status";

	public static String onLeaving = "§6O jogador $player saiu do HG $status";

	public static String onStarting = "§6O HG vai começar em $time";

	public static String onRestarting = "§6O HG vai reiniciar em $time";

	public static String onStart = "§6O HG começou!";

	public static String onRestart = "§6O HG reiniciou!";

	public static String onGameOver = "§6O HG acabou!";

	public static String onPlaying = "§6O HG vai terminar em $time";

	public static String onInvunerabilityIsOver =
		"§6A invunerabilidade vai acabar em $invul";

	public static String onInvunerabilityOver = "§6A invunerabilidade acabou!";

	public static void broadcast(String message) {

		for (Player p : HG.players) {
			p.sendMessage(
				message.replace("$time", getTime()).replace("$status", getStatus().replace("$invul", getInvul())));
		}
	}

	public static String getStatus() {

		return "§8(§e$amount§8/§6$max§8)".replace("$amount", "" + HG.players.size())
			.replace("$max", "" + HG.maxPlayers);
	}

	public static String getTime() {

		if (HGTimer.time > 60) {
			int min = HGTimer.time / 60;
			int seconds = HGTimer.time % 60;
			return "" + min + " minutos e " + seconds + " segudos!";
		}
		return "" + HGTimer.time + " segundos!";
	}

	public static String getInvul() {

		if (HG.inuvunerableTime > 60) {
			int min = HG.inuvunerableTime / 60;
			int seconds = HG.inuvunerableTime % 60;
			return "" + min + " minutos e " + seconds + " segudos!";
		}
		return "" + HG.inuvunerableTime + " segundos!";
	}

	public static String getTime2() {

		if (HGTimer.time > 60) {
			int min = HGTimer.time / 60;
			int seconds = HGTimer.time % 60;
			return "" + min + "min " + seconds + "s";
		}
		return "" + HGTimer.time + "s";
	}

}
