
package me.eduard.template;

import org.bukkit.conversations.PlayerNamePrompt;
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
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class Eventos implements Listener {

	@EventHandler
	public void BlocoAparecerAosPoucos(BlockFormEvent e) {

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

	// @EventHandler
	// public void BlocoColocarVarios(BlockMultiPlaceEvent e) {
	//
	// }
	@EventHandler
	public void BlocoMover(BlockFromToEvent e) {

	}

	@EventHandler
	public void BlocoNascer(BlockGrowEvent e) {

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
	public void BlocoXP(BlockExpEvent e) {

	}

	@EventHandler
	public void EntidadeAtiraComFlecha(EntityShootBowEvent e) {

	}

	@EventHandler
	public void EntidadeBlocoFormar(EntityBlockFormEvent e) {

	}

	@EventHandler
	public void EntidadeCriarPortal(EntityCreatePortalEvent e) {

	}

	@EventHandler
	public void EntidadeDano(EntityDamageEvent e) {

	}

	@EventHandler
	public void EntidadeDanoPorBloco(EntityDamageByBlockEvent e) {

	}

	@EventHandler
	public void EntidadeDanoPorEntidade(EntityDamageByEntityEvent e) {

	}

	@EventHandler
	public void EntidadeDesprender(EntityUnleashEvent e) {

	}

	@EventHandler
	public void EntidadeExplode(EntityExplodeEvent e) {

	}

	@EventHandler
	public void EntidadeInteragir(EntityInteractEvent e) {

	}

	@EventHandler
	public void EntidadeMira(EntityTargetEvent e) {

	}

	@EventHandler
	public void EntidadeMiraUnidadeViva(EntityTargetLivingEntityEvent e) {

	}

	@EventHandler
	public void EntidadeMorrer(EntityDeathEvent e) {

	}

	@EventHandler
	public void EntidadeMudarBloco(EntityChangeBlockEvent e) {

	}

	@EventHandler
	public void EntidadePegandoFogoPorBloco(EntityCombustByBlockEvent e) {

	}

	@EventHandler
	public void EntidadePegandoFogoPorEntidade(EntityCombustByEntityEvent e) {

	}

	@EventHandler
	public void EntidadePegarFogo(EntityCombustEvent e) {

	}

	@EventHandler
	public void EntidadePortal(EntityPortalEvent e) {

	}

	@EventHandler
	public void EntidadePortalEntrar(EntityPortalEnterEvent e) {

	}

	@EventHandler
	public void EntidadePortalSair(EntityPortalExitEvent e) {

	}

	@EventHandler
	public void EntidadeQuebraPorta(EntityBreakDoorEvent e) {

	}

	@EventHandler
	public void EntidadeRecuperaVida(EntityRegainHealthEvent e) {

	}

	@EventHandler
	public void EntidadeTame(EntityTameEvent e) {

	}

	@EventHandler
	public void EntidadeTeleportar(EntityTeleportEvent e) {

	}

	@EventHandler
	public void JogadorAnimacao(PlayerAnimationEvent e) {

	}

	@EventHandler
	public void JogadorAtivarCorrida(PlayerToggleSprintEvent e) {

	}

	@EventHandler
	public void JogadorAtivarEsgueirar(PlayerToggleSneakEvent e) {

	}

	@EventHandler
	public void JogadorAtivarVoo(PlayerToggleFlightEvent e) {

	}

	@EventHandler
	public void JogadorBalde(PlayerBucketEvent e) {

	}

	@EventHandler
	public void JogadorBaldeEncher(PlayerBucketFillEvent e) {

	}

	@EventHandler
	public void JogadorBaldeEsvasiar(PlayerBucketEmptyEvent e) {

	}

	@EventHandler
	public void JogadorCanal(PlayerChannelEvent e) {

	}

	@EventHandler
	public void JogadorChatTabCompletar(PlayerChatTabCompleteEvent e) {

	}

	@EventHandler
	public void JogadorComandoProcesso(PlayerCommandPreprocessEvent e) {

	}

	@EventHandler
	public void JogadorCortarOvelha(PlayerShearEntityEvent e) {

	}

	@EventHandler
	public void JogadorDesprenderEntidade(PlayerUnleashEntityEvent e) {

	}

	@EventHandler
	public void JogadorEditarBook(PlayerEditBookEvent e) {

	}

	@EventHandler
	public void JogadorEntrar(PlayerJoinEvent e) {

	}

	@EventHandler
	public void JogadorEntrarCama(PlayerBedEnterEvent e) {

	}

	@EventHandler
	public void JogadorGamemodeMudar(PlayerGameModeChangeEvent e) {

	}

	@EventHandler
	public void JogadorInteragir(PlayerInteractEvent e) {

	}

	@EventHandler
	public void JogadorInteragirEntidade(PlayerInteractEntityEvent e) {

	}

	@EventHandler
	public void JogadorItemConsumir(PlayerItemConsumeEvent e) {

	}

	@EventHandler
	public void JogadorItemDropar(PlayerDropItemEvent e) {

	}

	@EventHandler
	public void JogadorItemQuebrar(PlayerItemBreakEvent e) {

	}

	@EventHandler
	public void JogadorItemSlotMudar(PlayerItemHeldEvent e) {

	}

	@EventHandler
	public void JogadorJogarOvos(PlayerEggThrowEvent e) {

	}

	@EventHandler
	public void JogadorKickar(PlayerKickEvent e) {

	}

	@EventHandler
	public void JogadorLevelMudar(PlayerLevelChangeEvent e) {

	}

	@EventHandler
	public void JogadorLogin(PlayerLoginEvent e) {

	}

	@EventHandler
	public void JogadorMorrer(PlayerDeathEvent e) {

	}

	@EventHandler
	public void JogadorMover(PlayerMoveEvent e) {

	}

	@EventHandler
	public void JogadorMudarXP(PlayerExpChangeEvent e) {

	}

	@EventHandler
	public void JogadorName(PlayerNamePrompt e) {

	}

	@EventHandler
	public void JogadorPegarItem(PlayerPickupItemEvent e) {

	}

	@EventHandler
	public void JogadorPescar(PlayerFishEvent e) {

	}

	@EventHandler
	public void JogadorPortal(PlayerPortalEvent e) {

	}

	@EventHandler
	public void JogadorPremioGanhado(PlayerAchievementAwardedEvent e) {

	}

	@EventHandler
	public void JogadorPrenderEntidade(PlayerLeashEntityEvent e) {

	}

	@EventHandler
	public void JogadorRegistrarCanal(PlayerRegisterChannelEvent e) {

	}

	@EventHandler
	public void JogadorRenascer(PlayerRespawnEvent e) {

	}

	@EventHandler
	public void JogadorSair(PlayerQuitEvent e) {

	}

	@EventHandler
	public void JogadorSairCama(PlayerBedLeaveEvent e) {

	}

	@EventHandler
	public void JogadorStatsMudar(PlayerStatisticIncrementEvent e) {

	}

	@EventHandler
	public void JogadorTeleportar(PlayerTeleportEvent e) {

	}

	@EventHandler
	public void JogadorTrocarMundo(PlayerChangedWorldEvent e) {

	}

	@EventHandler
	public void JogadorUnCanal(PlayerUnregisterChannelEvent e) {

	}

	@EventHandler
	public void JogadorVelocidade(PlayerVelocityEvent e) {

	}

	@EventHandler
	public void PlayerChat(AsyncPlayerChatEvent e) {

	}

	@EventHandler
	public void PlayerPreLogin(AsyncPlayerPreLoginEvent e) {

	}

}