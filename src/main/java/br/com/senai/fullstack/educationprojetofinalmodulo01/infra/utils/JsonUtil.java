package br.com.senai.fullstack.educationprojetofinalmodulo01.infra.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private JsonUtil() {}

    public static String objetoParaJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}

