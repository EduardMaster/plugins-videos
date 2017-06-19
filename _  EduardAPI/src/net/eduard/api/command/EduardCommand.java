
package net.eduard.api.command;

import net.eduard.api.manager.CMD;

public class EduardCommand extends CMD {

	public EduardCommand() {
		super("eduard");
		register(new EduardCopyCommand());
		register(new EduardPasteCommand());
		register(new EduardPos1Command());
		register(new EduardPos2Command());
		register(new EduardLoadCommand());
		register(new EduardSaveCommand());
		register(new EduardWorldCommand());
	}

}
