package mx.unam.ciencias.icc.proyecto2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/**
 * Clase para cargar las aplicaciones del proyecto2
 */
public class Application {
    
    /* Código de terminación por error de uso. */
    private static final int ERROR_USO = 1;
    
    /* Imprime en pantalla cómo debe usarse el programa y lo termina. */
    private static void uso() {
	System.out.println("Uso: java -jar target/proyecto2.jar [-r|-o <archivo>] <archivos> \n");
	System.exit(ERROR_USO);
    }

    /**
     * Crea una instancia de la aplicación e invoca a su método ejecuta
     * @param args el arreglo de cadenas de la línea de comandos.
     */
    public static void launch(String[] args){
	try {
	    EntradaEstandar ee = new EntradaEstandar(args);
	    String bandera = formatoBandera(ee);
	    Aplicacion aplicacion;
	    if(ee.getArchivos().esVacia()) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		aplicacion = new Aplicacion(bandera, in, ee.getIdentificador());
	    } else 
		aplicacion = new Aplicacion(bandera, ee.getArchivos(), ee.getIdentificador());
	    aplicacion.ejecuta();
	} catch (IOException |
		 IllegalArgumentException e) {
	    uso();
	}
    }

    /**
     * Nos dice que formato debe tener la bandera.
     * @param ee la entrada estándar de la cual debe obtener las banderas.
     * @return el formato que debe tener la bandera.
     */
    private static String formatoBandera(EntradaEstandar ee) {
	if(ee.getGuarda() == true && ee.getReversa() == true)
	    return "-or";
	if(ee.getGuarda() == true)
	    return "-o";
	if(ee.getReversa() == true)
	    return "-r";
	return "";
    }
}
