package net.eduard.api.config;

import java.util.HashMap;
import java.util.Map;

import sun.security.action.GetLongAction;

public class SimpleText {
	private Map<String, Object> objects = new HashMap<>();
	private String tag;
	private Object value;
	private StringBuilder str = new StringBuilder();
	private char[] array;
	private int index = 0;
	private boolean tagReaded;
	private boolean tagClosed;

	public SimpleText(String text) {
		array = text.toCharArray();
		startReading();

	}

	public char letra() {
		return array[index];
	}

	public void readTagStart() {
		while (true) {
			if (letra() == '>') {
				index++;
				tagReaded = true;
			} else {
				str.append(letra());
				index++;
			}

		}

	}

	public void startReading() {
		while (true) {
			if (!tagReaded) {
				if (letra() == '<') {
					index++;
					readTagStart();

				}
			}else if (!tagClosed) {
				if (letra()=='<') {
					
				}
			}

		}

	}

	public void findHeader() {

	}

}
