package net.eduard.api.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.TimeAPI;

public class Cooldown extends Events {

	private transient Map<UUID, TimeAPI> playersInCooldown = new HashMap<>();
	private String onCooldownMessage = "§6Voce esta em Cooldown!";
	private String overCooldownMessage = "§6Voce saiu do Cooldown!";
	private String startCooldownMessage = "§6Voce usou a Habilidade!";

	public boolean cooldown(Player player) {
		if (onCooldown(player)) {
			sendOnCooldown(player);
			return false;
		}
		setOnCooldown(player);
		sendStartCooldown(player);
		return true;
	}
	public Cooldown stopCooldown(Player player) {
		playersInCooldown.get(player).getTask().cancel();
		playersInCooldown.remove(player);
		return this;
	}

	public boolean onCooldown(Player player) {
		return getResult(player) > 0;
	}
	public Cooldown sendOnCooldown(Player player) {
		player.sendMessage(onCooldownMessage);	
		return this;
		
		
	}

	public Cooldown sendStartCooldown(Player player) {
		player.sendMessage(startCooldownMessage);
		return this;
	}
	public Cooldown setOnCooldown(Player player) {
		if (onCooldown(player)) {
			stopCooldown(player);
		}
		
		TimeAPI cd = new TimeAPI() {
			public void run() {
				sendOverCooldown(player);
			}
		};
		cd.setTime(getTime());
		cd.delay(getPlugin());
		playersInCooldown.put(player.getUniqueId(), cd);
		return this;

	}
	public Cooldown sendOverCooldown(Player player){
		player.sendMessage(overCooldownMessage);
		return this;
	}

	public long getResult(Player player) {
		if (playersInCooldown.containsKey(player.getUniqueId())) {
			long now = API.getNow();
			TimeAPI cd = playersInCooldown.get(player.getUniqueId());
			Long before = cd.getStartTime();
			long cooldown = cd.getTime() * 50;
			long calc = before + cooldown;
			long result = calc - now;
			if (result <= 0) {
				return 0;
			}
			return result / 50;
		}
		return 0;
	}
	public int getCooldown(Player player){
		
		
		return (int) ((getResult(player)/20));
	}
	public String getOverCooldownMessage() {
		return overCooldownMessage;
	}
	public Cooldown setOverCooldownMessage(String overCooldownMessage) {
		this.overCooldownMessage = overCooldownMessage;
		return this;
	}

}
