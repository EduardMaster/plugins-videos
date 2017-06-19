package net.eduard.api.minigame;

public class MinigameAttributes {

	private int timeIntoStart=60;
	private int timeIntoRestart=20;
	private int timeIntoGameOver=15*60;
	private int timeWithoutPvP=2*60;
	private int timeOnRestartTimer=40;
	private int timeOnForceTimer=10;
	
	
	public int getTimeIntoStart() {
		
		return timeIntoStart;
	}
	public void setTimeIntoStart(int timeIntoStart) {
		this.timeIntoStart = timeIntoStart;
	}
	public int getTimeIntoRestart() {
		return timeIntoRestart;
	}
	public void setTimeIntoRestart(int timeIntoRestart) {
		this.timeIntoRestart = timeIntoRestart;
	}
	public int getTimeIntoGameOver() {
		return timeIntoGameOver;
	}
	public void setTimeIntoGameOver(int timeIntoGameOver) {
		this.timeIntoGameOver = timeIntoGameOver;
	}
	public int getTimeWithoutPvP() {
		return timeWithoutPvP;
	}
	public void setTimeWithoutPvP(int timeWithoutPvP) {
		this.timeWithoutPvP = timeWithoutPvP;
	}
	public int getTimeOnRestartTimer() {
		return timeOnRestartTimer;
	}
	public void setTimeOnRestartTimer(int timeOnRestartTimer) {
		this.timeOnRestartTimer = timeOnRestartTimer;
	}
	public int getTimeOnForceTimer() {
		return timeOnForceTimer;
	}
	public void setTimeOnForceTimer(int timeOnForceTimer) {
		this.timeOnForceTimer = timeOnForceTimer;
	}
	
	
}
