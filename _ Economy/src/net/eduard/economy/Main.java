
package net.eduard.economy;

import java.text.DecimalFormat;

import net.eduard.api.server.EduardPlugin;
import net.eduard.economy.event.MoneyEvents;

public class Main extends EduardPlugin {
	private static Main plugin;

	public static Main getInstance() {
		return plugin;
	}
	public static String format(double numero) {
		DecimalFormat format = new DecimalFormat("###,###.00");
		String formatado = format.format(numero);
		String v = formatado.split(",")[0];
		formatado = numero >= 1000000 && numero <= 999999999 ? v + "M"
				: numero >= 1000000000 && numero <= 999999999999L ? v + "B"
						: numero >= 1000000000000L && numero <= 999999999999999L ? v + "T"
								: numero >= 1000000000000000L && numero <= 999999999999999999L ? v + "Q"
										: numero >= 1000000000000000000L && String.valueOf(numero).length() <= 21
												? v + "QUI"
												: String.valueOf(numero).length() > 21 ? "999QUI"
														: String.valueOf(numero).length() < 7 ? formatado : formatado;

		return formatado;
	}

	@Override
	public void onEnable() {
		plugin = this;
		reload();
		new MoneyEvents().register(this);
	}

	public void save() {

	}

	public void reload() {

	}

	@Override
	public void onDisable() {
		save();
	}

}
