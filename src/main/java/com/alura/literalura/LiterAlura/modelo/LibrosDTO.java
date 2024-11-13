package com.alura.literalura.LiterAlura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record LibrosDTO(
        int id,
        @JsonAlias("title") String nombreLibro,
        List<Autor> authors
) {
    public LibrosDTO {
        if (nombreLibro == null || nombreLibro.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del libro no puede estar vacío");
        }
    }

    public record Autor(
            @JsonAlias("name") String nombreAutor,
            @JsonAlias("birth_year") Integer fechaNacimiento,
            @JsonAlias("death_year") Integer fechaMuerte
    ) {
        public Autor {
            if (nombreAutor == null || nombreAutor.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del autor no puede estar vacío");
            }
        }
    }
}
