package com.utils.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EncoderUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson (Object object) {
        String jsonFormat;
        try{
            jsonFormat = objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonFormat;
    }

    public static JsonNode toJsonNode (String request) {
        JsonNode jsonNode;
        try{
            jsonNode = objectMapper.readTree(request);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }
}
