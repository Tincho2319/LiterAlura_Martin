package com.alura.literalura.LiterAlura.controller;

import com.alura.literalura.LiterAlura.modelo.DatosLibro;
import com.alura.literalura.LiterAlura.modelo.Libro;
import com.alura.literalura.LiterAlura.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> listarLibros() {
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        return ResponseEntity.ok(libros);
    }



}
