package com.alura.literalura.LiterAlura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("authors")List <DatosAutor> autor,
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> lengua,
        @JsonAlias("download_count") Integer descargas
        ) {

        @Override
        public String toString() {
                return  autor + "\n" + "Titulo= " + titulo + "\n" +
                        "Lengua= " + lengua + "\n" +
                        "Descargas= " + descargas ;
        }
}
