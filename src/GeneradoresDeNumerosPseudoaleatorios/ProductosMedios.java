package GeneradoresDeNumerosPseudoaleatorios;

public class ProductosMedios extends GeneradorBase {

    private long anterior;
    private final int digitos;

    public ProductosMedios(long semilla1, long semilla2, int digitos) {
        super(semilla2, Math.pow(10, digitos));
        this.anterior = semilla1;
        this.digitos = digitos;
    }

    @Override
    public long siguiente() {
        long producto = anterior * valorActual;
        String productoStr = NumerosUtil.rellenarConCeros(String.valueOf(producto), digitos * 2);
        String medio = NumerosUtil.extraerCentro(productoStr, digitos);

        anterior = valorActual;
        valorActual = Long.parseLong(medio);
        return valorActual;
    }

    @Override
    public String estadosAnteriores() {
        return anterior + ", " + valorActual;
    }
}
