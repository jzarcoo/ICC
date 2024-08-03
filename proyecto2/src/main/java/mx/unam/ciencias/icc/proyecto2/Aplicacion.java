package mx.unam.ciencias.icc.proyecto2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import mx.unam.ciencias.icc.ExcepcionIndiceInvalido;
import mx.unam.ciencias.icc.IteradorLista;
import mx.unam.ciencias.icc.Lista;

/**
 * Clase para aplicaciones del proyecto2
 */
public class Aplicacion {

    /* Modo de la aplicación. */
    private enum Modo {
        /* Modo para guardar. */
        GUARDA(1),
        /* Modo para reversa. */
        REVERSA(2),
        /* Modo para guardar y reversa. */
	GUARDAYREVERSA(3),
	/* Modo para imprimir. */
	IMPRIME(4);
	
        /* Código de terminación. */
        private int codigo;
	
        /* Constructor. */
        private Modo(int codigo) {
            this.codigo = codigo;
        }
	
        /* Regresa el código. */
        public int getCodigo() {
            return codigo;
        }

        /* Regresa el modo de la bandera. */
        public static Modo getModo(String bandera) {
            switch (bandera) {
            case "-o": return GUARDA;
            case "-r": return REVERSA;
	    case "-or": return GUARDAYREVERSA;
            default: return IMPRIME;
	    }
	}
    }
    
    /* Código de terminación por error de lectura. */
    private static final int ERROR_LECTURA = 5;
    
    // Códigos ANSI para colores en la terminal
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    
    /* La lista de líneas de archivos. */
    private Lista<Cadena> lineasArchivos;
    /* La lista de líneas de archivos ordenada. */    
    private Lista<Cadena> lineasOrdenadas;
    /* La ruta del archivo identificador. */
    private String identificador;
    /* El modo de la aplicación. */
    private Modo modo;

    /**
     * Define el estado inicial de la aplicación.
     * @param bandera la bandera de modo.
     * @param archivos la lista de archivos a leer.
     * @param identificador la ruta del archivo identificador.
     */
    public Aplicacion (String bandera, Lista<String> archivos, String identificador) {
	lineasArchivos = new Lista<Cadena>();
	modo = Modo.getModo(bandera);
	this.identificador = identificador;
	leeArchivos(archivos);
    }
    
    /**
     * Define el estado inicial de la aplicación.
     * @param bandera la bandera de modo.
     * @param in la entrada estándar a leer.
     * @param identificador la ruta del archivo identificador.
     */
    public Aplicacion (String bandera, BufferedReader in, String identificador) {
	lineasArchivos = new Lista<Cadena>();
	modo = Modo.getModo(bandera);
	this.identificador = identificador;
	leeEntradaEstandar(in);
    }

    /**
     * Lee la lista de archivos.
     * @param archivos la lista de archivos a leer.
     */
    private void leeArchivos(Lista<String> archivos) {
	for(String archivo : archivos) {
	    try {
		BufferedReader in = new BufferedReader(
				        new InputStreamReader(
					    new FileInputStream(archivo)));
		agregaLineas(in);
	    } catch (IOException ioe) {
		System.err.printf(RED +
				  "No pude leer el archivo \"%s\".\n",
				  archivo
				  + RESET);
		System.exit(ERROR_LECTURA);
	    }
	}
    }
    
    /**
     * Lee la entrada estándar.
     * @param in la entrada estándar a leer.
     */
    private void leeEntradaEstandar(BufferedReader in) {
	try {
	    agregaLineas(in);
	} catch (IOException ioe) {
	    System.err.printf(RED +
			      "No pude cargar de la entrada estándar. \n"
			      + RESET);
	    System.exit(ERROR_LECTURA);
	}
    }
    
    /**
     * Agrega líneas a la lista de líneas de archivos.
     * @param in la entrada estándar a leer.
     */
    private void agregaLineas(BufferedReader in) throws IOException {
	String linea;
	while((linea = in.readLine()) != null)
	    lineasArchivos.agregaFinal(new Cadena(linea));
	in.close();
    }
    
    /**
     * Ejecuta la aplicación.
     */
    public void ejecuta() {
	ordena();
	switch(modo){
	case GUARDA:
	    Archivos.guarda(identificador, lineasOrdenadas);
	    break;
	case GUARDAYREVERSA:
	    Archivos.guardaReversa(identificador, lineasOrdenadas);
	    break;
	case REVERSA:
	    Archivos.reversa(lineasOrdenadas);
	    break;
	case IMPRIME:
	    Archivos.imprime(lineasOrdenadas);
	    break;
	default:
	    break;
	}
    }
    
    /**
     * Ordena la lista de líneas de archivos.
     */
    private void ordena() {
	lineasOrdenadas = lineasArchivos.mergeSort((a, b) -> a.compareTo(b));
    }
    
}
