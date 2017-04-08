
package net.eduard.api.command;

import net.eduard.api.manager.Commands;

public class WhiteListCommand extends Commands {
	public WhiteListCommand() {
		super("whitelist");
		addSub(new WhiteListHelpCommand());
		addSub(new WhiteListAddCommand());
		addSub(new WhiteListRemoveCommand());
		addSub(new WhiteListOnCommand());
		addSub(new WhiteListOffCommand());
		addSub(new WhiteListListommand());

	}

}
