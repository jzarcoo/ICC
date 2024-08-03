package mx.unam.ciencias.icc.proyecto2;

import mx.unam.ciencias.icc.ExcepcionIndiceInvalido;
import mx.unam.ciencias.icc.IteradorLista;
import mx.unam.ciencias.icc.Lista;

/**
 * Clase para representar la entrada estándar/línea de comandos. Una entrada estándar tiene
 * archivos y banderas, en este caso reversa y guarda, donde en esta última posee un
 * identificador.
 */
public class EntradaEstandar {
    
    // Códigos ANSI para colores en la terminal
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";

    /* La lista de archivos de la entrada estándar. */
    private Lista<String> archivos;
    /* La bandera reversa de la entrada estándar. */
    private boolean reversa;
    /* La bandera guarda de la entrada estándar. */
    private boolean guarda;
    /* El archivo identificador de la entrada estándar. */
    private String identificador;

    /**
     * Define el estado inicial de una entrada estándar.
     * @param args el arreglo de cadenas de la línea de comandos.
     * @throws IllegalArgumentException si se paso una bandera inválida o
     *         un archivo inválido.
     */
    public EntradaEstandar(String[] args) {
	archivos = new Lista<String>();
	for(int i = 0; i < args.length; i++) {
	    if(args[i].contains(".txt"))
		archivos.agregaFinal(args[i]); // agrega archivos a la lista
	    else if(args[i].contains("-")) { // obtiene banderas
		if(args[i].equals("-r"))
		    reversa = true;
		else if(args[i].equals("-o")) {
		    fijaIdentificador(++i, args);
		    guarda = true;
		} else { // bandera inválida
		    System.err.printf(RED +
				      "Bandera inválida \"%s\".\n",
				      args[i]
				      + RESET);
		    throw new IllegalArgumentException();
		}
	    } else { // tipo de archivo inválido
		System.err.printf(RED +
				  "Ingresaste un archivo no válido \"%s\".\n",
				  args[i]
				  + RESET);
		throw new IllegalArgumentException();
	    }
	}
    }

    /**
     * Fija el identificador de la entrada estándar.
     * @param indice la localidad donde se encuentra el identificador en el arreglo.
     * @param args el arreglo de cadenas de la línea de comandos.
     * @throws IllegalArgumentException si no se colocó un identificador o
     *         se colocaron varios de ellos.
     */
    private void fijaIdentificador(int indice, String[] args) {
	try {
	    if(indice >= args.length)
		throw new ExcepcionIndiceInvalido("Índice inválido");
	    else
		identificador = args[indice];
	} catch (ExcepcionIndiceInvalido e) {
	    System.err.printf(RED +
			      "La opción '-o' requiere un argumento. \n"
			      + RESET);
	    throw new IllegalArgumentException();
	}
	if(guarda == true) {
	    System.err.printf(RED +
			      "Múltiples archivos de salida especificados. \n"
			      + RESET);
	    throw new IllegalArgumentException();
	}
    }
    
    /**
     * Regresa el valor de la bandera reversa de la entrada estándar.
     * @return el valor de la bandera reversa de la entrada estándar.
     */
    public boolean getReversa(){
	return reversa;
    }

    /**
     * Regresa el valor de la bandera guarda de la entrada estándar.
     * @return el valor de la bandera guarda de la entrada estándar.
     */
    public boolean getGuarda(){
	return guarda;
    }

    /**
     * Regresa el archivo identificador de la entrada estándar.
     * @return el archivo identificador de la entrada estándar.
     */
    public String getIdentificador(){
	return identificador;
    }

    /**
     * Regresa la lista de archivos de la entrada estándar.
     * @return la lista de archivos de la entrada estándar.
     */
    public Lista<String> getArchivos(){
	return archivos;
    }
    
}
