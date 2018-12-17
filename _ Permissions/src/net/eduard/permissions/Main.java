
package net.eduard.permissions;

import net.eduard.api.server.EduardPlugin;
import net.eduard.permissions.command.TemplateCommand;
import net.eduard.permissions.event.TemplateEvents;

public class Main extends EduardPlugin  {
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}

	@Override
	public void onEnable() {
		plugin = this;
		reload();
		new TemplateEvents().register(this);
		new TemplateCommand().register();
	}
	public void save() {
		
	}
	public void reload() {
		
	}
	@Override
	public void onDisable() {
		save();
	}

}
