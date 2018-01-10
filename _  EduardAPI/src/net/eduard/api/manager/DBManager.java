package net.eduard.api.manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.eduard.api.setup.StorageAPI.Copyable;
import net.eduard.api.setup.StorageAPI.Storable;

/**
 * API de Controle de MySQL ou SQLite com apenas 1 conexão
 * 
 * @author Eduard-PC
 *
 */
public class DBManager implements Storable,Copyable {

	private static boolean debug = true;
	private String user = "root";
	private String pass = "";
	private String host = "localhost";
	private String port = "3306";
	private String database = "teste";
	private String type = "jdbc:mysql://";
	private boolean useSQLite;
	private transient Connection connection;
	private transient ResultSet result;
	private transient PreparedStatement statement;
	@Override
	public DBManager copy() {
		return copy(this);
	}
	static {
		hasMySQL();
		hasSQLlite();
	}

	/**
	 * Fecha tudo
	 */
	public void closeAll() {
		closeState();
		closeResult();
		closeConnection();
	}

	/**
	 * Fecha o Select feito
	 */
	public void closeSelect() {
		closeState();
		closeResult();
	}

	public DBManager() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Ve se existe MySql na Maquina
	 * 
	 * @return Se esta instalado MySQL na Maquina
	 */
	public static boolean hasMySQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return true;
		} catch (Exception e) {
			// dont has mysql
			return false;
		}
	}

	public static boolean hasSQLlite() {
		try {
			Class.forName("org.sqlite.JDBC");
			return true;

		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Fecha a conecção do Banco
	 */
	public void closeConnection() {
		if (hasConnection()) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Fecha o Estado da Query
	 */
	protected void closeState() {
		try {
			if (result != null) {
				statement.close();
				this.statement = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fecha o Resultado da Query
	 */
	protected void closeResult() {
		try {
			if (result != null) {
				result.close();
				this.result = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria uma connecção com a Database
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection connectBase() throws Exception {
		return DriverManager.getConnection(getURL() + database, user, pass);
	}

	/**
	 * Cria uma conneção com o Driver
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection connect() throws Exception {
		if (useSQLite) {
			return DriverManager.getConnection("jdbc:sqlite:" + database);
		} else {
			return DriverManager.getConnection(getURL(), user, pass);
		}
	}

	/**
	 * Cria um Texto baseado nas variaveis
	 * 
	 * @return Texto estilo URL
	 */
	private String getURL() {
		return type + host + ":" + port + "/";
	}

	/**
	 * Abre a coneção com o banco de dados caso não exista ainda
	 * 
	 * @return Mesma instacia da classe DBManager
	 */
	public DBManager openConnection() {
		if (!hasConnection()) {
			try {
				this.connection = connect();
				if (!useSQLite) {
					createDatabase(database);
					useDatabase(database);
				}
			} catch (Exception e) {
				if (debug) {
					e.printStackTrace();
				}
			}
		}

		return this;
	}

	/**
	 * Ve se a conecção não esta nula
	 * 
	 * @return Se a coneção existe
	 */
	public boolean hasConnection() {
		return connection != null;
	}

	/**
	 * Volta a conecção da variavel
	 * 
	 * @return Conecção atual
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Construtor pedindo Usuario, Senha, Host sem conectar com nenhum database
	 * apenas no Driver
	 * 
	 * @param user
	 *            Usuario
	 * @param pass
	 *            Senha
	 * @param host
	 *            Host
	 */
	public DBManager(String user, String pass, String host) {
		this(user, pass, host, "mine");
	}

	/**
	 * Contrutor pedindo Usuario, Senha, Host, Database
	 * 
	 * @param user
	 *            Usuario
	 * @param pass
	 *            Senha
	 * @param host
	 *            Host
	 * @param database
	 *            Database
	 */
	public DBManager(String user, String pass, String host, String database) {
		this.user = user;
		this.pass = pass;
		this.host = host;
		this.database = database;
	}

	/**
	 * Criar uma database
	 * 
	 * @param database
	 *            Database
	 */
	public void createDatabase(String database) {
		update("create database if not exists " + database
				+ " default character set utf8 default collate utf8_general_ci");
	}

	/**
	 * Conecta com a database
	 * 
	 * @param database
	 *            Database
	 */
	public void useDatabase(String database) {
		update("USE " + database);
	}

	/**
	 * Cria uma tabela
	 * 
	 * @param table
	 *            Tabela
	 * @param values
	 *            Valores
	 */
	public void createTable(String table, String values) {
		if (useSQLite) {
			update("CREATE TABLE IF NOT EXISTS " + table + " (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " + values
					+ ");");
		} else {
			update("CREATE TABLE IF NOT EXISTS " + table + " (ID INT NOT NULL AUTO_INCREMENT, " + values
					+ ", PRIMARY KEY(ID)) default charset = utf8");
		}
	}

	/**
	 * Deleta todas tabelas da database
	 * 
	 * @param database
	 *            Database
	 */
	public void clearDatabase(String database) {
		// update("TRUNCATE DATABASE " + database);
	}

	/**
	 * Deleta database
	 * 
	 * @param database
	 *            Database
	 */
	public void deleteDatabase(String database) {
		update("DROP DATABASE " + database);
	}

	/**
	 * Insere um registro
	 * 
	 * @param table
	 *            Tabela
	 * @param objects
	 *            Objetos
	 */
	public void insert(String table, Object... objects) {
		if (useSQLite) {
			update("INSERT INTO " + table + " values ( NULL , " + hashes(objects.length) + " )", objects);
		} else {
			update("INSERT INTO " + table + " values (default, " + inters(objects.length) + " )", objects);
		}
	}

	/**
	 * Deleta um registro
	 * 
	 * @param table
	 *            Tabela
	 * @param index
	 *            Index (ID)
	 */
	public void delete(String table, int index) {
		update("DELETE FROM " + table + " WHERE ID = ?", index);
	}

	/**
	 * Deleta um registro
	 * 
	 * @param table
	 *            Tablea
	 * @param where
	 *            Como
	 * @param values
	 *            Valores
	 */
	public void delete(String table, String where, Object... values) {
		update("DELETE FROM " + table + " WHERE " + where, values);
	}

	/**
	 * Deleta uma coluna
	 * 
	 * @param table
	 *            Tale
	 * @param column
	 *            Coluna
	 */
	public void delete(String table, String column) {
		alter(table, "drop column " + column);
	}

	/**
	 * Adiciona no Começo da tabela uma coluna
	 * 
	 * @param table
	 *            Tabela
	 * @param columnComplete
	 *            Coluna
	 */
	public void addFirst(String table, String columnComplete) {
		alter(table, "add column " + columnComplete + " first");
	}

	public void addReference(String table, String key, String references) {
		update("ALTER TABLE " + table + " ADD FOREIGN KEY (" + key + ") REFERENCES " + references);
	}

	public void createView(String view, String select) {
		update("CREATE OR REPLACE VIEW " + view + " AS " + select);
	}

	public void deleteView(String view) {
		update("DROP VIEW " + view);
	}

	/**
	 * Renomeia a Tabela para uma Nova Tabela
	 * 
	 * @param table
	 *            Tabela
	 * @param newTable
	 *            Nova tabela
	 */
	public void renameTable(String table, String newTable) {
		alter(table, "rename to " + newTable);
	}

	/**
	 * Modifica uma Coluna de uma Tabela
	 * 
	 * @param table
	 *            Tabela
	 * @param column
	 *            Coluna
	 * @param modification
	 *            Modificação
	 */
	public void modify(String table, String column, String modification) {
		alter(table, "modify column " + column + " " + modification);
	}

	/**
	 * Adiciona chave primaria na tabela
	 * 
	 * @param table
	 *            Tabela
	 * @param key
	 *            Chave
	 */
	public void addKey(String table, String key) {
		alter(table, "add primary key (" + key + ")");
	}

	/**
	 * Altera uma tabala
	 * 
	 * @param table
	 * @param alter
	 */
	public void alter(String table, String alter) {
		if (hasConnection())
			update("alter table " + table + " " + alter);
	}

	/**
	 * Modifica alguns registros da tabela
	 * 
	 * @param table
	 *            Tabela
	 * @param where
	 *            Como
	 * @param edit
	 *            Modificação
	 * @param values
	 *            Valores
	 */
	public void change(String table, String edit, String where, Object... values) {
		update("UPDATE " + table + " SET " + edit + " WHERE " + where, values);
	}

	/**
	 * Cria um join entre as tabelas
	 * 
	 * @param table
	 *            Tabela
	 * @param joinTable
	 *            Tabela2
	 * @param onClause
	 *            Comparador
	 * @param select
	 *            Select completo
	 * @return ResultSet
	 */
	public ResultSet join(String table, String joinTable, String onClause, String select) {
		return select(select + " FROM " + table + " JOIN " + joinTable + " ON " + onClause);
	}

	/**
	 * Deleta a tabela
	 * 
	 * @param table
	 *            Tabela
	 */
	public void deleteTable(String table) {
		update("DROP TABLE " + table);
	}

	/**
	 * Limpa a tabela removendo todos registros
	 * 
	 * @param table
	 */
	public void clearTable(String table) {
		update("TRUNCATE TABLE " + table);
	}

	/**
	 * 
	 * @param table
	 * @param where
	 * @param values
	 * @return
	 */
	public boolean contains(String table, String where, Object... values) {
		return contains("select * from " + table + " where " + where, values);
	}

	/**
	 * Executa um Select e volta se tem algum registro
	 * 
	 * @param query
	 *            Query
	 * @param replacers
	 *            Objetos
	 * @return Se tem ou não registro com esta Query
	 */
	public boolean contains(String query, Object... replacers) {
		boolean has = false;
		if (hasConnection())
			try {
				ResultSet rs = select(query, replacers);
				has = rs.next();
				closeSelect();

			} catch (Exception e) {
				e.printStackTrace();
			}
		return has;

	}

	/**
	 * Executa uma Atualização com um Query
	 * 
	 * @param query
	 *            Query Pesquisa
	 * @param replacers
	 *            Objetos
	 */
	public void update(String query, Object... replacers) {
		if (hasConnection())
			try {
				query(query, replacers).executeUpdate();
				closeSelect();
			} catch (Exception e) {
				e.printStackTrace();

			}
	}

	/**
	 * Cria um PreparedStatement com uma Query dada, e aplica os Replacers
	 * 
	 * @param query
	 *            Query
	 * @param replacers
	 *            Objetos
	 * @return PreparedStatement (Estado da Query)
	 */
	public PreparedStatement query(String query, Object... replacers) {
		try {

			if (!query.endsWith(";")) {
				query += ";";
			}
			if (useSQLite) {
				query = query.replaceAll("\\?", "#");
				for (Object replacer : replacers) {
					query = query.replaceFirst("#", "'" + replacer + "'");
				}
				System.out.println("[SQLite] " + query);
			}

			PreparedStatement state = connection.prepareStatement(query);
			if (!useSQLite) {
				int id = 1;
				for (Object replacer : replacers) {
					if (replacer == null) {
						state.setObject(id, replacer);
					} else if (replacer instanceof Date) {
						state.setDate(id, (Date) replacer);
					} else if (replacer instanceof Time) {
						state.setTime(id, (Time) replacer);
					} else if (replacer instanceof Timestamp) {
						state.setTimestamp(id, (Timestamp) replacer);
					} else {
						state.setString(id, "" + replacer);
					}
					id++;
				}
				query = query.replaceAll("\\?", "#");
				for (Object replacer : replacers) {
					query = query.replaceFirst("#", "'" + replacer + "'");
				}
				System.out.println("[MySQL] " + query);
			}

			// System.out.println(state.get);
			return statement = state;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getString(String table, String column, String where, Object... replacers) {
		String result = "";
		ResultSet rs = selectAll(table, where, replacers);
		try {
			if (rs.next()) {
				result = rs.getString(column);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeSelect();
		return result;
	}

	public Date getDate(String table, String column, String where, Object... replacers) {
		Date result = null;
		ResultSet rs = selectAll(table, where, replacers);
		try {
			if (rs.next()) {
				result = rs.getDate(column);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeSelect();
		return result;
	}

	public UUID getUUID(String table, String column, String where, Object... replacers) {
		return UUID.fromString(getString(table, column, where, replacers));
	}

	public int getInt(String table, String column, String where, Object... replacers) {
		int result = -1;
		ResultSet rs = selectAll(table, where, replacers);
		try {
			if (rs.next()) {
				result = rs.getInt(column);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeSelect();
		return result;
	}

	public double getDouble(String table, String column, String where, Object... replacers) {
		double result = -1;
		ResultSet rs = selectAll(table, where, replacers);
		try {
			if (rs.next()) {
				result = rs.getDouble(column);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeSelect();
		return result;
	}

	public ResultSet selectAll(String table, String where, Object... replacers) {
		return select("SELECT * FROM " + table + " WHERE " + where, replacers);
	}

	/**
	 * Executa um Query e volta um ResultSet
	 * 
	 * @param query
	 *            Pesquisa
	 * @param replacers
	 *            Objetos
	 * @return ResultSet (Resultado da Query)
	 */
	public ResultSet select(String query, Object... replacers) {
		try {
			return result = query(query, replacers).executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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

	@Override
	public Object restore(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Map<String, Object> map, Object object) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gera um texto com "?" baseado na quantidade<br< Exemplo 5 = "? , ?,?,?,?"
	 * 
	 * @param size
	 *            Quantidade
	 * @return Texto criado
	 */
	public String inters(int size) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i != 0)
				builder.append(",");
			builder.append("?");
		}
		return builder.toString();
	}

	public String hashes(int size) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i != 0)
				builder.append(",");
			builder.append("#");
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return "DBManager [user=" + user + ", pass=" + pass + ", host=" + host + ", port=" + port + ", database="
				+ database + ", type=" + type + "]";
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		DBManager.debug = debug;
	}

	public boolean useSQLite() {
		return useSQLite;
	}

	public void setUseSQLite(boolean useSQLite) {
		this.useSQLite = useSQLite;
	}

	/**
	 * Seleciona tudo que o Select volta e transforma em Lista de Mapa<br>
	 * Lista = Linhas<br>
	 * Mapa = Colunas<br>
	 * 
	 * @param query
	 *            Query
	 * @param replacers
	 *            Objetos
	 * @return Lista de Mapa
	 */
	public List<Map<String, String>> getResult(String query, Object... replacers) {
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
			closeSelect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> selectAll(String table, Object... replacers) {
		List<Map<String, Object>> lista = new ArrayList<>();
		try {
			ResultSet rs = select("SELECT * FROM " + table, replacers);
			while (rs.next()) {
				Map<String, Object> mapa = new HashMap<>();
				ResultSetMetaData meta = rs.getMetaData();
				for (int colunaId = 1; colunaId <= meta.getColumnCount(); colunaId++) {
					String name = meta.getColumnName(colunaId);
					mapa.put(name, rs.getObject(name));
				}
				lista.add(mapa);
			}
			rs.getStatement().getConnection().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public Map<String, Object> selectOne(String table, Object... replacers) {
		Map<String, Object> mapa = new HashMap<>();
		try {
			ResultSet rs = select("SELECT * FROM " + table, replacers);
			if (rs.next()) {
				ResultSetMetaData meta = rs.getMetaData();
				for (int colunaId = 1; colunaId <= meta.getColumnCount(); colunaId++) {
					String name = meta.getColumnName(colunaId);
					mapa.put(name, rs.getObject(name));
				}
			}
			rs.getStatement().getConnection().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapa;
	}

	public Map<String, VarInfo> getOne(String query, Object... replacers) {
		Map<String, VarInfo> mapa = new LinkedHashMap<>();
		try {
			ResultSet rs = select(query, replacers);
			ResultSetMetaData meta = rs.getMetaData();
			if (rs.next()) {
				for (int colunaID = 1; colunaID <= meta.getColumnCount(); colunaID++) {
					String coluna = meta.getColumnName(colunaID);
					String classe = meta.getColumnClassName(colunaID);
					int type = meta.getColumnType(colunaID);
					String typeName = meta.getColumnTypeName(colunaID);
					Object valor = rs.getObject(colunaID);
					String texto = rs.getString(colunaID);
					// String calalog = meta.getCatalogName(colunaID);
					// String label = meta.getColumnLabel(colunaID);
					// int displaySize = meta.getColumnDisplaySize(colunaID);
					// int precision = meta.getPrecision(colunaID);
					// int scale = meta.getScale(colunaID);
					VarInfo campo = new VarInfo();
					campo.setText(texto);
					campo.setValue(valor);
					campo.setTypeName(typeName);
					campo.setType(type);
					campo.setClassName(classe);
					campo.setName(coluna);
					campo.setId(colunaID);
					mapa.put(coluna, campo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeSelect();
		return mapa;
	}

	public List<Map<String, VarInfo>> getAll(String query, Object... objects) {
		List<Map<String, VarInfo>> lista = new LinkedList<>();

		try {
			ResultSet rs = select(query);
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next()) {
				Map<String, VarInfo> mapa = new LinkedHashMap<>();
				lista.add(mapa);
				for (int colunaID = 1; colunaID <= meta.getColumnCount(); colunaID++) {
					String coluna = meta.getColumnName(colunaID);
					String classe = meta.getColumnClassName(colunaID);
					int type = meta.getColumnType(colunaID);
					String typeName = meta.getColumnTypeName(colunaID);
					Object valor = rs.getObject(colunaID);
					String texto = rs.getString(colunaID);
					// String calalog = meta.getCatalogName(colunaID);
					// String label = meta.getColumnLabel(colunaID);
					// int displaySize = meta.getColumnDisplaySize(colunaID);
					// int precision = meta.getPrecision(colunaID);
					// int scale = meta.getScale(colunaID);
					VarInfo campo = new VarInfo();
					campo.setText(texto);
					campo.setValue(valor);
					campo.setTypeName(typeName);
					campo.setType(type);
					campo.setClassName(classe);
					campo.setName(coluna);
					campo.setId(colunaID);
					mapa.put(coluna, campo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		closeSelect();

		return lista;

	}

	public static class VarInfo {
		private int id;
		private String name;
		private String text;
		private Object value;
		private int type;
		private String typeName;
		private String className;

		public Object get() {
			return getValue();
		}

		public String getString() {
			return text;
		}

		public Date getDate() {
			return (Date) getValue();
		}

		public int getInt() {
			return (int) getValue();
		}

		public double getDouble() {
			return (double) getValue();
		}

		public long getLong() {
			return (long) getValue();
		}

		public UUID getUUID() {
			return UUID.fromString(text);
		}

		public Class<?> getClassType() throws ClassNotFoundException {
			return Class.forName(className);
		}

		@Override
		public String toString() {
			return "" + value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

	public void insert(AutoBase base) {
		base.insert(getConnection());
	}

	public void change(AutoBase base) {
		base.change(getConnection());
	}

	public void update(AutoBase base) {
		base.update(getConnection());
	}

	public void delete(AutoBase base) {
		base.delete(getConnection());
	}

	public <E extends AutoBase> List<E> getAll(E base, String collumn, boolean decending) {
		return base.getAll(base, collumn, decending, connection);
	}

	public <E extends AutoBase> List<E> getAll(E base) {
		return base.getAll(base, base.getPrimaryKey().getName(), false, connection);
	}
	public <E extends AutoBase> List<E> getAll(Class<E> clazz) {
		E instance = null;
		try {
			instance = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return instance.getAll(instance, instance.getPrimaryKey().getName(), false, connection);
	}
	public void createTable(AutoBase base) {
		base.createTable(getConnection());
	}

	// public void getAll(AutoBase base) {
	// return base.getAll(base,getConnection());
	// }

	public static interface AutoBase {

		public default String defaultCharset() {
			return "utf8";
		}

		public default void change(Connection connection) {
			try {
				StringBuilder builder = new StringBuilder();
				builder.append("UPDATE " + getTableName() + " SET ");
				int x = 1;
				Map<Integer, Object> values = new HashMap<>();
				boolean first = true;
				for (Field field : getClass().getDeclaredFields()) {
					if (Modifier.isStatic(field.getModifiers())) {
						continue;
					}
					if (Modifier.isAbstract(field.getModifiers())) {
						continue;
					}

					field.setAccessible(true);
					Object valor = field.get(this);
					if (field.isAnnotationPresent(Info.class)) {
						Info options = field.getAnnotation(Info.class);
						// Class<?> type = field.getType();
						if (options.primaryKey()) {
							continue;
						}
						if (!options.canBeNull() && valor == null) {
							continue;
						}
					}
					if (!first) {
						builder.append(", ");
					} else {
						first = false;
					}
					builder.append(getFieldName(field) + " = ? ");
					values.put(x++, valor);

				}
				builder.append(" WHERE " + getFieldName(getPrimaryKey()) + " = ? ;");
				// System.out.println(getPrimaryKey().get(this));
				values.put(x++, getPrimaryKey().get(this));
				PreparedStatement st = connection.prepareStatement(builder.toString());
				for (Entry<Integer, Object> entry : values.entrySet()) {
					Integer id = entry.getKey();
					Object value = entry.getValue();
					st.setObject(id, value);
				}
				st.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public default void insert(Connection connection) {
			try {
				StringBuilder builder = new StringBuilder();
				builder.append("INSERT INTO " + getTableName() + " VALUES (");
				boolean first = true;
				for (Field field : getClass().getDeclaredFields()) {
					if (Modifier.isStatic(field.getModifiers())) {
						continue;
					}
					if (Modifier.isAbstract(field.getModifiers())) {
						continue;
					}
					if (!first) {
						builder.append(", ");
					} else {
						first = false;
					}

					field.setAccessible(true);
					Object valor = field.get(this);
					if (field.isAnnotationPresent(Info.class)) {
						Info options = field.getAnnotation(Info.class);
						// Class<?> type = field.getType();
						if (options.primaryKey()) {
							builder.append(" DEFAULT");
							continue;
						}
						if (!options.canBeNull() && valor == null) {
							builder.append(" ''");
							continue;
						}
					}
					if (valor != null) {
						builder.append(" '" + valor + "' ");
					} else {
						builder.append(" NULL ");
					}

				}
				builder.append(")");
				PreparedStatement st = connection.prepareStatement(builder.toString());
				st.executeUpdate();
				st.close();
				// getPrimaryKey().set(this, rs.getObject(getFieldName(getPrimaryKey())));
				// rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			restorePrimaryKey();

		}

		public default Connection connect() {

			return null;
		}

		public default void insert() {
			insert(connect());

		}

		public default Field getUpdaterField() {
			for (Field field : getClass().getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				if (Modifier.isAbstract(field.getModifiers())) {
					continue;
				}
				field.setAccessible(true);
				if (field.isAnnotationPresent(Info.class)) {
					Info options = field.getAnnotation(Info.class);
					if (options.update()) {
						return field;
					}
				}
			}
			return null;

		}

		public default Field getPrimaryKey() {
			for (Field field : getClass().getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				if (Modifier.isAbstract(field.getModifiers())) {
					continue;
				}
				field.setAccessible(true);
				if (field.isAnnotationPresent(Info.class)) {
					Info options = field.getAnnotation(Info.class);
					if (options.primaryKey()) {
						return field;
					}
				}
			}

			return null;
		}

		public default void delete() {
			delete(connect());
		}

		public default void delete(Connection connection) {
			try {
				Field primary = getPrimaryKey();
				Object valor = primary.get(this);

				PreparedStatement st = connection.prepareStatement(
						"DELETE FROM " + getTableName() + " WHERE " + getFieldName(primary) + " = '" + valor + "';");
				st.executeUpdate();
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public default List<AutoBase> getAll() {
			return getAll(this);
		}

		public default List<AutoBase> getAll(String collumnOrdened, boolean decending) {
			return getAll(this, collumnOrdened, decending, connect());
		}

		public default <E extends AutoBase> List<E> getAll(E e) {
			return getAll(e, getPrimaryKey().getName(), false, connect());
		}

		@SuppressWarnings("unchecked")
		public default <E extends AutoBase> List<E> getAll(E e, String collumnOrdened, boolean decending,
				Connection connection) {
			List<E> lista = new ArrayList<>();
			try {
				// Field primary = getPrimaryKey();
				// String primaryName = getFieldName(primary);
				// Field updater = getUpdaterField();
				// String updaterName = getFieldName(updater);
				StringBuilder builder = new StringBuilder();

				builder.append(
						"SELECT * FROM " + getTableName() + " ORDER BY ? " + ((decending) ? "DESC" : "ASC") + ";");
				System.out.println(builder.toString());
				PreparedStatement st = connection.prepareStatement(builder.toString());

				Field var = getClass().getDeclaredField(collumnOrdened);
				var.setAccessible(true);

				st.setObject(1, getFieldName(var));
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					E newE = null;
					try {
						newE = (E) e.getClass().newInstance();
					} catch (Exception ex) {
						continue;
					}

					for (Field field : newE.getClass().getDeclaredFields()) {
						if (Modifier.isStatic(field.getModifiers())) {
							continue;
						}
						if (Modifier.isAbstract(field.getModifiers())) {
							continue;
						}

						field.setAccessible(true);
						Object value = rs.getObject(getFieldName(field));
						field.set(newE, value);
					}
					lista.add(newE);
				}
				rs.close();
				st.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return lista;
		}

		public default boolean update() {
			return update(connect());
		}

		public default boolean update(Connection connection) {
			boolean hasUpdateCode = false;
			try {
				Field primary = getPrimaryKey();
				String primaryName = getFieldName(primary);
				Field updater = getUpdaterField();
				String updaterName = getFieldName(updater);
				StringBuilder builder = new StringBuilder();
				builder.append("SELECT * FROM " + getTableName() + " WHERE " + primaryName + " = ? OR " + updaterName
						+ " = ? ;");
				
				PreparedStatement st = connection.prepareStatement(builder.toString());
				Object updaterValue = updater.get(this);
				Object primaryValue = primary.get(this);
				if (primaryValue!=null) {
					if (primaryValue instanceof UUID) {
						primaryValue = ""+primaryValue;
						
					}
				}
				if (updaterValue!=null) {
					if (updaterValue instanceof UUID) {
						updaterValue = ""+updaterValue;
						
					}
				}
				st.setObject(1, primaryValue);
				st.setObject(2, updaterValue);
				 System.out.println("[MySQL] "+builder.toString());
				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					hasUpdateCode = true;
					for (Field field : getClass().getDeclaredFields()) {
						if (Modifier.isStatic(field.getModifiers())) {
							continue;
						}
						if (Modifier.isAbstract(field.getModifiers())) {
							continue;
						}

						field.setAccessible(true);
						Object value = rs.getObject(getFieldName(field));
						field.set(this, value);
					}
				}
				rs.close();
				st.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return hasUpdateCode;
		}

		public default void restorePrimaryKey() {
			createTable(connect());
		}

		public default void restorePrimaryKey(Connection connection) {

			try {
				Field primary = getPrimaryKey();
				String primaryName = getFieldName(primary);
				Field updater = getUpdaterField();
				String updaterName = getFieldName(updater);
				if (primary.get(this) == null || Integer.valueOf(0).equals(primary.get(this))) {
					StringBuilder builder = new StringBuilder();
					builder.append("SELECT * FROM " + getTableName() + " WHERE " + updaterName + " = ?" + ";");
					// System.out.println(builder.toString());
					PreparedStatement st = connection.prepareStatement(builder.toString());
					st.setObject(1, updater.get(this));
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						Object value = rs.getObject(primaryName);
						primary.set(this, value);
					}
					rs.close();
					st.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public default void change() {
			change(connect());
		}

		// public static <E> E get(Class<E> claz,String query){
		//
		// return null;
		// }
		//
		// public List<AutoBase> selectAll(String whereIf,Connection connection) {
		// return select(whereIf, "ORDER BY " + column);
		// }
		//
		// public List<AutoBase> selectAllOrdened(String whereIf, String
		// column,Connection connection) {
		//
		//
		// List<E> lista = new LinkedList<>();
		//
		// return lista;
		//
		// }
		public default String getTableName() {

			if (getClass().isAnnotationPresent(Info.class)) {
				Info info = getClass().getAnnotation(Info.class);
				return info.name();
			}
			return getClass().getSimpleName();
		}

		public default void createTable() {
			createTable(connect());
		}

		public default void createTable(Connection connection) {

			StringBuilder builder = new StringBuilder();

			builder.append("CREATE TABLE IF NOT EXISTS " + getTableName() + " (");
			Class<? extends AutoBase> claz = getClass();
			boolean first = true;
			for (Field field : claz.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				if (Modifier.isAbstract(field.getModifiers())) {
					continue;
				}
				if (!first) {
					builder.append(", ");
				} else {
					first = false;
				}
				builder.append(" " + getFieldName(field));
				builder.append(" " + getType(field));

			}
			builder.append(")");
			builder.append(" default charset = ");
			builder.append(defaultCharset());

			builder.append(";");
			// System.out.println(builder.toString());
			try {
				connection.prepareStatement(builder.toString()).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// public void createTable(String table, String values) {
			// update("CREATE TABLE IF NOT EXISTS " + table
			// + " (ID INT NOT NULL AUTO_INCREMENT , " + values
			// + ", PRIMARY KEY(ID)) default charset = utf8");
			// }
		}

		public default String getFieldName(Field field) {
			if (field.isAnnotationPresent(Info.class)) {
				Info info = field.getAnnotation(Info.class);
				if (!info.name().equals("")) {
					return getTableName() + "_" + info.name();
				}
			}
			return getTableName() + "_" + field.getName();
		}

		public default String getType(Field field) {
			StringBuilder builder = new StringBuilder();
			try {
				field.setAccessible(true);

				if (field.isAnnotationPresent(Info.class)) {
					Info options = field.getAnnotation(Info.class);
					Class<?> type = field.getType();
					if (type.equals(String.class)) {
						builder.append("VARCHAR");
					} else if (type.equals(Integer.class) || type.equals(int.class)) {
						builder.append("INT");
					} else if (type.equals(Double.class) || type.equals(double.class)) {
						builder.append("DOUBLE");
					} else if (type.equals(UUID.class)) {
						builder.append("VARCHAR");
					}
					builder.append("(" + options.size() + ")");
					if (!options.canBeNull()) {
						builder.append(" NOT NULL");
					}
					if (options.unique()) {
						builder.append(" UNIQUE");
					}
					if (options.primaryKey()) {
						builder.append(" AUTO_INCREMENT PRIMARY KEY");
					}

				} else {
					builder.append("VARCHAR(50) NOT NULL");
				}

			} catch (Exception e) {
				e.printStackTrace();
				builder.append("VARCHAR(100) NOT NULL");
			}
			// System.out.println(builder.toString());
			return builder.toString();
		}

	}

	@Target({ ElementType.FIELD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Info {

		boolean primaryKey() default false;

		String name() default "";

		boolean unique() default false;

		boolean update() default false;

		boolean canBeNull() default false;

		int size() default 100;

	}

}
