
package net.eduard.hg;

import org.bukkit.scheduler.BukkitRunnable;

public class HGTimer extends BukkitRunnable{

	public static int time = 0;

	public void starting() {

		if (HG.players.size() < HG.minPlayers) {
			time = HG.timeIntoStart;
		}
		if (HG.startTimes.contains(time)) {
			HGMessages.broadcast(HGMessages.onStarting);
		}
		if (time == 0) {
			HG.state = HGState.PLAYING;
			time = HG.timeIntoOver;
			
			HGMessages.broadcast(HGMessages.onStart);
			HGEvents.start();
			return;
		}
		HGEvents.starting();

	}

	public void playing() {

//		if (HG.players.size() == 1) {
//			Player p = HG.players.get(0);
//			HGEvents.wining(p);
//			HG.state = HGState.RESTARTING;
//			time = HG.timeToRestart;
//			HGMessages.broadcast(HGMessages.onGameOver);
//			HGEvents.gameOver();
//			return;
//		}
		if (HG.gameOverTimes.contains(time)) {
			HGMessages.broadcast(HGMessages.onPlaying);
		}
		if (time == 0) {
			HG.state = HGState.RESTARTING;
			time = HG.timeToRestart;
			HGMessages.broadcast(HGMessages.onGameOver);
			HGEvents.noWiner();
			HGEvents.gameOver();
			return;
		}
		HGEvents.playing();
	}

	public void restarting() {

		if (HG.restartTimes.contains(time)) {
			HGMessages.broadcast(HGMessages.onRestarting);
		}
		if (time == 0) {
			HG.state = HGState.STARTING;
			time = HG.timeIntoStart;
			HGMessages.broadcast(HGMessages.onRestart);
			HGEvents.restart();
			return;
		}
		HGEvents.restarting();
	}

	public void run() {

		if (HG.state == HGState.STARTING) {
			starting();
		}else if (HG.state == HGState.PLAYING) {
			playing();
		}else if (HG.state == HGState.RESTARTING) {
			restarting();
		}
		time--;
	}

}
