package net.eduard.hg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class HG implements Runnable, Listener {
	public HG(Main main) {
		setMain(main);
	}
	private int time;
	private Main main;
	private int timeInvunerable = 60 * 2;
	private int timeIntoStart = 5*60;
	private int timeIntoEnd= 30*60;
	private List<Player> players = new ArrayList<>();
	private int maxPlayersAmount = 20;
	private HGState state = HGState.STARTING;
	private int minPlayersAmount;
	private int timeIntoRestart;
	public void sendMessage(String message) {
		for (Player player : players) {
			player.sendMessage(message);;
		}
	}
	@EventHandler
	public void aoSeConectar(AsyncPlayerPreLoginEvent e) {
		if (state != HGState.STARTING) {

		}
	}
	@EventHandler
	public void aoLogar(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		p.setHealth(p.getMaxHealth());
	}
	@EventHandler
	public void aoEntrar(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (state == HGState.STARTING) {
			e.setJoinMessage(
					main.message("join").replace("$player", p.getName()));
		} else {
			if (!p.hasPermission("hg.spectate")) {
				e.setJoinMessage(main.message("already started"));
			}
		}

	}

	public HGState getState() {
		return state;
	}

	public void setState(HGState state) {
		this.state = state;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getMaxPlayersAmount() {
		return maxPlayersAmount;
	}

	public void setMaxPlayersAmount(int maxPlayersAmount) {
		this.maxPlayersAmount = maxPlayersAmount;
	}

	@EventHandler
	public void semPvPInvunerability(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (players.contains(p)) {
				if (state == HGState.INVUNERABILITY) {
					e.setCancelled(true);
				}
			}

		}
	}
	public void playSound(Sound sound) {
		for (Player player : players) {
			player.playSound(player.getLocation(), sound, 2, 1);
		}
	}

	@Override
	public void run() {
		if (state == HGState.STARTING) {
			if (time == 0) {
				if (players.size() <= minPlayersAmount) { 
					
					time = timeIntoStart;
					return;
				}
				state = HGState.INVUNERABILITY;
				time = timeInvunerable;
			} else {
				if (time < 10 || time % 30 == 0) {
					sendMessage(main.message("starting"));
					playSound(Sound.CLICK);
				}
			}
			time--;
		} else if (state == HGState.INVUNERABILITY) {
			if (time == 0) {
				state = HGState.PLAYING;
			}
			time--;
		} else if (state == HGState.PLAYING) {
			if (time == timeIntoEnd) {
				state = HGState.RESTARTING;
				time = timeIntoRestart;
			}
			time++;
		} else if (state == HGState.RESTARTING) {

			time--;
		}
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	public int getTimeIntoEnd() {
		return timeIntoEnd;
	}
	public void setTimeIntoEnd(int timeIntoEnd) {
		this.timeIntoEnd = timeIntoEnd;
	}
	public int getTimeIntoStart() {
		return timeIntoStart;
	}
	public void setTimeIntoStart(int timeIntoStart) {
		this.timeIntoStart = timeIntoStart;
	}

}
