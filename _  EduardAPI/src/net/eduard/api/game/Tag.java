package net.eduard.api.game;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.Save;

public class Tag implements Save {

	private String prefix, suffix, name;
	
	private int rank;

	public Tag(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public Tag setPrefix(String prefix) {
		this.prefix = prefix;
		return this;

	}

	public String getSuffix() {
		return suffix;
	}

	public Tag setSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

	public String getName() {
		return name;
	}

	public Tag setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Object get(ConfigSection section) {
		return null;
	}

	@Override
	public void save(ConfigSection section, Object value) {

	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
