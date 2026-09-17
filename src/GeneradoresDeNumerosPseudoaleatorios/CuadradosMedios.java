package GeneradoresDeNumerosPseudoaleatorios;

public class CuadradosMedios extends GeneradorBase {

    private final int digitos;

    public CuadradosMedios(long semillaInicial, int digitos) {
        super(semillaInicial, Math.pow(10, digitos));
        this.digitos = digitos;
    }

    @Override
    public long siguiente() {
        long cuadrado = valorActual * valorActual;
        String cuadradoStr = NumerosUtil.rellenarConCeros(String.valueOf(cuadrado), digitos * 2);
        String medio = NumerosUtil.extraerCentro(cuadradoStr, digitos);

        valorActual = Long.parseLong(medio);
        return valorActual;
    }
}