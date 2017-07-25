package net.eduard.api.central;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.craftbukkit.v1_8_R3.util.MojangNameLookup;

public final class MojangAPI {

	
	public static Optional<String> getUUID(String name) {
		String output = readURL(
				"https://api.mojang.com/users/profiles/minecraft/" + name);

		if ((output == null) || (output.isEmpty()) || (output
				.contains("\"error\":\"TooManyRequestsException\""))) {
			output = readURL("http://mcapi.ca/uuid/player/" + name).replace(" ",
					"");

			String idbeg = "\"uuid\":\"";
			String idend = "\",\"id\":";

			String response = getStringBetween(output, idbeg, idend);

			if (response.startsWith("[{\"uuid\":null")) {
				// erro
			}
			return Optional.of(response);
		}

		return Optional.of(output.substring(7, 39));
	}

	private static String readURL(String url) {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url)
					.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "SkinsRestorer");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setUseCaches(false);

			StringBuilder output = new StringBuilder();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				output.append(line);
			}
			in.close();

			return output.toString();
		} catch (Exception e) {
		}
		return null;
	}

	private static String getStringBetween(String base, String begin,
			String end) {
		Pattern patbeg = Pattern.compile(Pattern.quote(begin));
		Pattern patend = Pattern.compile(Pattern.quote(end));

		int resbeg = 0;
		int resend = base.length() - 1;

		Matcher matbeg = patbeg.matcher(base);

		while (matbeg.find()) {
			resbeg = matbeg.end();
		}
		Matcher matend = patend.matcher(base);

		while (matend.find()) {
			resend = matend.start();
		}
		return base.substring(resbeg, resend);
	}
//	public static class SkinRequestException extends Exception {
//		private static final long serialVersionUID = 5969055162529998032L;
//		private String reason;
//
//		public SkinRequestException(String reason) {
//			this.reason = reason;
//		}
//
//		public String getReason() {
//			return this.reason;
//		}
//	}
}
