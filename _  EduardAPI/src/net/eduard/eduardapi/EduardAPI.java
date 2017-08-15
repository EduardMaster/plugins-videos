package net.eduard.eduardapi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.event.ChatMessageEvent;
import net.eduard.api.event.PlayerTargetEvent;
import net.eduard.api.game.ChatChannel;
import net.eduard.api.game.Drop;
import net.eduard.api.manager.EduardPlugin;
import net.eduard.api.manager.Scores;
import net.eduard.api.setup.ExtraAPI;
import net.eduard.api.setup.GameAPI;
import net.eduard.api.setup.RexAPI;
import net.eduard.eduardapi.command.ApiCommand;
import net.eduard.eduardapi.command.ConfigCommand;
import net.eduard.eduardapi.command.EnchantCommand;
import net.eduard.eduardapi.command.GotoCommand;
import net.eduard.eduardapi.command.MapCommand;
import net.eduard.eduardapi.command.SoundCommand;
import net.eduard.eduardapi.command.lag.LagCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
/**
 * Nomes para os Meus Plugins MasterPlugin (Minigames , Eventos, Grandes)
 * ePlugin (Economia, Loja, Dinheiro, GUI) EduPlugin (Geral) EMPlugin (Plugins
 * Gratis) Posso usar _ ou - entre as Palavras ou não opcional Os preços que
 * colocar já é incluido tudo configuravel
 * 
 * Meus nicks de Jogo EduardKillerPro EduardMaster
 * 
 * @author Eduard-PC
 *
 */
public class EduardAPI extends EduardPlugin implements Listener {
	private static JavaPlugin plugin;

	public static JavaPlugin getInstance() {
		return plugin;
	}

	@Override
	public void setup() {
		plugin = this;
		API.resetScoreboards();
		ExtraAPI.consoleMessage("§bEduardAPI §fBarAPI §aativado!");
		ExtraAPI.consoleMessage("§bEduardAPI §fDataBase §agerada!");
		saveObjects();
		reload();
		API.event(this);
		API.event(new Drop());
		new MapCommand().register();
		new ConfigCommand().register();
		new SoundCommand().register();
		new ApiCommand().register();
		new GotoCommand().register();
		new LagCommand().register();
		new EnchantCommand().register();
		ExtraAPI.consoleMessage("§bEduardAPI §aativado!");
		Scores.setTagsEnabled(true);

	}
	public static void sendMessage(Player player, String message,
			ChatChannel channel) {
		ChatMessageEvent chat = new ChatMessageEvent(player, channel, message);
		API.callEvent(chat);
		if (!chat.isCancelled()) {
			String permission = "chat." + chat.getChannel().getName();
			message = chat.getFormat();
			for (Entry<String, String> map : chat.getTags().entrySet()) {
				message = message.replaceAll(map.getKey(), map.getValue());
			}
			message = chat.getFormat()
					.replace("$chat_prefix", chat.getChannel().getPrefix())
					.replace("$chat_suffix", chat.getChannel().getSuffix())
					.replace("$message", chat.getMessage())
					.replace("$player", player.getName());
			if (API.CHAT_SPIGOT) {
				TextComponent text = new TextComponent(message);
				if (chat.getOnClickCommand() != null) {
					text.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND,
							chat.getOnClickCommand()));
				}
				if (chat.getOnHoverText() != null) {
					ComponentBuilder builder = new ComponentBuilder("");
					for (String line : chat.getOnHoverText()) {
						builder.append(line + "\n");
					}
					text.setHoverEvent(new HoverEvent(
							HoverEvent.Action.SHOW_TEXT, builder.create()));
				}

				for (Player p : API.getPlayers()) {
					if (p.hasPermission(permission)) {
						p.spigot().sendMessage(text);
					}
				}
			} else {
				API.broadcast(message, permission);
			}
		}
	}

	public void reload() {
		API.loadMaps();
		config.add("sound-teleport", API.SOUND_TELEPORT);
		config.add("sound-error", API.SOUND_ERROR);
		config.add("sound-success", API.SOUND_SUCCESS);
		ChatChannel local = new ChatChannel("local",
				"$chat_prefix $player $chat_suffix: $message", "&e(L)&f", "",
				"l");
		ChatChannel global = new ChatChannel("global",
				"$chat_prefix $player $chat_suffix: $message", "&e(L)&f", "",
				"g");
		config.add("chat-clicable", true,
				"Precisa ser Spigot se for Craftbukkit vai dar erro");
		config.add("chats.local", local);
		config.add("chats.global", global);
		config.add("chat-default", "local");
		ExtraAPI.consoleMessage("§bEduardAPI §fMapas §acarregados!");
		API.AUTO_RESPAWN = config.getBoolean("auto-respawn");
		API.NO_JOIN_MESSAGE = config.getBoolean("no-join-message");
		API.NO_QUIT_MESSAGE = config.getBoolean("no-quit-message");
		API.NO_DEATH_MESSAGE = config.getBoolean("no-death-message");
		API.CUSTOM_CHAT = config.getBoolean("custom-chat");
		API.ON_JOIN = config.message("on-join-message");
		API.ON_QUIT = config.message("on-quit-message");
		API.SERVER_TAG = config.message("server-tag");
		API.SOUND_TELEPORT = config.getSound("sound-teleport");
		API.SOUND_ERROR = config.getSound("sound-error");
		API.SOUND_SUCCESS = config.getSound("sound-success");
		API.CHAT_SPIGOT = config.getBoolean("chat-clicable");
		for (ConfigSection sec : config.getValues("chats")) {
			ChatChannel chat = (ChatChannel) sec.getValue();
			API.CHATS.put(chat.getName(), chat);
		}

		API.CHAT = API.CHATS.getOrDefault(config.getString("chat-default"),
				local);
		if (config.getBoolean("auto-rejoin")) {
			for (Player p : API.getPlayers()) {
				API.callEvent(new PlayerJoinEvent(p, null));
			}
		}
		config.saveConfig();
		ExtraAPI.consoleMessage("§bEduardAPI §arecarregando!");
	}
	@Override
	public void onDisable() {
		API.saveMaps();
		ExtraAPI.consoleMessage("§bEduardAPI §aMapas salvados!");
		ExtraAPI.consoleMessage("§bEduardAPI §cdesativado!");
	}
	@EventHandler
	public void event(PluginEnableEvent e) {
		for (Config config : Config.CONFIGS) {
			if (e.getPlugin().equals(config.getPlugin())) {
				config.reloadConfig();
			}
		}
	}
	@EventHandler
	public void event(PluginDisableEvent e) {
		for (Config config : Config.CONFIGS) {
			if (config.isAutoSave()) {
				config.saveConfig();
			}
		}
	}
	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (API.CUSTOM_CHAT) {

			for (ChatChannel channel : API.CHATS.values()) {
				if (ExtraAPI.commandEquals(e.getMessage(),
						"/" + channel.getName())) {
					e.setCancelled(true);
					sendMessage(p, e.getMessage().replaceFirst("/", ""),
							channel);
					break;
				}
				if (ExtraAPI.commandEquals(e.getMessage(),
						"/" + channel.getCommand())) {
					e.setCancelled(true);
					sendMessage(p, e.getMessage().replaceFirst("/", ""),
							channel);
					break;
				}
			}

		}

		// if (e.getMessage().startsWith("/cu")) {
		// ForceVillagerTrade trader = new ForceVillagerTrade("teste");
		// trader.addTrande(new ItemStack(Material.DIAMOND),
		// new ItemStack(Material.EMERALD, 10));
		// trader.openTrande(p);
		// }
	}

	public void updateTargets() {
		for (Player p : API.getPlayers()) {

			PlayerTargetEvent event = new PlayerTargetEvent(p,
					GameAPI.getTarget(p,
							GameAPI.getPlayerAtRange(p.getLocation(), 100)));
			API.callEvent(event);

		}
	}

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		if (API.CUSTOM_CHAT) {
			Player player = e.getPlayer();
			e.setCancelled(true);
			sendMessage(player, e.getMessage(), API.CHAT);
		}

	}

	@EventHandler
	public void entrar(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (config.getBoolean("save-players")) {
			saveObject("Players/" + p.getName() + " " + p.getUniqueId(), p);
		}
		if (config.getBoolean("custom-join-message")) {
			e.setJoinMessage(API.ON_JOIN.replace("$player", p.getName()));
		}

		if (API.NO_JOIN_MESSAGE) {
			e.setJoinMessage(null);
			return;
		}

	}
	@EventHandler
	public void morrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (API.AUTO_RESPAWN) {
			if (p.hasPermission("eduardapi.autorespawn")) {
				API.TIME.delay(1L, new Runnable() {

					@Override
					public void run() {
						if (p.isDead()) {
							p.setFireTicks(0);
							try {
								RexAPI.makeRespawn(p);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});
			}

		}
		if (API.NO_DEATH_MESSAGE) {
			e.setDeathMessage("");
		}
	}
	@EventHandler
	public void sair(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		if (config.getBoolean("custom-quit-message"))
			e.setQuitMessage(API.ON_QUIT.replace("$player", p.getName()));
		if (API.NO_QUIT_MESSAGE) {
			e.setQuitMessage("");
		}

	}

	public static void saveEnum(Class<?> value) {
		saveEnum(value, false);
	}
	public static void saveClassLikeEnum(Class<?> value) {
		try {
			Config config = new Config(
					"DataBase/" + value.getSimpleName() + ".yml");
			for (Field field : value.getFields()) {
				if (field.getType().equals(value)) {
					Object obj = field.get(value);
					ConfigSection section = config.getSection(field.getName());
					for (Method method : obj.getClass().getDeclaredMethods()) {
						String name = method.getName();
						if ((method.getParameterCount() == 0)
								&& name.startsWith("get")
										| name.startsWith("is")
										| name.startsWith("can")) {
							method.setAccessible(true);
							Object test = method.invoke(obj);
							if (test instanceof Class)
								continue;
							section.add(method.getName(), test);
						}
					}
				}
			}
			config.saveConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void saveObject(String local, Object value) {
		try {
			Config config = new Config("DataBase/" + local + ".yml");
			ConfigSection section = config.getConfig();
			for (Method method : value.getClass().getDeclaredMethods()) {
				String name = method.getName();
				if ((method.getParameterCount() == 0) && name.startsWith("get")
						| name.startsWith("is") | name.startsWith("can")) {
					method.setAccessible(true);
					Object test = method.invoke(value);
					if (test == null)
						continue;
					if (test instanceof Class)
						continue;
					section.set(method.getName(), test);
				}
			}
			config.saveConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void saveEnum(Class<?> value, boolean perConfig) {
		try {
			if (perConfig) {

				for (Object part : value.getEnumConstants()) {

					try {
						Enum<?> obj = (Enum<?>) part;
						Config config = new Config(
								"DataBase/" + value.getSimpleName() + "/"
										+ obj.name() + ".yml");
						ConfigSection section = config.getConfig();
						section.set("number", obj.ordinal());
						for (Method method : obj.getClass()
								.getDeclaredMethods()) {
							String name = method.getName();
							if ((method.getParameterCount() == 0)
									&& name.startsWith("get")
											| name.startsWith("is")
											| name.startsWith("can")) {
								method.setAccessible(true);
								Object test = method.invoke(obj);
								if (test instanceof Class)
									continue;
								section.add(method.getName(), test);
							}
						}
						config.saveConfig();
					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}
				}

			} else {
				Config config = new Config(
						"DataBase/" + value.getSimpleName() + ".yml");
				boolean used = false;
				for (Object part : value.getEnumConstants()) {
					try {
						Enum<?> obj = (Enum<?>) part;
						ConfigSection section = config.add(obj.name(),
								obj.ordinal());

						for (Method method : obj.getClass()
								.getDeclaredMethods()) {
							String name = method.getName();
							if ((method.getParameterCount() == 0)
									&& name.startsWith("get")
											| name.startsWith("is")
											| name.startsWith("can")) {
								try {
									method.setAccessible(true);
									Object test = method.invoke(obj);
									if (test == null)
										continue;
									if (test instanceof Class)
										continue;
									section.add(method.getName(), test);
									used = true;
								} catch (Exception ex) {
									ExtraAPI.consoleMessage(
											"§bDataBase §fO metodo §c" + name
													+ "§f causou erro!");
									ex.printStackTrace();
									continue;
								}

							}
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}
				}
				if (!used)
					config.setIndent(0);
				config.saveConfig();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void saveObjects() {

		saveEnum(DamageCause.class);
		saveEnum(Material.class);
		saveEnum(Effect.class);
		saveEnum(EntityType.class);
		saveEnum(TargetReason.class);
		saveEnum(Sound.class);
		saveEnum(EntityEffect.class);
		saveEnum(Environment.class);
		saveEnum(PotionType.class);
		saveClassLikeEnum(PotionEffectType.class);
		if (getConfigs().getBoolean("save-worlds")) {
			for (World world : Bukkit.getWorlds()) {
				saveObject("Worlds/" + world.getName(), world);
			}
		}
		if (getConfigs().getBoolean("save-players")) {
			for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
				String name = player.getName();
				saveObject("Players/" + name + " " + player.getUniqueId(),
						player);
			}
		}
		saveObject("Server", Bukkit.getServer());
	}

	@Override
	protected void configs() {

	}

	@Override
	protected void commands() {

	}

	@Override
	protected void events() {
		// TODO Auto-generated method stub

	}
}
