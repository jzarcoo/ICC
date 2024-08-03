package mx.unam.ciencias.icc.proyecto2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import mx.unam.ciencias.icc.ExcepcionIndiceInvalido;
import mx.unam.ciencias.icc.IteradorLista;
import mx.unam.ciencias.icc.Lista;

/**
 * Clase para manejar los archivos de texto a través de listas genéricas.
 */
public class Archivos {
    
    // Códigos ANSI para colores en la terminal
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
      
    /* Código de terminación por error de guardado. */
    private static final int ERROR_GUARDA = 1;
    
    /* Modo de imprimir de la aplicación. */
    public static <T> void imprime(Lista<T> ordenada) {
	for(T linea : ordenada)
	    System.out.println(linea);
    }

    /* Modo de reversa de la aplicación. */
    public static <T> void reversa(Lista<T> ordenada){
	IteradorLista<T> i = ordenada.iteradorLista();
	i.end();
	while(i.hasPrevious())
	    System.out.println(i.previous());
    }
    
    /* Modo de guarda de la aplicación. */
    public static <T> void guarda(String identificador, Lista<T> ordenada) {
	try {
	    BufferedWriter out = new BufferedWriter(
				     new OutputStreamWriter(
					 new FileOutputStream(identificador)));
	    for(T linea : ordenada) {
		System.out.println(linea);
		out.write(linea + "\n");
	    }
	    out.close();
	} catch (IOException ioe) {
	    System.err.printf(RED +
			      "No pude guardar en el archivo \"%s\".\n",
			      identificador
			      + RESET);
	    System.exit(ERROR_GUARDA);
	}
    }
    
    /* Modo de guarda y reversa de la aplicación. */
    public static <T> void guardaReversa(String identificador, Lista<T> ordenada) {
	try {
	    BufferedWriter out = new BufferedWriter(
				     new OutputStreamWriter(
					 new FileOutputStream(identificador)));
	    IteradorLista<T> i = ordenada.iteradorLista();
	    i.end();
	    while(i.hasPrevious()) {
		T linea = i.previous();
		System.out.println(linea);
		out.write(linea + "\n");
	    }
	    out.close();
	} catch (IOException ioe) {
	    System.err.printf(RED +
			      "No pude guardar en el archivo \"%s\".\n",
			      identificador
			      + RESET);
	    System.exit(ERROR_GUARDA);
	}
    }
}
