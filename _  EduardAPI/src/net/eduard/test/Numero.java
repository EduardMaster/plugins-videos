package net.eduard.test;

public class Numero {

	public static long converterNumeroDecimalParaBinario(long numeroDecimal) {
		StringBuilder builder = new StringBuilder();
		while (numeroDecimal != 0) {
			builder.append(numeroDecimal%2);
			numeroDecimal = numeroDecimal/2;
		}
		return Long.valueOf(builder.reverse().toString());
	}
	public static long converterNumeroBinarioParaDecimal(long numeroBinario) {
		String value = String.valueOf(numeroBinario);
		long numero = 0;
		for (int i = 0; i < value.length(); i++) {
			int number = Character.getNumericValue(value.charAt(i));
			int potencia = value.length()-1-i;
			numero+=(number*pegarPotenciaDe(2, potencia));
		}
		return numero;
	}
	public static long pegarPotenciaDe(long valor,long potencia) {
		long result = 1;
		while(potencia!=0) {
			result *= valor;
			potencia--;
		}
		return result;
	}
	public static void main(String[] args) {
		long valor = 100;
		long bin = converterNumeroDecimalParaBinario(valor);
		System.out.println("Numero em binario de "+100 + " é "+bin);
		System.out.println("Numero em decimal de "+bin + " é "+converterNumeroBinarioParaDecimal(bin));
	}
}
