package net.eduard.api.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * API de Controle de MySQL com apenas 1 conexão
 * @author Eduard-PC
 *
 */
public class SQL {

	private String user= "root";
	private String pass="";
	private String host = "localhost";
	private String port = "3306";
	private String database="teste";
	private String type = "jdbc:mysql://";
	private Connection connection;
	private int modications = 0;

	static {
		// test if has mysql
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// dont has mysql
			e.printStackTrace();
		}
	}

	public Connection getNewConnection() throws Exception {
		return DriverManager.getConnection(
				 getURL()+ database, user, pass);
	}
	public Connection newConnection() throws Exception {
		return DriverManager.getConnection(
				 getURL(), user, pass);
	}
	private String getURL(){
		return type + host + ":" + port + "/";
	}

	public synchronized void openConnection() {
		try {
			if (!hasConnection())
				connection = getNewConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public synchronized boolean hasModifications() {
		return modications > 0;
	}
	public synchronized Connection getConnection() {
		return connection;
	}
	public List<Map<String, String>> getAll(String query,Object... replacers) {
		List<Map<String, String>> list = new ArrayList<>();
		try {
			ResultSet rs = select(query);
			while (rs.next()) {
				Map<String, String> mapa = new HashMap<>();
				ResultSetMetaData meta = rs.getMetaData();
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					String name = meta.getColumnName(i);
					mapa.put(name, rs.getString(name));
				}
				list.add(mapa);
			}
			
			endQuery();
		} catch (Exception e) {
		}
		return list;
	}
	public boolean contains(String query) {
		boolean has = false;
		
		try {
			ResultSet rs = select(query);
			has = rs.next();
			
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
			
		} catch (Exception e) {
		}
		
		
		return has;
		
	}

	public synchronized int update(String query,Object... replacers) {
		startQuery();
		try {
			return query(query, replacers).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			endQuery();
		}
	}
	public synchronized PreparedStatement query(String query,Object... replacers){
		try {
			
			if (!query.endsWith(";")){
				query+=";";
			}
			// adiciona os atributos no lugar dos "?"
			query=query.replace('?', '&');
			for (Object replacer:replacers){
				query=query.replaceFirst("&", "'"+replacer.toString()+"'");
			}
			// outra maneira abaixo de fazer a mesma coisa
			PreparedStatement state = connection.prepareStatement(query);
			int id = 1;
			for (Object replacer:replacers){
				state.setString((id), replacer.toString());
				id++;
				
			}
			return state;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized ResultSet select(String query,Object... replacers) {
		startQuery();
		try {
			return query(query, replacers).executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public synchronized void startQuery() {
		openConnection();
		modications++;
	}
	public synchronized void endQuery() {
		modications--;
		closeConnection();
	}

	public synchronized boolean hasConnection() {
		return connection != null;
	}
	public synchronized void closeConnection() {
		try {
			if (hasConnection() && !hasModifications()) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SQL(String user, String pass, String host, String database) {
		super();
		this.user = user;
		this.pass = pass;
		this.host = host;
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
