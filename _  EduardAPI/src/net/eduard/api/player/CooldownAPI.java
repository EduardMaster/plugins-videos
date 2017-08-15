/*     */ package net.eduard.api.player;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;

import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.Configs;
/*     */
/*     */ public class CooldownAPI extends Configs
/*     */ {

	private String tag = "Cooldowns.";

	public CooldownAPI() {
		super("cooldowns.yml", API.PLUGIN);

	}

	public void addCooldown(Player player, String cooldown, long timeToWait) {
		set(tag + getId(player) + cooldown + ".before",
				System.currentTimeMillis());
		set(tag + getId(player) + cooldown + ".cooldown", timeToWait);
	}
	private String getId(Player player) {
		return player.getUniqueId() + ".";
	}
	public boolean inCooldown(Player player, String cooldown) {
		if (getCooldown(player, cooldown) == -1)
			return false;
		return true;

	}

	public String getTime(Player player, String cooldown) {
		return formatTime(getCooldown(player, cooldown));
	}

	public long getCooldown(Player player, String cooldown) {

		if (contains(tag + getId(player) + cooldown)) {
			long before = getBefore(player, cooldown);
			long now = System.currentTimeMillis();
			long wait = getDelay(player, cooldown);
			long result = now - (before+wait);
			// 10     4       20 = 0
			
			return result>0?result:-1;
			
		}
		return -1;

	}
	public long getDelay(Player player, String cooldown) {
		return getLong(tag + getId(player) + cooldown + ".cooldown");
	}

	public long getBefore(Player player,String cooldown) {
		return getLong(tag + getId(player) + cooldown + ".before");
	}
	public void removeCooldown(Player player, String cooldown) {
		remove(tag + getId(player) + cooldown);
	}

	/*     */
	/*     */ public String formatTime(long time)
	/*     */ {
		/* 75 */ if (time == 0L) {
			/* 76 */ return "never";
			/*     */ }
		/* 78 */ long day = TimeUnit.MILLISECONDS.toDays(time);
		/* 79 */ long hours = TimeUnit.MILLISECONDS.toHours(time) - day * 24L;
		/* 80 */ long minutes = TimeUnit.MILLISECONDS.toMinutes(time)
				- TimeUnit.MILLISECONDS.toHours(time) * 60L;
		/* 81 */ long seconds = TimeUnit.MILLISECONDS.toSeconds(time)
				- TimeUnit.MILLISECONDS.toMinutes(time) * 60L;
		/* 82 */ StringBuilder sb = new StringBuilder();
		/* 83 */ if (day > 0L) {
			/* 84 */ sb.append(day).append(" ")
					.append(day == 1L ? "dia" : "dias").append(" ");
			/*     */ }
		/* 86 */ if (hours > 0L) {
			/* 87 */ sb.append(hours).append(" ")
					.append(hours == 1L ? "hora" : "horas").append(" ");
			/*     */ }
		/* 89 */ if (minutes > 0L) {
			/* 90 */ sb.append(minutes).append(" ")
					.append(minutes == 1L ? "minuto" : "minutos").append(" ");
			/*     */ }
		/* 92 */ if (seconds > 0L) {
			/* 93 */ sb.append(seconds).append(" ")
					.append(seconds == 1L ? "segundo" : "segundos");
			/*     */ }
		/* 95 */ String diff = sb.toString();
		/* 96 */ return diff.isEmpty() ? "agora" : diff;
		/*     */ }
	/*     */
	/*     */ public String formatDiference(long timestamp) {
		/* 100 */ return formatTime(timestamp - System.currentTimeMillis());
		/*     */ }
	/*     */
	/*     */ public long parseDateDiff(String time, boolean future)
			throws Exception {
		/* 104 */ Pattern timePattern = Pattern.compile(
				"(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?",
				/* 107 */ 2);
		/* 108 */ Matcher m = timePattern.matcher(time);
		/* 109 */ int years = 0;
		/* 110 */ int months = 0;
		/* 111 */ int weeks = 0;
		/* 112 */ int days = 0;
		/* 113 */ int hours = 0;
		/* 114 */ int minutes = 0;
		/* 115 */ int seconds = 0;
		/* 116 */ boolean found = false;
		/* 117 */ while (m.find())
			/* 118 */ if ((m.group() != null) && (!m.group().isEmpty()))
			/*     */ {
				/* 121 */ for (int i = 0; i < m.groupCount(); i++) {
					/* 122 */ if ((m.group(i) != null)
							&& (!m.group(i).isEmpty())) {
						/* 123 */ found = true;
						/* 124 */ break;
						/*     */ }
					/*     */ }
				/* 127 */ if (found) {
					/* 128 */ if ((m.group(1) != null)
							&& (!m.group(1).isEmpty())) {
						/* 129 */ years = Integer.parseInt(m.group(1));
						/*     */ }
					/* 131 */ if ((m.group(2) != null)
							&& (!m.group(2).isEmpty())) {
						/* 132 */ months = Integer.parseInt(m.group(2));
						/*     */ }
					/* 134 */ if ((m.group(3) != null)
							&& (!m.group(3).isEmpty())) {
						/* 135 */ weeks = Integer.parseInt(m.group(3));
						/*     */ }
					/* 137 */ if ((m.group(4) != null)
							&& (!m.group(4).isEmpty())) {
						/* 138 */ days = Integer.parseInt(m.group(4));
						/*     */ }
					/* 140 */ if ((m.group(5) != null)
							&& (!m.group(5).isEmpty())) {
						/* 141 */ hours = Integer.parseInt(m.group(5));
						/*     */ }
					/* 143 */ if ((m.group(6) != null)
							&& (!m.group(6).isEmpty())) {
						/* 144 */ minutes = Integer.parseInt(m.group(6));
						/*     */ }
					/* 146 */ if ((m.group(7) == null)
							|| (m.group(7).isEmpty()))
						break;
					/* 147 */ seconds = Integer.parseInt(m.group(7));
					/*     */
					/* 149 */ break;
					/*     */ }
				/*     */ }
		/* 152 */ if (!found) {
			/* 153 */ throw new Exception("Illegal Date");
			/*     */ }
		/* 155 */ if (years > 20) {
			/* 156 */ throw new Exception("Illegal Date");
			/*     */ }
		/* 158 */ Calendar c = new GregorianCalendar();
		/* 159 */ if (years > 0) {
			/* 160 */ c.add(1, years * (future ? 1 : -1));
			/*     */ }
		/* 162 */ if (months > 0) {
			/* 163 */ c.add(2, months * (future ? 1 : -1));
			/*     */ }
		/* 165 */ if (weeks > 0) {
			/* 166 */ c.add(3, weeks * (future ? 1 : -1));
			/*     */ }
		/* 168 */ if (days > 0) {
			/* 169 */ c.add(5, days * (future ? 1 : -1));
			/*     */ }
		/* 171 */ if (hours > 0) {
			/* 172 */ c.add(11, hours * (future ? 1 : -1));
			/*     */ }
		/* 174 */ if (minutes > 0) {
			/* 175 */ c.add(12, minutes * (future ? 1 : -1));
			/*     */ }
		/* 177 */ if (seconds > 0) {
			/* 178 */ c.add(13, seconds * (future ? 1 : -1));
			/*     */ }
		/* 180 */ return c.getTimeInMillis();
		/*     */ }
	/*     */

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
