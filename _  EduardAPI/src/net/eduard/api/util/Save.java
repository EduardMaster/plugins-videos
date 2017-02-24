package net.eduard.api.util;

import net.eduard.api.config.Section;

public interface Save {
	Object get(Section section);

	void save(Section section, Object value);

}

