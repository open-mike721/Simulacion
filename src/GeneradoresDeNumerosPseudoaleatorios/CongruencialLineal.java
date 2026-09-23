package GeneradoresDeNumerosPseudoaleatorios;

import java.math.BigInteger;

public class CongruencialLineal extends GeneradorBase {

    private final long a;
    private final long b;
    private final long m;


    public CongruencialLineal(long semillaInicial, long a, long b, long m) {
        super(semillaInicial, m);
        this.a = a;
        this.b = b;
        this.m = m;
    }

    @Override
    public long siguiente() {
        // Fórmula: X_i = (a * X_{i-1} + b) mod m.
        // Se usa BigInteger para admitir CUALQUIER módulo m (y coeficientes
        // grandes) sin desbordamiento de tipo long en el producto a * X.
        valorActual = BigInteger.valueOf(a)
                .multiply(BigInteger.valueOf(valorActual))
                .add(BigInteger.valueOf(b))
                .mod(BigInteger.valueOf(m))
                .longValue();
        return valorActual;
    }
}
