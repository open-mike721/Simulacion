package GeneradoresDeNumerosPseudoaleatorios;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CongruencialAditivo extends GeneradorBase {

    // K semillas iniciales: X_0 .. X_{k-1}. La ventana siempre guarda los
    // último K estados; con X_{i-1} al final y X_{i-k} al inicio.
    private final List<Long> ventana;
    private final long m;
    private final int k;

    public CongruencialAditivo(long[] semillas, long m) {
        super(semillas[semillas.length - 1], m);
        this.m = m;
        this.k = semillas.length;
        this.ventana = new ArrayList<>(k + 1);
        for (long semilla : semillas) {
            ventana.add(semilla);
        }
    }

    @Override
    public long siguiente() {
        // X_i = (X_{i-1} + X_{i-k}) mod m, i >= k.
        // BigInteger evita el desbordamiento de la suma para cualquier m.
        long x = BigInteger.valueOf(ventana.get(ventana.size() - 1))
                .add(BigInteger.valueOf(ventana.get(0)))
                .mod(BigInteger.valueOf(m))
                .longValue();
        ventana.add(x);
        if (ventana.size() > k) {
            ventana.remove(0);
        }
        valorActual = x;
        return valorActual;
    }

    @Override
    public String estadosAnteriores() {
        // Operandos del paso: X_{i-k} y X_{i-1}.
        return ventana.get(0) + ", " + ventana.get(ventana.size() - 1);
    }
}