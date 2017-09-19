package net.eduard.eduardapi.test;

public class JavaTest {
public static void main(String[] args) throws InterruptedException {
	while(true) {
		
		double x = 0;
		System.out.println((x = Math.random())<= 50/100D);
		System.out.println(x);
		
		Thread.sleep(500);
	}
}
}
