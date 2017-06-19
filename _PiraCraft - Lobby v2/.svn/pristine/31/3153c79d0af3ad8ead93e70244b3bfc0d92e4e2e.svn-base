package br.com.piracraft.lobby2.extencoes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.util.MySQL;

public class Entradas {

	public static List<Entradas> whitelist = new ArrayList<Entradas>();

	private String uuid;
	private int idsala;
	private int idNetwork;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getIdsala() {
		return idsala;
	}

	public void setIdsala(int idsala) {
		this.idsala = idsala;
	}
	
	public int getIdNetwork() {
		return idNetwork;
	}

	public void setIdNetwork(int idNetwork) {
		this.idNetwork = idNetwork;
	}

	public static boolean contains(String uuid) {
		boolean a = false;
		List<String> uid = new ArrayList<String>();
		for (int x = 0; x < whitelist.size(); x++) {
			uid.add(whitelist.get(x).getUuid());
		}
		if (uid.contains(uuid)) {
			a = true;
		}
		return a;
	}

	public boolean isDisponivel(int sala) {
		boolean a = false;
		try{ ResultSet rs = MySQL.getQueryResult("SELECT * FROM MINIGAMES_SALAS_E_SERVIDORES WHERE 7_DIAS_ATIVO = 1 AND ID_MINIGAMESSALAS = '"+sala+"'");
		     if(rs.next()){
	    		 a = true;
		     }
		     rs.getStatement().getConnection().close();
		    	 }catch(SQLException e){
		    		 e.printStackTrace();
		    	 }
		return a;
	}

}
