package net.eduard.eduardapi.test;

import java.util.Calendar;
import java.util.Date;

public class TestTempo {

	public static void main(String[] args) {
	Calendar data = Calendar.getInstance();;
		
	Calendar diaseguinte = Calendar.getInstance();
	diaseguinte.add(Calendar.DAY_OF_MONTH, 1);
	data.after(diaseguinte);
	
	}
}
