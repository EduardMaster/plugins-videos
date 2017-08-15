package net.eduard.api.game;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.setup.ExtraAPI;
import net.eduard.api.util.Save;

public class Vip implements Save{

	private int days;

	private long vipStart;

	private String name;

	public boolean hasVip() {
		return days > 0;
	}
	public void addDays(int days) {
		this.days += days;
	}
	public void removeDays(int days) {
		this.days -= days;
	}
	public void removeVip() {
		this.days = 0;
	}
	public boolean isVip() {
		return ExtraAPI.inCooldown(vipStart, days * ExtraAPI.DAY_IN_SECONDS);
	}
	public long getTimeLeft() {
		return ExtraAPI.getCooldown(vipStart, days * ExtraAPI.DAY_IN_SECONDS);
	}
	public void setVipEternal() {
		this.days = -1;
	}
	public boolean isVipEternal() {
		return days == -1;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getVipStart() {
		return vipStart;
	}
	public void setVipStart(long vipStart) {
		this.vipStart = vipStart;
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
