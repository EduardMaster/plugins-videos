package net.eduard.api.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
public class MySQLAccess {
	private String url_base;
	private String host;
	private String name;
	private String username;
	private String password;
	private String table;
	private Connection connection;

	public MySQLAccess(String url_base, String host, String name, String username,
			String password, String table) {
		this.url_base = url_base;
		this.host = host;
		this.name = name;
		this.username = username;
		this.password = password;
		this.table = table;
	}

	public void connection() {
		if (!isConnected())
			try {
				this.connection = DriverManager.getConnection(
						this.url_base + this.host + "/" + this.name,
						this.username, this.password);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void deconnection() {
		if (isConnected())
			try {
				this.connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private boolean isConnected() {
		try {
			if ((this.connection == null) || (this.connection.isClosed())
					|| (!this.connection.isValid(5))) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private Connection getConnection() {
		return this.connection;
	}

	public void createAccount(Player p, String rank) {
		try {
			PreparedStatement sts = getConnection().prepareStatement(
					"SELECT coins FROM " + this.table + " WHERE uuid = ?");
			sts.setString(1, p.getUniqueId().toString());
			ResultSet rs = sts.executeQuery();
			if (!rs.next()) {
				sts.close();

				PreparedStatement sts2 = getConnection()
						.prepareStatement("INSERT INTO " + this.table
								+ "(uuid, kills, deaths, wins, coins, rank) VALUES (?, ?, ?, ?, ?, ?)");
				sts2.setString(1, p.getUniqueId().toString());
				sts2.setInt(2, 0);
				sts2.setInt(3, 0);
				sts2.setInt(4, 0);
				sts2.setInt(5, 0);
				sts2.setString(6, rank);
				sts2.executeUpdate();
				sts2.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addKills(String uuid, int kills) {
		try {
			PreparedStatement sts = getConnection().prepareStatement("UPDATE "
					+ this.table + " SET kills = kills + ? WHERE uuid = ?");
			sts.setInt(1, kills);
			sts.setString(2, uuid);
			sts.executeUpdate();
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addDeaths(String uuid, int deaths) {
		try {
			PreparedStatement sts = getConnection().prepareStatement("UPDATE "
					+ this.table + " SET deaths = deaths + ? WHERE uuid = ?");
			sts.setInt(1, deaths);
			sts.setString(2, uuid);
			sts.executeUpdate();
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addWins(String uuid, int wins) {
		try {
			PreparedStatement sts = getConnection().prepareStatement("UPDATE "
					+ this.table + " SET wins = wins + ? WHERE uuid = ?");
			sts.setInt(1, wins);
			sts.setString(2, uuid);
			sts.executeUpdate();
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addCoins(String uuid, int coins) {
		try {
			PreparedStatement sts = getConnection().prepareStatement("UPDATE "
					+ this.table + " SET coins = coins + ? WHERE uuid = ?");
			sts.setInt(1, coins);
			sts.setString(2, uuid);
			sts.executeUpdate();
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeCoins(String uuid, int coins) {
		try {
			PreparedStatement sts = getConnection().prepareStatement("UPDATE "
					+ this.table + " SET coins = coins - ? WHERE uuid = ?");
			sts.setInt(1, coins);
			sts.setString(2, uuid);
			sts.executeUpdate();
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setRank(String uuid, String rank) {
		try {
			PreparedStatement sts = getConnection().prepareStatement(
					"UPDATE " + this.table + " SET rank = ? WHERE uuid = ?");
			sts.setString(1, rank);
			sts.setString(2, uuid);
			sts.executeUpdate();
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer getKills(String uuid) {
		int kills = 0;
		try {
			PreparedStatement sts = getConnection().prepareStatement(
					"SELECT kills FROM " + this.table + " WHERE uuid = ?");
			sts.setString(1, uuid);
			ResultSet rs = sts.executeQuery();
			if (!rs.next()) {
				return Integer.valueOf(kills);
			}
			kills = rs.getInt("kills");
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Integer.valueOf(kills);
	}

	public Integer getDeaths(String uuid) {
		int deaths = 0;
		try {
			PreparedStatement sts = getConnection().prepareStatement(
					"SELECT deaths FROM " + this.table + " WHERE uuid = ?");
			sts.setString(1, uuid);
			ResultSet rs = sts.executeQuery();
			if (!rs.next()) {
				return Integer.valueOf(deaths);
			}
			deaths = rs.getInt("deaths");
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Integer.valueOf(deaths);
	}

	public Integer getWins(String uuid) {
		int wins = 0;
		try {
			PreparedStatement sts = getConnection().prepareStatement(
					"SELECT wins FROM " + this.table + " WHERE uuid = ?");
			sts.setString(1, uuid);
			ResultSet rs = sts.executeQuery();
			if (!rs.next()) {
				return Integer.valueOf(wins);
			}
			wins = rs.getInt("wins");
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Integer.valueOf(wins);
	}

	public Integer getCoins(String uuid) {
		int coins = 0;
		try {
			PreparedStatement sts = getConnection().prepareStatement(
					"SELECT coins FROM " + this.table + " WHERE uuid = ?");
			sts.setString(1, uuid);
			ResultSet rs = sts.executeQuery();
			if (!rs.next()) {
				return Integer.valueOf(coins);
			}
			coins = rs.getInt("coins");
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Integer.valueOf(coins);
	}

	public String getRank(String uuid) {
		String rank = "S/ Rank";
		try {
			PreparedStatement sts = getConnection().prepareStatement(
					"SELECT rank FROM " + this.table + " WHERE uuid = ?");
			sts.setString(1, uuid);
			ResultSet rs = sts.executeQuery();
			if (!rs.next()) {
				return rank;
			}
			rank = rs.getString("rank");
			sts.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rank;
	}
}
