package br.com.piracraft.lobby2.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.util.MySQL;
import br.com.piracraft.lobby2.Main;
import br.com.piracraft.lobby2.extencoes.Entradas;
import br.com.piracraft.lobby2.extencoes.Loja;
import br.com.piracraft.lobby2.extencoes.LojaMinecraft;
import br.com.piracraft.lobby2.extencoes.NomesBungee;
import br.com.piracraft.lobby2.extencoes.SevenDays;

public class SevenDaysQuerys {
	
	public static void loadAll(){
		try {
			ResultSet s = MySQL.getQueryResult("SELECT * FROM `MINIGAMES_SALAS_E_SERVIDORES` ORDER BY `ID_MINIGAMESSALAS`");
			while (s.next()) {
				NomesBungee nb = new NomesBungee();

				nb.setIdsala(s.getInt("ID_MINIGAMESSALAS"));
				nb.setNome(s.getString("NOME_BUNGEECORD"));

				NomesBungee.nomes.add(nb);
			}
			s.getStatement().getConnection().close();
		} catch (SQLException e) {
			System.out.println("erro");
		}
		
		new BukkitRunnable() {
			public void run() {
				try {
					ResultSet asmar = MySQL.getQueryResult(
							"SELECT SALA_REFRESH FROM MINIGAMES_SALAS_E_SERVIDORES WHERE SALA_REFRESH = 1");
					if (asmar.next()) {
						MySQL.execute(
								"UPDATE MINIGAMES_SALAS_E_SERVIDORES SET SALA_REFRESH = 0 WHERE SALA_REFRESH = 1");
				
						Entradas.whitelist = new ArrayList<Entradas>();
						try {
							ResultSet rs = MySQL.getQueryResult(
											"SELECT * FROM V_7_DIAS_USUARIOS_LIBERADOS WHERE ACESSO_BLOQUEADO = 0");
							while (rs.next()) {
								Entradas e = new Entradas();

								e.setUuid(rs.getString("UUID"));
								e.setIdsala(rs.getInt("ID_SALA"));
								e.setIdNetwork(rs.getInt("ID_NETWORK"));

								Entradas.whitelist.add(e);
							}
							rs.getStatement().getConnection().close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					asmar.getStatement().getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 200L);
		
		new BukkitRunnable() {
			public void run() {
				SevenDays.reviver = new ArrayList<SevenDays>();
				SevenDays.disponiveis = new ArrayList<SevenDays>();
				Entradas.whitelist = new ArrayList<Entradas>();
				try {
					ResultSet rs = MySQL.getQueryResult
							("SELECT * FROM V_7_DIAS_USUARIOS_LIBERADOS WHERE ACESSO_BLOQUEADO = 0");
					while (rs.next()) {
						Entradas e = new Entradas();

						e.setUuid(rs.getString("UUID"));
						e.setIdsala(rs.getInt("ID_SALA"));
						e.setIdNetwork(rs.getInt("ID_NETWORK"));

						Entradas.whitelist.add(e);
					}
					rs.getStatement().getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					ResultSet s = MySQL.getQueryResult
							("SELECT * FROM LOJAMINECRAFT ORDER BY `ID`");
					while (s.next()) {
						Loja l = new Loja();

						l.setItem(s.getInt("ID_ITEM"));
						l.setVariacao(s.getInt("ID_VARIACAO"));
						l.setQuantidade(s.getInt("QUANT"));
						l.setIdTabela(s.getInt("ID"));
						if (s.getString("VALOR_CASH") != null) {
							l.setCash(true);
							l.setValorCash(s.getInt("VALOR_CASH"));
						} else {
							l.setCash(false);
							l.setValorCoins(s.getInt("VALOR_COINS"));
						}
						if (s.getInt("ATIVO") == 1) {
							l.setAtivo(true);

							LojaMinecraft lm = new LojaMinecraft();

							lm.setId(s.getInt("ID"));
							lm.setItem(s.getInt("ID_ITEM"));
							lm.setVariacao(s.getInt("ID_VARIACAO"));
							lm.setNomeBukkit(s.getString("DESCRICAO"));

							Loja.lojaMinecraft.add(lm);
						} else {
							l.setAtivo(false);
						}
						Loja.shop.add(l);
					}
					s.getStatement().getConnection().close();
				} catch (SQLException e) {
					System.out.println("erro");
				}
				try {
					ResultSet rs = MySQL.getQueryResult(
							"SELECT * FROM MINIGAMES_SALAS_E_SERVIDORES WHERE 7_DIAS_ATIVO = 1 AND 7_DIAS_COMPRADISPONIVEL = 1 ORDER BY `ID_MINIGAMESSALAS`");
					while (rs.next()) {
						SevenDays sd = new SevenDays();

						sd.setDisponivel(rs.getInt("7_DIAS_COMPRADISPONIVEL"));
						sd.setServidor(rs.getString("NOMEDASALA"));
						sd.setCustoIngresso(rs.getInt("7_DIAS_CUSTO_INGRESSO"));
						sd.setComprasDisponiveis(rs.getInt("7_DIAS_TOTAL_INGRESSOS_DISPONIVEIS"));
						sd.setReviverDisponivel(rs.getInt("7_DIAS_REVIVER_ATIVO"));
						sd.setCustoReviver(rs.getInt("7_DIAS_CUSTO_REVIVER"));
						sd.setPremio(rs.getString("7_DIAS_PREMIO_DESCRICAO"));
						sd.setDataFinal(rs.getString("DT_FINAL"));

						SevenDays.disponiveis.add(sd);

						System.out.println(rs.getString("NOMEDASALA"));
					}
					rs.getStatement().getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					ResultSet rs = MySQL.getQueryResult(
							"SELECT * FROM MINIGAMES_SALAS_E_SERVIDORES WHERE 7_DIAS_ATIVO = 1 AND 7_DIAS_REVIVER_ATIVO = 1 ORDER BY `ID_MINIGAMESSALAS`");
					while (rs.next()) {
						SevenDays sd = new SevenDays();

						sd.setServidor(rs.getString("NOMEDASALA"));
						sd.setReviverDisponivel(rs.getInt("7_DIAS_REVIVER_ATIVO"));
						sd.setCustoReviver(rs.getInt("7_DIAS_CUSTO_REVIVER"));

						SevenDays.reviver.add(sd);
					}
					rs.getStatement().getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 6000L);
	}

}
