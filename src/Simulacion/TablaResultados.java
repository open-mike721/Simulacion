package Simulacion;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class TablaResultados {

    private TablaResultados() {
    }

    public static void imprimirTabla(String titulo, String cabeceraEstado,
                                     List<Fila> filas) {
        int anchoI = 8, anchoEstado = 20, anchoNuevo = 16, anchoU = 20;
        for (Fila f : filas) {
            anchoI = Math.max(anchoI, String.valueOf(f.indice()).length());
            anchoEstado = Math.max(anchoEstado, f.estadosAnteriores().length());
            anchoNuevo = Math.max(anchoNuevo, String.valueOf(f.nuevo()).length());
        }

        String formatoFila = "| %-" + anchoI + "s | %-" + anchoEstado + "s | %-"
                + anchoNuevo + "s | %-" + anchoU + "s |%n";
        String separador = "+" + "-".repeat(anchoI + 2)
                + "+" + "-".repeat(anchoEstado + 2)
                + "+" + "-".repeat(anchoNuevo + 2)
                + "+" + "-".repeat(anchoU + 2) + "+";

        System.out.println("\n>> " + titulo);
        System.out.println(separador);
        System.out.printf(formatoFila, "Índice", cabeceraEstado,
                "Nuevo estado", "Número aleatorio");
        System.out.println(separador);
        for (Fila f : filas) {
            System.out.printf(formatoFila, f.indice(), f.estadosAnteriores(),
                    f.nuevo(), String.format("%.9f", f.uniforme()));
        }
        System.out.println(separador);
        System.out.println("Filas generadas: " + filas.size());
    }

    public static void exportarCSV(String nombre, List<Fila> filas) {
        String archivo = "secuencia_" + nombre + "_" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
        try (PrintWriter escritor = new PrintWriter(archivo, StandardCharsets.UTF_8)) {
            escritor.println("Indice,EstadosAnteriores,NuevoEstado,NumeroAleatorio");
            for (Fila f : filas) {
                escritor.printf("%d,\"%s\",%d,%.9f%n", f.indice(),
                        f.estadosAnteriores(), f.nuevo(), f.uniforme());
            }
            System.out.println("Tabla guardada en: " + archivo);
        } catch (IOException e) {
            System.out.println("ERROR al guardar el archivo CSV: " + e.getMessage());
        }
    }
}