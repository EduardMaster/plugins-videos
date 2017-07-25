package net.eduard.eduardapi.test.ac;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
		try {
			URL url = Teste.class.getResource("teste.yml");
			URI uri = url.toURI();
			Path arquivo = Paths.get(uri);

			List<String> lines = Files.readAllLines(arquivo);
			Complex root = new Complex("root");
			root.read(lines);
			// List<String> newLista = new ArrayList<>();
			// for (String line : string) {
			// if (line.contains(":")) {
			// int in = line.indexOf(":");
			// CharSequence name = line.subSequence(0, in);
			// System.out.println(name);
			// System.out.println(in);
			// System.out.println(line);
			// String value = line.substring(in+1);
			//// System.out.println(value);
			// value=value.replaceFirst(" ", "");
			// if (value.startsWith("[")&&value.endsWith("]")) {
			// String itens = getStringBetween(value, "[", "]");
			// System.out.println(itens);
			// }
			// }
			//
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static String getStringBetween(String text, String prefix,
			String suffix) {

		return text.substring(text.indexOf(prefix) + 1,
				text.lastIndexOf(suffix));
	}
}
