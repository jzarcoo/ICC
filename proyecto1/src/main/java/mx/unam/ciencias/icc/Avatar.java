package mx.unam.ciencias.icc;

/**
 * Clase para representar avatares. Un avatar tiene nombre, maná, vida
 * ataque, defensa. La clase implementa {@link Registro}, por lo que
 * puede seriarse en una línea de texto y deseriarse de una línea de
 * texto; además de determinar si sus campos casan valores arbitrarios y
 * actualizarse con los valores de otro avatar.
 */
public class Avatar implements Registro<Avatar, CampoAvatar> {

    /* Nombre del avatar. */
    private String nombre;
    /* Nivel de maná */
    private int mana;
    /* Nivel de vida. */
    private int vida;
    /* Nivel de ataque. */
    private double ataque;
    /* Nivel de defensa. */
    private double defensa;
    /* Habilidades del avatar. */
    private Lista<String> habilidades;

    /**
     * Define el estado inicial de un avatar.
     * @param nombre el nombre del avatar.
     */
    public Avatar(String nombre,
		  int    mana,
		  int    vida,
		  double ataque,
		  double defensa,
		  Lista<String> habilidades) {
	this.nombre = nombre;
	this.mana = mana;
	this.vida = vida;
	this.ataque = ataque;
	this.defensa = defensa;
	this.habilidades = habilidades;
    }

    public Avatar () {}

    /**
     * Regresa el nombre del avatar.
     * @return el nombre del avatar.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre del avatar.
     * @param nombre el nuevo nombre del avatar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Regresa el nivel de maná del avatar.
     * @return el nivel de maná del avatar.
     */
    public int getMana() {
        return mana;
    }

    /**
     * Define el nivel de maná del avatar.
     * @param mana el nuevo nivel de maná del avatar.
     */
    public void setMana(int mana){
	this.mana = mana;
    }
    
    /**
     * Regresa el nivel de vida del avatar.
     * @return el nivel de vida del avatar.
     */
    public int getVida() {
        return vida;
    }
    
    /**
     * Define el nivel de vida de un avatar.
     * @param vida el nuevo nivel de vida del avatar.
     */
    public void setVida(int vida){
	this.vida = vida;
    }
    
    /**
     * Regresa el nivel de ataque del avatar.
     * @return el nivel de ataque del avatar.
     */
    public double getAtaque() {
        return ataque;
    }
    
    /**
     * Define el nivel de ataque de un avatar.
     * @param ataque el nuevo nivel de ataque del avatar.
     */
    public void setAtaque(double ataque){
	this.ataque = ataque;
    }
    
    /**
     * Regresa el nivel de defenda del avatar.
     * @return el nivel de defensa del avatar.
     */
    public double getDefensa() {
        return defensa;
    }
    
    /**
     * Define el nivel de ataque de un avatar.
     * @param ataque el nuevo nivel de ataque del avatar.
     */
    public void setDefensa(double defensa){
	this.defensa = defensa;
    }

    /**
     * Regresa las habilidades del avatar.
     * @return las habilidades del avatar.
     */
    public Lista<String> getHabilidades() {
        return habilidades;
    }

    /**
     * Define las habilidades del avatar.
     * @param habilidades las nuevas habilidades del avatar
     */
    public void setHabilidades(Lista<String> habilidades) {
        this.habilidades = habilidades;
    }

    /**
     * Regresa una representación en cadena del avatar.
     * @return una representación en cadena del avatar.
     */
    @Override public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append(String.format("Nombre      : %s\n", nombre));
        sb.append(String.format("Mana        : %d\n", mana));
	sb.append(String.format("Vida        : %d\n", vida));
	sb.append(String.format("Ataque      : %2.2f\n", ataque));
	sb.append(String.format("Defensa     : %2.2f\n", defensa));
	sb.append(String.format("Habilidades : %s\n", habilidades));
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
        return (nombre.equals(avatar.nombre) &&
		mana == avatar.mana &&
		vida == avatar.vida &&
		ataque == avatar.ataque &&
		defensa == avatar.defensa) &&
	        habilidades.equals(avatar.habilidades);
    }

    /**
     * Regresa el avatar seriado en una línea de texto. La línea de
     * texto que este método regresa debe ser aceptada por el método {@link
     * Avatar#deseria}.
     * @return la seriación del avatar en una línea de texto.
     */
    @Override public String seria() {
	return String.format("%s\t%d\t%d\t%2.2f\t%2.2f\t%s\n",
			     nombre, mana, vida, ataque, defensa, habilidades);
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
	if(campos.length != 6)
	    throw new ExcepcionLineaInvalida("Línea inválida");
	nombre = campos[0];
	try{
	    mana = Integer.parseInt(campos[1]);
	    vida = Integer.parseInt(campos[2]);
	    ataque = Double.parseDouble(campos[3]);
	    defensa = Double.parseDouble(campos[4]);
	    habilidades = deseriaHabilidades(campos[5]);
	}catch(NumberFormatException nfe){
	    throw new ExcepcionLineaInvalida("Línea inválida");
	}
    }
    
    private Lista<String> deseriaHabilidades(String linea){
	String l = linea.substring(1, linea.length()-1); // Elimina corchetes
	String[] campos = l.split(", ");
	Lista<String> habilidades = new Lista<String>();
	for(String campo : campos)
	    if(!campo.isEmpty())
		habilidades.agregaFinal(campo);
	return habilidades;
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
	case HABILIDADES:
	    return casaHabilidades(valor);
	default: return false;
	}
    }

    private boolean casaHabilidades(Object valor){
	if(!(valor instanceof String))
	    return false;
	String v = (String) valor;
	String valorHabilidad = v.trim();
	if(valorHabilidad.isEmpty())
	    return false;
     	for(String habilidad : habilidades)
	    if(habilidad.contains(valorHabilidad))
		return true;
	return false;
    }

    private boolean casaNombre(Object valor){
	if(!(valor instanceof String))
	    return false;
	String valorNombre = (String) valor;
	String nombre = valorNombre.trim();
	if(nombre.isEmpty())
	    return false;
	return (this.nombre.contains(nombre));
    }
    
    private boolean casaMana(Object valor){
	if(!(valor instanceof Integer))
	    return false;
	Integer mana = (Integer) valor;
	return mana.intValue() <= this.mana;
    }
    
    private boolean casaVida(Object valor){
	if(!(valor instanceof Integer))
	    return false;
	Integer vida = (Integer) valor;
	return vida.intValue() <= this.vida;
    }
    
    private boolean casaAtaque(Object valor){
	if(!(valor instanceof Double))
	    return false;
	Double ataque = (Double) valor;
	return ataque.doubleValue() <= this.ataque;
    }
    
    private boolean casaDefensa(Object valor){
	if(!(valor instanceof Double))
	    return false;
	Double defensa = (Double) valor;
	return defensa.doubleValue() <= this.defensa;
    }

    /**
     * Actualiza los valores del avatar con los del avatar recibido.
     * @param avatar el avatar con el cual actualizar los valores.
     * @throws IllegalArgumentException si el avatar es <code>null</code>.
     */
    @Override public void actualiza(Avatar avatar) {
	if(avatar == null)
	    throw new IllegalArgumentException("Avatar inválido");
	nombre = avatar.nombre;
	mana = avatar.mana;
	vida = avatar.vida;
	ataque = avatar.ataque;
	defensa = avatar.defensa;
	habilidades = avatar.habilidades;
	return;
    }
}
