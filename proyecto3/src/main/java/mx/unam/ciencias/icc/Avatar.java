package mx.unam.ciencias.icc;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase para representar avatares. Un avatar tiene nombre, maná, vida
 * ataque, defensa. La clase implementa {@link Registro}, por lo que
 * puede seriarse en una línea de texto y deseriarse de una línea de
 * texto; además de determinar si sus campos casan valores arbitrarios y
 * actualizarse con los valores de otro avatar.
 */
public class Avatar implements Registro<Avatar, CampoAvatar> {

    /* Nombre del avatar. */
    private final StringProperty nombre;
    /* Nivel de maná */
    private final IntegerProperty mana;
    /* Nivel de vida. */
    private final IntegerProperty vida;
    /* Nivel de ataque. */
    private final DoubleProperty ataque;
    /* Nivel de defensa. */
    private final DoubleProperty defensa;

    /**
     * Define el estado inicial de un avatar.
     * @param nombre el nombre del avatar.
     * @param mana nivel de mana del avatar.
     * @param vida nivel de vida del avatar.
     * @param ataque nivel de ataque del avatar.
     * @param defensa nivel de defensa del avatar.
     */
    public Avatar(String nombre,
		  int    mana,
		  int    vida,
		  double ataque,
		  double defensa) {
	this.nombre = new SimpleStringProperty(nombre);
	this.mana = new SimpleIntegerProperty(mana);
	this.vida = new SimpleIntegerProperty(vida);
	this.ataque = new SimpleDoubleProperty(ataque);
	this.defensa = new SimpleDoubleProperty(defensa);
    }

    /**
     * Regresa el nombre del avatar.
     * @return el nombre del avatar.
     */
    public String getNombre() {
        return nombre.get();
    }

    /**
     * Define el nombre del avatar.
     * @param nombre el nuevo nombre del avatar.
     */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    /**
     * Regresa la propiedad del nombre.
     * @return la propiedad del nombre.
     */
    public StringProperty nombreProperty() {
        return this.nombre;
    }

    /**
     * Regresa la mana del avatar.
     * @return la mana del avatar.
     */
    public int getMana() {
	return mana.get();
    }

    /**
     * Define la mana del avatar.
     * @param mana la nueva mana del avatar.
     */
    public void setMana(int mana) {
        this.mana.set(mana);
    }

    /**
     * Regresa la propiedad de la mana.
     * @return la propiedad de la mana.
     */
    public IntegerProperty manaProperty() {
        return this.mana;
    }

    /**
     * Regresa la vida del avatar.
     * @return la vida del avatar.
     */
    public int getVida() {
	return vida.get();
    }

    /**
     * Define la vida del avatar.
     * @param vida la nueva vida del avatar.
     */
    public void setVida(int vida) {
        this.vida.set(vida);
    }

    /**
     * Regresa la propiedad de la vida.
     * @return la propiedad de la vida.
     */
    public IntegerProperty vidaProperty() {
        return this.vida;
    }

    /**
     * Regresa el ataque del avatar.
     * @return el ataque del avatar.
     */
    public double getAtaque() {
	return ataque.get();
    }

    /**
     * Define el ataque del avatar.
     * @param ataque el nuevo ataque del avatar.
     */
    public void setAtaque(double ataque) {
        this.ataque.set(ataque);
    }

    /**
     * Regresa la propiedad del ataque.
     * @return la propiedad del ataque.
     */
    public DoubleProperty ataqueProperty() {
	return this.ataque;
    }

    /**
     * Regresa el defensa del avatar.
     * @return el defensa del avatar.
     */
    public double getDefensa() {
	return defensa.get();
    }

    /**
     * Define el defensa del avatar.
     * @param defensa el nuevo defensa del avatar.
     */
    public void setDefensa(double defensa) {
        this.defensa.set(defensa);
    }

    /**
     * Regresa la propiedad del defensa.
     * @return la propiedad del defensa.
     */
    public DoubleProperty defensaProperty() {
	return this.defensa;
    }

    /**
     * Regresa una representaciódn en cadena del avatar.
     * @return una representación en cadena del avatar.
     */
    @Override public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append(String.format("Nombre      : %s\n", getNombre()));
        sb.append(String.format("Mana        : %d\n", getMana()));
	sb.append(String.format("Vida        : %d\n", getVida()));
	sb.append(String.format("Ataque      : %2.2f\n", getAtaque()));
	sb.append(String.format("Defensa     : %2.2f\n", getDefensa()));
	return sb.toString();
    }

    /**
     * Nos dice si el objeto recibido es un avatar igual al que manda llamar
     * el método.
     * @param objeto el objeto con el que el avatar se comparará.
     * @return <code>true</code> si el objeto recibido es un avatar con las
     *         mismas propiedades que el objeto que manda llamar al método,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (!(objeto instanceof Avatar))
            return false;
        Avatar avatar = (Avatar) objeto;
        return (getNombre().equals(avatar.getNombre()) &&
		getMana() == avatar.getMana() &&
		getVida() == avatar.getVida() &&
		getAtaque() == avatar.getAtaque() &&
		getDefensa() == avatar.getDefensa());
    }

    /**
     * Regresa el avatar seriado en una línea de texto. La línea de
     * texto que este método regresa debe ser aceptada por el método {@link
     * Avatar#deseria}.
     * @return la seriación del avatar en una línea de texto.
     */
    @Override public String seria() {
	return String.format("%s\t%d\t%d\t%2.2f\t%2.2f\n",
			     getNombre(), getMana(), getVida(), getAtaque(), getDefensa());
    }

    /**
     * Deseria una línea de texto en las propiedades del avater. La
     * seriación producida por el método {@link Avatar#seria} debe
     * ser aceptada por este método.
     * @param linea la línea a deseriar.
     * @throws ExcepcionLineaInvalida si la línea recibida es nula, vacía o no
     *         es una seriación válida de un avatar.
     */
    @Override public void deseria(String linea) {
	if(linea == null)
	    throw new ExcepcionLineaInvalida("Línea inválida");
	String l = linea.trim();
	if(l.isEmpty())
	    throw new ExcepcionLineaInvalida("Línea inválida");
	String[] campos = l.split("\t");
	if(campos.length != 5)
	    throw new ExcepcionLineaInvalida("Línea inválida");
        setNombre(campos[0]);
	try {
	    setMana(Integer.parseInt(campos[1]));
	    setVida(Integer.parseInt(campos[2]));
	    setAtaque(Double.parseDouble(campos[3]));
	    setDefensa(Double.parseDouble(campos[4]));
	} catch (NumberFormatException nfe) {
	    throw new ExcepcionLineaInvalida("Línea inválida");
	}
    }

    /**
     * Actualiza los valores del avatar con los del avatar recibido.
     * @param avatar el avatar con el cual actualizar los valores.
     * @throws IllegalArgumentException si el avatar es <code>null</code>.
     */
    @Override public void actualiza(Avatar avatar) {
	if(avatar == null)
	    throw new IllegalArgumentException("Avatar inválido");
	setNombre(avatar.getNombre());
	setMana(avatar.getMana());
	setVida(avatar.getVida());
	setAtaque(avatar.getAtaque());
	setDefensa(avatar.getDefensa());
	return;
    }

    /**
     * Nos dice si el avatar casa el valor dado en el campo especificado.
     * @param campo el campo que hay que casar.
     * @param valor el valor con el que debe casar el campo del registro.
     * @return <code>true</code> si:
     *         <ul>
     *           <li><code>campo</code> es {@link CampoAvatar#NOMBRE} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del nombre del avatar.</li>
     *           <li><code>campo</code> es {@link CampoAvatar#MANA} y
     *              <code>valor</code> es instancia de {@link Integer} y su
     *              valor entero es menor o igual al nivel de mana del
     *              avatar.</li>
     *           <li><code>campo</code> es {@link CampoAvatar#VIDA} y
     *              <code>valor</code> es instancia de {@link Integer} y su
     *              valor entero es menor o igual al nivel de vida del
     *              avatar.</li>
     *           <li><code>campo</code> es {@link CampoAvatar#ATAQUE} y
     *              <code>valor</code> es instancia de {@link Double} y su
     *              valor doble es menor o igual al nivel de ataque del
     *              avatar.</li>
     *           <li><code>campo</code> es {@link CampoAvatar#DEFENSA} y
     *              <code>valor</code> es instancia de {@link Double} y su
     *              valor doble es menor o igual al nivel de defensa del
     *              avatar.</li>
     *           <li><code>campo</code> es {@link CampoAvatar#HABILIDADES} y
     *              <code>valor</code> es instancia de {@link Lista<String>} y es una
     *              subcadena de una habilidad de la lista de habilidades del avatar.</li>
     *         </ul>
     *         <code>false</code> en otro caso.
     * @throws IllegalArgumentException si el campo es <code>null</code>.
     */
    @Override public boolean casa(CampoAvatar campo, Object valor) {
	if(!(campo instanceof CampoAvatar))
	    throw new IllegalArgumentException("Campo inválido");
	CampoAvatar campoAvatar = (CampoAvatar) campo;
	switch(campoAvatar){
	case NOMBRE:
	    return casaNombre(valor);
	case MANA:
	    return casaMana(valor);
	case VIDA:
	    return casaVida(valor);
	case ATAQUE:
	    return casaAtaque(valor);
	case DEFENSA:
	    return casaDefensa(valor);
	default:
	    return false;
	}
    }

    private boolean casaNombre(Object valor){
	if(!(valor instanceof String))
	    return false;
	String valorNombre = (String) valor;
	String nombre = valorNombre.trim();
	if(nombre.isEmpty())
	    return false;
	return (getNombre().contains(valorNombre));
    }
    
    private boolean casaMana(Object valor){
	if(!(valor instanceof Integer))
	    return false;
	Integer mana = (Integer) valor;
	return mana.intValue() <= getMana();
    }
    
    private boolean casaVida(Object valor){
	if(!(valor instanceof Integer))
	    return false;
	Integer vida = (Integer) valor;
	return vida.intValue() <= getVida();
    }
    
    private boolean casaAtaque(Object valor){
	if(!(valor instanceof Double))
	    return false;
	Double ataque = (Double) valor;
	return ataque.doubleValue() <= getAtaque();
    }
    
    private boolean casaDefensa(Object valor){
	if(!(valor instanceof Double))
	    return false;
	Double defensa = (Double) valor;
	return defensa.doubleValue() <= getDefensa();
    }
}
