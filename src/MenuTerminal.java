import GeneradoresDeNumerosPseudoaleatorios.CongruencialLineal;
import GeneradoresDeNumerosPseudoaleatorios.CuadradosMedios;
import GeneradoresDeNumerosPseudoaleatorios.GeneradorBase;
import GeneradoresDeNumerosPseudoaleatorios.MultiplicacionConstante;
import GeneradoresDeNumerosPseudoaleatorios.NumerosUtil;
import GeneradoresDeNumerosPseudoaleatorios.ProductosMedios;
import GeneradoresDeNumerosPseudoaleatorios.SemillasDiferenteLongitudException;

import java.util.List;
import java.util.Scanner;

public class MenuTerminal {

    private final Scanner scanner = new Scanner(System.in);

    private static final long SEMILLA_MAXIMA = 999_999_999L;

    public void ejecutar() {
        System.out.println("======================================");
        System.out.println(" ¡Bienvenido! Generadores de Números");
        System.out.println("     Pseudoaleatorios");
        System.out.println("======================================");

        boolean salir = false;
        while (!salir) {
            switch (mostrarMenu()) {
                case 1 -> ejecutarCuadradosMedios();
                case 2 -> ejecutarProductosMedios();
                case 3 -> ejecutarMultiplicacionConstante();
                case 4 -> ejecutarCongruencialLineal();
                case 5 -> salir = true;
                default -> System.out.println("ERROR: Opción no válida.");
            }
        }
        System.out.println("¡Hasta luego!");
    }


    private int mostrarMenu() {
        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1) Método de Cuadrados Medios");
            System.out.println("2) Método de Productos Medios");
            System.out.println("3) Método de Multiplicación Constante");
            System.out.println("4) Método Congruencial Lineal");
            System.out.println("5) Salir");
            System.out.print("Elige una opción (1-5): ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 5) {
                    return opcion;
                }
                System.out.println("ERROR: La opción debe estar entre 1 y 5.");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes ingresar un número entero (1-5).");
            }
        }
    }

    private void ejecutarCuadradosMedios() {
        System.out.println("\n=== Método de Cuadrados Medios ===");
        while (true) {
            long semilla = EntradaConsola.leerSemilla(scanner, "Semilla");
            long n = EntradaConsola.leerN(scanner);
            int digitos = NumerosUtil.contarDigitos(semilla);

            GeneradorBase generador = new CuadradosMedios(semilla, digitos);
            List<Fila> filas = GeneradorSecuencia.generarFilas(generador, n);

            TablaResultados.imprimirTabla("Cuadrados Medios", "Estado anterior", filas);
            if (!procesarPosGeneracion("cuadrados_medios", filas)) {
                return;
            }
        }
    }

    private void ejecutarProductosMedios() {
        System.out.println("\n=== Método de Productos Medios ===");
        while (true) {
            long[] semillas = leerSemillasMismoTamaño();
            long n = EntradaConsola.leerN(scanner);
            int digitos = NumerosUtil.contarDigitos(semillas[0]);

            GeneradorBase generador = new ProductosMedios(semillas[0], semillas[1], digitos);
            List<Fila> filas = GeneradorSecuencia.generarFilas(generador, n);

            TablaResultados.imprimirTabla("Productos Medios", "Estados anteriores", filas);
            if (!procesarPosGeneracion("productos_medios", filas)) {
                return;
            }
        }
    }

    private void ejecutarMultiplicacionConstante() {
        System.out.println("\n=== Método de Multiplicación Constante ===");
        long constante = EntradaConsola.leerLong(scanner,
                "Constante (número de máximo 9 dígitos): ", 0, SEMILLA_MAXIMA,
                "La constante debe tener como máximo 9 dígitos.");
        while (true) {
            long semilla = EntradaConsola.leerSemilla(scanner, "Semilla");
            long n = EntradaConsola.leerN(scanner);
            int digitos = NumerosUtil.contarDigitos(semilla);

            GeneradorBase generador = new MultiplicacionConstante(semilla, constante, digitos);
            List<Fila> filas = GeneradorSecuencia.generarFilas(generador, n);

            TablaResultados.imprimirTabla("Multiplicación Constante", "Estado anterior", filas);
            if (!procesarPosGeneracion("multiplicacion_constante", filas)) {
                return;
            }
        }
    }

    private void ejecutarCongruencialLineal() {
        System.out.println("\n=== Método Congruencial Lineal ===");
        long m = EntradaConsola.leerLong(scanner, "Módulo (m, entero positivo): ",
                1, Long.MAX_VALUE, "El módulo debe ser un entero positivo.");
        long a = EntradaConsola.leerLong(scanner, "Multiplicador (a, 0 ≤ a < m): ",
                0, m - 1, "El multiplicador debe estar entre 0 y m-1.");
        long c = EntradaConsola.leerLong(scanner, "Incremento (c, 0 ≤ c < m): ",
                0, m - 1, "El incremento debe estar entre 0 y m-1.");

        while (true) {
            long semilla = EntradaConsola.leerSemilla(scanner, "Semilla");
            long n = EntradaConsola.leerN(scanner);

            GeneradorBase generador = new CongruencialLineal(semilla, a, c, m);
            List<Fila> filas = GeneradorSecuencia.generarFilas(generador, n);

            TablaResultados.imprimirTabla("Congruencial Lineal", "Estado anterior", filas);
            if (!procesarPosGeneracion("congruencial_lineal", filas)) {
                return;
            }
        }
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

    private boolean procesarPosGeneracion(String nombre, List<Fila> filas) {
        if (EntradaConsola.confirmar(scanner, "¿Deseas guardar la tabla en formato CSV? (s/n): ")) {
            TablaResultados.exportarCSV(nombre, filas);
        }
        return EntradaConsola.confirmar(scanner, "\n¿Deseas generar otra secuencia? (s/n): ");
    }
}