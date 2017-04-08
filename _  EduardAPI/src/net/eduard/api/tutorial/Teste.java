package net.eduard.api.tutorial;

public class Teste {
	public static void main(String[] args) {
		System.out.println(desobfuscar(obfuscar("Meu deus")));
	}
	public static String obfuscar(String str) {
		String build = "";
		for (int i = 0; i < str.length(); i++) {
			build = build.equals("")
					? "" + (str.charAt(i) + str.length() * str.length())
					: build + ";"
							+ (str.charAt(i) + str.length() * str.length());
		}
		return build;
	}

	public static String desobfuscar(String str) {
		final String[] split = str.split(";");
		final int[] parse = new int[split.length];
		for (int i = 0; i < split.length; i++) {
			parse[i] = Integer.parseInt(split[i]) - split.length * split.length;
		}
		String build = "";
		for (int i = 0; i < split.length; i++) {
			build = build + (char) parse[i];
		}
		return build;
	}
}
