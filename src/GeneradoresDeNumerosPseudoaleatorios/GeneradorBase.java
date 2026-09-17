package GeneradoresDeNumerosPseudoaleatorios;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneradorBase {

    protected long valorActual; // Semilla Incial
    private final double divisor; // Valor que divide la semilla para obtener el numero en formato decimal


    protected GeneradorBase(long valorInicial, double divisor) {
        this.valorActual = valorInicial;
        this.divisor = divisor;
    }

    public abstract long siguiente();

    public double comoDecimal() {
        return valorActual / divisor;
    }

    public long getValorActual() {
        return valorActual;
    }

    public String estadosAnteriores() {
        return String.valueOf(valorActual);
    }

    public List<Long> generarSecuencia(int cantidad) {
        List<Long> numeros = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            numeros.add(siguiente());
        }
        return numeros;
    }
}
