package zLuck.zComandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import zLuck.zAPIs.KitAPI;
import zLuck.zGuis.KitGui;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Centro;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Kit implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("kit")) {
			if (args.length == 0) {
				KitGui.gui(p);
				return true;
			}
			if (args.length == 1) {
	            if (zLuck.estado == Estado.Proteção && !KitAPI.getKit(p).equalsIgnoreCase("Nenhum")) {
	                p.sendMessage(Uteis.prefix + " §cVoce nao pode escolher um kit apos a partida ja ter inicializada.");
	                return true; 
	            }
	            if (zLuck.estado == Estado.Jogo && Arrays.tempoA3 >= 300) {
	                p.sendMessage(Uteis.prefix + " §cVoce nao pode usar isto agora.");           	    
	                return true; 
	            }
	            if (zLuck.estado == Estado.Jogo && !KitAPI.getKit(p).equalsIgnoreCase("Nenhum")) {
	                p.sendMessage(Uteis.prefix + " §cVoce nao pode escolher um kit apos a partida ja ter inicializada.");
	                return true; 
	            }				
				if (args[0].equalsIgnoreCase("achilles")) {
	                if (p.hasPermission("kit.achilles")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
					    Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("anchor")) {
	                if (p.hasPermission("kit.anchor")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("ajnin")) {
	                if (p.hasPermission("kit.ajnin")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("avatar")) {
	                if (p.hasPermission("kit.avatar")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("barbarian")) {
	                if (p.hasPermission("kit.barbarian")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("berserker")) {
	                if (p.hasPermission("kit.berserker")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("boxer")) {
	                if (p.hasPermission("kit.boxer")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("burstmaster")) {
	                if (p.hasPermission("kit.burstmaster")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("butterfly")) {
	                if (p.hasPermission("kit.butterfly")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("cactus")) {
	                if (p.hasPermission("kit.cactus")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("cannibal")) {
	                if (p.hasPermission("kit.cannibal")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("cookiemonster")) {
	                if (p.hasPermission("kit.cookiemonster")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("cultivator")) {
	                if (p.hasPermission("kit.cultivator")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("creeper")) {
	                if (p.hasPermission("kit.creeper")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("c4")) {
	                if (p.hasPermission("kit.c4")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("copycat")) {
	                if (p.hasPermission("kit.copycat")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("chemist")) {
	                if (p.hasPermission("kit.chemist")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("drain")) {
	                if (p.hasPermission("kit.drain")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("digger")) {
	                if (p.hasPermission("kit.digger")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("endermage")) {
	                if (p.hasPermission("kit.endermage")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("endercult")) {
	                if (p.hasPermission("kit.endercult")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("explorer")) {
	                if (p.hasPermission("kit.explorer")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("forcefield")) {
	                if (p.hasPermission("kit.forcefield")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("fireman")) {
	                if (p.hasPermission("kit.fireman")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("fisherman")) {
	                if (p.hasPermission("kit.fisherman")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("flash")) {
	                if (p.hasPermission("kit.flash")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("forger")) {
	                if (p.hasPermission("kit.forger")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("fear")) {
	                if (p.hasPermission("kit.fear")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("gambler")) {
	                if (p.hasPermission("kit.gambler")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("gladiator")) {
	                if (p.hasPermission("kit.gladiator")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("glace")) {
	                if (p.hasPermission("kit.glace")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("grandpa")) {
	                if (p.hasPermission("kit.grandpa")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("grappler")) {
	                if (p.hasPermission("kit.grappler")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("hulk")) {
	                if (p.hasPermission("kit.hulk")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("icelord")) {
	                if (p.hasPermission("kit.icelord")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("jackhammer")) {
	                if (p.hasPermission("kit.jackhammer")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("kangaroo")) {
	                if (p.hasPermission("kit.kangaroo")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("lumberjack")) {
	                if (p.hasPermission("kit.lumberjack")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("launcher")) {
	                if (p.hasPermission("kit.launcher")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("magma")) {
	                if (p.hasPermission("kit.magma")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("miner")) {
	                if (p.hasPermission("kit.miner")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("monk")) {
	                if (p.hasPermission("kit.monk")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
				        Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("ninja")) {
	                if (p.hasPermission("kit.ninja")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("phantom")) {
	                if (p.hasPermission("kit.phantom")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("poseidon")) {
	                if (p.hasPermission("kit.poseidon")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("pyro")) {
	                if (p.hasPermission("kit.pyro")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("reaper")) {
	                if (p.hasPermission("kit.reaper")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("snail")) {
	                if (p.hasPermission("kit.snail")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("stomper")) {
	                if (p.hasPermission("kit.stomper")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("switcher")) {
	                if (p.hasPermission("kit.switcher")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("scout")) {
	                if (p.hasPermission("kit.scout")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("thor")) {
	                if (p.hasPermission("kit.thor")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("timelord")) {
	                if (p.hasPermission("kit.timelord")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("tower")) {
	                if (p.hasPermission("kit.tower")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("urgal")) {
	                if (p.hasPermission("kit.urgal")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Uteis.pegarKit(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("viking")) {
	                if (p.hasPermission("kit.viking")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("viper")) {
	                if (p.hasPermission("kit.viper")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
				if (args[0].equalsIgnoreCase("tower")) {
	                if (p.hasPermission("kit.tower")) {
						KitAPI.setarKit(p, args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase());
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Kit §6§l" + args[0].substring(0,1).toUpperCase() + args[0].substring(1).toLowerCase() + " §7selecionada com sucesso");
	                } else {
	                    p.sendMessage(Uteis.prefix + " §cVocê não possui este KIT!");
	                }
				}
			}
		}
		return false;
	}

}
