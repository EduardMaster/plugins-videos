package net.eduard.api.config;

import java.text.DecimalFormat;

import org.bukkit.entity.Player;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

import net.eduard.api.API;
import net.eduard.api.util.Replacer;

public class Replacers  {

	public Replacers() {
		if (API.hasPlugin("HardFacs")) {
			ConfigSection.addReplacer("$fac_money", new Replacer() {

				@Override
				public Object getText(Player p) {
					DecimalFormat decimal = new DecimalFormat("#,##0.00");
					return decimal.format(FPlayers.i.get(p).getFaction().money);
				}
			});
			ConfigSection.addReplacer("$fac_p_chat", new Replacer() {

				@Override
				public Object getText(Player p) {
					return FPlayers.i.get(p).getChatTag();
				}
			});
			ConfigSection.addReplacer("$fac_zone", new Replacer() {

				@Override
				public Object getText(Player p) {
					FPlayer player = FPlayers.i.get(p);
					Faction zone = Board.getFactionAt(player.getLastStoodAt());

					return zone.getColorTo(player) + zone.getTag();
					// return Board.getTerritoryAccessAt(new FLocation(fp)).
				}
			});
			ConfigSection.addReplacer("$fac_p_power", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getPowerRounded();
				}
			});
			ConfigSection.addReplacer("$fac_p_maxpower", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getPowerMaxRounded();
				}
			});
			ConfigSection.addReplacer("$fac_power", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getPowerRounded();
				}
			});
			ConfigSection.addReplacer("$fac_maxpower", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getPowerMaxRounded();
				}
			});

			ConfigSection.addReplacer("$fac_tag", new Replacer() {

				@Override
				public Object getText(Player p) {
					Faction f = FPlayers.i.get(p).getFaction();
					if (f != null & !f.getComparisonTag().equals("ZonaLivre"))
						return f.getTag();
					return "§7Sem Facção";
				}
			});
			ConfigSection.addReplacer("$fac_comptag", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getComparisonTag();
				}
			});
			ConfigSection.addReplacer("$fac_desc", new Replacer() {

				@Override
				public Object getText(Player p) {
					Faction f = FPlayers.i.get(p).getFaction();
					if (f != null)
						return f.getDescription();
					return "";
				}
			});
			ConfigSection.addReplacer("$fac_online", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction()
							.getFPlayersWhereOnline(true).size();
				}
			});
			ConfigSection.addReplacer("$fac_players", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getFPlayers().size();
				}
			});
			ConfigSection.addReplacer("$fac_claims", new Replacer() {

				@Override
				public Object getText(Player p) {

					return FPlayers.i.get(p).getFaction().getClaims().size();
				}
			});
		}
	}
}
