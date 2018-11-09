
package net.eduard.template;

import net.eduard.api.server.EduardPlugin;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvents;

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
	
	}

}
