package zLuck.zUteis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;

public class Uteis {
	
	public static String prefix = "§6§lPira§f§lCraft";
	public static String sempermissão = "§cVocê não tem permissão.";
	public static ArrayList<Block> forcefield = new ArrayList<Block>();

    public static void RegistrarConfig() {
        zLuck.getPlugin().saveDefaultConfig();
        File mensagens = new File(zLuck.getPlugin().getDataFolder(), "mensagens.yml");
        if (!mensagens.exists()) {
            mensagens.getParentFile().mkdirs();
            copiarConfig(zLuck.getPlugin().getResource("mensagens.yml"), mensagens);
        }
        zLuck.mensagens = YamlConfiguration.loadConfiguration(new File(zLuck.getPlugin().getDataFolder(), "mensagens.yml"));
        File coliseu = new File(zLuck.getPlugin().getDataFolder(), "coliseu.schematic");
        if (!coliseu.exists()) {
            coliseu.getParentFile().mkdirs();
            copiarConfig(zLuck.getPlugin().getResource("coliseu.schematic"), coliseu);
        }
        File arena = new File(zLuck.getPlugin().getDataFolder(), "arena.schematic");
        if (!arena.exists()) {
        	arena.getParentFile().mkdirs();
            copiarConfig(zLuck.getPlugin().getResource("arena.schematic"), arena);
        }
        File feast = new File(zLuck.getPlugin().getDataFolder(), "feast.schematic");
        if (!feast.exists()) {
        	feast.getParentFile().mkdirs();
            copiarConfig(zLuck.getPlugin().getResource("feast.schematic"), feast);
        }
        File minifeast = new File(zLuck.getPlugin().getDataFolder(), "minifeast.schematic");
        if (!minifeast.exists()) {
        	minifeast.getParentFile().mkdirs();
            copiarConfig(zLuck.getPlugin().getResource("minifeast.schematic"),minifeast);   
        }
    }
    public static void IniciarTempos() {
        Arrays.tempoA1 = 300;
        Arrays.tempoA2 = 120;
        Arrays.tempoA3 = 0;
    }
    public static void DeletarPasta(File folder) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
            	DeletarPasta(file);
            }
            file.delete();
        }
    }
    public static void copiarConfig(InputStream input, File arquivo) {
        try {
            OutputStream out = new FileOutputStream(arquivo);
            byte[] buf = new byte[1024];
            int len;
            while ((len = input.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            input.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    } 
    public static String numeroComPontos(int numero) {
        int ms = numero / 60;
        numero %= 60;
        String m = String.valueOf((ms < 10) ? "0" : "") + ms;
        String s = String.valueOf((numero < 10) ? "0" : "") + numero;
        return String.valueOf(m) + ":" + s;
    }
    public static void adicionarItem(Player p, Material m, int amount, int data, String nome, String desc, Enchantment enchant, int nivel) {
    	ItemStack item = new ItemStack(m,amount,(short)data);
    	ItemMeta itemmeta = item.getItemMeta();
    	itemmeta.setDisplayName(nome);
    	if (desc != null) {
    		ArrayList<String> lore = new ArrayList<String>();
    		lore.add(desc);
    		itemmeta.setLore(lore);
    	}
    	item.setItemMeta(itemmeta);
    	if (enchant != null && nivel != 0) {
    	item.addEnchantment(enchant, nivel);
    	}
    	
    	p.getInventory().addItem(item);
    }
    public static void setarItem(Player p, Material m, int amount, int data, String nome, String desc, Enchantment enchant, int nivel, int slot) {
    	ItemStack item = new ItemStack(m,amount,(short)data);
    	ItemMeta itemmeta = item.getItemMeta();
    	itemmeta.setDisplayName(nome);
    	if (desc != null) {
    		ArrayList<String> lore = new ArrayList<String>();
    		lore.add(desc);
    		itemmeta.setLore(lore);
    	}
    	item.setItemMeta(itemmeta);
    	if (enchant != null && nivel != 0) {
    	item.addUnsafeEnchantment(enchant, nivel);
    	}
    	
    	p.getInventory().setItem(slot,item);
    }   
	public static void setarKit(String perm, Inventory inv, Player p, Material item, int quantidade, int data, String nome, String... descricao) {
		if (p.hasPermission("kit." + perm)) {
			ItemStack icone = new ItemStack(item, quantidade, (short)data);
			ItemMeta iconem = icone.getItemMeta();
		    iconem.setDisplayName(nome);
		    iconem.setLore(java.util.Arrays.asList(descricao));
		    icone.setItemMeta(iconem);
		    
		    inv.addItem(icone);
		}
	}
	public static void setarInv(Inventory inv, Material item, int quantidade, int data, String nome, int slot, String... descricao) {
		ItemStack icone = new ItemStack(item, quantidade, (short)data);
		ItemMeta iconem = icone.getItemMeta();
	    iconem.setDisplayName(nome);
	    iconem.setLore(java.util.Arrays.asList(descricao));
	    icone.setItemMeta(iconem);
	    
	    inv.setItem(slot, icone);
	}
	public static void Hotbar(Player p) {
	    for (PotionEffect effect : p.getActivePotionEffects()) {
  	        p.removePotionEffect(effect.getType());
  	    }
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
				setarItem(p, Material.CHEST, 1, 0, "§6Kits", "§7- Clique para para escolher seu §a§lKIT", null, 0, 0);
		setarItem(p, Material.DIAMOND, 1, 0, "§e§lLoja", "§7- Clique para para comprar a alguns §a§lKIT", null, 0, 4);
		setarItem(p, Material.DIAMOND_SWORD, 1, 0, "§4TreinoPvP", "§7- Clique para para §c§lTreinar§aPvP", null, 0, 8);
	}
	public static void EntrarSpec(Player p) {
        KitAPI.setarKit(p, "Nenhum");
		
		Arrays.jogador.remove(p);
		Arrays.spec.add(p);
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.hidePlayer(p);
			if (!Arrays.jogador.contains(all)) {
				p.hidePlayer(all);
			}
		}
	    for (PotionEffect effect : p.getActivePotionEffects()) {
  	        p.removePotionEffect(effect.getType());
  	    }
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.sendMessage(prefix + " §7Voce entrou no modo §c§lSPECTADOR");
		p.setGameMode(GameMode.CREATIVE);
		setarItem(p, Material.SKULL_ITEM, 1, 0, "§aJogadores", " ", null, 0, 4);
	}
	@SuppressWarnings("deprecation")
	public static void pegarKit(Player p) {
	    if (zLuck.estado == Estado.Iniciando) {
	    	return;
	    }		
		if (KitAPI.kit.get(p).equalsIgnoreCase("Avatar")) {
			Uteis.adicionarItem(p, Material.LAPIS_BLOCK, 1, 0, "§bAvatar §c- §9§lAGUA", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("barbarian")) {
			Uteis.adicionarItem(p, Material.WOOD_SWORD, 1, 0, "§c§oBarbarian 1", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("burstmaster")) {
			Uteis.adicionarItem(p, Material.GOLD_HOE, 1, 0, "§bBurstMaster", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("butterfly")) {
			Uteis.adicionarItem(p, Material.FEATHER, 1, 0, "§bButterfly", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("cactus")) {
			p.getInventory().setHelmet(new ItemStack(Material.CACTUS));
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("cannibal")) {
    		p.getInventory().addItem(new ItemStack(Material.getMaterial(383), 1, (short)98));
    		p.getInventory().addItem(new ItemStack(Material.COOKED_FISH));
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("c4")) {
			Uteis.adicionarItem(p, Material.TNT, 1, 0, "§bC4", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("chemist")) {
    		p.getInventory().addItem(new ItemStack(Material.getMaterial(373), 1, (short)16420));
    		p.getInventory().addItem(new ItemStack(Material.getMaterial(373), 1, (short)16424));
    		p.getInventory().addItem(new ItemStack(Material.getMaterial(373), 1, (short)16428));
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("drain")) {
			Uteis.adicionarItem(p, Material.NETHER_STAR, 1, 0, "§bDrain", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("digger")) {
			Uteis.adicionarItem(p, Material.DRAGON_EGG, 1, 0, "§bDigger", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("endermage")) {
			Uteis.adicionarItem(p, Material.ENDER_PORTAL, 1, 0, "§bEndermage", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("endercult")) {
			Uteis.adicionarItem(p, Material.ENDER_PORTAL, 1, 0, "§bEndercult", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("forcefield")) {
			Uteis.adicionarItem(p, Material.IRON_FENCE, 1, 0, "§bForcefield", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("fireman")) {
			Uteis.adicionarItem(p, Material.BUCKET, 1, 0, "§bFireman", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("fisherman")) {
			Uteis.adicionarItem(p, Material.FISHING_ROD, 1, 0, "§bFisherman", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("flash")) {
			Uteis.adicionarItem(p, Material.REDSTONE_TORCH_ON, 1, 0, "§bFlash", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("forger")) {
			Uteis.adicionarItem(p, Material.COAL, 9, 0, "§bForger", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("fear")) {
			Uteis.adicionarItem(p, Material.GOLD_HOE, 1, 0, "§bFear", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("gambler")) {
			Uteis.adicionarItem(p, Material.STONE_BUTTON, 1, 0, "§bGambler", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("gladiator")) {
			Uteis.adicionarItem(p, Material.IRON_FENCE, 1, 0, "§bGladiator", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("glace")) {
			Uteis.adicionarItem(p, Material.PACKED_ICE, 1, 0, "§bGlace", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("grandpa")) {
			Uteis.adicionarItem(p, Material.STICK, 1, 0, "§bGrandpa", null, Enchantment.KNOCKBACK, 2);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("grappler")) {
			Uteis.adicionarItem(p, Material.LEASH, 1, 0, "§bGrappler", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("icelord")) {
			Uteis.adicionarItem(p, Material.SNOW_BALL, 16, 0, "§bIcelord", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("jackhammer")) {
			Uteis.adicionarItem(p, Material.STONE_PICKAXE, 1, 0, "§bJackHammer", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("kangaroo")) {
			Uteis.adicionarItem(p, Material.FIREWORK, 1, 0, "§bKangaroo", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("lumberjack")) {
			Uteis.adicionarItem(p, Material.STONE_AXE, 1, 0, "§bLumberjack", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("launcher")) {
			Uteis.adicionarItem(p, Material.SPONGE, 20, 0, "§bLauncher", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("miner")) {
			Uteis.adicionarItem(p, Material.STONE_PICKAXE, 1, 0, "§bMiner", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("monk")) {
			Uteis.adicionarItem(p, Material.BLAZE_ROD, 1, 0, "§bMonk", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("phantom")) {
			Uteis.adicionarItem(p, Material.FEATHER, 1, 0, "§bPhantom", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("pyro")) {
			Uteis.adicionarItem(p, Material.BLAZE_POWDER, 1, 0, "§bPyro", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("reaper")) {
			Uteis.adicionarItem(p, Material.GOLD_HOE, 1, 0, "§bReaper", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("switcher")) {
			Uteis.adicionarItem(p, Material.SNOW_BALL, 32, 0, "§bSwitcher", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("scout")) {
			Uteis.adicionarItem(p, Material.POTION, 3, 16386, "§bScout", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("thor")) {
			Uteis.adicionarItem(p, Material.WOOD_AXE, 1, 0, "§bThor", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("timelord")) {
			Uteis.adicionarItem(p, Material.WATCH, 1, 0, "§bTimelord", null, null, 0);
		}
		if (KitAPI.kit.get(p).equalsIgnoreCase("urgal")) {
			Uteis.adicionarItem(p, Material.POTION, 3, 8201, "§bUrgal", null, null, 0);
		}
	}
    @SuppressWarnings("deprecation")
	public static void gerarBorda() {
        for (int x = -400; x <= 400; ++x) {
            if (x == -400 || x == 400) {
                for (int z = -400; z <= 400; ++z) {
                    for (int y = 0; y <= 400; ++y) {
                        Location local = new Location(Bukkit.getWorld("world"), x, y, z);
                        if (!local.getChunk().isLoaded()) {
                            local.getChunk().load();
                        }
                        Block bloco = local.getBlock();
                        forcefield.add(bloco);
                        if (new Random().nextBoolean()) {
                            local.getBlock().setTypeId(155);
                        } else {
                            local.getBlock().setTypeId(86);
                        }
                    }
                }
            }
        }
        for (int z2 = -400; z2 <= 400; ++z2) {
            if (z2 == -400 || z2 == 400) {
                for (int x2 = -400; x2 <= 400; ++x2) {
                    for (int y = 0; y <= 400; ++y) {
                        Location local = new Location(Bukkit.getWorld("world"), x2, y, z2);
                        if (!local.getChunk().isLoaded()) {
                            local.getChunk().load();
                        }
                        Block bloco = local.getBlock();
                        forcefield.add(bloco);
                        if (new Random().nextBoolean()) {
                            local.getBlock().setTypeId(155);
                        } else {
                            local.getBlock().setTypeId(86);
                        }
                    }
                }
            }
        }
    }
    public static String nomes(ItemStack item) {
    	String nome;
        if (item.getType() == Material.AIR) {
            nome = "§b§lSoco";
        } else if (item.getType() == Material.WOOD_SWORD) {
            nome = "§b§lEspada de Madeira";
        } else if (item.getType() == Material.STONE_SWORD) {
            nome = "§b§lEspada de Pedra";
        } else if (item.getType() == Material.IRON_SWORD) {
            nome = "§b§lEspada de Ferro";
        } else if (item.getType() == Material.DIAMOND_SWORD) {
            nome = "§b§lEspada de Diamante";
        } else if (item.getType() == Material.GOLD_SWORD) {
            nome = "§b§lEspada de Ouro";
        } else if (item.getType() == Material.WOOD_AXE) {
            nome = "§b§lMachado de Madeira";
        } else if (item.getType() == Material.BOWL) {
            nome = "§b§lPote";
        } else if (item.getType() == Material.STONE_AXE) {
            nome = "§b§lMachado de Pedra";
        } else if (item.getType() == Material.IRON_AXE) {
            nome = "§b§lMachado de Ferro";
        } else if (item.getType() == Material.DIAMOND_AXE) {
            nome = "§b§lMachado de Diamante";
        } else if (item.getType() == Material.GOLD_AXE) {
            nome = "§b§lMachado de Ouro";
        } else if (item.getType() == Material.WOOD_SPADE) {
            nome = "§b§lPá de Madeira";
        } else if (item.getType() == Material.STONE_SPADE) {
            nome = "§b§lPá de Pedra";
        } else if (item.getType() == Material.IRON_SPADE) {
            nome = "§b§lPá de Ferro";
        } else if (item.getType() == Material.DIAMOND_SPADE) {
            nome = "§b§lPá de Diamante";
        } else if (item.getType() == Material.GOLD_SPADE) {
            nome = "§b§lPá de Ouro";
        } else if (item.getType() == Material.WOOD_PICKAXE) {
            nome = "§b§lPicareta de Madeira";
        } else if (item.getType() == Material.STONE_PICKAXE) {
            nome = "§b§lPicareta de Pedra";
        } else if (item.getType() == Material.IRON_PICKAXE) {
            nome = "§b§lPicareta de Ferro";
        } else if (item.getType() == Material.DIAMOND_PICKAXE) {
            nome = "§b§lPicareta de Diamante";
        } else if (item.getType() == Material.GOLD_PICKAXE) {
            nome = "§b§lPicareta de Ouro";
        } else if (item.getType() == Material.STICK) {
            nome = "§b§lGraveto";
        } else if (item.getType() == Material.MUSHROOM_SOUP) {        	
            nome = "§b§lSopa";
        } else if (item.getType() == Material.BOWL) {
            nome = "§b§lTigéla";
        } else if (item.getType() == Material.COMPASS) {
            nome = "§b§lBússola";
        } else {
            nome = "§b§l"+item.getType().toString();
        }
        return nome;
    }
	@SuppressWarnings("deprecation")
	public static void ReceitasSopa() {
		
        ItemStack sopaA = new ItemStack(Material.MUSHROOM_SOUP, 1);    
        ShapelessRecipe ingredientesA = new ShapelessRecipe(sopaA);
        ingredientesA.addIngredient(1, Material.getMaterial(39));
        ingredientesA.addIngredient(1, Material.getMaterial(40));
        ingredientesA.addIngredient(1, Material.BOWL);
        zLuck.getPlugin().getServer().addRecipe(ingredientesA);
		
        ItemStack sopaA1 = new ItemStack(Material.MUSHROOM_SOUP, 1);
        ShapelessRecipe ingredientesA1 = new ShapelessRecipe(sopaA1);
        ingredientesA1.addIngredient(1, Material.getMaterial(351), (short)3);
        ingredientesA1.addIngredient(1, Material.BOWL);
        zLuck.getPlugin().getServer().addRecipe(ingredientesA1);
        
        ItemStack sopaA2 = new ItemStack(Material.MUSHROOM_SOUP, 1);   
        ShapelessRecipe ingredientesA2 = new ShapelessRecipe(sopaA2);
        ingredientesA2.addIngredient(1, Material.CACTUS);
        ingredientesA2.addIngredient(1, Material.CACTUS);
        ingredientesA2.addIngredient(1, Material.BOWL);
        zLuck.getPlugin().getServer().addRecipe(ingredientesA2);
        
        ItemStack sopaA3 = new ItemStack(Material.MUSHROOM_SOUP, 1);
        ShapelessRecipe ingredientesA3 = new ShapelessRecipe(sopaA3);
        ingredientesA3.addIngredient(1, Material.RED_ROSE);
        ingredientesA3.addIngredient(1, Material.YELLOW_FLOWER);
        ingredientesA3.addIngredient(1, Material.BOWL);
        zLuck.getPlugin().getServer().addRecipe(ingredientesA3);
        
        ItemStack sopaA4 = new ItemStack(Material.MUSHROOM_SOUP, 1);
        ShapelessRecipe ingredientesA4 = new ShapelessRecipe(sopaA4);
        ingredientesA4.addIngredient(1, Material.NETHER_STALK);
        ingredientesA4.addIngredient(1, Material.NETHER_STALK);
        ingredientesA4.addIngredient(1, Material.BOWL);
        zLuck.getPlugin().getServer().addRecipe(ingredientesA4);
        
        ItemStack sopaA5 = new ItemStack(Material.MUSHROOM_SOUP, 1);
        ShapelessRecipe ingredientesA5 = new ShapelessRecipe(sopaA5);
        ingredientesA5.addIngredient(1, Material.PUMPKIN_SEEDS);
        ingredientesA5.addIngredient(1, Material.PUMPKIN_SEEDS);
        ingredientesA5.addIngredient(1, Material.BOWL);
        zLuck.getPlugin().getServer().addRecipe(ingredientesA5);
        
        ItemStack sopaA6 = new ItemStack(Material.MUSHROOM_SOUP, 1);
        ShapelessRecipe ingredientesA6 = new ShapelessRecipe(sopaA6);
        ingredientesA6.addIngredient(1, Material.MELON_SEEDS);
        ingredientesA6.addIngredient(1, Material.MELON_SEEDS);
        ingredientesA6.addIngredient(1, Material.BOWL);
        zLuck.getPlugin().getServer().addRecipe(ingredientesA6);
        
        ItemStack sopaA7 = new ItemStack(Material.MUSHROOM_SOUP, 1);
        ShapelessRecipe ingredientesA7 = new ShapelessRecipe(sopaA7);
        ingredientesA7.addIngredient(1, Material.WHEAT);
        ingredientesA7.addIngredient(1, Material.WHEAT);
        ingredientesA7.addIngredient(1, Material.BOWL);
        zLuck.getPlugin().getServer().addRecipe(ingredientesA7);
    }

}
