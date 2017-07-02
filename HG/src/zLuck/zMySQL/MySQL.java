package zLuck.zMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	
	private String HOST = "";
	private String DATABASE = "";
	private String USER = "";
	private String PASSWORD = "";
	
	private Connection con;
	
	public MySQL(String host, String database, String user, String password) {
		this.HOST = host;
		this.DATABASE = database;
		this.USER = user;
		this.PASSWORD = password;
		
		connect();
	}

	public void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
			System.out.println("[MySQL] Conectado ao MySQL");
		} catch (SQLException e) {
			System.out.println("[MySQL] Houve algum ERRO");
		}
	}
	
	public void close() {
		try {
			if(con != null) {
				con.close();
				System.out.println("[MySQL] MySQL desconectado!");
			}
		} catch (SQLException e) {
			System.out.println("[MySQL] Houve algum ERRO");
		}
	}
	
	public void update(String qry) {
		try {
			Statement st = con.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
	}
	
	public ResultSet query(String qry) {
		ResultSet rs = null;
		
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
		return rs;
	}
}