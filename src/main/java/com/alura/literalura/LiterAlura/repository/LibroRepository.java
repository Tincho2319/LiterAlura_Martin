package com.alura.literalura.LiterAlura.repository;

import com.alura.literalura.LiterAlura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findTop5ByOrderByDescargasDesc();

    @Query("SELECT l FROM Libro l JOIN l.autores a " +
            "WHERE a.nacimientoFecha >= :fechaInicioEstablecida " +
            "AND a.muerteFecha <= :fechaFinEstablecida")
    List<Libro> obtenerLibrosEnUnLapsoDeTiempo(int fechaInicioEstablecida, int fechaFinEstablecida);

    @Query("SELECT l FROM Libro l " +
    "WHERE l.lengua = :lenguaBuscada")
    List<Libro> obtenerLibrosPorLengua(String lenguaBuscada);

}
