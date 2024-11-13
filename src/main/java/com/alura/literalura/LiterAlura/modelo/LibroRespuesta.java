package com.alura.literalura.LiterAlura.modelo;

import java.util.List;

public record LibroRespuesta(
        int count,
        String next,
        String previous,
        List<LibrosDTO> results
) {}
