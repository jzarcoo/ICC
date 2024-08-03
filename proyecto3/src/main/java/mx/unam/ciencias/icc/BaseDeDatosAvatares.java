package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de avatares.
 */
public class BaseDeDatosAvatares
    extends BaseDeDatos<Avatar, CampoAvatar> {

    /**
     * Crea un avatar en blanco.
     * @return un avatar en blanco.
     */
    @Override public Avatar creaRegistro() {
        return new Avatar(null, 0, 0, 0, 0);
    }
}
