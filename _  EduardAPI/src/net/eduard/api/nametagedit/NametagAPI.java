package net.eduard.api.nametagedit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class NametagAPI {
	public static LinkedHashMap<String, LinkedHashMap<String, String>> groups = null;
	public static LinkedHashMap<String, LinkedHashMap<String, String>> config = null;
	public static boolean tabListDisabled = false;
	public static boolean deathMessageEnabled = false;
	public static boolean checkForUpdatesEnabled = false;
	public static boolean consolePrintEnabled = false;
	public String permissions = "";
	public static String name = "";
	public static String type = "";
	public static String version = "";
	public String link = "";

	public static void startNameTag() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(),
				new Runnable() {
					public void run() {
						LinkedHashMap<String, LinkedHashMap> JogadorData2 = new LinkedHashMap<>();

						if (JogadorData2 != null)
							for (String Jogadorname : JogadorData2.keySet()) {
								LinkedHashMap<?, ?> JogadorData = (LinkedHashMap<?, ?>) JogadorData2
										.get(Jogadorname);
								String Prefix = (String) JogadorData
										.get("prefix");
								String Suffix = (String) JogadorData
										.get("suffix");
								if (Prefix != null) {
									Prefix = formatColors(Prefix);
								}
								if (Suffix != null) {
									Suffix = formatColors(Suffix);
								}
								Overlap(Jogadorname, Prefix, Suffix);
							}
					}
				});
	}

	public static void setNameTagHard(String Jogador, String Prefix,
			String Suffix, NameTagChangeEvent.NametagChangeReason Reacão) {
		NameTagChangeEvent e = new NameTagChangeEvent(Jogador,
				NametagAPI.getPrefix(Jogador), NametagAPI.getSuffix(Jogador),
				Prefix, Suffix, NameTagChangeEvent.NametagChangeType.HARD,
				Reacão);

		Bukkit.getServer().getPluginManager().callEvent(e);
		if (!e.isCancelled())
			Overlap(Jogador, Prefix, Suffix);
	}

	public static void setNameTagSoft(String Jogador, String Prefix,
			String Suffix, NameTagChangeEvent.NametagChangeReason Reacão) {
		NameTagChangeEvent e = new NameTagChangeEvent(Jogador,
				NametagAPI.getPrefix(Jogador), NametagAPI.getSuffix(Jogador),
				Prefix, Suffix, NameTagChangeEvent.NametagChangeType.SOFT,
				Reacão);
		Bukkit.getServer().getPluginManager().callEvent(e);
		if (!e.isCancelled())
			Overlap(Jogador, Prefix, Suffix);
	}
	public static Plugin getPlugin() {
		return JavaPlugin.getProvidingPlugin(NametagAPI.class);
	}

	public static void setPrefix(String p, final String prefix) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(),
				new Runnable() {
					public void run() {
						NameTagChangeEvent e = new NameTagChangeEvent(name,
								NametagAPI.getPrefix(name),
								NametagAPI.getSuffix(name), prefix, "",
								NameTagChangeEvent.NametagChangeType.SOFT,
								NameTagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled()) {
							updateTag(p, prefix, "");
							update(p, prefix, "");
						}
					}
				});
	}

	public static void setSuffix(String p, final String suffix) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(),
				new Runnable() {
					public void run() {
						NameTagChangeEvent e = new NameTagChangeEvent(name,
								NametagAPI.getPrefix(name),
								NametagAPI.getSuffix(name), "", suffix,
								NameTagChangeEvent.NametagChangeType.SOFT,
								NameTagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled()) {
							updateTag(p, "", suffix);
							update(p, "", suffix);
						}
					}
				});
	}

	public static void setNametagHard(String p, final String Prefix,
			final String Suffix) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(),
				new Runnable() {
					public void run() {
						NameTagChangeEvent e = new NameTagChangeEvent(name,
								NametagAPI.getPrefix(name),
								NametagAPI.getSuffix(name), Prefix, Suffix,
								NameTagChangeEvent.NametagChangeType.HARD,
								NameTagChangeEvent.NametagChangeReason.CUSTOM);

						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled()) {
							Overlap(name, Prefix, Suffix);
							overlap(name, Prefix, Suffix);
						}
					}
				});
	}

	public static void SetNameTagSoft(String name, final String Prefix,
			final String Suffix) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(),
				new Runnable() {
					public void run() {
						NameTagChangeEvent e = new NameTagChangeEvent(name,
								NametagAPI.getPrefix(name),
								NametagAPI.getSuffix(name), Prefix, Suffix,
								NameTagChangeEvent.NametagChangeType.SOFT,
								NameTagChangeEvent.NametagChangeReason.CUSTOM);

						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled()) {
							updateTag(name, Prefix, Suffix);
							update(name, Prefix, Suffix);
						}
					}
				});
	}

	public static void updateNameTagHard(String name, final String Prefix,
			final String Suffix) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(),
				new Runnable() {
					public void run() {
						NameTagChangeEvent e = new NameTagChangeEvent(name,
								NametagAPI.getPrefix(name),
								NametagAPI.getSuffix(name), Prefix, Suffix,
								NameTagChangeEvent.NametagChangeType.HARD,
								NameTagChangeEvent.NametagChangeReason.CUSTOM);

						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled())
							Overlap(name, Prefix, Suffix);
					}
				});
	}

	public static void updateNameTagSoft(String name, final String Prefix,
			final String Suffix) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(),
				new Runnable() {
					public void run() {
						NameTagChangeEvent e = new NameTagChangeEvent(name,
								NametagAPI.getPrefix(name),
								NametagAPI.getSuffix(name), Prefix, Suffix,
								NameTagChangeEvent.NametagChangeType.SOFT,
								NameTagChangeEvent.NametagChangeReason.CUSTOM);
						Bukkit.getServer().getPluginManager().callEvent(e);
						if (!e.isCancelled())
							updateTag(name, Prefix, Suffix);
					}
				});
	}

	public static void resetNameTag(String name) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(),
				new Runnable() {
					public void run() {
						clearTag(name);
						removePlayer(name, null);

						Player targetPlayer = Bukkit.getPlayerExact(name);
						if (targetPlayer != null)
							for (String key : (String[]) groups.keySet()
									.toArray(new String[groups.keySet()
											.size()])) {
								if (targetPlayer.hasPermission(key)) {
									String prefix = (String) ((LinkedHashMap<?, ?>) groups
											.get(key)).get("prefix");

									String suffix = (String) ((LinkedHashMap<?, ?>) groups
											.get(key)).get("suffix");

									if (prefix != null) {
										prefix = formatColors(prefix);
									}

									if (suffix != null) {
										suffix = formatColors(suffix);
									}

									setNameTagHard(name, prefix, suffix,
											NameTagChangeEvent.NametagChangeReason.GROUP_NODE);
									break;
								}
							}
					}
				});
	}

	public static String getTag(String name) {
		return getFormattedName(name);
	}

	public static boolean getCustomName(String name) {
		LinkedHashMap<?, ?> Map = getPlayer(name);
		if (Map == null) {
			return false;
		}
		String Prefix = (String) Map.get("prefix");
		String Suffix = (String) Map.get("suffix");
		if (((Prefix == null) || (Prefix.isEmpty()))
				&& ((Suffix == null) || (Suffix.isEmpty()))) {
			return false;
		}
		return true;
	}
	private static final String TEAM_NAME_PREFIX = "NTE";
	private static List<Integer> list = new ArrayList<Integer>();

	private static HashMap<TeamInfo, List<String>> teams = new HashMap<TeamInfo, List<String>>();

	private static void addTeam(TeamInfo team, String Jogador) {
		RemoverTeam(Jogador);
		List<String> List = (List<String>) teams.get(team);
		if (List != null) {
			List.add(Jogador);
			Player Jogadores = Bukkit.getPlayerExact(Jogador);
			if (Jogadores != null) {
				sendPacketsAddTeams(team, Jogadores.getName());
			} else {
				OfflinePlayer JogadoresOff = Bukkit.getOfflinePlayer(Jogador);
				sendPacketsAddTeams(team, JogadoresOff.getName());
			}
		}
	}

	private static void register(TeamInfo team) {
		teams.put(team, new ArrayList<String>());
		MandarPacketsAdicionarteams(team);
	}

	private static boolean removeTeam(String name) {
		for (TeamInfo team : (TeamInfo[]) teams.keySet()
				.toArray(new TeamInfo[teams.size()])) {
			if (team.getName().equals(name)) {
				removeTeam(team);
				return true;
			}
		}
		return false;
	}

	private static void removeTeam(TeamInfo team) {
		sendPacketsTeamRemoved(team);
		teams.remove(team);
	}

	private static TeamInfo RemoverTeam(String Jogador) {
		for (TeamInfo team : (TeamInfo[]) teams.keySet()
				.toArray(new TeamInfo[teams.size()])) {
			List<?> list = (List<?>) teams.get(team);
			for (String Tags : (String[]) list
					.toArray(new String[list.size()])) {
				if (Tags.equals(Jogador)) {
					Player Jogador1 = Bukkit.getPlayerExact(Jogador);
					if (Jogador1 != null) {
						MandarPacketsRemoverteams(team, Jogador1.getName());
					} else {
						OfflinePlayer Jogador2 = Bukkit.getOfflinePlayer(Tags);
						MandarPacketsRemoverteams(team, Jogador2.getName());
					}
					list.remove(Tags);

					return team;
				}
			}
		}
		return null;
	}

	private static TeamInfo getTeam(String name) {
		for (TeamInfo team : (TeamInfo[]) teams.keySet()
				.toArray(new TeamInfo[teams.size()])) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		return null;
	}

	private static TeamInfo[] getTeams() {
		TeamInfo[] list = new TeamInfo[teams.size()];
		int at = 0;
		for (TeamInfo team : (TeamInfo[]) teams.keySet()
				.toArray(new TeamInfo[teams.size()])) {
			list[at] = team;
			at++;
		}
		return list;
	}

	private static String[] getPlayersTeam(TeamInfo team) {
		List<String> items =  teams.get(team);
		if (items != null) {
			return (String[]) items.toArray(new String[items.size()]);
		}
		return new String[0];
	}

	public static void updateTag(String Jogador, String Prefix, String Suffix) {
		if ((Prefix == null) || (Prefix.isEmpty())) {
			Prefix = getPrefix(Jogador);
		}
		if ((Suffix == null) || (Suffix.isEmpty())) {
			Suffix = getSuffix(Jogador);
		}
		TeamInfo t = getInfo(Prefix, Suffix);
		addTeam(t, Jogador);
	}

	public static void Overlap(String Jogador, String Prefix, String Suffix) {
		if (Prefix == null) {
			Prefix = "";
		}
		if (Suffix == null) {
			Suffix = "";
		}
		TeamInfo t = getInfo(Prefix, Suffix);
		addTeam(t, Jogador);
	}

	public static void clearTag(String player) {
		RemoverTeam(player);
	}

	public static String getPrefix(String Jogador) {
		for (TeamInfo team : getTeams()) {
			for (String Tags : getPlayersTeam(team)) {
				if (Tags.equals(Jogador)) {
					return team.getPrefix();
				}
			}
		}
		return "";
	}

	public static String getSuffix(String Jogador) {
		for (TeamInfo team : getTeams()) {
			for (String Tags : getPlayersTeam(team)) {
				if (Tags.equals(Jogador)) {
					return team.getSuffix();
				}
			}
		}
		return "";
	}

	public static String getFormattedName(String Jogador) {
		return getPrefix(Jogador) + Jogador + getSuffix(Jogador);
	}

	private static TeamInfo getDeclareteam(String name, String Prefix,
			String Suffix) {
		if (getTeam(name) != null) {
			TeamInfo team = getTeam(name);
			removeTeam(team);
		}

		TeamInfo team = new TeamInfo(name);

		team.setPrefix(Prefix);
		team.setSuffix(Suffix);

		register(team);

		return team;
	}

	private static TeamInfo getInfo(String Prefix, String Suffix) {
		update();

		Integer[] arr$ = (Integer[]) list.toArray(new Integer[list.size()]);
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			int t = arr$[i$].intValue();

			if (getTeam("NTE" + t) != null) {
				TeamInfo team = getTeam("NTE" + t);

				if ((team.getSuffix().equals(Suffix))
						&& (team.getPrefix().equals(Prefix))) {
					return team;
				}
			}
		}

		return getDeclareteam("NTE" + NextName(), Prefix, Suffix);
	}

	private static int NextName() {
		int at = 0;
		// boolean cont = true;
		// int len$;
		// int i$;
		// for (; cont;
		// i$ < len$)
		// {
		// cont = false;
		// Integer[] arr$ = (Integer[])list.toArray(new Integer[list.size()]);
		// len$ = arr$.length; i$ = 0; continue; int t = arr$[i$].intValue();
		// if (t == at) {
		// at++;
		// cont = true;
		// }
		// i$++;
		// }
		//
		// list.add(Integer.valueOf(at));
		return at;
	}

	private static void update() {
		for (TeamInfo team : getTeams()) {
			int Entry = -1;
			try {
				Entry = Integer.parseInt(team.getName());
			} catch (Exception localException) {
			}
			if ((Entry != -1) && (getPlayersTeam(team).length == 0)) {
				removeTeam(team);
				list.remove(new Integer(Entry));
			}
		}
	}

	public static void sendTeamsToPlayer(Player Jogador) {
		try {
			for (TeamInfo team : getTeams()) {
				PacketPlayOut PacketPlayOut = new PacketPlayOut(team.getName(),
						team.getPrefix(), team.getSuffix(), new ArrayList<Object>(), 0);

				PacketPlayOut.sendToPlayer(Jogador);
				PacketPlayOut = new PacketPlayOut(team.getName(),
						Arrays.asList(getPlayersTeam(team)), 3);

				PacketPlayOut.sendToPlayer(Jogador);
			}
		} catch (Exception e) {
			System.out.println(
					"Failed to send packet for player (Packet209SetScoreboardteam) : ");

			e.printStackTrace();
		}
	}

	private static void MandarPacketsAdicionarteams(TeamInfo team) {
		try {
			for (Player Jogadores : Bukkit.getOnlinePlayers()) {
				PacketPlayOut PacketPlayOut = new PacketPlayOut(team.getName(),
						team.getPrefix(), team.getSuffix(), new ArrayList<Object>(), 0);
				PacketPlayOut.sendToPlayer(Jogadores);
			}
		} catch (Exception e) {
			System.out.println(
					"Failed to send packet for player (Packet209SetScoreboardteam) : ");

			e.printStackTrace();
		}
	}

	private static void sendPacketsTeamRemoved(TeamInfo team) {
		boolean Cont = false;
		for (TeamInfo team2 : getTeams()) {
			if (team2 == team) {
				Cont = true;
			}
		}
		if (!Cont) {
			return;
		}
		try {
			for (Player Jogadores : Bukkit.getOnlinePlayers()) {
				PacketPlayOut PacketPlayOut = new PacketPlayOut(team.getName(),
						team.getPrefix(), team.getSuffix(), new ArrayList<Object>(), 1);
				PacketPlayOut.sendToPlayer(Jogadores);
			}
		} catch (Exception e) {
			System.out.println(
					"Failed to send packet for player (Packet209SetScoreboardteam) : ");
			e.printStackTrace();
		}
	}

	private static void sendPacketsAddTeams(TeamInfo team,
			String Jogador) {
		boolean Cont = false;
		for (TeamInfo team2 : getTeams()) {
			if (team2 == team) {
				Cont = true;
			}
		}
		if (!Cont) {
			return;
		}
		try {
			for (Player Jogadores : Bukkit.getOnlinePlayers()) {
				PacketPlayOut PacketPlayOut = new PacketPlayOut(team.getName(),
						Arrays.asList(new String[]{Jogador}), 3);

				PacketPlayOut.sendToPlayer(Jogadores);
			}
		} catch (Exception e) {
			System.out.println(
					"Failed to send packet for player (Packet209SetScoreboardteam) : ");

			e.printStackTrace();
		}
	}

	private static void MandarPacketsRemoverteams(TeamInfo team, String Jogador)
  {
    boolean Cont = false;
    for (TeamInfo team2 : getTeams()) {
      if (team2 == team) {
        for (String nameDoJogador : getPlayersTeam(team2)) {
          if (nameDoJogador.equals(Jogador)) {
            Cont = true;
          }
        }
      }
    }
    if (!Cont) {
      return;
    }
    try
    {
      for (Player Jogadores : Bukkit.getOnlinePlayers()) {
        PacketPlayOut PacketPlayOut = new PacketPlayOut(team.getName(), Arrays.asList(new String[] { Jogador }), 4);
        PacketPlayOut.sendToPlayer(Jogadores);
      }
    } catch (Exception e) {
      System.out.println("Failed to send packet for player (Packet209SetScoreboardteam) : ");

      e.printStackTrace();
    }
  }

	public static void Resetar() {
		for (TeamInfo team : getTeams())
			removeTeam(team);
	}
	

	  public static LinkedHashMap<String, LinkedHashMap<String, String>> Carregar(JavaPlugin Plugin)
	  {
	    String folder = "plugins/" + Plugin.getName();
	    File folderFile = new File(folder);
	    if (!folderFile.exists()) {
	      folderFile.mkdir();
	    }
	    String path = "plugins/" + Plugin.getName() + "/groups.txt";
	    File source = new File(path);
	    if (source.exists())
	      return CarregarConfig(source);
	    try
	    {
	      source.createNewFile();
	    } catch (IOException e) {
	      Print("Failed to create config file: ");
	      e.printStackTrace();
	    }
	    return CriarConfig(source);
	  }

	  private static LinkedHashMap<String, LinkedHashMap<String, String>> CriarConfig(File target) {
	    PrintWriter out = null;
	    try {
	      out = new PrintWriter(target);
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
	    out.println("// This file declares custom permissions and ties prefixes and suffixes to them.");
	    out.println("// Players who possess these permissions will have the prefix and suffix assigned to the given permission.");
	    out.println("nametag.group.admin prefix = \"[&cAdmin&f] \"");
	    out.println("nametag.group.mod prefix = \"[&bMod&f] \"");
	    out.println("nametag.group.member prefix = \"[&eMember&f] \"");
	    out.println("nametag.group.swag prefix = \"&eThe &b\"");
	    out.println("nametag.group.swag suffix = \" &cSwagmaster\"");

	    LinkedHashMap<String,LinkedHashMap<String, String>> map = new LinkedHashMap<>();

	    LinkedHashMap<String,String> admin = new LinkedHashMap<>();
	    admin.put("prefix", "[&cAdmin&f] ");
	    map.put("nametag.group.admin", admin);

	    LinkedHashMap<String, String> mod = new LinkedHashMap<String, String>();
	    mod.put("prefix", "[&bMod&f] ");
	    map.put("nametag.group.mod", mod);

	    LinkedHashMap<String, String> member = new LinkedHashMap<String, String>();
	    member.put("prefix", "[&eMember&f] ");
	    map.put("nametag.group.member", member);

	    LinkedHashMap<String, String> swag = new LinkedHashMap<String, String>();
	    swag.put("prefix", "&eThe &b");
	    swag.put("suffix", " &cSwagmaster");
	    map.put("nametag.group.swag", swag);

	    out.close();

	    return map;
	  }

	  public static LinkedHashMap<String, LinkedHashMap<String, String>> CarregarConfig(File source) {
	    Scanner in = null;
	    try {
	      in = new Scanner(source);
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }

	    LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<String, LinkedHashMap<String, String>>();

	    boolean syntaxError = false;

	    while (in.hasNext()) {
	      String line = in.nextLine();
	      if ((!line.trim().startsWith("//")) && (!line.isEmpty())) {
	        syntaxError = CheckWords(line);
	        if (syntaxError) {
	          Print("Error in syntax, not enough elements on line!");
	        }

	        Scanner lineScanner = new Scanner(line);

	        String node = lineScanner.next();
	        String operation = lineScanner.next();
	        String equals = lineScanner.next();
	        if (!equals.trim().equals("=")) {
	          Print("Error in syntax, \"=\" expected at third element, \"" + equals + "\" given.");

	          syntaxError = true;
	          break;
	        }
	        String rawValue = lineScanner.nextLine();
	        syntaxError = CheckValue(rawValue);
	        if (syntaxError) {
	          Print("Error in syntax, value not encased in quotation marks!");
	          break;
	        }
	        String value = PegarValue(rawValue);

	        LinkedHashMap<String, String> entry = new LinkedHashMap<String, String>();

	        if (map.get(node) != null) {
	          entry = (LinkedHashMap<String, String>)map.get(node);
	        }

	        entry.put(operation.toLowerCase(), value);

	        if (map.get(node) == null) {
	          map.put(node, entry);
	        }
	        lineScanner.close();
	      }
	    }
	    in.close();

	    if (syntaxError) {
	      return new LinkedHashMap<String, LinkedHashMap<String, String>>();
	    }
	    return map;
	  }

	  private static void Print(String Jogador) {
	    System.out.println("[NAMETAG CONFIG] " + Jogador);
	  }


	  private static boolean CheckWords(String line) {
	    int count = 0;
	    Scanner reader = new Scanner(line);
	    while (reader.hasNext()) {
	      count++;
	      reader.next();
	    }
	    reader.close();
	    if (count >= 4) {
	      return false;
	    }
	    return true;
	  }

	  private static boolean CheckValue(String rawValue) {
	    rawValue = rawValue.trim();
	    if (!rawValue.startsWith("\"")) {
	      return true;
	    }
	    if (!rawValue.endsWith("\"")) {
	      return true;
	    }
	    return false;
	  }

	  private static String PegarValue(String rawValue) {
	    rawValue = rawValue.trim();
	    String f1 = "";
	    String f2 = "";
	    for (int t = 1; t < rawValue.length() - 1; t++) {
	      f1 = f1 + rawValue.charAt(t);
	    }
	    for (int t = 0; (t < f1.length()) && (t < 16); t++) {
	      f2 = f2 + f1.charAt(t);
	    }
	    return f2;
	  }
	  public static String formatColors(String str)
	  {
	    char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f', 'n', 'r', 'l', 'k', 'o', 'm' };
	    char[] array = str.toCharArray();
	    for (int t = 0; t < array.length - 1; t++) {
	      if (array[t] == '&') {
	        for (char c : chars) {
	          if (c == array[(t + 1)]) {
	            array[t] = '§';
	          }
	        }
	      }
	    }
	    return new String(array);
	  }

	  public static void box(String[] paragraph, String title) {
	    ArrayList<String> buffer = new ArrayList<String>();
	    String at = "";

	    int side1 = (int)Math.round(25.0D - (title.length() + 4) / 2.0D);
	    int side2 = (int)(26.0D - (title.length() + 4) / 2.0D);
	    at = at + '+';
	    for (int t = 0; t < side1; t++) {
	      at = at + '-';
	    }
	    at = at + "{ ";
	    at = at + title;
	    at = at + " }";
	    for (int t = 0; t < side2; t++) {
	      at = at + '-';
	    }
	    at = at + '+';
	    buffer.add(at);
	    at = "";
	    buffer.add("|                                                   |");
	    String[] arrayOfString = paragraph; int j = paragraph.length; for (int i = 0; i < j; i++) { String s = arrayOfString[i];
	      at = at + "| ";
	      int left = 49;
	      for (int t = 0; t < s.length(); t++) {
	        at = at + s.charAt(t);
	        left--;
	        if (left == 0) {
	          at = at + " |";
	          buffer.add(at);
	          at = "";
	          at = at + "| ";
	          left = 49;
	        }
	      }
	      while (left-- > 0) {
	        at = at + ' ';
	      }
	      at = at + " |";
	      buffer.add(at);
	      at = "";
	    }
	    buffer.add("|                                                   |");
	    buffer.add("+---------------------------------------------------+");

	    System.out.println(" ");
	    for (String line : (String[])buffer.toArray(new String[buffer.size()])) {
	      System.out.println(line);
	    }
	    System.out.println(" ");
	  }

	  public static String trim(String input) {
	    if (input.length() > 16) {
	      String temp = input;
	      input = "";
	      for (int t = 0; t < 16; t++) {
	        input = input + temp.charAt(t);
	      }
	    }
	    return input;
	  }
	  private static String getValue2(String rawValue) {
		    rawValue = rawValue.trim();
		    String f1 = "";
		    String f2 = "";
		    for (int t = 1; t < rawValue.length() - 1; t++) {
		      f1 = f1 + rawValue.charAt(t);
		    }
		    for (int t = 0; (t < f1.length()) && (t < 16); t++) {
		      f2 = f2 + f1.charAt(t);
		    }
		    return f2;
		  }
	  public static String getValue(String rawValue)
	  {
	    if ((!rawValue.startsWith("\"")) || (!rawValue.endsWith("\""))) {
	      return rawValue;
	    }
	    rawValue = rawValue.trim();
	    String f1 = "";
	    for (int t = 1; t < rawValue.length() - 1; t++) {
	      f1 = f1 + rawValue.charAt(t);
	    }
	    return f1;
	  }

	  public static boolean compareVersion(String old, String newer) {
	    ArrayList<Integer> oldValues = new ArrayList<Integer>();
	    ArrayList<Integer> newValues = new ArrayList<Integer>();
	    String at = "";
	    for (char c : old.toCharArray())
	      if (c != '.') {
	        at = at + c;
	      } else {
	        try {
	          oldValues.add(Integer.valueOf(Integer.parseInt(at)));
	        } catch (Exception e) {
	          return false;
	        }
	        at = "";
	      }
	    try
	    {
	      oldValues.add(Integer.valueOf(Integer.parseInt(at)));
	    } catch (Exception e) {
	      return false;
	    }
	    at = "";
	    for (char c : newer.toCharArray())
	      if (c != '.') {
	        at = at + c;
	      } else {
	        try {
	          newValues.add(Integer.valueOf(Integer.parseInt(at)));
	        } catch (Exception e) {
	          return false;
	        }
	        at = "";
	      }
	    try
	    {
	      newValues.add(Integer.valueOf(Integer.parseInt(at)));
	    } catch (Exception e) {
	      return false;
	    }
	    int size = oldValues.size();
	    boolean defaultToOld = true;
	    if (newValues.size() < size) {
	      size = newValues.size();
	      defaultToOld = false;
	    }
	    for (int t = 0; t < size; t++) {
	      if (((Integer)oldValues.get(t)).intValue() < ((Integer)newValues.get(t)).intValue())
	        return true;
	      if (((Integer)oldValues.get(t)).intValue() > ((Integer)newValues.get(t)).intValue()) {
	        return false;
	      }
	    }
	    if (oldValues.size() == newValues.size()) {
	      return false;
	    }
	    if (defaultToOld) {
	      return true;
	    }
	    return false;
	  }
	 
	  private static String path = null;

	  public static LinkedHashMap<String, LinkedHashMap<String, String>> load(JavaPlugin plugin) {
	    String folder = "plugins/" + plugin.getName();
	    File folderFile = new File(folder);
	    if (!folderFile.exists()) {
	      folderFile.mkdir();
	    }
	    path = "plugins/" + plugin.getName() + "/tags.txt";
	    File source = new File(path);
	    if (source.exists())
	      return loadConfig();
	    try
	    {
	      source.createNewFile();
	    } catch (IOException e) {
	      print("Failed to create config file: ");
	      e.printStackTrace();
	    }
	    return generateConfig(source, plugin);
	  }

	  static void addPlayer(String name, String operation, String value) {
	    ArrayList<String> buffer = new ArrayList<String>();
	    File file = new File(path);
	    Scanner in = null;
	    PrintWriter out = null;
	    value = value.replace("§", "&");
	    try {
	      in = new Scanner(new File(path));
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
	    while (in.hasNext()) {
	      buffer.add(in.nextLine());
	    }
	    in.close();
	    try {
	      out = new PrintWriter(file);
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
	    for (String line : (String[])buffer.toArray(new String[buffer.size()])) {
	      out.println(line);
	    }
	    out.println(name + " " + operation + " = \"" + value + "\"");
	    out.close();
	  }

	  public static void update(String name, String prefix, String suffix) {
	    LinkedHashMap<?, ?> player = getPlayer(name);

	    removePlayer(name, null);
	    if ((prefix != null) && (!prefix.isEmpty())) {
	      prefix = prefix.replace("§", "&");
	      addPlayer(name, "prefix", prefix);
	    } else if ((player != null) && (player.get("prefix") != null)) {
	      addPlayer(name, "prefix", (String)player.get("prefix"));
	    }
	    if ((suffix != null) && (!suffix.isEmpty())) {
	      suffix = suffix.replace("§", "&");
	      addPlayer(name, "suffix", suffix);
	    } else if ((player != null) && (player.get("suffix") != null)) {
	      addPlayer(name, "suffix", (String)player.get("suffix"));
	    }
	  }

	  static void overlap(String name, String prefix, String suffix) {
	    prefix = prefix.replace("§", "&");
	    suffix = suffix.replace("§", "&");
	    removePlayer(name, null);
	    if ((prefix != null) && (!prefix.isEmpty()))
	      addPlayer(name, "prefix", prefix);
	    if ((suffix != null) && (!suffix.isEmpty()))
	      addPlayer(name, "suffix", suffix);
	  }
	  

	  static void removePlayer(String name, String operation)
	  {
	    ArrayList<String> buffer = new ArrayList<String>();
	    File file = new File(path);
	    Scanner in = null;
	    PrintWriter out = null;
	    try {
	      in = new Scanner(new File(path));
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
	    while (in.hasNext()) {
	      buffer.add(in.nextLine());
	    }
	    in.close();
	    try {
	      out = new PrintWriter(file);
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
	    for (String line : (String[])buffer.toArray(new String[buffer.size()])) {
	      Scanner lineScanner = new Scanner(line);

	      String lName = lineScanner.next();
	      String lOperation = lineScanner.next();

	      lineScanner.close();

	      boolean skip = false;

	      if (name.equals(lName)) {
	        if ((operation != null) && (operation.equals(lOperation)))
	          skip = true;
	        else if (operation == null)
	          skip = true;
	      }
	      if (!skip)
	        out.println(line);
	    }
	    out.close();
	  }

	  public static LinkedHashMap<String, String> getPlayer(String name) {
	    LinkedHashMap<?, ?> playerMap = loadConfig();
	    for (String key : (String[])playerMap.keySet().toArray(new String[playerMap.keySet().size()])) {
	      if (key.equals(name)) {
	        return (LinkedHashMap<String, String>)playerMap.get(key);
	      }
	    }
	    return null;
	  }

	  private static LinkedHashMap<String, LinkedHashMap<String, String>> generateConfig(File target, JavaPlugin plugin) {
	    PrintWriter out = null;
	    try {
	      out = new PrintWriter(target);
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }

	    out.close();

	    return loadConfig();
	  }

	  private static LinkedHashMap<String, LinkedHashMap<String, String>> loadConfig() {
	    File source = new File(path);
	    Scanner in = null;
	    try {
	      in = new Scanner(source);
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }

	    LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<String, LinkedHashMap<String, String>>();

	    boolean syntaxError = false;

	    while (in.hasNext()) {
	      String line = in.nextLine();
	      if ((!line.trim().startsWith("//")) && (!line.isEmpty())) {
	        syntaxError = checkWords(line);

	        Scanner lineScanner = new Scanner(line);

	        String node = lineScanner.next();
	        String operation = lineScanner.next();
	        String equals = lineScanner.next();
	        if (!equals.trim().equals("="))
	        {
	          syntaxError = true;
	          break;
	        }
	        String rawValue = lineScanner.nextLine();
	        syntaxError = checkValue(rawValue);
	        if (syntaxError) {
	          break;
	        }
	        String value = getValue(rawValue);

	        LinkedHashMap<String, String> entry = new LinkedHashMap<String, String>();

	        if (map.get(node) != null) {
	          entry = (LinkedHashMap<String, String>)map.get(node);
	        }

	        entry.put(operation.toLowerCase(), value);

	        if (map.get(node) == null) {
	          map.put(node, entry);
	        }
	        lineScanner.close();
	      }
	    }
	    in.close();

	    if (syntaxError)
	      return new LinkedHashMap<String, LinkedHashMap<String, String>>();
	    return map;
	  }

	  private static void print(String p)
	  {
	  }


	  private static boolean checkWords(String line) {
	    int count = 0;
	    Scanner reader = new Scanner(line);
	    while (reader.hasNext()) {
	      count++;
	      reader.next();
	    }
	    reader.close();
	    if (count >= 4) {
	      return false;
	    }
	    return true;
	  }

	  private static boolean checkValue(String rawValue) {
	    rawValue = rawValue.trim();
	    if (!rawValue.startsWith("\""))
	      return true;
	    if (!rawValue.endsWith("\""))
	      return true;
	    return false;
	  }

	
}