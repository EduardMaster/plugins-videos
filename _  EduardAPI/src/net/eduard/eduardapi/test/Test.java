package net.eduard.eduardapi.test;

public class Test {

	public static void main(String[] args) {
//		System.out.println(code("\017\026)F`R%E4W`[!G5_.W`S3B\u00A1\026\"Z/G5S!R/\032`F!D!\026,_\"S2W2\026/\026!U%E3Yl\026!U%E3S`Y`F!_.S,\0260D)X#_0W,\026$W`E5W`Z/\\!\032`S`W$_#_/X%\033/\026,\u00D7"));
		System.out.println(code2("\u00A2'\\!c\u0090\024!\u00E276\006Y74\037D.&r7\u00E911"));
		System.out.println(code("\u00A2'\\!c\u0090\024!\u00E276\006Y74\037D.&r7\u00E911"));
	}
	private static String code(String str) {
		int i = str.length();
		char[] a = new char[i];
		int i0 = i - 1;
		while (true) {
			if (i0 >= 0) {
				int i1 = str.charAt(i0);
				int i2 = i0 + -1;
				int i3 = (char) (i1 ^ 56);
				a[i0] = (char) i3;
				if (i2 >= 0) {
					i0 = i2 + -1;
					int i4 = str.charAt(i2);
					int i5 = (char) (i4 ^ 70);
					a[i2] = (char) i5;
					continue;
				}
			}
			return new String(a);
		}
	}
	
	private static String code2(String str) {
		int i = str.length();
		char[] a = new char[i];
		int i0 = i - 1;
		while (true) {
			if (i0 >= 0) {
				int i1 = str.charAt(i0);
				int i2 = i0 + -1;
				int i3 = (char) (i1 ^ 100);
				a[i0] = (char) i3;
				if (i2 >= 0) {
					i0 = i2 + -1;
					int i4 = str.charAt(i2);
					int i5 = (char) (i4 ^ 0);
					a[i2] = (char) i5;
					continue;
				}
			}
			return new String(a);
		}
	}
	
}
