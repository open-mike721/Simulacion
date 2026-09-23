package Simulacion;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner = new Scanner(System.in);

    public void ejecutar() {
        System.out.println("======================================");
        System.out.println("           SIMULACIÓN");
        System.out.println("Generadores de Números Pseudoaleatorios");
        System.out.println("======================================");

        boolean salir = false;
        while (!salir) {
            switch (mostrarMenu()) {
                case 1 -> new MenuTerminal(scanner).ejecutar();
                // Nuevo: módulo de pruebas estadísticas (en construcción).
                case 2 -> ejecutarPruebasEstadisticas();
                case 3 -> salir = true;
                default -> System.out.println("ERROR: Opción no válida.");
            }
        }
        System.out.println("¡Hasta luego!");
    }

    private int mostrarMenu() {
        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1) Generar números aleatorios");
            System.out.println("2) Pruebas Estadísticas");
            System.out.println("3) Salir");
            System.out.print("Elige una opción (1-3): ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 3) {
                    return opcion;
                }
                System.out.println("ERROR: La opción debe estar entre 1 y 3.");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes ingresar un número entero (1-3).");
            }
        }
    }

    // Nuevo: aviso del futuro módulo de pruebas estadísticas. Las clases del
    // paquete PruebasEstadisticas se implementarán cuando se indique.
    private void ejecutarPruebasEstadisticas() {
        System.out.println("\n=== Pruebas Estadísticas ===");
        System.out.println("Módulo en construcción (disponible próximamente).");
    }
}