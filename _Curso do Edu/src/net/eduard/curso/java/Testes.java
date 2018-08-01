package net.eduard.curso.java;

import java.text.DecimalFormat;

public class Testes {
	public static void main(String[] args) {
		DecimalFormat d = new DecimalFormat("0,000.00");
		
		System.out.println(d.format(13809182308d));
	}


}
