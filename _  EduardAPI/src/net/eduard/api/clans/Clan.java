package net.eduard.api.clans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Clan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String prefix;
	private String suffix;
	private double power;
	private ClanMember leader;
	private List<Clan> allies = new ArrayList<>();
	private List<Clan> rivals = new ArrayList<>();
	private List<ClanMember> members = new ArrayList<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public ClanMember getLeader() {
		return leader;
	}
	public void setLeader(ClanMember leader) {
		this.leader = leader;
	}
	public List<Clan> getAllies() {
		return allies;
	}
	public void setAllies(List<Clan> allies) {
		this.allies = allies;
	}
	public List<Clan> getRivals() {
		return rivals;
	}
	public void setRivals(List<Clan> rivals) {
		this.rivals = rivals;
	}
	public List<ClanMember> getMembers() {
		return members;
	}
	public void setMembers(List<ClanMember> members) {
		this.members = members;
	}

}
