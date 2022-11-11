package org.flowwork.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 作者:admin on 2020/6/8.
 */
public class JsonConvert {
	public static ObjectMapper objectMapper=new ObjectMapper();
	public static ObjectMapper objectMapperSnakeCase=new ObjectMapper();
	public static ObjectMapper defaultObjectMapper=new ObjectMapper();

	static {

		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, true);
		objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE,true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		objectMapper.registerModule(new SimpleModule()
				.addSerializer(Date.class, new JsonSerializer<Date>() {
					@Override
					public void serialize(Date v, JsonGenerator gen, SerializerProvider p) throws IOException {
						gen.writeString(DateTimeFormatter.ISO_INSTANT.format(v.toInstant()));
					}
				})
		);

		objectMapperSnakeCase.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapperSnakeCase.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	}
}
