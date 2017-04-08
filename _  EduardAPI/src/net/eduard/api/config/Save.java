package net.eduard.api.config;

public interface Save {
	Object get(Section section);

	void save(Section section, Object value);

}

