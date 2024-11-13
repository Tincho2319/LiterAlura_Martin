package com.alura.literalura.LiterAlura.principal;

import com.alura.literalura.LiterAlura.modelo.LibrosDTO;
import com.alura.literalura.LiterAlura.principal.BuscandoLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class LibroPrincipal {

    private final BuscandoLibro buscandoLibro;
    private final Scanner teclado = new Scanner(System.in);

    @Autowired
    public LibroPrincipal(BuscandoLibro buscandoLibro) {
        this.buscandoLibro = buscandoLibro;
    }

    public void mostrarMenu() {
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        String nombreLibro = teclado.nextLine().trim();

        if (nombreLibro.isEmpty()) {
            System.out.println("El nombre del libro no puede estar vacío. Intente de nuevo.");
            return;
        }

        try {
            List<LibrosDTO> librosEncontrados = buscandoLibro.buscarLibro(nombreLibro);
            if (!librosEncontrados.isEmpty()) {
                System.out.println("Libros encontrados: ");
                librosEncontrados.forEach(libro -> System.out.println(libro.nombreLibro()));
            } else {
                System.out.println("No se encontraron libros con ese nombre.");
            }
        } catch (IOException e) {
            System.err.println("Ocurrió un error al procesar la búsqueda: " + e.getMessage());
        }
    }
}
