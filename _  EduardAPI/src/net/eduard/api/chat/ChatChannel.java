package net.eduard.api.chat;

public class ChatChannel {
	
	public static ChatChannel LOCAL = new ChatChannel("local","§e(L)");

	private String name;
	private String tag = "";
	
	public ChatChannel(String name, String tag) {
		super();
		this.name = name;
		this.tag = tag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

}
