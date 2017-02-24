package net.eduard.api.dev;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.Save;
import net.eduard.api.util.SimpleEffect;
import net.eduard.api.util.TimeEffect;

public class Game implements Save {
	private long startTime;
	private long time=1;
	private BukkitTask task;

	public Game(long ticks) {
		setTime(ticks);
	}
	public Game(int seconds) {
		setTime(seconds);
	}
	public Game() {
	}

	public long getStartTime() {
		return startTime;
	}

	public Game setStartTime(long startTime) {
		this.startTime = startTime;
		return this;
	}

	public long getTime() {
		return time;
	}

	public Game setTime(int seconds) {
		this.time = seconds*20;
		return this;
	}
	public Game setTime(long ticks) {
		this.time = ticks;
		return this;
	}
	public Game delay(SimpleEffect effect) {
		task = Bukkit.getScheduler().runTaskLater(API.getAPI(), new Runnable() {

			public void run() {
				effect.effect();
				stop();
			}
		}, time);
		return this;
	}

	public Game timer(SimpleEffect effect) {
		task = Bukkit.getScheduler().runTaskTimer(API.getAPI(), new Runnable() {

			public void run() {
				effect.effect();
			}
		}, time, time);
		return this;
	}
	public Game countdown(TimeEffect effect,int times) {
		
		task = Bukkit.getScheduler().runTaskTimer(API.getAPI(), new Runnable() {
			int value = times;
			public void run() {
				effect.effect(value);
				value--;
				if (value==0) {
					stop();
				}
			}
		}, time, time);
		return this;
	}
	public Game repete(TimeEffect effect,int times) {
		
		task = Bukkit.getScheduler().runTaskTimer(API.getAPI(), new Runnable() {
			int value = 0;
			public void run() {
				value++;
				effect.effect(value);
				if (value==times) {
					stop();
				}
			}
		}, time, time);
		return this;
	}

	public boolean exists() {
		return task != null;
	}

	public Game stop() {
		if (exists()) {
			task.cancel();
			task = null;
		}
		return this;
	}

	public Object get(Section section) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Section section, Object value) {
		// TODO Auto-generated method stub

	}

}
