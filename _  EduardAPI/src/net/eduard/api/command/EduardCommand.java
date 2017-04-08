
package net.eduard.api.command;

import net.eduard.api.manager.Commands;

public class EduardCommand extends Commands {

	public EduardCommand() {
		super("eduard");
		addSub(new EduardCopyCommand());
		addSub(new EduardPasteCommand());
		addSub(new EduardPos1Command());
		addSub(new EduardPos2Command());
		addSub(new EduardLoadCommand());
		addSub(new EduardSaveCommand());
	}

}
