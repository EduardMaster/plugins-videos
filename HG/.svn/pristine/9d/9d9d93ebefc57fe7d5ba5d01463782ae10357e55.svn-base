package zLuck.zTempos;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import br.com.piracraft.api.Main;
import zLuck.zEventos.PlayerStatus;
import zLuck.zMain.zLuck;
import zLuck.zScoreboard.Scoreboards;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Schematic;
import zLuck.zUteis.Uteis;

public class Jogo {
	
	public Jogo() {
		Arrays.tempo3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(zLuck.getPlugin(), new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				Arrays.tempoA3++;
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (!Scoreboards.score.contains(all)) {
					    Scoreboards.setarJogo(all);
					}
				}
				if (Arrays.tempoA3 == 300) {
                    Bukkit.broadcastMessage(Uteis.prefix + " §7Agora, §a§lVIPS §7não irão mas respawnar.");
				}
				if (Arrays.tempoA3 == 600) {
                    Schematic.SchematicMiniFeast();
				}
				if (Arrays.tempoA3 == 900) {
                    Schematic.SchematicMiniFeast();
				}
				if (Arrays.tempoA3 == 1500) {
                    Schematic.SchematicFeast();
				}
				if (Arrays.tempoA3 == 3000) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arena");
				}
				if (Arrays.tempoA3 == 3600) {
                    Bukkit.broadcastMessage(Uteis.prefix + " §7Não houve nenhum vencedor! Servidor reiniciando");
                    Bukkit.getScheduler().scheduleSyncRepeatingTask(zLuck.getPlugin(), new Runnable() { 
                        public void run() {
                            Bukkit.shutdown();
                        }
                    }, 0, 20);
				}
				if (Arrays.jogador.size() == 0) {
					Bukkit.shutdown();
				} 
				if (Arrays.jogador.size() == 1) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (Arrays.jogador.contains(all)) {
							Bukkit.getScheduler().cancelTask(Arrays.tempo3);
							Arrays.ganhou = true;
							Player ganhador = Bukkit.getPlayer(Arrays.ganhador = all.getName());
							
							for(int x = 0; x < PlayerStatus.status.size(); x++){
								if(PlayerStatus.status.get(x).getUuid().equals(Main.uuid.get(ganhador))) {
									PlayerStatus.status.get(x).setVencedor(PlayerStatus.status.get(x).getVencedor()+1);
									PlayerStatus.status.get(x).setCoins(PlayerStatus.status.get(x).getCoins()+100);
									PlayerStatus.status.get(x).setXp(PlayerStatus.status.get(x).getXp()+50);
									PlayerStatus.status.get(x).setCash(PlayerStatus.status.get(x).getCash()+1);
								}
							}
							
							ganhador.getInventory().clear();
							ganhador.getInventory().setArmorContents(null);
							ganhador.setAllowFlight(true);
							ganhador.setFlying(true);
                            ganhador.getInventory().setItem(4, new ItemStack(Material.WATER_BUCKET));
                            ganhador.playSound(ganhador.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                            
                            Location ender = new Location(ganhador.getWorld(), ganhador.getLocation().getX(), ganhador.getLocation().getY() + 50, ganhador.getLocation().getZ());
                            EnderDragon end = (EnderDragon) ganhador.getWorld().spawnCreature(ender, EntityType.ENDER_DRAGON); 
                            end.setPassenger(ganhador);
                            
                            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
                                public void run() {
                                    ganhador.kickPlayer("§6O jogador " + ganhador.getDisplayName() + " §6ganhou o torneio\n§6Servidor reiniciando!");
                                    Bukkit.shutdown();
                                }
                            }, 30 * 20);
                            Bukkit.getScheduler().scheduleSyncRepeatingTask(zLuck.getPlugin(), new Runnable() { 
                                public void run() {
                                    Bukkit.broadcastMessage(Uteis.prefix + " §6O jogador §l" + ganhador.getDisplayName() + " §6ganhou o torneio!");
                                }
                            }, 0, 80);
                            Bukkit.getScheduler().scheduleSyncRepeatingTask(zLuck.getPlugin(), new Runnable() {
                                public void run() {                            
                                    Firework fogueteA1 = ganhador.getWorld().spawn(ganhador.getLocation(), Firework.class);
                                    FireworkMeta foguetemeta = fogueteA1.getFireworkMeta();
                                    foguetemeta.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.ORANGE).withFade(Color.BLUE).build());
                                    foguetemeta.setPower(1);
                                    fogueteA1.setFireworkMeta(foguetemeta);
                                }
                            }, 0, 20);
						}
					}
				}
			}			
		}, 0, 20);
	}
	
}
