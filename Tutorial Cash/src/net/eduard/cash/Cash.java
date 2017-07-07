package net.eduard.cash;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.cash.command.CashCommand;
import net.eduard.cash.event.CashEvent;

public class Cash extends JavaPlugin{

	@Override
	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(new CashEvent(), this);
		getCommand("cash").setExecutor(new CashCommand());
		
	}
}
