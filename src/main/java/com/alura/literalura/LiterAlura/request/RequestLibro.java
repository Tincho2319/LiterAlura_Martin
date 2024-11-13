package com.alura.literalura.LiterAlura.request;

import com.alura.literalura.LiterAlura.request.RespuestaApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestLibro {

    public File recibiendoDatos(int numeroPagina) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books/?page=" + numeroPagina))
                .build();

        try {
            // Enviar solicitud y obtener la respuesta como String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Convertir la respuesta JSON en un objeto de la clase RespuestaApi
            ObjectMapper mapper = new ObjectMapper();
            RespuestaApi respuestaApi = mapper.readValue(responseBody, RespuestaApi.class);

            // Guardar el objeto JSON en un archivo
            File archivo = new File(System.getProperty("user.dir") + "/libros.json");
            mapper.writeValue(archivo, respuestaApi);

            return archivo;

        } catch (IOException | InterruptedException e) {
            System.err.println("Error al recibir datos de la API: " + e.getMessage());
            return null;
        }
    }
}
