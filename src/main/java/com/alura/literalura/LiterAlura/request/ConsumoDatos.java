package com.alura.literalura.LiterAlura.request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsumoDatos implements IConsumoDatos {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return mapper.readValue(json.toString(), clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}