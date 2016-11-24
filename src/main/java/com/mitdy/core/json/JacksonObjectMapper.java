package com.mitdy.core.json;

import java.util.Date;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class JacksonObjectMapper extends ObjectMapper {

	public JacksonObjectMapper() {
		SimpleModule module = new SimpleModule("mitdy", new Version(1, 0, 0,
				null));
		module.addSerializer(Date.class, new CustomDateSerializer());
		module.addDeserializer(Date.class, new CustomDateDeserializer());
		configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		registerModule(module);
	}
}
