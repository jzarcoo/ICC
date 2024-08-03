package mx.unam.ciencias.icc;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para listas genéricas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas implementan la interfaz {@link Iterable}, y por lo tanto se
 * pueden recorrer usando la estructura de control <em>for-each</em>. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Iterable<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
	    start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
	    if(!hasNext())
		throw new NoSuchElementException("El iterador no tiene elemento siguiente");
	    anterior = siguiente;
	    siguiente = siguiente.siguiente;
	    return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
	    if(!hasPrevious())
		throw new NoSuchElementException("El iterador no tiene elemento anterior");
	    siguiente = anterior;
	    anterior = anterior.anterior;
	    return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
	    anterior = null;
	    siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
	    siguiente = null;
	    anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacia() {
        return (cabeza == null || rabo == null || longitud == 0);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
	if(elemento == null)
	    throw new IllegalArgumentException("Elemento inválido");
	Nodo n = new Nodo(elemento);
	if(esVacia()) {
	    cabeza = rabo = n;
	} else {
	    n.anterior = rabo;
	    rabo.siguiente = n;
	    rabo = n;
	}
	++longitud;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
	if(elemento == null)
	    throw new IllegalArgumentException("Elemento inválido");
	Nodo n = new Nodo(elemento);
	if(esVacia()) {
	    cabeza = rabo = n;
	} else {
	    n.siguiente = cabeza;
	    cabeza.anterior = n;
	    cabeza = n;
	}
	++longitud;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
	if(elemento == null)
	    throw new IllegalArgumentException("Elemento inválido");
	if(i <= 0) {
	    agregaInicio(elemento);
	} else if(i >= longitud) {
	    agregaFinal(elemento);
	} else {
	    Nodo n = new Nodo(elemento);
	    Nodo iesimo = getNodo(cabeza.siguiente, i, 1);
	    iesimo.anterior.siguiente = n;
	    n.anterior = iesimo.anterior;
	    iesimo.anterior = n;
	    n.siguiente = iesimo;
	    ++longitud;
	}
    }
    private Nodo getNodo(Nodo n, int indice, int j){
	return (indice == j) ? n : getNodo(n.siguiente, indice, ++j);
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(T elemento) {
	if(elemento == null)
	    return;
	Nodo n = buscaNodo(elemento, cabeza);
	if(n == null)
	    return;
	if(longitud == 1 && cabeza.elemento.equals(n.elemento)) {
	    limpia();
	} else if(cabeza.equals(n)) {
	    eliminaPrimero();
	} else if(rabo.equals(n)) {
	    eliminaUltimo();
	} else {
	    n.anterior.siguiente = n.siguiente;
	    n.siguiente.anterior = n.anterior;
	    --longitud;
	}
    }
    private Nodo buscaNodo(T o, Nodo n){
	if(n == null)
	    return null;
	return (n.elemento.equals(o)) ? n : buscaNodo(o, n.siguiente);
     }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
	if(cabeza == null)
	    throw new NoSuchElementException("Lista vacía");
	Nodo n = cabeza;
	if(longitud == 1) {
	    limpia();
	} else {
	    cabeza = cabeza.siguiente;
	    cabeza.anterior = null;
	    --longitud;
	}
	return n.elemento;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
	if(cabeza == null)
	    throw new NoSuchElementException("Lista vacía");
	Nodo n = rabo;
	if(longitud == 1) {
	    limpia();
	} else {
	    rabo = rabo.anterior;
	    rabo.siguiente = null;
	    --longitud;
	}
	return n.elemento;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(T elemento) {
	return (buscaNodo(elemento, cabeza) != null);
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
	Lista<T> reversa = new Lista<T>();
	for(T elemento : this)
	    reversa.agregaInicio(elemento);
	return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
	Lista<T> c = new Lista<T>();
	for(T elemento : this)
	    c.agregaFinal(elemento);
	return c;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    public void limpia() {
        cabeza = rabo = null;
	longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
	if(esVacia())
	    throw new NoSuchElementException("La lista es vacía");
	return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
	if(esVacia())
	    throw new NoSuchElementException("La lista es vacía");
	return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
	if(esVacia() || i < 0 || i >= longitud)
	    throw new ExcepcionIndiceInvalido("Índice inválido");
	return getNodo(cabeza, i, 0).elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
	if(elemento == null)
	    return -1;
	int j = 0;
	for(T t : this) {
	    if(elemento.equals(t))
		return j;
	    ++j;
	}
	return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
	if(esVacia())
	    return "[]";
	StringBuffer sb = new StringBuffer();
	sb.append(String.format("[%s", cabeza.elemento.toString()));
	Nodo n = cabeza.siguiente;
	while(n != null) {
	    sb.append(String.format(", %s", n.elemento.toString()));
	    n = n.siguiente;
	}
	sb.append("]");
	return sb.toString();
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>) objeto;
        if(lista == null || longitud != lista.longitud)
	    return false;
	Nodo n = lista.cabeza;
	for(T elemento : this) {
	    if(!elemento.equals(n.elemento))
		return false;
	    n = n.siguiente;
	}
	return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
	return mergeSort(copia(), comparador);
    }
    
    private Lista<T> mergeSort(Lista<T> lista, Comparator<T> comparador) {
	if(lista.getLongitud() <= 1)
	    return lista;
	Lista<T> l1 = new Lista<>();
	Lista<T> l2 = new Lista<>();
	int mitad = lista.getLongitud() / 2;
	int i = 0; 
	for(T elemento : lista)
	    if(i++ < mitad)
		l1.agregaFinal(elemento);
	    else
		l2.agregaFinal(elemento);
	return mezcla(mergeSort(l1, comparador), mergeSort(l2, comparador), comparador);
    }

    private Lista<T> mezcla(Lista<T> l1, Lista<T> l2, Comparator<T> comparador) {
	Lista<T> ordenada = new Lista<>();
	Nodo i = l1.cabeza;
	Nodo j = l2.cabeza;
	while(i != null && j != null) {
	    if(comparador.compare(i.elemento, j.elemento) <= 0) {
		ordenada.agregaFinal(i.elemento);
		i = i.siguiente;
	    } else {
		ordenada.agregaFinal(j.elemento);
		j = j.siguiente;
	    }		    
	}
	while(i != null) {
	    ordenada.agregaFinal(i.elemento);
	    i = i.siguiente;
	}
	while(j != null) {
	    ordenada.agregaFinal(j.elemento);
	    j = j.siguiente;
	}
	return ordenada;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
	if(comparador.compare(elemento, getPrimero()) < 0 ||
	   comparador.compare(elemento, getUltimo()) > 0)
			      return false;
	for(T t : this)
	    if(comparador.compare(elemento, t) == 0)
		return true;
	return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
