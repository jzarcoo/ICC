package mx.unam.ciencias.icc.red.test;

import mx.unam.ciencias.icc.BaseDeDatosAvatares;
import mx.unam.ciencias.icc.Avatar;
import mx.unam.ciencias.icc.test.TestAvatar;

/**
 * Clase con métodos estáticos utilitarios para las pruebas unitarias de red.
 */
public class UtilRed {

    /* Evitamos instanciación. */
    private UtilRed() {}

    /* Nivel de maná inicial. */
    public static final int MANA_INICIAL = 1000000;

    /* Contador de números de maná. */
    private static int contador;

    /**
     * Espera el número de milisegundos de forma segura.
     * @param milisegundos el número de milisegundos a esperar.
     */
    public static void espera(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException ie) {}
    }

    /**
     * Llena una base de datos de avatares.
     * @param bdd la base de datos a llenar.
     * @param total el total de avatares.
     */
    public static void llenaBaseDeDatos(BaseDeDatosAvatares bdd, int total) {
        for (int i = 0; i < total; i++) {
            int m = MANA_INICIAL + i;
            bdd.agregaRegistro(TestAvatar.avatarAleatorio(m));
        }
    }

    /**
     * Crea un avatar aleatorio.
     * @param total el total de avatares.
     * @return un avatar aleatorio con un número de mana único.
     */
    public static Avatar avatarAleatorio(int total) {
        int m = MANA_INICIAL + total*2 + contador++;
        return TestAvatar.avatarAleatorio(m);
    }

}
