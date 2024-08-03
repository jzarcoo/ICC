package mx.unam.ciencias.icc.red;

import java.io.IOException;
import mx.unam.ciencias.icc.Avatar;
import mx.unam.ciencias.icc.BaseDeDatos;
import mx.unam.ciencias.icc.BaseDeDatosAvatares;
import mx.unam.ciencias.icc.CampoAvatar;

/**
 * Clase para servidores de bases de datos de avatares.
 */
public class ServidorBaseDeDatosAvatares
    extends ServidorBaseDeDatos<Avatar> {

    /**
     * Construye un servidor de base de datos de avatares.
     * @param puerto el puerto dónde escuchar por conexiones.
     * @param archivo el archivo en el disco del cual cargar/guardar la base de
     *                datos.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    public ServidorBaseDeDatosAvatares(int puerto, String archivo)
        throws IOException {
        super(puerto, archivo);
    }

    /**
     * Crea una base de datos de avatares.
     * @return una base de datos de avatares.
     */
    @Override public
    BaseDeDatos<Avatar, CampoAvatar> creaBaseDeDatos() {
        return new BaseDeDatosAvatares();
    }
}
