package net.eduard.api.config;

import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class Serz<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient E objectValue;

	public boolean isNull() {
		return objectValue == null;
	}
	public void paste() {
		copy(this, objectValue);
	}
	public void copy() {
		copy(objectValue, this);
	}

	public E get() {
		return objectValue;
	}

	public void set(E value) {
		this.objectValue = value;
		paste();
	}
	public static void copy(Object copied, Object pasted) {

		try {
			for (Field var : copied.getClass().getDeclaredFields()) {
				var.setAccessible(true);
				Object dataCopied = var.get(copied);
				Field varPasted = pasted.getClass().getDeclaredField(var.getName());
				varPasted.setAccessible(true);
				if (varPasted.getType().equals(var.getType())) {
					varPasted.set(pasted, dataCopied);
				}
				
			}

		} catch (Exception ex) {
		}

	}
	public abstract void save(E value);

	public abstract E reload();

}
