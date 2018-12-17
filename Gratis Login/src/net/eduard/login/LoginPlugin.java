package net.eduard.login;

import net.eduard.api.lib.storage.StorageAPI;
import net.eduard.api.server.EduardPlugin;
import net.eduard.login.events.LoginEvents;
import net.eduard.login.manager.LoginAPI;
import net.eduard.login.manager.PlayerAccount;
import net.eduard.login.manager.PlayerAccountManager;

public class LoginPlugin extends EduardPlugin {
	private static LoginPlugin plugin;

	private PlayerAccountManager manager;

	public static LoginPlugin getInstance() {
		return plugin;
	}

	@Override
	public void onEnable() {
		plugin = this;
		new LoginEvents().register(this);
		StorageAPI.register(PlayerAccount.class);
		StorageAPI.register(PlayerAccountManager.class);
		LoginAPI.reload();

	}

	@Override
	public void onDisable() {
		save();
	}

	public void reload() {
		config.reloadConfig();
		storage.reloadConfig();
		if (storage.contains("login")) {
			manager = (PlayerAccountManager) storage.get("login");
		} else {
			manager = new PlayerAccountManager();
			save();
		}
	}

	public void save() {
		storage.set("login", manager);
		storage.saveConfig();
	}

	public PlayerAccountManager getManager() {
		return manager;
	}

	public void setManager(PlayerAccountManager manager) {
		this.manager = manager;
	}

}
