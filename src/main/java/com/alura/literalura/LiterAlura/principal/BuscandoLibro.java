package com.alura.literalura.LiterAlura.principal;

import com.alura.literalura.LiterAlura.modelo.LibroRespuesta;
import com.alura.literalura.LiterAlura.modelo.LibrosDTO;
import com.alura.literalura.LiterAlura.request.RequestLibro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BuscandoLibro {
    private final RequestLibro requestLibro = new RequestLibro();
    private final ObjectMapper mapeador = new ObjectMapper(); // Usar una instancia común

    public List<LibrosDTO> buscarLibro(String tituloBuscado) throws IOException {
        List<LibrosDTO> librosEncontrados = new ArrayList<>();
        int limitePag = 2330;
        int numeroPagina = 1;
        boolean encontrado = false;

        while (!encontrado && numeroPagina <= limitePag) {
            // Obtener datos de la API y guardar en archivo JSON
            File archivo = requestLibro.recibiendoDatos(numeroPagina);
            if (archivo == null || !archivo.exists()) {
                System.err.println("No se pudo crear o leer el archivo en la página " + numeroPagina);
                break; // Terminar si el archivo no se generó correctamente
            }

            // Cargar los datos del archivo JSON
            try {
                LibroRespuesta respuestaApi = mapeador.readValue(archivo, LibroRespuesta.class);

                // Buscar el libro dentro de los resultados de la página actual
                for (LibrosDTO libro : respuestaApi.results()) {
                    if (libro.nombreLibro().equalsIgnoreCase(tituloBuscado)) {
                        System.out.println("Se encontró el libro: " + tituloBuscado);
                        librosEncontrados.add(libro);
                        encontrado = true;
                        break; // Salir del ciclo de búsqueda si se encuentra el libro
                    }
                }

                // Optimización: Si no hay más resultados en la siguiente página, salir del bucle
                if (respuestaApi.next() == null) {
                    break;
                }

                numeroPagina++; // Incrementar solo si hay más páginas

            } catch (IOException e) {
                System.err.println("Error al procesar el archivo en la página " + numeroPagina + ": " + e.getMessage());
                break; // Terminar si hay un error al leer el archivo
            }
        }

        if (librosEncontrados.isEmpty()) {
            System.out.println("No se encontró ningún libro con el título: " + tituloBuscado);
        }

        return librosEncontrados;
    }
}
