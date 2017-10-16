package net.eduard.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.eduard.api.config.Config;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.event.PlayerTargetEvent;
import net.eduard.api.game.Sounds;
import net.eduard.api.manager.CommandManager;
import net.eduard.api.minigame.Schematic;
import net.eduard.api.setup.Arena;
import net.eduard.api.setup.Extra;
import net.eduard.api.setup.Mine;
import net.eduard.api.setup.Mine.FakeOfflinePlayer;
import net.eduard.api.setup.Mine.TimeManager;
import net.eduard.api.setup.StorageAPI;

/**
 * API principal da EduardAPI contendo muitos codigos bons e utilitarios Boolean
 * = Teste
 * 
 * @author Eduard
 *
 */
@SuppressWarnings("unchecked")
public class API {
	/**
	 * Mapa de Arenas dos Jogadores
	 */
	public static Map<Player, Arena> MAPS = new HashMap<>();
	private final static API INSTANCE = new API();
	/**
	 * Mapa de Arenas registradas
	 */
	public static Map<String, Arena> SCHEMATICS = new HashMap<>();
	/**
	 * Mapa das posições 1 dos jogadores
	 */
	public static Map<Player, Location> POSITION1 = new HashMap<>();
	/**
	 * Mapa das posições 2 dos jogadores
	 */
	public static Map<Player, Location> POSITION2 = new HashMap<>();
	/**
	 * Som do rosnar do gato
	 */
	public static final Sounds ROSNAR = Sounds.create(Sound.CAT_PURR);

	public static Config MAPS_CONFIG;

	
	/**
	 * Mensagem de quando console digita um comando
	 */
	public static String ONLY_PLAYER = "§cApenas jogadores pode fazer este comando!";
	/**
	 * Mensagem de quando o Mundo é invalido
	 */
	public static String WORLD_NOT_EXISTS = "§cEste mundo $world não existe!";
	/**
	 * Mensagem de quando o jogador é invalido
	 */
	public static String PLAYER_NOT_EXISTS = "§cEste jogador $player não existe!";
	/**
	 * Mensagem de quando plugin é invalido
	 */
	public static String PLUGIN_NOT_EXITS = "§cEste plugin $plugin não exite!";
	/**
	 * Mensagem de quando não tem permissão
	 */
	public static String NO_PERMISSION = "§cVoce não tem permissão para usar este comando!";
	/**
	 * Mensagem de quando Entrar no Servidor
	 */
	public static String ON_JOIN = "§6O jogador $player entrou no Jogo!";
	/**
	 * Mensagem de quando Sair do Servidor
	 */
	public static String ON_QUIT = "§6O jogador $player saiu no Jogo!";
	/**
	 * Prefixo de Ajuda dos Comandos
	 */
	public static String USAGE = "§FDigite: §c";
	/**
	 * Lista de Comandos para efeito Positivo
	 */
	public static List<String> COMMANDS_ON = new ArrayList<>(
			Arrays.asList("on", "ativar"));
	/**
	 * Lista de Comandos para efeito Negativo
	 */
	public static List<String> COMMANDS_OFF = new ArrayList<>(
			Arrays.asList("off", "desativar"));
	/**
	 * Som para o Teleporte
	 */
	public static Sounds SOUND_TELEPORT = Sounds
			.create(Sound.ENDERMAN_TELEPORT);
	/**
	 * Som para algum sucesso
	 */
	public static Sounds SOUND_SUCCESS = Sounds.create(Sound.LEVEL_UP);
	/**
	 * Som para algum erro
	 */
	public static Sounds SOUND_ERROR = Sounds.create(Sound.NOTE_BASS_DRUM);
	/**
	 * Desativar mensagem de morte
	 */
	public static boolean NO_DEATH_MESSAGE = true;
	/**
	 * Desativar mensagem de entrada
	 */
	public static boolean NO_JOIN_MESSAGE = true;
	/**
	 * Desativar mensagem de saida
	 */
	public static boolean NO_QUIT_MESSAGE = true;

	/**
	 * Velocidade minima de corrida
	 */
	public static double MIN_WALK_SPEED = 0.2;
	/**
	 * Velocidade minima de voo
	 */
	public static double MIN_FLY_SPEED = 0.1;
	/**
	 * Ligar sistema de Respawn Automatico
	 */
	public static boolean AUTO_RESPAWN = true;

	/**
	 * Controlador de Tempo da API
	 */
	public static TimeManager TIME;
	/**
	 * Plugin da API
	 */
	public static JavaPlugin PLUGIN;
	/**
	 * Mapa dos Comandos do Servidor
	 */
	private static Map<String, Command> commands = new HashMap<>();
	
	public static boolean hasPos1(Player p) {
		return POSITION1.get(p) != null;
	}
	public static boolean hasPos2(Player p) {
		return POSITION2.get(p) != null;
	}
	public static Schematic getSchematic(Player player) {
		return new Schematic(player.getLocation(), POSITION1.get(player), POSITION2.get(player));
	}
	/**
	 * Ligando algumas coisas
	 */
	static {
		try {
			PLUGIN = JavaPlugin.getProvidingPlugin(API.class);
			TIME = new TimeManager(PLUGIN);
			MAPS_CONFIG = new Config(PLUGIN,"maps.yml");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Object map = Extra.getValue(Bukkit.getServer().getPluginManager(),
					"commandMap");

			commands = (Map<String, Command>) Extra.getValue(map,
					"knownCommands");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void updateTargets() {
		for (Player p : API.getPlayers()) {

			PlayerTargetEvent event = new PlayerTargetEvent(p,
					Mine.getTarget(p,
							Mine.getPlayerAtRange(p.getLocation(), 100)));
			Mine.callEvent(event);

		}
	}

	public static Map<String, Command> getCommands() {
		return commands;
	}

	public static PluginCommand command(String commandName,
			CommandExecutor command) {
		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermissionMessage(
				API.NO_PERMISSION.replace("$permission", cmd.getPermission()));
		return cmd;
	}

	public static PluginCommand command(String commandName,
			CommandExecutor command, String permission) {

		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermission(permission);
		cmd.setPermissionMessage(
				API.NO_PERMISSION.replace("$permission", cmd.getPermission()));
		return cmd;
	}

	public static boolean existsPlayer(CommandSender sender, String player) {

		Player p = Bukkit.getPlayer(player);
		if (p == null) {
			sender.sendMessage(
					API.PLAYER_NOT_EXISTS.replace("$player", player));
			return false;
		}
		return true;
	}
	public static boolean existsPlugin(CommandSender sender, String plugin) {

		Plugin p = getPlugin(plugin);
		if (p == null) {
			sender.sendMessage(API.PLUGIN_NOT_EXITS.replace("$plugin", plugin));
			return false;
		}
		return true;
	}
	public static Plugin getPlugin(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin);
	}
	public static boolean existsWorld(CommandSender sender, String name) {
		World world = Bukkit.getWorld(name);
		if (world == null) {
			sender.sendMessage(API.WORLD_NOT_EXISTS.replace("$world", name));
			return false;
		}
		return true;
	}

	public static JavaPlugin getAPI() {
		if (hasAPI()) {
			PLUGIN = (JavaPlugin) Bukkit.getPluginManager()
					.getPlugin("EduardAPI");
		}
		return PLUGIN;
	}

	public static boolean hasAPI() {

		return Mine.hasPlugin("EduardAPI");
	}

	public static List<Player> getPlayers() {
		return Mine.getPlayers();
	}

	public static boolean hasPerm(CommandSender sender, String permission) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(
					API.NO_PERMISSION.replace("$permission", permission));
			return false;
		}
		return true;

	}
	@SafeVarargs
	public static void commands(ConfigSection section, CommandManager... cmds) {
		for (CommandManager cmd : cmds) {
			try {

				String name = cmd.getName();
				if (section != null) {
					if (section.contains(name)) {
						cmd = (CommandManager) section.get(name);

					}

				}
				cmd.register();
				if (section != null) {
					section.add(name, cmd);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public static boolean noConsole(CommandSender sender) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(API.ONLY_PLAYER);
			return false;
		}
		return true;
	}

	public static boolean onlyPlayer(CommandSender sender) {
		return noConsole(sender);
	}

	
	public static void console(String message) {
		Mine.console(message);
	}
	public static void broadcast(String message) {
		Mine.broadcast(message);
	}
	
	public static void removeAliaseFromCommand(PluginCommand cmd,
			String aliase) {
		String cmdName = cmd.getName().toLowerCase();
		if (getCommands().containsKey(aliase)) {
			getCommands().remove(aliase);
			console("§bCommandAPI §fremovendo aliase §a" + aliase
					+ "§f do comando §b" + cmdName);
		} else {
			console("§bCommandAPI §fnao foi encontrado a aliase §a" + aliase
					+ "§f do comando §b" + cmdName);
		}
	}

	public static void removeCommand(String name) {
		if (getCommands().containsKey(name)) {
			PluginCommand cmd = Bukkit.getPluginCommand(name);
			String pluginName = cmd.getPlugin().getName();
			String cmdName = cmd.getName();
			for (String aliase : cmd.getAliases()) {
				removeAliaseFromCommand(cmd, aliase);
				removeAliaseFromCommand(cmd,
						pluginName.toLowerCase() + ":" + aliase);
			}
			try {
				getCommands().remove(cmd.getName());
			} catch (Exception e) {
			}
			console("§bCommandAPI §fremovendo o comando §a" + cmdName
					+ "§f do Plugin §b" + pluginName);
		} else {
			console("§bCommandAPI §fnao foi encontrado a commando §a" + name);
		}

	}


	public static void addPermission(Player p, String permission) {
		p.addAttachment(API.PLUGIN, permission, true);
	}
	public static void removePermission(Player p, String permission) {
		p.addAttachment(API.PLUGIN, permission, false);
	}

	@SuppressWarnings("deprecation")
	public static Scoreboard applyScoreboard(Player player, String title,
			String... lines) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("score", "dummy");
		obj.setDisplayName(title);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		int id = 15;
		for (String line : lines) {
			String empty = ChatColor.values()[id - 1].toString();
			obj.getScore(new FakeOfflinePlayer(line.isEmpty() ? empty : line))
					.setScore(id);;
			id--;
			if (id == 0) {
				break;
			}
		}

		player.setScoreboard(board);
		return board;
	}
	public static Scoreboard newScoreboard(Player player, String title,
			String... lines) {
		return applyScoreboard(player, title, lines);
	}


	
	public static void loadMaps() {
		if (MAPS_CONFIG.contains("MAPS")) {

			try {
				StorageAPI.restoreField(INSTANCE,
						MAPS_CONFIG.getSection("MAPS").toMap(),
						Extra.getField(INSTANCE, "MAPS"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	public static void saveMaps() {

		try {
			Object value = StorageAPI.storeField(INSTANCE,
					Extra.getField(INSTANCE, "MAPS"));
			MAPS_CONFIG.set("MAPS", value);

		} catch (Exception e) {
			e.printStackTrace();
		}
		MAPS_CONFIG.saveConfig();
	}
	public static void chat(CommandSender sender, String message) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			sender.sendMessage(Mine.getReplacers(message, player));	
		}else {
			sender.sendMessage(message);;
		}
		
	}

}
