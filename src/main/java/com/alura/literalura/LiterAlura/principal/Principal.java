package com.alura.literalura.LiterAlura.principal;

import com.alura.literalura.LiterAlura.modelo.DatosGeneral;
import com.alura.literalura.LiterAlura.modelo.DatosLibro;
import com.alura.literalura.LiterAlura.modelo.Libro;
import com.alura.literalura.LiterAlura.request.ConsumoDatos;
import com.alura.literalura.LiterAlura.request.DatosApi;
import com.alura.literalura.LiterAlura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final DatosApi consumoAPI = new DatosApi();
    private final ConsumoDatos conversor = new ConsumoDatos();
    private LibroService libroService;

    @Autowired
    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    // Mostrar el menú de opciones
    public void mostrarMenu() {
        while (true) {
            System.out.println("""
                    *************************************************************************
                    Bienvenido a la biblioteca Alura!
                    Elige el método para realizar la búsqueda de tu libro:
                    ----------------------------------------------------------------------
                    1# Por nombre del libro
                    2# Por un trozo del nombre del libro (pueden aparecer varios libros)
                    3# Mostrar libros guardados por nombres 
                    4# Si deseas eliminar un libro de la biblioteca
                    5# Buscar libros por autores en determinado lapso de tiempo
                    6# Buscar libros por idioma     
                    7# Obtener top 5 mas descargados 
                    8# Obtener top 10 mas descargados desde la Api
                    ----------------------------------------------------------------------
                    3# Salir.
                    *************************************************************************
                    """);
            System.out.print("Ingresa el número de la opción deseada: ");
            int opcion = obtenerOpcion();

            switch (opcion) {
                case 1: obtenerLibro();
                    break;
                case 2: obtenerListaLibros();
                    break;
                case 3: mostrarLibrosGuardadosPorTitulo();
                    break;
                case 4: removerLibro();
                    break;
                case 5: mostrarLibrosPorLapsoDeTiempo();
                    break;
                case 6: mostrarLibrosPorLenguaje();
                    break;
                case 7: obtenerTop5();
                    break;
                case 8: obtenerTop10DesdeApi();
                    break;
            }

            System.out.print("¿Desea volver al menu? (Si/No): ");
            String respuesta = teclado.nextLine().trim().toLowerCase();
            if (respuesta.equals("no") || respuesta.equals("n")) {
                System.out.println("Gracias por usar Biblioteca Alura. ¡Hasta pronto!");
                System.exit(0); // Termina el programa
            }
        }
    }
    //Constructor de la URL
    private String construirUrlBusqueda(String termino) {
        return URL_BASE + "?search=" + termino.replace(" ", "+");
    }

    private String obtenerNombre() {
        while (true) {
            System.out.print("Ingrese el nombre o fragmento del libro que desea buscar: ");
            String nombreLibro = teclado.nextLine().trim();
            if (!nombreLibro.isEmpty()) {
                return nombreLibro;
            }
            System.out.println("El nombre del libro no puede estar vacío. Intenta de nuevo.");
        }
    }
    //Obtener datos de los libros
    private DatosGeneral getDatosLibro(){
        var nombreLibro = obtenerNombre();
        String json = consumoAPI.obtenerDatos(construirUrlBusqueda(nombreLibro));
        DatosGeneral datosBusqueda = conversor.obtenerDatos(json, DatosGeneral.class);
        Optional<DatosLibro> libro = datosBusqueda.libro().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if(libro.isPresent()){
            System.out.println("**********************************");
            System.out.println("Libro encontrado:" + libro.get());
            System.out.println("**********************************");
        }
        return datosBusqueda;
    }


    // Metodo para buscar un libro por nombre exacto
    private void obtenerLibro() {
        // Obtiene los datos generales a partir del nombre ingresado por el usuario
        DatosGeneral datos = getDatosLibro();

        // Busca el primer libro de los resultados
        Optional<DatosLibro> libroEncontrado = datos.libro().stream().findFirst();

        // Procesa el libro encontrado
        libroEncontrado.ifPresentOrElse(
                datosLibro -> {
                    try {
                        // Convierte DatosLibro a Libro
                        Libro libro = new Libro(datosLibro);
                        if(libroService.obtenerTodosLosLibros().stream().noneMatch(l -> l.getTitulo().equals(libro.getTitulo()))){
                            // Guarda en el repositorio
                            libroService.guardarLibro(libro);
                            System.out.println("Libro guardado exitosamente: " + libro.getTitulo());}
                        else {
                            System.out.println("El libro ya se encuentra en la base de datos.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al guardar el libro: " + e.getMessage());
                    }
                },
                () -> System.out.println("No se encontró un libro para guardar.")
        );
    }


    private List<DatosLibro> convertirDatosGeneralADatosLibro(DatosGeneral datosGeneral) {
        return datosGeneral.libro().stream()  // Obtenemos la lista de DatosLibro de DatosGeneral
                .map(libro -> new DatosLibro(
                        libro.autor(),// Convertimos cada objeto DatosLibro
                        libro.titulo(),           // Accedemos al título con el método getter
                        libro.lengua(),
                        libro.descargas()// Accedemos a la lengua con el método getter
                )).collect(Collectors.toList());  // Recogemos los elementos convertidos en una lista
    }



    // Método para mostrar los libros encontrados por fragmento del nombre
    private void obtenerListaLibros() {
        var nombreLibro = obtenerNombre();
        String json = consumoAPI.obtenerDatos(construirUrlBusqueda(nombreLibro));
        DatosGeneral datosBusqueda = conversor.obtenerDatos(json, DatosGeneral.class);

        List<DatosLibro> librosEncontrados = convertirDatosGeneralADatosLibro(datosBusqueda);
        if (librosEncontrados.isEmpty()) {
            System.out.println("No se encontraron libros con ese nombre.");
        } else {
            AtomicInteger contador = new AtomicInteger(1); // Inicia el contador en 1
            System.out.println("**********************************");
            System.out.println("Libros encontrados:");
            for (int i = 0; i < librosEncontrados.size(); i++) {
                System.out.printf("[%d] %s%n", contador.getAndIncrement(), librosEncontrados.get(i).titulo());
            }
            System.out.println("**********************************");

            // Pedir al usuario qué libros desea guardar
            System.out.println("¿Qué libros desea guardar? (Ingrese los números separados por comas o -1 para salir):");

            while (true) {
                String entrada = teclado.nextLine().trim();

                if (entrada.equals("-1")) { // Finalizar si el usuario ingresa -1
                    System.out.println("Operación finalizada.");
                    break;
                }

                // Procesar la entrada del usuario
                try {
                    List<Integer> opciones = List.of(entrada.split(","))
                            .stream()
                            .map(String::trim) // Elimina espacios en blanco
                            .map(Integer::parseInt) // Convierte a enteros
                            .collect(Collectors.toList());

                    for (int opcion : opciones) {
                        if (opcion >= 1 && opcion <= librosEncontrados.size()) {
                            DatosLibro datosLibroSeleccionado = librosEncontrados.get(opcion - 1); // Ajusta índice
                            Libro libro = new Libro(datosLibroSeleccionado); // Crea un objeto Libro a partir de DatosLibro

                            try {
                                libroService.guardarLibro(libro);
                                System.out.println("Libro guardado exitosamente: " + libro.getTitulo());
                            } catch (Exception e) {
                                System.err.println("Error al guardar el libro: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Opción inválida: " + opcion + ". Por favor, elija un número válido.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Asegúrese de ingresar números separados por comas.");
                }
            }
        }
    }

    // Obtener la opción seleccionada por el usuario
    private int obtenerOpcion() {
        while (true) {
            try {
                int opcion = teclado.nextInt();
                teclado.nextLine(); // Limpia el buffer
                if (opcion >= 1 && opcion <= 8) {
                    return opcion;
                }
                System.out.println("Por favor, ingresa una opción válida");
            } catch (Exception e) {
                System.out.println("Entrada no válida. Intenta de nuevo.");
                teclado.nextLine(); // Limpia el buffer
            }
        }
    }

    private void removerLibro() {
        // Obtenemos todos los libros
        var libros = libroService.obtenerTodosLosLibros();

        // Creamos una lista con las representaciones de los libros
        var eliminarId = libros.stream()
                .map(l -> "ID ] " + l.getId() + " [Titulo: " + l.getTitulo())
                .collect(Collectors.toList());
        System.out.println(eliminarId);

        // Creamos una lista solo con los IDs para la verificación
        var idList = libros.stream()
                .map(l -> l.getId())
                .collect(Collectors.toList());

        System.out.println("Introduce el ID del libro que deseas eliminar:");
        var elegirId = teclado.nextLong();

        // Verificamos si el ID existe en la lista de IDs
        if (idList.contains(elegirId)) {
            libroService.eliminarLibro(elegirId);
            System.out.println("Libro eliminado con éxito");
        } else {
            System.out.println("El ID introducido no existe en la lista. \n");
        }
        teclado.nextLine();
    }




    private void mostrarLibrosGuardadosPorTitulo() {
        // Obtener todos los libros desde el repositorio
        List<Libro> libros = libroService.obtenerTodosLosLibros(); // Necesitas tener acceso a LibroService

        if (libros.isEmpty()) {
            System.out.println("No hay libros almacenados.");
        } else {
            System.out.println("Libros almacenados:");
            // Iterar y mostrar los títulos
            for (Libro libro : libros) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Lengua: " + libro.getLengua());
                System.out.println("Descargas: " + libro.getDescargas());
                System.out.println("------------------------");
            }
        }
    }

    private void mostrarLibrosPorLapsoDeTiempo() {
        System.out.println("Ingrese un lapso de tiempo para mostrar los autores que hayan estrito un libro en el mismo: ");
        System.out.println("Ficha inicio: ");
        int fechaInicioEstablecida = teclado.nextInt();
        System.out.println("Ficha fin: ");
        int fechaFinEstablecida = teclado.nextInt();
        teclado.nextLine();

        List<Libro> libros = libroService.librosPorLapsoDeTiempo(fechaInicioEstablecida, fechaFinEstablecida);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con autores en ese lapso de tiempo.");
        } else {
            System.out.println("Libros encontrados:");
            libros.forEach(libro -> System.out.println("Título: " + libro.getTitulo()));
        }
    }

    private void mostrarLibrosPorLenguaje() {
        System.out.println("Ingrese el idioma que desea tener en sus libros: [ES] [EN]");
        String lenguaBuscada = teclado.nextLine();
        List<Libro> libros = libroService.librosPorLengua(lenguaBuscada);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese idioma.");
        }else {
            libros.forEach(libro -> System.out.println("[Titulo: "+ libro.getTitulo() + "] [Lengua: " + libro.getLengua().toUpperCase()+"]"));
        }
    }
    // Obtener top 10 desde la Api
    private void obtenerTop10DesdeApi() {
        var top10 = URL_BASE + "?download_count=&page=1";
        String json = consumoAPI.obtenerDatos(top10);
        DatosGeneral datosBusqueda = conversor.obtenerDatos(json, DatosGeneral.class);
        List<DatosLibro> librosEncontrados = convertirDatosGeneralADatosLibro(datosBusqueda);
        librosEncontrados.stream()
                .limit(10)
                .map(l ->"[Titulo] "+ l.titulo() +" [Descargas] "+l.descargas())
                .forEach(System.out::println);
    }
    //Obtener top 5 desde la base de datos previamente guardada
    private void obtenerTop5() {
        List<Libro> top5Descargas = libroService.libroPorDescargassTop5();
        AtomicInteger contador = new AtomicInteger(1);
        top5Descargas.stream()
                .map(l ->"[Top "+ contador.getAndIncrement()+"] " + "[Titulo] "+ l.getTitulo())
                .forEach(System.out::println);
    }

}