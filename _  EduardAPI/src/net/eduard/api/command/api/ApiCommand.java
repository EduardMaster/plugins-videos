
package net.eduard.api.command.api;

import net.eduard.api.manager.CommandManager;

public class ApiCommand extends CommandManager {

	public ApiCommand() {
		super("api");
		register(new ApiReloadCommand());
	}
	

}
