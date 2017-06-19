
package net.eduard.template;

import net.eduard.api.manager.EduardPlugin;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvent;

public class Main extends EduardPlugin {
	private static Main plugin;
	public static Main getPlugin() {
		return plugin;
	}

	public void commands() {
	}
	public void events() {

	}
	public void init() {
		new TemplateEvent().register(this);
		new TemplateCommand().register();
	}

	public void onDisable() {
	}

}
