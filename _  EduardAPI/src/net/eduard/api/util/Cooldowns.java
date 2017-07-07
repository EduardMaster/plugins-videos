package net.eduard.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Cooldowns {

	private long ticks;

	private Map<UUID, CooldownEvent> cooldowns = new HashMap<>();
	
	public abstract void onLeftCooldown(Player player);
	public abstract void onStartCooldown(Player player);
	public abstract void onInCooldown(Player player);
	
	public Cooldowns(int seconds) {
		setTime(seconds);
	}
	
	public void setTime(int seconds){
		ticks = seconds*20;
	}

	public long getTicks() {
		return ticks;
	}

	public void setTicks(long ticks) {
		this.ticks = ticks;
	}
	
	public void setOnCooldown(Player player){
		removeFromCooldown(player);
		onStartCooldown(player);
		new CooldownEvent(ticks) {
			
			public void run() {
				removeFromCooldown(player);
			}
			
		}.runTaskLater(getPlugin(), ticks);
	}
	public int getCooldown(Player player){
		if (onCooldown(player)){
			CooldownEvent cooldown = cooldowns.get(player.getUniqueId());
			int result = (int) ((-cooldown.getStartTime()+System.currentTimeMillis())/1000);
			return (int) (cooldown.getCooldownTime()-result);
		}
		return -1;
	}
	public JavaPlugin getPlugin(){
		return JavaPlugin.getProvidingPlugin(getClass());
	}
	public boolean onCooldown(Player player){
		return cooldowns.containsKey(player);
	}
	public void removeFromCooldown(Player player){
		if (onCooldown(player)){
			onLeftCooldown(player);
			cooldowns.get(player.getUniqueId()).cancel();
			cooldowns.remove(player.getUniqueId());
		}
	}
	public boolean cooldown(Player player)
	{
		if (onCooldown(player)){
			onInCooldown(player);
			return false;
		}
		setOnCooldown(player);
		return true;
		
	}

	public static abstract class CooldownEvent extends BukkitRunnable {
		
		public CooldownEvent(long cooldownTime) {
			setStartTime(System.currentTimeMillis());
			setCooldownTime(cooldownTime);
		}

		private long cooldownTime;
		private long startTime;

		public long getStartTime() {
			return startTime;
		}

		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}

		public long getCooldownTime() {
			return cooldownTime;
		}

		public void setCooldownTime(long cooldownTime) {
			this.cooldownTime = cooldownTime;
		}
		
		
		
	}

}
