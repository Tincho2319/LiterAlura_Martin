package com.alura.literalura.LiterAlura.request;

public interface IConsumoDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}