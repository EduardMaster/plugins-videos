package net.eduard.curso.faction;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;

import com.sun.management.jmx.Trace;

public class FactionManager {

	private ArrayList<FactionUser> users = new ArrayList<>();

	public ArrayList<FactionUser> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<FactionUser> users) {
		this.users = users;
	}



	public void getUser(Player p) {

	}

}
