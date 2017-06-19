
package mega.plugins.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import mega.plugins.kitpvp.command.SetSpawnCommand;
import mega.plugins.kitpvp.command.SpawnCommand;
import mega.plugins.kitpvp.event.GameListener;
import mega.plugins.kitpvp.event.MenuListener;
import mega.plugins.kitpvp.event.PlayerListener;
import mega.plugins.kitpvp.kits.KitPvp;
import mega.plugins.kitpvp.kits.Poseidon;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getConsoleSender().sendMessage("");
		getServer().getConsoleSender().sendMessage("    §3(KitPvP)");
		getServer().getConsoleSender().sendMessage("     §bAtivando...");
		getServer().getConsoleSender()
				.sendMessage(" §3Criado por KyzeYT e Eduard");
		getServer().getConsoleSender().sendMessage("     §bATIVADO!");
		getServer().getConsoleSender().sendMessage("    §3(KitPvP)");
		getServer().getConsoleSender().sendMessage("");
		Bukkit.getPluginManager().registerEvents(new MenuListener(),
				this);
		Bukkit.getPluginManager()
				.registerEvents(new PlayerListener(), this);

		Bukkit.getPluginManager().registerEvents(new GameListener(),
				this);
		Bukkit.getPluginManager().registerEvents(new KitPvp(),
				this);
		Bukkit.getPluginManager().registerEvents(new Poseidon(),
				this);
		getCommand("setspawn").setExecutor(new SetSpawnCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage("");
		getServer().getConsoleSender().sendMessage("    §4(KitPvP)");
		getServer().getConsoleSender().sendMessage("     §cDesativando...");
		getServer().getConsoleSender()
				.sendMessage(" §4Criado por KyzeYT e Eduard");
		getServer().getConsoleSender().sendMessage("     §cDESATIVADO!");
		getServer().getConsoleSender().sendMessage("    §4(KitPvP)");
		getServer().getConsoleSender().sendMessage("");
	}

	public static Main getInstance() {
		return Main.getPlugin(Main.class);
	}
}
