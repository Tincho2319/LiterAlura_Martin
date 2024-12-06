package com.alura.literalura.LiterAlura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class DatosAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Clave primaria generada automáticamente

    @Column(name = "nombre_autor") // Mapea a la columna "nombre_autor" en la base de datos
    @JsonAlias("name") // Mapea el campo "name" del JSON
    private String nombreAutor;

    @Column(name = "nacimiento_fecha") // Mapea a la columna "nacimiento_fecha"
    @JsonAlias("birth_year") // Mapea el campo "birth_year" del JSON
    private int nacimientoFecha;

    @Column(name = "muerte_fecha") // Mapea a la columna "muerte_fecha"
    @JsonAlias("death_year") // Mapea el campo "death_year" del JSON
    private int muerteFecha;

    // Constructor sin parámetros (necesario para JPA)
    public DatosAutor() {}

    // Constructor completo
    public DatosAutor(String nombreAutor, int nacimientoFecha, int muerteFecha) {
        this.nombreAutor = nombreAutor;
        this.nacimientoFecha = nacimientoFecha;
        this.muerteFecha = muerteFecha;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public int getNacimientoFecha() {
        return nacimientoFecha;
    }

    public void setNacimientoFecha(int nacimientoFecha) {
        this.nacimientoFecha = nacimientoFecha;
    }

    public int getMuerteFecha() {
        return muerteFecha;
    }

    public void setMuerteFecha(int muerteFecha) {
        this.muerteFecha = muerteFecha;
    }

    // Método `toString` para representar el objeto como texto
    @Override
    public String toString() {
        return "Autor: " + nombreAutor + "\n" + "Año de nacimiento: " + nacimientoFecha +"\n"+
                "Año de fallecimiento: " + muerteFecha + "\n";
    }
}
