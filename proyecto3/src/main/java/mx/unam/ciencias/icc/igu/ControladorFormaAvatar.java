package mx.unam.ciencias.icc.igu;

/**
 * Clase abstracta para controladores del contenido de diálogo con formas con
 * datos de avatares que se aceptan o rechazan.
 */
public abstract class ControladorFormaAvatar extends ControladorForma {

    /** El valor del nombre. */
    protected String nombre;
    /** El valor del nivel de maná. */
    protected int mana;
    /** El valor del nivel de vida. */
    protected int vida;
    /** El valor del nivel de ataque. */
    protected double ataque;
    /** El valor del nivel de defensa. */
    protected double defensa;

    /**
     * Verifica que el nombre sea válido.
     * @param nombre el nombre a verificar.
     * @return <code>true</code> si el nombre es válido; <code>false</code> en
     *         otro caso.
     */
    protected boolean verificaNombre(String nombre) {
	if (nombre == null || nombre.isEmpty())
	    return false;
	this.nombre = nombre;
	return true;
    }

    /**
     * Verifica que el mana sea válida.
     * @param mana el mana a verificar.
     * @return <code>true</code> si el mana es válido; <code>false</code> en
     *         otro caso.
     */
    protected boolean verificaMana(String mana) {
        if (mana == null || mana.isEmpty())
	    return false;
	try {
	    this.mana = Integer.parseInt(mana);
	} catch (NumberFormatException nfe) {
	    return false;
	}
	return true;
    }

    /**
     * Verifica que la vida sea válida.
     * @param vida la vida a verificar.
     * @return <code>true</code> si la vida es válida; <code>false</code> en
     *         otro caso.
     */
    protected boolean verificaVida(String vida) {
        if (vida == null || vida.isEmpty())
	    return false;
	try {
	    this.vida = Integer.parseInt(vida);
	} catch (NumberFormatException nfe) {
	    return false;
	}
	return true;
    }

    /**
     * Verifica que el ataque sea válido.
     * @param ataque el ataque a verificar.
     * @return <code>true</code> si el ataque es válido; <code>false</code> en
     *         otro caso.
     */
    protected boolean verificaAtaque(String ataque) {
        if (ataque == null || ataque.isEmpty())
	    return false;
	try {
	    this.ataque = Double.parseDouble(ataque);
	} catch (NumberFormatException nfe) {
	    return false;
	}
	return true;
    }

    /**
     * Verifica que la defensa sea válido.
     * @param defensa la defensa a verificar.
     * @return <code>true</code> si la defensa es válida; <code>false</code> en
     *         otro caso.
     */
    protected boolean verificaDefensa(String defensa) {
        if (defensa == null || defensa.isEmpty())
	    return false;
	try {
	    this.defensa = Double.parseDouble(defensa);
	} catch (NumberFormatException nfe) {
	    return false;
	}
	return true;
    }
}
