package net.eduard.api.dev;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.gui.Events;
import net.eduard.api.util.PlayerEffect;
import net.eduard.api.util.SimpleEffect;

public class Cooldown extends Events {

	private Map<UUID, Game> playersInCooldown = new HashMap<>();

	public long getResult(Player player) {
		if (playersInCooldown.containsKey(player.getUniqueId())) {
			long now = API.getTime();
			Game cd = playersInCooldown.get(player.getUniqueId());
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

	public boolean cooldown(Player player) {
		if (onCooldown(player)) {
			sendCooldown(player);
			return false;
		}
		set(player);
		sendUse(player);
		return true;
	}

	public void sendUse(Player player) {
		player.sendMessage(startCooldownMessage);
	}

	public void sendCooldown(Player player) {
		player.sendMessage(onCooldownMessage);
	}

	public String getOnCooldownMessage() {
		return onCooldownMessage;
	}

	public Cooldown setOnCooldownMessage(String onCooldownMessage) {
		this.onCooldownMessage = onCooldownMessage;
		return this;
	}

	public String getStartCooldownMessage() {
		return startCooldownMessage;
	}

	public Cooldown setStartCooldownMessage(String startCooldownMessage) {
		this.startCooldownMessage = startCooldownMessage;
		return this;
	}

	public Map<UUID, Game> getPlayersInCooldown() {
		return playersInCooldown;
	}

	public void setPlayersInCooldown(Map<UUID, Game> playersInCooldown) {
		this.playersInCooldown = playersInCooldown;
	}

	public long getCooldownInTicks() {
		return cooldownInTicks;
	}

	public void setCooldownInTicks(long cooldownInTicks) {
		this.cooldownInTicks = cooldownInTicks;
	}

	private long cooldownInTicks = 100;
	private String onCooldownMessage = "§6Voce esta em Cooldown!";
	private String startCooldownMessage = "§6Voce usou a Habilidade!";

	public Cooldown setTime(int seconds) {
		return setTime(seconds * 20L);
	}

	public Cooldown setTime(long ticks) {
		cooldownInTicks = ticks;
		return this;
	}

	public long getCooldown(Player player) {
		if (onCooldown(player)) {
			return playersInCooldown.get(player.getUniqueId()).getTime();
		}
		return 0;
	}

	public long getCooldown() {
		return cooldownInTicks;
	}

	public int getTime() {
		return (cooldownInTicks > 0 ? (int) (cooldownInTicks / 20) : 0);
	}

	public Cooldown set(Player player) {
		return set(player, cooldownInTicks, this);
	}

	public Cooldown set(Player player, String message) {
		return set(player, cooldownInTicks, message);
	}

	public Cooldown set(Player player, PlayerEffect effect) {
		return set(player, cooldownInTicks, effect);
	}

	public Cooldown set(Player player, long ticks) {
		return set(player, ticks, this);
	}

	public Cooldown set(Player player, long ticks, PlayerEffect effect) {
		remove(player);
		return setCooldown(player, ticks, effect);
	}

	public Cooldown set(Player player, long ticks, String message) {
		return set(player, ticks, new PlayerEffect() {

			public void effect(Player p) {
				p.sendMessage(message);
			}
		});
	}

	private Cooldown setCooldown(Player player, long ticks, PlayerEffect effect) {

		playersInCooldown.put(player.getUniqueId(), new Game(ticks).delay(new SimpleEffect() {

			public void effect() {
				effect.effect(player);
				remove(player);
			}
		}));
		return this;
	}

	public Cooldown remove(Player player) {
		if (playersInCooldown.containsKey(player.getUniqueId())) {
			playersInCooldown.get(player.getUniqueId()).stop();
			playersInCooldown.remove(player.getUniqueId());
		}
		return this;
	}

	public boolean onCooldown(Player player) {
		return getResult(player) > 0 ? true : false;
	}

}
