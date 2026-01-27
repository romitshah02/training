package com.sunbird.training.config;


import tools.jackson.core.JacksonException;
import tools.jackson.databind.ValueDeserializer;

public class LowercaseDeserializer extends ValueDeserializer<String>{

    @Override
    public String deserialize(tools.jackson.core.JsonParser p, tools.jackson.databind.DeserializationContext ctxt)
            throws JacksonException {
                String value = p.getValueAsString();
                return value != null ? value.toLowerCase() : null;
    }

   
}
