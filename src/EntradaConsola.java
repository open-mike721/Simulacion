import GeneradoresDeNumerosPseudoaleatorios.SemillaInvalidaException;

import java.util.Scanner;

public final class EntradaConsola {

    // Valor máximo permitido: semillas de máximo 9 dígitos.
    private static final long SEMILLA_MAXIMA = 999_999_999L;

    private EntradaConsola() {
    }


    public static long leerSemilla(Scanner scanner, String etiqueta) {
        while (true) {
            System.out.print(etiqueta + " (número de máximo 9 dígitos): ");
            try {
                long semilla = Long.parseLong(scanner.nextLine().trim());
                if (semilla < 0 || semilla > SEMILLA_MAXIMA) {
                    throw new SemillaInvalidaException(
                            "La semilla debe tener como máximo 9 dígitos.");
                }
                return semilla;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes ingresar un número entero.");
            } catch (SemillaInvalidaException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    public static long leerN(Scanner scanner) {
        while (true) {
            System.out.print("Cantidad de números a generar (N, usa 0 para " +
                    "generar hasta que se repitan): ");
            try {
                long n = Long.parseLong(scanner.nextLine().trim());
                if (n >= 0) {
                    return n;
                }
                System.out.println("ERROR: N no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes ingresar un número entero.");
            }
        }
    }

    public static long leerLong(Scanner scanner, String mensaje, long min,
                                long max, String errorFueraDeRango) {
        while (true) {
            System.out.print(mensaje);
            try {
                long valor = Long.parseLong(scanner.nextLine().trim());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("ERROR: " + errorFueraDeRango);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes ingresar un número entero.");
            }
        }
    }

    public static boolean confirmar(Scanner scanner, String pregunta) {
        while (true) {
            System.out.print(pregunta);
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.matches("s|si|sí|y|yes")) {
                return true;
            }
            if (respuesta.matches("n|no")) {
                return false;
            }
            System.out.println("ERROR: Responde 's' (sí) o 'n' (no).");
        }
    }
}