import GeneradoresDeNumerosPseudoaleatorios.GeneradorBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GeneradorSecuencia {

    private GeneradorSecuencia() {
    }

    public static List<Fila> generarFilas(GeneradorBase generador, long n) {
        List<Fila> filas = new ArrayList<>();
        if (n > 0) {
            for (long i = 1; i <= n; i++) {
                filas.add(calcularFila(generador, i));
            }
        } else {
            Map<Long, Long> primeraVez = new HashMap<>();
            primeraVez.put(generador.getValorActual(), 0L);
            long i = 1;
            while (true) {
                Fila fila = calcularFila(generador, i);
                filas.add(fila);
                Long aparecioAntes = primeraVez.putIfAbsent(fila.nuevo(), i);
                if (aparecioAntes != null) {
                    String origen = (aparecioAntes == 0L)
                            ? "la semilla (iteración 0)"
                            : "la iteración " + aparecioAntes;
                    System.out.printf("Ciclo detectado: el valor %d ya apareció en %s. "
                                    + "Generación detenida en la iteración %d.%n",
                            fila.nuevo(), origen, fila.indice());
                    break;
                }
                i++;
            }
        }
        return filas;
    }

    private static Fila calcularFila(GeneradorBase generador, long i) {
        String estadosAnteriores = generador.estadosAnteriores();
        long nuevo = generador.siguiente();         // X_i
        double uniforme = generador.comoDecimal();  // U_i
        return new Fila(i, estadosAnteriores, nuevo, uniforme);
    }
}