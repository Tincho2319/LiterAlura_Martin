package com.alura.literalura.LiterAlura.service;

import com.alura.literalura.LiterAlura.modelo.Libro;
import com.alura.literalura.LiterAlura.modelo.DatosLibro;
import com.alura.literalura.LiterAlura.modelo.DatosAutor;
import com.alura.literalura.LiterAlura.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // Obtener todos los libros
    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public void guardarLibro(Libro libro) {
        libroRepository.save(libro);  // Guarda el libro en el repositorio
    }

    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }

    public List<Libro> librosPorLapsoDeTiempo(int fechaInicioEstablecida, int fechaFinEstablecida) {
        return libroRepository.obtenerLibrosEnUnLapsoDeTiempo(fechaInicioEstablecida, fechaFinEstablecida);
    }

    public List<Libro> librosPorLengua(String lenguaBuscada) {
        return libroRepository.obtenerLibrosPorLengua(lenguaBuscada);
    }

    public List<Libro> libroPorDescargassTop5(){
        return libroRepository.findTop5ByOrderByDescargasDesc();
    }
}
