
package me.eduard.java;

public class Teste {

	public class ClasseInterface1_7 implements Interface1_7 {

		public ClasseInterface1_7() {
			add();
		}

		public void add() {

			// TODO Auto-generated method stub

		}

	}

	public class ClasseInterface1_8 implements Interface1_8 {

		public ClasseInterface1_8() {

			add();
			Interface1_8.addStatic();

		}

		public void add() {

			Interface1_8.super.add();
		}

	}

	public interface Interface1_7 {

		void add();

	}

	public interface Interface1_8 {

		static void addStatic() {

		}

		default void add() {

		}

	}

	public Teste() {

		Interface1_8.addStatic();

	}

}
