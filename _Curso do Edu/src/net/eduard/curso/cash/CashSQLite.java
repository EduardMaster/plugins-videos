package net.eduard.curso.cash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

//import net.eduard.api.setup.Mine;

public class CashSQLite implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			// 


			sender.sendMessage("§aSeu cash " + getCash(sender.getName()));
		} else {
//			Integer cash = Mine.toInt(args[0]);
//			sender.sendMessage("§aModificando para " + cash);
//			setCash(cash, sender.getName());
//			sender.sendMessage("§aSeu cash " + getCash(sender.getName()));
		}
		return false;
	}

	public static Connection con;

	public static void abrir() {
		try {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection("jdbc:sqlite:F:/Tudo/Teste/cash.db");

			Statement stmt = con.createStatement();
			stmt.execute("CREATE TABLE IF NOT EXISTS cash (nickname TEXT, amount INTEGER);");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fechar() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setCash(double cash, String nome) {
		/*
			
			*/
		if (hasAccount(nome)) {
			edit(nome, (int) cash);
		} else {
			try {
				Statement stmt = con.createStatement();
				stmt.execute("INSERT OR REPLACE INTO cash (nickname, amount) VALUES ('" + nome + "', '" + cash + "');");
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static boolean hasAccount(String nome) {
		boolean has = false;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cash where nickname = '" + nome + "';");

			if (rs.next()) {
				has = true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return has;
	}

	public static void edit(String nome, int valor) {
		try {
			Statement stmt = con.createStatement();
			stmt.execute("update cash set amount = '" + valor + "' where nickname = '" + nome + "';");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getCash(String nome) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cash where nickname = '" + nome + "';");
			int amount = 1;
			if (rs.next()) {
				amount = rs.getInt("amount");
			}
			rs.close();
			stmt.close();
			return amount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}

}
