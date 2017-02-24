package net.eduard.api.util;

public enum GuiSize {
	LINE1, LINE2, LINE3, LINE4, LINE5, LINE6;
	public int getValue() {
		return Integer.valueOf(name().replace("LINE", "")) * 9;
	}
}
