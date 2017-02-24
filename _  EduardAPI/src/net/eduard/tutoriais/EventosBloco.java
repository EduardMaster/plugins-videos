
package net.eduard.tutoriais;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

public class EventosBloco implements Listener {

	@EventHandler
	public void BlocoAparecerAosPoucos(BlockFormEvent e) {

	}

	@EventHandler
	public void BlocoCairFolhas(LeavesDecayEvent e) {

	}

	@EventHandler
	public void BlocoColocar(BlockPlaceEvent e) {

	}

	@EventHandler
	public void BlocoDano(BlockDamageEvent e) {

	}

	@EventHandler
	public void BlocoDispensar(BlockDispenseEvent e) {

	}

	@EventHandler
	public void BlocoEstende(BlockPistonExtendEvent e) {

	}

	// PARA 1.8
	// @EventHandler
	// public void BlocoColocarVarios(BlockMultiPlaceEvent e) {
	// }
	@EventHandler
	public void BlocoFermentar(BrewEvent e) {

	}

	@EventHandler
	public void BlocoFogaoCozinhar(FurnaceBurnEvent e) {

	}

	@EventHandler
	public void BlocoFogaoQueimar(FurnaceSmeltEvent e) {

	}

	@EventHandler
	public void BlocoMover(BlockFromToEvent e) {

	}

	@EventHandler
	public void BlocoNascer(BlockGrowEvent e) {

	}

	@EventHandler
	public void BlocoNotar(NotePlayEvent e) {

	}

	@EventHandler
	public void BlocoPegarFogo(BlockBurnEvent e) {

	}

	@EventHandler
	public void BlocoPegarFogo(BlockIgniteEvent e) {

	}

	@EventHandler
	public void BlocoPistao(BlockPistonEvent e) {

	}

	@EventHandler
	public void BlocoPodeModificalo(BlockCanBuildEvent e) {

	}

	@EventHandler
	public void BlocoPysical(BlockPhysicsEvent e) {

	}

	// Blocos
	@EventHandler
	public void BlocoQuebrar(BlockBreakEvent e) {

	}

	@EventHandler
	public void BlocoRedstone(BlockRedstoneEvent e) {

	}

	@EventHandler
	public void BlocoRetrai(BlockPistonRetractEvent e) {

	}

	@EventHandler
	public void BlocoSpread(BlockSpreadEvent e) {

	}

	@EventHandler
	public void BlocoSumirAosPoucos(BlockFadeEvent e) {

	}

	@EventHandler
	public void BlocoTrocarSign(SignChangeEvent e) {

	}

	@EventHandler
	public void BlocoXP(BlockExpEvent e) {

	}
}
