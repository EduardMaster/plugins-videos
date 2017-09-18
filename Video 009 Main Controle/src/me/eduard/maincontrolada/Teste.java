
package me.eduard.maincontrolada;

public class Teste {

	public Teste() {
		Main.m.getConfig().set("Teste", "Vai da erro");
		Main.m.saveConfig();
	}

	public Teste(Main m) {
		m.getConfig().set("Teste", "Vai da erro");
		m.saveConfig();
	}
}
