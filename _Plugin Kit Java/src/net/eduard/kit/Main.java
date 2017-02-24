
package net.eduard.kit;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.kit.command.KitCommand;
import net.eduard.kit.command.KitsCommand;
import net.eduard.kit.command.SetKitCommand;

public class Main extends JavaPlugin {

	public static Main plugin;

	public void onEnable() {

		plugin = this;
		config = new Config(this);

		config.add("Kits", "&6Kits disponiveis: &e$kits");
		config.add("SetKit", "&6Voce criou o kit: &e$kit");
		config.add("NoKitPerm", "&cVoce nao pode pegar o kit: &f$kit");
		config.add("Kit", "&6Voce pegou o kit: &e$kit");
		config.add("NoKit", "&cNao existe esse kit: &e$kit");
		config.add("KitInCooldown", "&cVoce precisa esperar mais $seconds segundos!");
		config.add("ClearInventory", false);
		config.saveConfig();
		API.command("kit", new KitCommand());
		API.command("kits", new KitsCommand());
		API.command("setkit", new SetKitCommand());
	}

	public static Config config;
	public static final String TAG = "Kits/";

	public static Config getKit(String name) {

		if (name.isEmpty())
			return config.createConfig(TAG+"Kits/");
		return config.createConfig(TAG+"Kits/" + name + ".yml");
	}
}
