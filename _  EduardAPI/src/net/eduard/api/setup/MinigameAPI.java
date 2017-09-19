package net.eduard.api.setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.eduard.api.API;
import net.eduard.api.setup.StorageAPI.Reference;
import net.eduard.api.setup.StorageAPI.Storable;
import net.eduard.api.setup.WorldAPI.Arena;

public final class MinigameAPI {
	public static abstract class Minigame implements Listener,Storable{

		private String name;
		private Location lobby;
		private Map<String, GameMap> maps = new HashMap<>();
		private transient Map<Integer, Game> rooms = new HashMap<>();
		private transient Map<Player, Game> playing = new HashMap<>();
		private transient BukkitTask minigameRunnning;
		public Minigame() {
		}
		public void register(Plugin plugin) {
			enable(plugin);
			ExtraAPI.event(this, plugin);
		}
		public Minigame(String name) {
			setName(name);
		}
		public Minigame(String name, Plugin plugin) {
			setName(name);
			enable(plugin);
		}
		public void disable() {
			if (isRunning()) {
				minigameRunnning.cancel();
				minigameRunnning = null;
			}
		}
		public boolean isRunning() {
			return minigameRunnning != null;
		}
		public void enable(Plugin plugin) {
			
			minigameRunnning = new BukkitRunnable() {

				@Override
				public void run() {
					for (Game game : rooms.values()) {
						MinigameEvent event = new MinigameEvent(Minigame.this, game);
						if (event.isCancelled()) {
							continue;
						}
						event(game);
					}

				}
			}.runTaskTimer(plugin, 20, 20);
		}
		public abstract void event(Game room);
		public void broadcast(Object... text) {
			API.send(playing.keySet(), text);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Map<String, GameMap> getMaps() {
			return maps;
		}

		public void setMaps(Map<String, GameMap> maps) {
			this.maps = maps;
		}

		public Map<Integer, Game> getRooms() {
			return rooms;
		}

		public void setRooms(Map<Integer, Game> rooms) {
			this.rooms = rooms;
		}

		public Map<Player, Game> getPlaying() {
			return playing;
		}

		public void setPlaying(Map<Player, Game> playing) {
			this.playing = playing;
		}

		


		public BukkitTask getMinigameRunnning() {
			return minigameRunnning;
		}

		public void setMinigameRunnning(BukkitTask minigameRunnning) {
			this.minigameRunnning = minigameRunnning;
		}

		public Location getLobby() {
			return lobby;
		}
		public void setLobby(Location lobby) {
			this.lobby = lobby;
		}

		
	}public static enum GameState {

		STARTING, PLAYING, RESTARTING, ENDING, INVULNERABILITY;

	}

	/**
	 * Sala do Minigame
	 * @author Eduard-PC
	 *
	 */
	public static class Game {

		private int id;
		private int time;
		private Minigame minigame;
		private GameMap map;
		private GameState state = GameState.STARTING;
		private World world = Bukkit.getWorlds().get(0);
		private List<Player> players = new ArrayList<>();
		private List<Player> spectators = new ArrayList<>();
		private List<Player> admins = new ArrayList<>();
		private List<Player> winners = new ArrayList<>();
		private List<GameTeam> teams= new ArrayList<>();
		
		
		public Game(Minigame minigame,GameMap map) {
			this.minigame = minigame;
			this.id = minigame.getRooms().size()+1;
			this.minigame.getRooms().put(id, this);
			this.map = map;
			this.time = map.getTimeIntoStart();
		}
		public void sendToPlayers(Object...text){
			API.send(players, text);
		}
		public void sendToSpecs(Object...text){
			API.send(spectators, text);
		}
		public void sendToAdmins(Object...text){
			API.send(admins, text);
		}
		public void broadcast(Object...text){
			sendToAdmins(text);
			sendToPlayers(text);
			sendToSpecs(text);
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public GameState getState() {
			return state;
		}

		public void setState(GameState state) {
			this.state = state;
		}

		public GameMap getMap() {
			return map;
		}

		public void setMap(GameMap map) {
			this.map = map;
		}

		public Minigame getMinigame() {
			return minigame;
		}

		public void setMinigame(Minigame minigame) {
			this.minigame = minigame;
		}


		public List<Player> getPlayers() {
			return players;
		}

		public void setPlayers(List<Player> players) {
			this.players = players;
		}

		public List<Player> getSpectators() {
			return spectators;
		}

		public void setSpectators(List<Player> spectators) {
			this.spectators = spectators;
		}

		public List<Player> getAdmins() {
			return admins;
		}

		public void setAdmins(List<Player> admins) {
			this.admins = admins;
		}


		public World getWorld() {
			return world;
		}


		public void setWorld(World world) {
			this.world = world;
		}
		public int getTime() {
			return time;
		}
		public void setTime(int time) {
			this.time = time;
		}
		public List<Player> getWinners() {
			return winners;
		}
		public void setWinners(List<Player> winners) {
			this.winners = winners;
		}
		public List<GameTeam> getTeams() {
			return teams;
		}
		public void setTeams(List<GameTeam> teams) {
			this.teams = teams;
		}

	}
	/**
	 * Minigame simplificado para ser Evento
	 * 
	 * @author Eduard-PC
	 *
	 */
	public static abstract class GameEvent extends Minigame {
		@Reference
		private Game game;
		@Reference
		private GameMap map;
		public GameEvent(String name, Plugin plugin) {
			super(name, plugin);
			setMap(new GameMap(name));
			setGame(new Game(this, map));
		}

		public GameEvent() {
		}

		public Game getGame() {
			return game;
		}

		public void setGame(Game game) {
			this.game = game;
		}

		public GameMap getMap() {
			return map;
		}

		public void setMap(GameMap map) {
			this.map = map;
		}

	}

	public static class GameTeam {
		private Player leader;
		private int maxSize = 5;
		private boolean friendlyFire;
		private List<Player> players = new ArrayList<>();
		public Player getLeader() {
			return leader;
		}
		public void setLeader(Player leader) {
			this.leader = leader;
		}
		public int getMaxSize() {
			return maxSize;
		}
		public void setMaxSize(int maxSize) {
			this.maxSize = maxSize;
		}
		public List<Player> getPlayers() {
			return players;
		}
		public void setPlayers(List<Player> players) {
			this.players = players;
		}
		public boolean isFriendlyFire() {
			return friendlyFire;
		}
		public void setFriendlyFire(boolean friendlyFire) {
			this.friendlyFire = friendlyFire;
		}

		}
	
	/**
	 * Mapa da Sala
	 * @author Eduard-PC
	 *
	 */
	public static class GameMap implements Storable{
		
		private String name;
		private World world;
		private int minPlayersAmount=2;
		private int maxPlayersAmount=20;
		private int neededPlayersAmount=16;
		private Location lobby;
		private Location spawn;
		private Arena feast;
		private Arena map;
		private List<Location> spawns = new ArrayList<>();
		private List<Arena> minifeasts= new ArrayList<>();
		private Location feastPosition;
		private Location highPosition;
		private Location lowPosition;
		private int timeIntoStart=60;
		private int timeIntoRestart=20;
		private int timeIntoGameOver=15*60;
		private int timeWithoutPvP=2*60;
		private int timeOnRestartTimer=40;
		private int timeOnForceTimer=10;
		
		public int getTimeIntoStart() {
			return timeIntoStart;
		}
		public GameMap() {
			// TODO Auto-generated constructor stub
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

		
		
		public GameMap(String name) {
			super();
			this.name = name;
		}

		public World getWorld() {
			return world;
		}

		public void setWorld(World world) {
			this.world = world;
		}

		public int getMinPlayersAmount() {
			return minPlayersAmount;
		}

		public void setMinPlayersAmount(int minPlayersAmount) {
			this.minPlayersAmount = minPlayersAmount;
		}

		public int getMaxPlayersAmount() {
			return maxPlayersAmount;
		}

		public void setMaxPlayersAmount(int maxPlayersAmount) {
			this.maxPlayersAmount = maxPlayersAmount;
		}

		public int getNeededPlayersAmount() {
			return neededPlayersAmount;
		}

		public void setNeededPlayersAmount(int neededPlayersAmount) {
			this.neededPlayersAmount = neededPlayersAmount;
		}

		public Location getLobby() {
			return lobby;
		}

		public void setLobby(Location lobby) {
			this.lobby = lobby;
		}

		public Location getSpawn() {
			return spawn;
		}

		public void setSpawn(Location spawn) {
			this.spawn = spawn;
		}

		public Arena getFeast() {
			return feast;
		}

		public void setFeast(Arena feast) {
			this.feast = feast;
		}

		public List<Arena> getMinifeasts() {
			return minifeasts;
		}

		public void setMinifeasts(List<Arena> minifeasts) {
			this.minifeasts = minifeasts;
		}

		public Location getFeastPosition() {
			return feastPosition;
		}

		public void setFeastPosition(Location feastPosition) {
			this.feastPosition = feastPosition;
		}

		public Location getHighPosition() {
			return highPosition;
		}

		public void setHighPosition(Location highPosition) {
			this.highPosition = highPosition;
		}

		public Location getLowPosition() {
			return lowPosition;
		}

		public void setLowPosition(Location lowPosition) {
			this.lowPosition = lowPosition;
		}

		public Arena getMap() {
			return map;
		}

		public void setMap(Arena map) {
			this.map = map;
		}

		public List<Location> getSpawns() {
			return spawns;
		}

		public void setSpawns(List<Location> spawns) {
			this.spawns = spawns;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public Object restore(Map<String, Object> map) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void store(Map<String, Object> map, Object object) {
			// TODO Auto-generated method stub
			
		}


	}
	public static class MinigameEvent extends Event implements Cancellable{

		private static final HandlerList events = new HandlerList();
		private Minigame minigame;
		private Game room;
		private GameMap map;
		private boolean cancelled;
		
		@Override
		public boolean isCancelled() {
			return cancelled;
		}

		@Override
		public void setCancelled(boolean cancelled) {
			this.cancelled = cancelled;
		}

		public MinigameEvent(Minigame minigame, Game room) {
		
			this.minigame = minigame;
			this.room = room;
			this.map = room.getMap();
		}

		public Minigame getMinigame() {
			return minigame;
		}

		public Game getRoom() {
			return room;
		}

		public GameMap getMap() {
			return map;
		}


		@Override
		public HandlerList getHandlers() {
			return events;
		}

		
	}

}
