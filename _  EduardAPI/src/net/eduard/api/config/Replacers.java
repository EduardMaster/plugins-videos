package net.eduard.api.config;

import java.text.DecimalFormat;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

import net.eduard.api.API;
import net.eduard.api.setup.ExtraAPI;
import net.eduard.api.setup.Replacer;
import net.eduard.api.setup.VaultAPI;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

public class Replacers {

	public Replacers() {
		ExtraAPI.newWrapper("<br>");
		ExtraAPI.newWrapper("\\n");
		ExtraAPI.newWrapper("$br");
		ExtraAPI.addReplacer("$player_group", new Replacer() {

			@Override
			public Object getText(Player p) {
				return VaultAPI.getPermission().getPrimaryGroup(p);
			}
		});
		ExtraAPI.addReplacer("$player_prefix", new Replacer() {

			@Override
			public Object getText(Player p) {
				return VaultAPI.getChat().getPlayerPrefix(p);
			}
		});
		ExtraAPI.addReplacer("$player_suffix", new Replacer() {

			@Override
			public Object getText(Player p) {
				return VaultAPI.getChat().getPlayerPrefix(p);
			}
		});
		ExtraAPI.addReplacer("$group_prefix", new Replacer() {

			@Override
			public Object getText(Player p) {
				return VaultAPI.getChat().getGroupPrefix("null",
						VaultAPI.getPermission().getPrimaryGroup(p));
			}
		});
		ExtraAPI.addReplacer("$group_suffix", new Replacer() {

			@Override
			public Object getText(Player p) {
				return VaultAPI.getChat().getGroupSuffix("null",
						VaultAPI.getPermission().getPrimaryGroup(p));
			}
		});
		ExtraAPI.addReplacer("$players_online", new Replacer() {

			@Override
			public Object getText(Player p) {
				return API.getPlayers().size();
			}
		});
		ExtraAPI.addReplacer("$player_world", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getWorld().getName();
			}
		});
		ExtraAPI.addReplacer("$player_displayname", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getDisplayName();
			}
		});
		ExtraAPI.addReplacer("$player_name", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getName();
			}
		});
		ExtraAPI.addReplacer("$player_health", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getHealth();
			}
		});
		ExtraAPI.addReplacer("$player_maxhealth", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getMaxHealth();
			}
		});
		ExtraAPI.addReplacer("$player_kills", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getStatistic(Statistic.PLAYER_KILLS);
			}
		});
		ExtraAPI.addReplacer("$player_deaths", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getStatistic(Statistic.DEATHS);
			}
		});
		ExtraAPI.addReplacer("$player_kdr", new Replacer() {

			@Override
			public Object getText(Player p) {
				int kill = p.getStatistic(Statistic.PLAYER_KILLS);
				int death = p.getStatistic(Statistic.DEATHS);
				if (kill == 0)
					return 0;
				if (death == 0)
					return 0;
				return kill / death;
			}
		});
		ExtraAPI.addReplacer("$player_kill/death", new Replacer() {

			@Override
			public Object getText(Player p) {
				int kill = p.getStatistic(Statistic.PLAYER_KILLS);
				int death = p.getStatistic(Statistic.DEATHS);
				if (kill == 0)
					return 0;
				if (death == 0)
					return 0;
				return kill / death;
			}
		});
		ExtraAPI.addReplacer("$clan_label", new Replacer() {

			@Override
			public Object getText(Player p) {

				ClanPlayer clan = SimpleClans.getInstance().getClanManager()
						.getClanPlayer(p);
				if (clan == null) {
					return "";
				}
				if (clan.getClan() == null) {
					return "";
				}

				return clan.getClan().getTagLabel();
			}
		});
		ExtraAPI.addReplacer("$clan_name", new Replacer() {

			@Override
			public Object getText(Player p) {

				ClanPlayer clan = SimpleClans.getInstance().getClanManager()
						.getClanPlayer(p);
				if (clan == null) {
					return "";
				}
				if (clan.getClan() == null) {
					return "";
				}

				return clan.getClan().getName();
			}
		});
		ExtraAPI.addReplacer("$clan_tag", new Replacer() {

			@Override
			public Object getText(Player p) {

				ClanPlayer clan = SimpleClans.getInstance().getClanManager()
						.getClanPlayer(p);
				if (clan == null) {
					return "";
				}
				if (clan.getClan() == null) {
					return "";
				}

				return  clan.getClan().getTag();
			}
		});
		ExtraAPI.addReplacer("$clan_color", new Replacer() {

			@Override
			public Object getText(Player p) {

				ClanPlayer clan = SimpleClans.getInstance().getClanManager()
						.getClanPlayer(p);
				if (clan == null) {
					return "";
				}
				if (clan.getClan() == null) {
					return "";
				}

				return  clan.getClan().getColorTag();
			}
		});
		ExtraAPI.addReplacer("$clan_name", new Replacer() {

			@Override
			public Object getText(Player p) {

				ClanPlayer clan = SimpleClans.getInstance().getClanManager()
						.getClanPlayer(p);
				if (clan == null) {
					return "";
				}
				if (clan.getClan() == null) {
					return "";
				}

				return  clan.getClan().getName();
			}
		});
		ExtraAPI.addReplacer("$player_money", new Replacer() {

			@Override
			public Object getText(Player p) {
				if (VaultAPI.hasVault() && VaultAPI.hasEconomy()) {

					DecimalFormat decimal = new DecimalFormat("#,##0.00");
					return decimal.format(VaultAPI.getEconomy().getBalance(p));

				}
				return "0.00";
			}
		});
		ExtraAPI.addReplacer("$player_x", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getLocation().getX();
			}
		});
		ExtraAPI.addReplacer("$player_y", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getLocation().getY();
			}
		});
		ExtraAPI.addReplacer("$player_z", new Replacer() {

			@Override
			public Object getText(Player p) {
				return p.getLocation().getZ();
			}
		});
		if (API.hasPlugin("HardFacs")) {
			ExtraAPI.addReplacer("$fac_money", new Replacer() {

				@Override
				public Object getText(Player p) {
					DecimalFormat decimal = new DecimalFormat("#,##0.00");
					return decimal.format(FPlayers.i.get(p).getFaction().money);
				}
			});
			ExtraAPI.addReplacer("$fac_p_chat", new Replacer() {

				@Override
				public Object getText(Player p) {
					return FPlayers.i.get(p).getChatTag();
				}
			});
			ExtraAPI.addReplacer("$fac_zone", new Replacer() {

				@Override
				public Object getText(Player p) {
					FPlayer player = FPlayers.i.get(p);
					Faction zone = Board.getFactionAt(player.getLastStoodAt());

					return zone.getColorTo(player) + zone.getTag();
					// return Board.getTerritoryAccessAt(new FLocation(fp)).
				}
			});
			ExtraAPI.addReplacer("$fac_p_power", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getPowerRounded();
				}
			});
			ExtraAPI.addReplacer("$fac_p_maxpower", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getPowerMaxRounded();
				}
			});
			ExtraAPI.addReplacer("$fac_power", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getPowerRounded();
				}
			});
			ExtraAPI.addReplacer("$fac_maxpower", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getPowerMaxRounded();
				}
			});

			ExtraAPI.addReplacer("$fac_tag", new Replacer() {

				@Override
				public Object getText(Player p) {
					Faction f = FPlayers.i.get(p).getFaction();
					if (f != null & !f.getComparisonTag().equals("ZonaLivre"))
						return f.getTag();
					return "§7Sem Facção";
				}
			});
			ExtraAPI.addReplacer("$fac_comptag", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getComparisonTag();
				}
			});
			ExtraAPI.addReplacer("$fac_desc", new Replacer() {

				@Override
				public Object getText(Player p) {
					Faction f = FPlayers.i.get(p).getFaction();
					if (f != null)
						return f.getDescription();
					return "";
				}
			});
			ExtraAPI.addReplacer("$fac_online", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction()
							.getFPlayersWhereOnline(true).size();
				}
			});
			ExtraAPI.addReplacer("$fac_players", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getFPlayers().size();
				}
			});
			ExtraAPI.addReplacer("$fac_claims", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getClaims().size();
				}
			});
		}
	}
}
