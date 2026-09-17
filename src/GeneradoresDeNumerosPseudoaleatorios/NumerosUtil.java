package GeneradoresDeNumerosPseudoaleatorios;

public class NumerosUtil {

    private NumerosUtil() {
    }


    public static String rellenarConCeros(String numero, int longitudEsperada) {

        StringBuilder resultado = new StringBuilder(numero);

        while (resultado.length() < longitudEsperada) {
            resultado.insert(0, "0");
        }
        return resultado.toString();
    }

    public static String extraerCentro(String numero, int digitosSalida) {
        int inicio = (numero.length() - digitosSalida) / 2;
        return numero.substring(inicio, inicio + digitosSalida);
    }

    public static int contarDigitos(long numero) {
        return String.valueOf(numero).length();
    }
}
