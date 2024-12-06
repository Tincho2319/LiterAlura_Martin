package com.alura.literalura.LiterAlura.modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(name = "descargas", nullable = false, columnDefinition = "integer default 0")
    private Integer descargas;

    private String lengua;



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id") // Relacionamos con la tabla "libros"
    private List<DatosAutor> autores;

    // Constructor vacío necesario para JPA
    public Libro() {}

    // Constructor para inicializar con DatosLibro
    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.lengua = datosLibro.lengua() != null && !datosLibro.lengua().isEmpty()
                ? String.join(", ", datosLibro.lengua())
                : "Desconocido"; // Valor predeterminado si la lengua está ausente
        this.descargas = datosLibro.descargas() != null ? datosLibro.descargas() : 0;
        this.autores = datosLibro.autor().stream()
                .map(autor -> new DatosAutor(
                        autor.getNombreAutor(),
                        autor.getNacimientoFecha(),
                        autor.getMuerteFecha()
                )).toList();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLengua() {
        return lengua;
    }

    public void setLengua(String lengua) {
        this.lengua = lengua;
    }

    public List<DatosAutor> getAutores() {
        return autores;
    }

    public void setAutores(List<DatosAutor> autores) {
        this.autores = autores;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas != null ? descargas : 0;
    }

    // Método para añadir un autor
    public void addAutor(DatosAutor autor) {
        if (autores == null) {
            autores = new ArrayList<>();
        }
        autores.add(autor);
    }

    // Representación como texto
    @Override
    public String toString() {
        return null;
    }
}
