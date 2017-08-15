package net.eduard.api.factions;

import java.io.Serializable;

public enum FactionRank implements Serializable {

	NEWBIE("+"), RECRUIT("++"), MEMBER("*"), CAPTAIN("**"), LEADER("***");

	private FactionRank(String tag) {
		this.tag = tag;
	}

	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	public FactionRank next() {

		if (ordinal() + 1 == values().length) {
			return this;
		}

		return values()[ordinal() + 1];
	}

	public FactionRank previous() {

		if (ordinal() == 0) {
			return this;
		}

		return values()[ordinal() - 1];
	}
}
