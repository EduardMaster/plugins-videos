package net.eduard.api.config;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.util.SimpleEffect;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public final class VaultAPI {

	private static Permission permission = null;

	private static Economy economy = null;

	private static Chat chat = null;

	public static Permission getPermission() {
		return permission;
	}

	public static Chat getChat() {
		return chat;
	}

	public static Economy getEconomy() {
		return economy;
	}

	static {
		if (hasVault()) {
			setupVault();
		}
		new Game(60).timer(new SimpleEffect() {
			
			public void effect() {
				if (hasVault()) {
					setupVault();
				}
			}
		});
		
	}

	public static boolean hasVault() {
		return API.hasPlugin("Vault");
	}
	public static boolean hasEconomy() {
		return economy!=null;
	}
	public static boolean hasChat() {
		return chat!=null;
	}
	public static boolean hasPermission() {
		return permission!=null;
	}

	private static boolean setupChat() {

		RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	private static boolean setupEconomy() {

		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	private static boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private static void setupVault() {

		setupEconomy();
		setupChat();
		setupPermissions();
	}
}
