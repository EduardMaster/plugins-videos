package net.eduard.api.game;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.Save;

public class ChatChannel implements Save{
	
	private String name;
	private String format;
	private String prefix = "";
	private String suffix = "";
	private String command;
	public ChatChannel() {
	}
	
	public ChatChannel(String name,String format, String prefix, String suffix,
			String command) {
		this.format = format;
		this.name = name;
		this.prefix = prefix;
		this.suffix = suffix;
		this.command = command;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	@Override
	public Object get(ConfigSection section) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void save(ConfigSection section, Object value) {
		// TODO Auto-generated method stub
		
	}

}
