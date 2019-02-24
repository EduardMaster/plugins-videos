
package net.eduard.vault;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.click.PlayerEffect;
import net.eduard.api.lib.manager.EffectManager;
import net.eduard.api.lib.menu.Menu;
import net.eduard.api.lib.menu.MenuButton;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin {

	public static Main instance;

	public static Permission permission = null;

	public static Economy economy = null;

	public static Chat chat = null;

	@Override
	public void onDisable() {

		Menu loja = new Menu("§8Loja", 3);
		loja.setOpenWithItem(new ItemStack(Material.BONE));
		MenuButton botao = new MenuButton(new ItemStack(Material.DIAMOND));
		botao.setEffects(new EffectManager());
		botao.getEffects().setEffect(new PlayerEffect() {

			@SuppressWarnings("deprecation")
			@Override
			public void effect(Player p) {

				if (p.hasPermission("loja.teste")) {
					Main.economy.depositPlayer(p.getName(), 200);
					p.sendMessage("§bVoce vendeu o diamante!");
					Main.permission.playerRemove(p, "loja.teste");

				} else {
					// Ta diferente por causa da API do Vault atualizada
					// '-'
					Main.economy.withdrawPlayer(p.getName(), 200);
					p.sendMessage("§aVoce comprou o diamante");
					Main.permission.playerAdd(p, "loja.teste");
				}
				p.setNoDamageTicks(20 * 10);
			}
		});
		loja.addButton(botao);
		loja.register(this);
	}

	@Override
	public void onEnable() {

		Main.instance = this;
		setupEconomy();
		setupPermissions();
		setupChat();
		Player p = null;
		permission.playerAdd(p, "permissao.teste");
	}

	private boolean setupChat() {

		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return chat != null;
	}

	private boolean setupEconomy() {

		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return economy != null;
	}

	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return permission != null;
	}
}
