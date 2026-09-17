package GeneradoresDeNumerosPseudoaleatorios;

public class MultiplicacionConstante extends GeneradorBase {

    private final long constante;
    private final int digitos;

    public MultiplicacionConstante(long semillaInicial, long constante, int digitos) {
        super(semillaInicial, Math.pow(10, digitos));
        this.constante = constante;
        this.digitos = digitos;
    }

    @Override
    public long siguiente() {
        long producto = constante * valorActual;
        String productoStr = NumerosUtil.rellenarConCeros(String.valueOf(producto), digitos * 2);
        String medio = NumerosUtil.extraerCentro(productoStr, digitos);

        valorActual = Long.parseLong(medio);
        return valorActual;
    }
}
