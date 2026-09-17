package GeneradoresDeNumerosPseudoaleatorios;

import java.math.BigInteger;

public class CongruencialLineal extends GeneradorBase {

    private final long a;
    private final long c;
    private final long m;


    public CongruencialLineal(long semillaInicial, long a, long c, long m) {
        super(semillaInicial, m);
        this.a = a;
        this.c = c;
        this.m = m;
    }

    @Override
    public long siguiente() {
        valorActual = BigInteger.valueOf(a)
                .multiply(BigInteger.valueOf(valorActual))
                .add(BigInteger.valueOf(c))
                .mod(BigInteger.valueOf(m))
                .longValue();
        return valorActual;
    }
}
