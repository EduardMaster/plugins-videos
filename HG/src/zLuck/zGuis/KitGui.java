package zLuck.zGuis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import zLuck.zMain.zLuck;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class KitGui implements Listener{
	
	@SuppressWarnings("deprecation")
	public static void gui(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54, "§aSeus kits");
		
		ItemStack icone = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
		ItemMeta iconem = icone.getItemMeta();
	    iconem.setDisplayName(" ");
	    icone.setItemMeta(iconem);
	    
		ItemStack iconeamarelo = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
		ItemMeta iconeamarelom = iconeamarelo.getItemMeta();
		iconeamarelom.setDisplayName(" ");
		iconeamarelo.setItemMeta(iconeamarelom);
	    
		Uteis.setarInv(inv, Material.CARPET, 1, 14, " ", 0, " ");
		Uteis.setarInv(inv, Material.STAINED_GLASS_PANE, 1, 15, " ", 1, " ");
		Uteis.setarInv(inv, Material.STAINED_GLASS_PANE, 1, 15, " ", 2, " ");
		Uteis.setarInv(inv, Material.INK_SACK, 1, 14, Uteis.prefix, 3, "");
		Uteis.setarInv(inv, Material.CAKE, 1, 0, "§6§lSurprise", 4, "§7Ganhe um kit aleátorio, mesmo nao tendo-o");
		Uteis.setarInv(inv, Material.INK_SACK, 1, 12, Uteis.prefix, 5, "");
		Uteis.setarInv(inv, Material.STAINED_GLASS_PANE, 1, 15, " ", 6, " ");
		Uteis.setarInv(inv, Material.STAINED_GLASS_PANE, 1, 15, " ", 7, " ");
		Uteis.setarInv(inv, Material.CARPET, 1, 13, "§aAvançar", 8, "  ");
		
		for (int i = 0; i < 9; i++) {
			inv.setItem(9 + i, iconeamarelo);
		}
		
		Uteis.setarKit("achilles", inv, p, Material.WOOD_SWORD, 1, 0, "§6§lAchilles", "§7§7Tome menos dano de espada de Pedra/Ferro/Diamante,", "§7porém, espada de madeira dará o dano de uma de Diamante.");
		Uteis.setarKit("anchor", inv, p, Material.ANVIL, 1, 0, "§6§lAnchor", "§7Nao receba, nem tome §c§lKNOCKBACK");
		Uteis.setarKit("ajnin", inv, p, Material.NETHER_STAR, 1, 0, "§6§lAjnin", "§7Seja um ajnin para ir aos seus inimigos rapidamente");
		Uteis.setarKit("avatar", inv, p, Material.LAPIS_BLOCK, 1, 0, "§6§lAvatar", "§7Domine os 4 poderes §4§lFOGO§7, §9§lAGUA§7, §2§lTERRA §7e §f§lAR");
		Uteis.setarKit("barbarian", inv, p, Material.STONE_SWORD, 1, 0, "§6§lBarbarian", "§7Evolua sua espada a cada kill, e atinja o nivel maximo!");
		Uteis.setarKit("berserker", inv, p, Material.POTION, 1, 16418, "§6§lBerserker", "§7Ao matar os inimigos, voce ira receber forca e speed");
		Uteis.setarKit("boxer", inv, p, Material.NETHER_STAR, 1, 0, "§6§lBoxer", "§7Receba 0.5 coracoes a menos e dêe 0.5 de dano extra");
		Uteis.setarKit("burstmaster", inv, p, Material.GOLD_HOE, 1, 0, "§6§lBurstmaster", "§7Mande uma bola de fogo para criar uma explosão");
		Uteis.setarKit("butterfly", inv, p, Material.FEATHER, 1, 0, "§6§lButterfly", "§7Lançe uma bola de neve e monte em cima", "§7dela e tomará um impulso para frente.");
		Uteis.setarKit("cactus", inv, p, Material.CACTUS, 1, 0, "§6§lCactus", "§7Cause reflexos de 1 dano de PvP");
		Uteis.setarKit("camel", inv, p, Material.SAND, 1, 0, "§6§lCamel", "§7Ao andar sobre a areia, ganhe regeneracao e velocidade");
		Uteis.setarKit("cannibal", inv, p, Material.getMaterial(383), 1, 98, "§6§lCannibal", "§7Inicie a partida com uma habilidade que,", "§7ao bater em um player, roube fome dele para voce.");
		Uteis.setarKit("cookiemonster", inv, p, Material.COOKIE, 1, 0, "§6§lCookiemonster", "§7Ao quebrar uma grama tenha 80% de chance,", "§7de dropar um Cookie, e ao comer, fique com Speed II");
		Uteis.setarKit("cultivator", inv, p, Material.SAPLING, 1, 0, "§6§lCultivator", "§7Ao plantar sementes, faça as crescer rapidamente.");
		Uteis.setarKit("creeper", inv, p, Material.getMaterial(397), 1, 4, "§6§lCreeper", "§7Não tome dano de explosões, e ao morrer, voce", "§7explodira tendo chance de matar o inimigo");
		Uteis.setarKit("c4", inv, p, Material.TNT, 1, 0, "§6§lC4", "§7Plante uma bomba e exploda quando quiser!");
		Uteis.setarKit("copycat", inv, p, Material.getMaterial(37), 1, 0, "§6§lCopycat", "§7Inicie a partida com uma habilidade que,", "§7ao matar um player, você recebera o kit dele.");
		Uteis.setarKit("chemist", inv, p, Material.getMaterial(373), 1, 16420, "§6§lChemist", "§7Inicie a partida com com 3 poções, sendo elas:", "§cDano Instantâneo II, Fraqueza I por 1:07, Veneno II por 0:16");
		Uteis.setarKit("drain", inv, p, Material.NETHER_STAR, 1, 0, "§6§lDrain", "§7Você poderá drenar a vida do seus inimigos, que", "§7não estejam com o kit Drain em um raio de 5 blocos.");
		Uteis.setarKit("digger", inv, p, Material.DRAGON_EGG, 1, 0, "§6§lDigger", "§7Inicie a partida com 5 Dragon Egg's,", "§7e ao colocar no chão abrirá um buraco de 5x5.");
		Uteis.setarKit("endermage", inv, p, Material.ENDER_PORTAL, 1, 0, "§6§lEndermage", "§7Inicie a partida com um portal que ao colocar ele no chão,", "§7teleportara para você os players que", "§7estão acima ou abaixo de sua localização.");
		Uteis.setarKit("endercult", inv, p, Material.VINE, 1, 0, "§6§lEndercult", "§7Inicie a partida com a junção de outras", "§7duas habilidades, Endermage e Cultivator");
		Uteis.setarKit("explorer", inv, p, Material.MAP, 1, 0, "§6§lExplorer", "§7Inicie a partida com a habilidade de ao nascer um", "§7minifeast/feast, você receberá as coordenadas exatas.");
		Uteis.setarKit("forcefield", inv, p, Material.IRON_FENCE, 1, 0, "§6§lForcefield", "§7Inicie a partida com o item que criará um", "§7campo de força (Forcefield) que dará dano");
		Uteis.setarKit("fireman", inv, p, Material.STATIONARY_LAVA, 1, 0, "§6§lFireman", "§7Inicie a partida com um balde de água e uma habilidade", "§7que ao entrar na lava, não tomara o dano dela");
		Uteis.setarKit("fisherman", inv, p, Material.FISHING_ROD, 1, 0, "§6§lFisherman", "§7Inicie a partida com uma vara de pesca que", "§7ao fisgar um player, teleporte ele ate você.");
		Uteis.setarKit("flash", inv, p, Material.REDSTONE_TORCH_ON, 1, 0, "§6§lFlash", "§7Inicie a partida com uma tocha de redstone, que", "§7ao clicar com o botão direito do mouse,", "§7será teleportado para onde você estiver olhando.");
		Uteis.setarKit("forger", inv, p, Material.COAL, 1, 0, "§6§lForger", "§7Inicie a partida com 9 carvões, ao clicar com eles", "§7em um minério de ferro, eles derreterão automaticamente.");
		Uteis.setarKit("fear", inv, p, Material.GOLD_HOE, 1, 0, "§6§lFear", "§7Ao clicar no oponente, deixe-o", "§7aterrorizado nos próximos 5 segundos.");
		Uteis.setarKit("gambler", inv, p, Material.STONE_BUTTON, 1, 0, "§6§lGambler", "§7Inicie a partida com um botão que ao aperta-lo", "§7você, ganha um efeito aleatórios.");
		Uteis.setarKit("gladiator", inv, p, Material.IRON_FENCE, 1, 0, "§6§lGladiator", "§7Inicie a partida com uma barra de ferro, que ao clicar", "§7no seu adversário, será teleportado para uma arena de vidro", "§7acima de vocês para tirarem um 1v1");
		Uteis.setarKit("glace", inv, p, Material.PACKED_ICE, 1, 0, "§6§lGlase", "§7Inicie a partida com a habilidade de ao bater em um jogador", "§7apos clicar em seu item, você faz o jogador levar", "§7uma repulsão e ficar congelado por 3 segundos");
		Uteis.setarKit("grandpa", inv, p, Material.STICK, 1, 0, "§6§lGrandpa", "§7Inicie a partida com um graveto de madeira,", "§7que ao bater em seu adversário, tem chance", "§7de dar um knockback extremo.");
		Uteis.setarKit("grappler", inv, p, Material.LEASH, 1, 0, "§6§lGrappler", "§7Inicie a partida com um laço que fará você se", "§7locomover mais rápido pelo mapa.");
		Uteis.setarKit("hulk", inv, p, Material.EMERALD_BLOCK, 1, 0, "§6§lHulk", "§7Inicie a partida com uma habilidade que ao clicar", "§7com o direito em seu inimigo, coloque ele em", "§7sua cabeça e ataque-o sem parar.");
		Uteis.setarKit("icelord", inv, p, Material.SNOW_BALL, 1, 0, "§6§lIcelord", "§7Inicie a partida com 16 bolas de neve, ao", "§7acertar um jogador ele perde 3 de corações", "§7e é congelado por 1 segundo.");
		
		for (int i = 0; i < 54; i++) {
			if (inv.getItem(i) == null) {
				inv.setItem(i, icone);
			}
		}
		
		p.openInventory(inv);
	}
	public static void gui1(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54, "§cSeus kits");
		
		ItemStack icone = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
		ItemMeta iconem = icone.getItemMeta();
	    iconem.setDisplayName(" ");
	    icone.setItemMeta(iconem);
	    icone.setAmount(0);
	    
		ItemStack iconeamarelo = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
		ItemMeta iconeamarelom = iconeamarelo.getItemMeta();
		iconeamarelom.setDisplayName(" ");
		iconeamarelo.setItemMeta(iconeamarelom);
	    
		Uteis.setarInv(inv, Material.CARPET, 1, 14, "§cRetornar", 0, " ");
		Uteis.setarInv(inv, Material.STAINED_GLASS_PANE, 1, 15, " ", 1, " ");
		Uteis.setarInv(inv, Material.STAINED_GLASS_PANE, 1, 15, " ", 2, " ");
		Uteis.setarInv(inv, Material.INK_SACK, 1, 14, Uteis.prefix, 3, "");
		Uteis.setarInv(inv, Material.CAKE, 1, 0, "§6§lSurprise", 4, "§7Ganhe um kit aleátorio, mesmo nao tendo-o");
		Uteis.setarInv(inv, Material.INK_SACK, 1, 12, Uteis.prefix, 5, "");
		Uteis.setarInv(inv, Material.STAINED_GLASS_PANE, 1, 15, " ", 6, " ");
		Uteis.setarInv(inv, Material.STAINED_GLASS_PANE, 1, 15, " ", 7, " ");
		Uteis.setarInv(inv, Material.CARPET, 1, 13, " ", 8, "  ");
		
		for (int i = 0; i < 9; i++) {
			inv.setItem(9 + i, iconeamarelo);
		}
		
		Uteis.setarKit("jackhammer", inv, p, Material.STONE_PICKAXE, 1, 0, "§6§lJackhammer", "§7Inicie a partida com um Machado de Pedra que", "§7ao usá-lo para quebrar um bloco, ele destruirá", "§7todos em sua camada(Tanto para cima, quanto para baixo).");
		Uteis.setarKit("kangaroo", inv, p, Material.FIREWORK, 1, 0, "§6§lKangaroo", "§7Inicie a partida com um fogo de artificio, que ao", "§7clicar com o botão direito, de um BOOST, para frente", "§7mais fique relaxado com torres, você tem uma", "§7habilidade que não morrerá de queda.");
		Uteis.setarKit("lumberjack", inv, p, Material.STONE_AXE, 1, 0, "§6§lLumberjack", "§7§7Inicie a partida com um machado de pedra,", "§7fazendo assim, que ao quebrar o bloco de base", "§7de alguma arvore, quebre os demais que ela contenha.");
		Uteis.setarKit("launcher", inv, p, Material.SPONGE, 20, 0, "§6§lLauncher", "§7Inicie a partida com 20 esponjas use elas para", "§7lançar os inimigos para a direção que você", "§7colocou a esponja!");
		Uteis.setarKit("magma", inv, p, Material.FIRE, 1, 0, "§6§lMagma", "§7Você não leva dano de lava/fogo, e ao bater em", "§7um jogador tem 10% de deixar o alvo queimando", "§7por 4 segundos.");
		Uteis.setarKit("miner", inv, p, Material.STONE_PICKAXE, 1, 0, "§6§lMiner", "§7Inicie a partida com Picareta de Pedra. Ao quebrar", "§7algum Minério, todos que estiverem grudados", "§7serão quebrados também.");
		Uteis.setarKit("monk", inv, p, Material.BLAZE_ROD, 1, 0, "§6§lMonk", "§7Inicie a partida com uma Blaze, ao clicar em um", "§7jogador, transfira o item que ele estiver", "§7selecionado na mão para o inventário dele!");
		Uteis.setarKit("ninja", inv, p, Material.NETHER_STAR, 1, 0, "§6§lNinja", "§7Inicie a partida com uma habilidade que ao bater", "§7em seu inimigo, ao apertar a tecla Shift,", "§7seja teleportado ate ele.");
		Uteis.setarKit("phantom", inv, p, Material.FEATHER, 1, 0, "§6§lPhantom", "§7Inicie a partida com uma Pena, que ao clicar", "§7com o direito nela, tenha 5 segundos de voo.");
		Uteis.setarKit("poseidon", inv, p, Material.STATIONARY_WATER, 1, 0, "§6§lPoseidon", "§7Inicie a partida com uma habilidade que ao andar", "§7em águas, fique com Força por 5 segundos.");
		Uteis.setarKit("pyro", inv, p, Material.BLAZE_POWDER, 1, 0, "§6§lPyro", "§7Inicie a partida com Bolas de Fogo arremessáveis.");
		Uteis.setarKit("reaper", inv, p, Material.GOLD_HOE, 1, 0, "§6§lReaper", "§7Inicie a partida com uma Enxada de ouro, que ao", "§7bater no player com ela, tenha 33% de chance de", "§7deixar seu inimigo com o efeito Whiter.");
		Uteis.setarKit("snail", inv, p, Material.SOUL_SAND, 1, 0, "§6§lSnail", "§7Inicie a partida com um grade de ferro e escolha", "§7um jogador para prendê-lo 10 blocos a cima de você.");
		Uteis.setarKit("stomper", inv, p, Material.IRON_BOOTS, 1, 0, "§6§lStomper", "§7Inicie a partida com uma habilidade que causa dano", "§7ao cair em cima de players, ao cair de alguns blocos,", "§7tome 2 corações de queda.");
		Uteis.setarKit("switcher", inv, p, Material.SNOW_BALL, 32, 0, "§6§lSwitcher", "§7Inicie a partida com algumas bolinhas de neve, que", "§7ao acertar outro jogador fará com que vocês troquem de posição.");
		Uteis.setarKit("scout", inv, p, Material.POTION, 3, 16386, "§6§lScout", "§7Inicie a partida com uma habilidade que poderá", "§7receber 3 poções de Speed II");
		Uteis.setarKit("thor", inv, p, Material.WOOD_AXE, 1, 0, "§6§lThor", "§7Inicie a partida com um poder que ao clicar,", "§7crie um raio nesta localização.");
		Uteis.setarKit("timelord", inv, p, Material.WATCH, 1, 0, "§6§lTimelord", "§7Inicie a partida com um relógio, que ao clicar com o", "§7direito, ira congelar seus inimigos mais próximos por alguns segundos.");
		Uteis.setarKit("tower", inv, p, Material.DIRT, 1, 0, "§6§lTower", "§7Inicie a partida com as habilidades do kit Stomper e Worm.");
		Uteis.setarKit("urgal", inv, p, Material.POTION, 3, 8201, "§6§lUrgal", "§7Inicie a partida com 3 poções de força. E assim", "§7ao longo da partida ganhe vantagens em seus inimigos.");
		Uteis.setarKit("viking", inv, p, Material.STONE_AXE, 1, 0, "§6§lViking", "§7Inicie a partida com uma habilidade que ao bater em um", "§7player com um Machado de madeira, dará o dano de uma espada", "§7de pedra, e assim em diante.");
		Uteis.setarKit("viper", inv, p, Material.FERMENTED_SPIDER_EYE, 1, 0, "§6§lViper", "§7Inicie a partida com uma habilidade que ao bater em", "§7seus inimigo terá 33% de chance de deixa-lo com Veneno.");
		Uteis.setarKit("worm", inv, p, Material.DIRT, 1, 0, "§6§lWorm", "§7Inicie a partida com uma habilidade que ao bater", "§7em uma terra, ela dropara automaticamente e servira", "§7para regenerar sua fome e vida.");
		
		for (int i = 0; i < 54; i++) {
			if (inv.getItem(i) == null) {
				inv.setItem(i, icone);
			}
		}
		
		p.openInventory(inv);
	}
	
	@EventHandler
	private void clicarbau(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.CHEST && zLuck.estado == Estado.Iniciando) {

			p.playSound(p.getLocation(), Sound.LEVEL_UP,1.0F, 1.0F);
			e.setCancelled(true);
		}
	}	
	@EventHandler
	private void clicarinv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if (e.getInventory().getName().equalsIgnoreCase("§6Kits")) {
			if (e.getCurrentItem() == null) {
				e.setCancelled(true);
				return;
			}
			if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
				e.setCancelled(true);
				p.closeInventory();
				return;
			}
			if (e.getCurrentItem().getType() == Material.IRON_FENCE) {
				e.setCancelled(true);
				p.closeInventory();
				return;
			}
			p.chat("/kit " + ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().replace("KIT - ", "")));
			p.closeInventory();
			e.setCancelled(true);
		}
	}

}
