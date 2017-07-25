package net.eduard.api.util;

import net.eduard.api.config.ConfigSection;

public interface Save {
	Object get(ConfigSection section);

	void save(ConfigSection section, Object value);

}

