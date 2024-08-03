package mx.unam.ciencias.icc;

/**
 * Enumeración para los campos de un {@link Avatar}.
 */
public enum CampoAvatar {

    /** El nombre del avatar. */
    NOMBRE,
    /** El nivel de maná del avatar. */
    MANA,
    /** El nivel de vida del avatar. */
    VIDA,
    /** El nivel de ataque del avatar. */
    ATAQUE,
    /** El nivel de defensa del avatar. */
    DEFENSA,
    /** Las habilidades del avatar. */
    HABILIDADES;

    /**
     * Regresa una representación en cadena del campo para ser usada en
     * interfaces gráficas.
     * @return una representación en cadena del campo.
     */
    @Override public String toString() {
	switch(this){
	case NOMBRE: return "Nombre";
	case MANA: return "Mana";
	case VIDA: return "Vida";
	case ATAQUE: return "Ataque";
	case DEFENSA: return "Defensa";
	case HABILIDADES: return "Habilidades";
	default: return "";
	}
    }
}
