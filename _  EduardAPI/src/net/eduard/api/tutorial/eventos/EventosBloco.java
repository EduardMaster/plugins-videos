
package net.eduard.api.tutorial.eventos;

import org.bukkit.event.EventHandler;
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

import net.eduard.api.API;
import net.eduard.api.manager.Manager;

public class EventosBloco extends Manager {

	@EventHandler
	public void BlocoAparecerAosPoucos(BlockFormEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoCairFolhas(LeavesDecayEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoColocar(BlockPlaceEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoDano(BlockDamageEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoDispensar(BlockDispenseEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoEstende(BlockPistonExtendEvent e) {
		API.broadcast(e.getEventName());
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
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoFogaoQueimar(FurnaceSmeltEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoMover(BlockFromToEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoNascer(BlockGrowEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoNotar(NotePlayEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoPegarFogo(BlockBurnEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoPegarFogo(BlockIgniteEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoPodeModificalo(BlockCanBuildEvent e) {
		API.broadcast(e.getEventName());
	}

//	@EventHandler
//	public void BlocoPysical(BlockPhysicsEvent e) {
//		ChatAPI.broadcast(e.getEventName())
//	}

	// Blocos
	@EventHandler
	public void BlocoQuebrar(BlockBreakEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoRedstone(BlockRedstoneEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoRetrai(BlockPistonRetractEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoSpread(BlockSpreadEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoSumirAosPoucos(BlockFadeEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoTrocarSign(SignChangeEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void BlocoXP(BlockExpEvent e) {
		API.broadcast(e.getEventName());
	}
}
