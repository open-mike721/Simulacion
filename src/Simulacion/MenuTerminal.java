package Simulacion;

import GeneradoresDeNumerosPseudoaleatorios.CongruencialAditivo;
import GeneradoresDeNumerosPseudoaleatorios.CongruencialLineal;
import GeneradoresDeNumerosPseudoaleatorios.CuadradosMedios;
import GeneradoresDeNumerosPseudoaleatorios.GeneradorBase;
import GeneradoresDeNumerosPseudoaleatorios.MultiplicacionConstante;
import GeneradoresDeNumerosPseudoaleatorios.NumerosUtil;
import GeneradoresDeNumerosPseudoaleatorios.ProductosMedios;
import GeneradoresDeNumerosPseudoaleatorios.SemillasDiferenteLongitudException;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class MenuTerminal {

    private final Scanner scanner;

    private static final long SEMILLA_MAXIMA = 999_999_999L;

    public MenuTerminal(Scanner scanner) {
        this.scanner = scanner;
    }

    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            switch (mostrarMenu()) {
                case 1 -> ejecutarCuadradosMedios();
                case 2 -> ejecutarProductosMedios();
                case 3 -> ejecutarMultiplicacionConstante();
                case 4 -> ejecutarCongruencialLineal();
                case 5 -> ejecutarCongruencialAditivo();
                case 6 -> salir = true;
                default -> System.out.println("ERROR: Opción no válida.");
            }
        }
    }

    private int mostrarMenu() {
        while (true) {
            System.out.println("\n--- MENÚ DE GENERADORES ---");
            System.out.println("1) Método de Cuadrados Medios");
            System.out.println("2) Método de Productos Medios");
            System.out.println("3) Método de Multiplicación Constante");
            System.out.println("4) Método Congruencial Lineal");
            System.out.println("5) Método Congruencial Aditivo");
            System.out.println("6) Volver al menú principal");
            System.out.print("Elige una opción (1-6): ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 6) {
                    return opcion;
                }
                System.out.println("ERROR: La opción debe estar entre 1 y 6.");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes ingresar un número entero (1-6).");
            }
        }
    }

    private void ejecutarCuadradosMedios() {
        System.out.println("\n=== Método de Cuadrados Medios ===");
        ejecutarSecuencias("Cuadrados Medios", "cuadrados_medios", "Estado anterior", () -> {
            long semilla = EntradaConsola.leerSemilla(scanner, "Semilla");
            int digitos = NumerosUtil.contarDigitos(semilla);
            return new CuadradosMedios(semilla, digitos);
        });
    }

    private void ejecutarProductosMedios() {
        System.out.println("\n=== Método de Productos Medios ===");
        ejecutarSecuencias("Productos Medios", "productos_medios", "Estados anteriores", () -> {
            long[] semillas = leerSemillasMismoTamaño();
            int digitos = NumerosUtil.contarDigitos(semillas[0]);
            return new ProductosMedios(semillas[0], semillas[1], digitos);
        });
    }

    private void ejecutarMultiplicacionConstante() {
        System.out.println("\n=== Método de Multiplicación Constante ===");
        long constante = EntradaConsola.leerLong(scanner,
                "Constante (número de máximo 9 dígitos): ", 0, SEMILLA_MAXIMA,
                "La constante debe tener como máximo 9 dígitos.");
        ejecutarSecuencias("Multiplicación Constante", "multiplicacion_constante",
                "Estado anterior", () -> {
                    long semilla = EntradaConsola.leerSemilla(scanner, "Semilla");
                    int digitos = NumerosUtil.contarDigitos(semilla);
                    return new MultiplicacionConstante(semilla, constante, digitos);
                });
    }

    private void ejecutarCongruencialLineal() {
        System.out.println("\n=== Método Congruencial Lineal ===");
        long m = EntradaConsola.leerLong(scanner, "Módulo (m, entero positivo): ",
                1, Long.MAX_VALUE, "El módulo debe ser un entero positivo.");
        long a = EntradaConsola.leerLong(scanner, "Multiplicador (a, 0 ≤ a < m): ",
                0, m - 1, "El multiplicador debe estar entre 0 y m-1.");
        long b = EntradaConsola.leerLong(scanner, "Incremento (b, 0 ≤ b < m): ",
                0, m - 1, "El incremento debe estar entre 0 y m-1.");

        while (true) {
            long semilla = EntradaConsola.leerSemilla(scanner, "Semilla");
            long n = EntradaConsola.leerN(scanner);

            GeneradorBase generador = new CongruencialLineal(semilla, a, b, m);
            List<Fila> filas = GeneradorSecuencia.generarFilas(generador, n);

            TablaResultados.imprimirTabla("Congruencial Lineal", "Estado anterior", filas);
            if (!procesarPosGeneracion("congruencial_lineal", filas)) {
                return;
            }

            // Nuevo: pregunta si se mantienen las mismas constantes a, b y m
            // para la siguiente secuencia; si no, se vuelven a pedir.
            if (EntradaConsola.confirmar(scanner,
                    "\n¿Deseas seguir usando las mismas constantes (a, b, m)? (s/n): ")) {
                continue;
            }
            m = EntradaConsola.leerLong(scanner, "Módulo (m, entero positivo): ",
                    1, Long.MAX_VALUE, "El módulo debe ser un entero positivo.");
            a = EntradaConsola.leerLong(scanner, "Multiplicador (a, 0 ≤ a < m): ",
                    0, m - 1, "El multiplicador debe estar entre 0 y m-1.");
            b = EntradaConsola.leerLong(scanner, "Incremento (b, 0 ≤ b < m): ",
                    0, m - 1, "El incremento debe estar entre 0 y m-1.");
        }
    }

    private void ejecutarCongruencialAditivo() {
        System.out.println("\n=== Método Congruencial Aditivo ===");
        long m = EntradaConsola.leerLong(scanner, "Módulo (m, entero positivo): ",
                1, Long.MAX_VALUE, "El módulo debe ser un entero positivo.");
        ejecutarSecuencias("Congruencial Aditivo", "congruencial_aditivo",
                "Estados anteriores", () -> {
                    int numSemillas = (int) EntradaConsola.leerLong(scanner,
                            "Número de semillas (n, entre 2 y 20): ", 2, 20,
                            "Se necesitan al menos 2 semillas.");
                    long[] semillas = new long[numSemillas];
                    for (int i = 0; i < numSemillas; i++) {
                        semillas[i] = EntradaConsola.leerSemilla(scanner,
                                "Semilla " + (i + 1));
                    }
                    return new CongruencialAditivo(semillas, m);
                });
    }

    private long[] leerSemillasMismoTamaño() {
        while (true) {
            long semilla1 = EntradaConsola.leerSemilla(scanner, "Primera semilla");
            long semilla2 = EntradaConsola.leerSemilla(scanner, "Segunda semilla");
            try {
                if (NumerosUtil.contarDigitos(semilla1)
                        != NumerosUtil.contarDigitos(semilla2)) {
                    throw new SemillasDiferenteLongitudException(
                            "En Productos Medios ambas semillas deben tener el "
                                    + "mismo número de dígitos.");
                }
                return new long[]{semilla1, semilla2};
            } catch (SemillasDiferenteLongitudException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    // Nuevo: ciclo común de una secuencia (semillas → generador → tabla →
    // pos-generación), reutilizable por cualquier método y por el futuro
    // módulo de Pruebas Estadísticas.
    private void ejecutarSecuencias(String titulo, String csvNombre,
                                    String cabeceraEstado,
                                    Supplier<GeneradorBase> fabrica) {
        while (true) {
            GeneradorBase generador = fabrica.get();
            long n = EntradaConsola.leerN(scanner);

            List<Fila> filas = GeneradorSecuencia.generarFilas(generador, n);

            TablaResultados.imprimirTabla(titulo, cabeceraEstado, filas);
            if (!procesarPosGeneracion(csvNombre, filas)) {
                return;
            }
        }
    }

    private boolean procesarPosGeneracion(String nombre, List<Fila> filas) {
        if (EntradaConsola.confirmar(scanner, "¿Deseas guardar la tabla en formato CSV? (s/n): ")) {
            TablaResultados.exportarCSV(nombre, filas);
        }
        return EntradaConsola.confirmar(scanner, "\n¿Deseas generar otra secuencia? (s/n): ");
    }
}