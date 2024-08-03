package mx.unam.ciencias.icc;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for(int i = 0; i < arreglo.length; i++) {
	    int menor = i;
	    for(int j = i+1; j < arreglo.length; j++)
		if(comparador.compare(arreglo[j], arreglo[menor]) < 0)
		    menor = j;
	    intercambia(arreglo, i, menor);
	}
    }
    
    private static <T> void intercambia(T[] arreglo, int i, int m) {
	if(i == m)
	    return;
	T t = arreglo[m];
	arreglo[m] = arreglo[i];
	arreglo[i] = t;
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
	if(arreglo.length <= 1)
	    return;
	Lista<Integer> pila = new Lista<Integer>();
	pila.agregaFinal(0);
	pila.agregaFinal(arreglo.length - 1);
	while(!pila.esVacia()) {
	    int fin = pila.eliminaUltimo().intValue();
	    int ini = pila.eliminaUltimo().intValue();
	    if(fin <= ini)
		continue;
	    int i = ini + 1;
	    int j = fin;
	    while(i < j) 
		if(comparador.compare(arreglo[i], arreglo[ini]) > 0 && comparador.compare(arreglo[j], arreglo[ini]) <= 0)
		    intercambia(arreglo, i++, j--);
		else if(comparador.compare(arreglo[i], arreglo[ini]) <= 0)
		    i++;
		else
		    j--;
	    if(comparador.compare(arreglo[i], arreglo[ini]) > 0)
		i--;
	    intercambia(arreglo, ini, i);
	    pila.agregaFinal(ini);
	    pila.agregaFinal(i - 1);
	    pila.agregaFinal(i + 1);
	    pila.agregaFinal(fin);
	}
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        return busquedaBinaria(arreglo, elemento, comparador, 0, arreglo.length - 1);
    }
    
    private static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador, int ini, int fin) {
	if(fin < ini)
	    return -1;
	int mitad = ini + ((fin - ini) / 2);
	if(comparador.compare(elemento, arreglo[mitad]) == 0)
	    return mitad;
	else if(comparador.compare(elemento, arreglo[mitad]) < 0)
	    return busquedaBinaria(arreglo, elemento, comparador, ini, mitad - 1);
	else
	    return busquedaBinaria(arreglo, elemento, comparador, mitad + 1, fin);
    }
}
