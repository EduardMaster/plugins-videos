package net.eduard.api.server.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.Plugin;

import net.eduard.api.internet.fanciful.FancyMessage;
import net.eduard.api.setup.Mine;
import net.eduard.api.setup.StorageAPI.Storable;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * API de manipulação de Cores e CHAT do Minecraft
 * 
 * @author Eduard
 *
 */
public final class ChatAPI {

	public static void enable(Plugin plugin) {
		disable();
		Bukkit.getPluginManager().registerEvents(listener, plugin);
		listener.setRegistred(true);
		listener.setPlugin(plugin);
	}
	public static void disable() {
		HandlerList.unregisterAll(listener);
		listener.setRegistred(false);
		listener.setPlugin(null);
	}
	public static boolean isActive() {
		return listener.isRegistred();
	}

	private static boolean enabled = true;

	public static boolean isEnabled() {
		return enabled;
	}
	public static void setEnabled(boolean enabled) {
		ChatAPI.enabled = enabled;
	}

	private static String messageChatDisabled = "&cChat desabilitado tempariamente!";
	private static String messageChatPermission = "§cVocê não tem permissão para falar neste Chat!";
	private static ChatChannel chatDefault = new ChatChannel("local", "",
			"§e§l(L) ", "", "l");
	private static ChatType chatType = ChatType.SPIGOT;
	public static enum ChatType {
		SPIGOT, BUKKIT, FANCYFUL,
	}
	private static Map<String, ChatChannel> channels = new HashMap<>();

	public static void register(ChatChannel channel) {
		channels.put(channel.getName().toLowerCase(), channel);
	}
	public static void unregister(ChatChannel channel) {
		channels.remove(channel.getName().toLowerCase());
	}
	public static class ChatMessageListener implements Listener {
		private Plugin plugin;
		private boolean registred;

		public boolean isRegistred() {
			return registred;
		}
		public void setRegistred(boolean registred) {
			this.registred = registred;
		}
		@EventHandler(priority = EventPriority.HIGHEST)
		public void onChat(AsyncPlayerChatEvent event) {
			event.setCancelled(true);
			chatDefault.chat(event.getPlayer(), event.getMessage());
		}
		@EventHandler
		public void onCommand(PlayerCommandPreprocessEvent event) {
			String msg = event.getMessage();
			String cmd = Mine.getCmd(msg);
			for (ChatChannel channel : channels.values()) {
				if (Mine.startWith(cmd, "/" + channel.getName())) {
					channel.chat(event.getPlayer(), msg.replaceFirst(cmd, ""));
					event.setCancelled(true);
					break;
				}
				if (Mine.startWith(cmd, "/" + channel.getCommand())) {
					channel.chat(event.getPlayer(), msg.replaceFirst(cmd, ""));
					event.setCancelled(true);
					break;
				}
			}
		}
		public Plugin getPlugin() {
			return plugin;
		}
		public void setPlugin(Plugin plugin) {
			this.plugin = plugin;
		}
	}
	private static ChatMessageListener listener = new ChatMessageListener();
	public static class ChatMessageEvent extends PlayerEvent
			implements
				Cancellable {
		@Override
		public HandlerList getHandlers() {
			return handlers;
		}
		public static HandlerList getHandlerList() {
			return handlers;
		}
		private static final HandlerList handlers = new HandlerList();
		private Map<String, String> tags = new HashMap<>();
		private String message = "";
		private String format;
		private String onClickCommand;
		private List<Player> playersInChannel = new ArrayList<>();
		private List<String> onHoverText;
		private boolean cancelled;
		private ChatChannel channel;

		public Map<String, String> getTags() {
			return tags;
		}

		public void setTagValue(String tag, String value) {
			tags.put(tag, value);
		}
		public String getTagValue(String tag) {
			return tags.get(tag);
		}

		public ChatMessageEvent(Player player, ChatChannel channel,
				String message) {
			super(player);
			this.message = message;
			if (player.hasPermission("chat.color"))
				this.message = ChatColor.translateAlternateColorCodes('&',
						message);
			setFormat(channel.getFormat());
			setPlayersInChannel(channel.getPlayers(player));
			resetTags();
		}

		public void resetTags() {
			for (int i = 0; i < format.length(); i++) {
				if (format.charAt(i) == '{') {
					String tag = format.substring(i + 1).split("}")[0]
							.toLowerCase();
					if ((!tag.equals("msg")) && (!this.tags.containsKey(tag)))
						this.tags.put(tag, "");
				}
			}
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public boolean isCancelled() {
			return cancelled;
		}

		@Override
		public void setCancelled(boolean cancel) {
			cancelled = cancel;

		}

		public ChatChannel getChannel() {
			return channel;
		}

		public void setChannel(ChatChannel channel) {
			this.channel = channel;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public void setTags(Map<String, String> tags) {
			this.tags = tags;
		}
		public String getOnClickCommand() {
			return onClickCommand;
		}
		public void setOnClickCommand(String onClickCommand) {
			this.onClickCommand = onClickCommand;
		}
		public List<String> getOnHoverText() {
			return onHoverText;
		}
		public void setOnHoverText(List<String> onHoverText) {
			this.onHoverText = onHoverText;
		}
		public List<Player> getPlayersInChannel() {
			return playersInChannel;
		}
		public void setPlayersInChannel(List<Player> playersInChannel) {
			this.playersInChannel = playersInChannel;
		}

	}

	/**
	 * Canal de Chat
	 * 
	 * @author Eduard-PC
	 *
	 */
	public static class ChatChannel implements Storable {

		private String name;
		private String format;
		private String prefix = "";
		private String suffix = "";
		private int distance = 100;
		private boolean global = true;
		private String permission;
		private String command;
		public ChatChannel() {
		}

		public ChatChannel(String name, String format, String prefix,
				String suffix, String command) {
			this.format = format;
			this.name = name;
			this.prefix = prefix;
			this.suffix = suffix;
			this.command = command;
			this.permission = "chat." + name;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCommand() {
			return command;
		}
		public void setCommand(String command) {
			this.command = command;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public void chat(Player player, String message) {
			if (enabled) {
				if (!player.hasPermission(permission)) {
					player.sendMessage(message);
					return;
				}
				ChatMessageEvent event = new ChatMessageEvent(player, this,
						message);
				Mine.callEvent(event);
				if (event.isCancelled())
					return;
				List<Player> players = event.getPlayersInChannel();
				String msg = event.getMessage();
				String form = event.getFormat();
				form = form.replace("{msg}", msg);
				for (Entry<String, String> entry : event.getTags().entrySet()) {
					form = form.replace(
							"{" + entry.getKey().toLowerCase() + "}",
							entry.getValue());
				}
				if (chatType == ChatType.BUKKIT) {
					for (Player p : players) {
						p.sendMessage(form);
					}
				} else if (chatType == ChatType.SPIGOT) {

					TextComponent text = new TextComponent(form);
					if (event.getOnClickCommand() != null) {
						ClickEvent clickEvent = new ClickEvent(
								ClickEvent.Action.RUN_COMMAND,
								event.getOnClickCommand());
						text.setClickEvent(clickEvent);
					}
					if (!event.getOnHoverText().isEmpty()) {

						ComponentBuilder textBuilder = new ComponentBuilder("");
						for (String line : event.getOnHoverText()) {
							textBuilder.append(line + "\n");
						}

						HoverEvent hoverEvent = new HoverEvent(
								HoverEvent.Action.SHOW_TEXT,
								textBuilder.create());
						text.setHoverEvent(hoverEvent);
					}
					for (Player p : Mine.getPlayers()) {
						p.spigot().sendMessage(text);
					}

				} else if (chatType == ChatType.FANCYFUL) {

					FancyMessage text = new FancyMessage(form);
					if (event.getOnClickCommand() != null) {
						text.command(event.getOnClickCommand());
					}
					if (!event.getOnHoverText().isEmpty()) {

						text.tooltip(event.getOnHoverText());

					}
					text.send(players);

				}
			} else {
				player.sendMessage(messageChatDisabled);
			}
		}
		public List<Player> getPlayers(Player player) {
			List<Player> list = new ArrayList<>();
			for (Player p : Mine.getPlayers()) {
				if (!global) {
					if (!p.getWorld().equals(player.getWorld())) {
						continue;
					}
					if (distance > 0) {
						double newDistance = p.getLocation()
								.distance(player.getLocation());
						if (newDistance > distance)
							continue;
					}
				}
				list.add(p);
			}
			return list;
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

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		public boolean isGlobal() {
			return global;
		}

		public void setGlobal(boolean global) {
			this.global = global;
		}

		public String getPermission() {
			return permission;
		}

		public void setPermission(String permission) {
			this.permission = permission;
		}

	}

	
	/**
	 * Pega um Coração vermelho
	 * 
	 * @return Simbolo Coração
	 */
	public static String getRedHeart() {
		return ChatColor.RED + getHeart();
	}
	/**
	 * Pega um Coração normal
	 * 
	 * @return Simbolo Coração
	 */
	public static String getHeart() {
		return "♥";
	}

	public static String getArrow() {
		return "➵";
	}

	public static String getArrowRight() {
		return "››";
	}

	public static String getArrowLeft() {
		return "‹‹";
	}

	public static String getSquared() {
		return "❑";
	}

	public static String getInterrogation() {
		return "➁";
	}

	public static String getALlSimbols() {
		return "❤❥✔✖✗✘❂⋆✢✣✤✥✦✩✪✫✬✭✵✴✳✲✱★✰✯✮✶✷✸✹✺✻✼❄❅✽✡☆❋❊❉❈❇❆✾✿❀❁❃✌♼♽✂➣➢⬇➟⬆⬅➡✈✄➤➥➦➧➨➚➘➙➛➶➵➴➳➲➸➞➝➜➷➹➹➺➻➼➽Ⓜ⬛⬜ℹ☕▌▄▆▜▀▛█";
	}

	public static String getAllSimbols2() {
		return "™⚑⚐☃⚠⚔⚖⚒⚙⚜⚀⚁⚂⚃⚄⚅⚊⚋⚌⚍⚏⚎☰☱☲☳☴☵☶☷⚆⚇⚈⚉♿♩♪♫♬♭♮♯♠♡♢♗♖♕♔♧♛♦♥♤♣♘♙♚♛♜♝♞♟⚪➃➂➁➀➌➋➊➉➈➇➆➅➄☣☮☯⚫➌➋➊➉➈➇➆➅➄➍➎➏➐➑➒➓ⓐⓑⓚ";
	}

	public static String getAllSimbols3() {
		return "웃유♋♀♂❣¿⌚☑▲☠☢☿Ⓐ✍☤✉☒▼⌘⌛®©✎♒☁☼ツღ¡Σ☭✞℃℉ϟ☂¢£⌨⚛⌇☹☻☺☪½∞✆☎⌥⇧↩←→↑↓⚣⚢⌲♺☟☝☞☜➫❑❒◈◐◑«»‹›×±※⁂‽¶—⁄—–≈÷≠π†‡‡¥€‰●•·";
	}
	public static ChatChannel getChatDefault() {
		return chatDefault;
	}
	public static void setChatDefault(ChatChannel chatDefault) {
		ChatAPI.chatDefault = chatDefault;
	}
	public static Map<String, ChatChannel> getChannels() {
		return channels;
	}
	public static void setChannels(Map<String, ChatChannel> channels) {
		ChatAPI.channels = channels;
	}
	public static ChatType getChatType() {
		return chatType;
	}
	public static void setChatType(ChatType chatType) {
		ChatAPI.chatType = chatType;
	}
	public static String getMessageChatPermission() {
		return messageChatPermission;
	}
	public static void setMessageChatPermission(String messageChatPermission) {
		ChatAPI.messageChatPermission = messageChatPermission;
	}
	public static String getMessageChatDisabled() {
		return messageChatDisabled;
	}
	public static void setMessageChatDisabled(String messageChatDisabled) {
		ChatAPI.messageChatDisabled = messageChatDisabled;
	}
	public static ChatColor SUCCESS = ChatColor.GREEN;
	public static ChatColor SUCCESS_ARGUMENT = ChatColor.DARK_GREEN;
	public static ChatColor ERROR = ChatColor.RED;
	public static ChatColor ERROR_ARGUMENT = ChatColor.DARK_RED;
	public static ChatColor MESSAGE = ChatColor.GOLD;
	public static ChatColor MESSAGE_ARGUMENT = ChatColor.YELLOW;
	public static ChatColor CHAT_CLEAR = ChatColor.WHITE;
	public static ChatColor CHAT_NORMAL = ChatColor.GRAY;
	public static ChatColor GUI_TITLE = ChatColor.BLACK;
	public static ChatColor GUI_TEXT = ChatColor.DARK_GRAY;
	public static ChatColor CONFIG = ChatColor.AQUA;
	public static ChatColor CONFIG_ARGUMENT = ChatColor.DARK_AQUA;
	public static ChatColor ITEM_TITLE = ChatColor.LIGHT_PURPLE;
	public static ChatColor ITEM_TEXT = ChatColor.DARK_PURPLE;
	public static ChatColor TITLE = ChatColor.DARK_BLUE;
	public static ChatColor TEXT = ChatColor.BLUE;

}
