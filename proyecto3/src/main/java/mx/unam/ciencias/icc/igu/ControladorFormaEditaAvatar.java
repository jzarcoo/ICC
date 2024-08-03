package mx.unam.ciencias.icc.igu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import mx.unam.ciencias.icc.Avatar;

/**
 * Clase para el controlador del contenido del diálogo para editar y crear
 * avatares.
 */
public class ControladorFormaEditaAvatar
    extends ControladorFormaAvatar {

    /* La entrada verificable para el nombre. */
    @FXML private EntradaVerificable entradaNombre;
    /* La entrada verificable para el nivel de maná. */
    @FXML private EntradaVerificable entradaMana;
    /* La entrada verificable para el nivel de vida. */
    @FXML private EntradaVerificable entradaVida;
    /* La entrada verificable para la nivel de ataque. */
    @FXML private EntradaVerificable entradaAtaque;
    /* La entrada verificable para la nivel de defensa. */
    @FXML private EntradaVerificable entradaDefensa;

    /* El avatar creado o editado. */
    private Avatar avatar;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaNombre.setVerificador(n -> verificaNombre(n));
        entradaMana.setVerificador(m -> verificaMana(m));
        entradaVida.setVerificador(v -> verificaVida(v));
        entradaAtaque.setVerificador(a -> verificaAtaque(a));
        entradaDefensa.setVerificador(d -> verificaDefensa(d));

        entradaNombre.textProperty().addListener(
            (o, v, n) -> verificaAvatar());
        entradaMana.textProperty().addListener(
            (o, v, n) -> verificaAvatar());
        entradaVida.textProperty().addListener(
            (o, v, n) -> verificaAvatar());
        entradaAtaque.textProperty().addListener(
            (o, v, n) -> verificaAvatar());
        entradaDefensa.textProperty().addListener(
            (o, v, n) -> verificaAvatar());
    }

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        actualizaAvatar();
        aceptado = true;
        escenario.close();
    }

    /* Actualiza al avatar, o lo crea si no existe. */
    private void actualizaAvatar() {
        if (avatar != null) {
            avatar.setNombre(nombre);
            avatar.setMana(mana);
            avatar.setVida(vida);
            avatar.setAtaque(ataque);
	    avatar.setDefensa(defensa);
        } else {
            avatar = new Avatar(nombre, mana, vida, ataque, defensa);
        }
    }

    /**
     * Define el avatar del diálogo.
     * @param avatar el nuevo avatar del diálogo.
     */
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
        if (avatar == null)
            return;
        this.avatar = new Avatar(null, 0, 0, 0, 0);
        this.avatar.actualiza(avatar);
        entradaNombre.setText(avatar.getNombre());
        entradaMana.setText(String.valueOf(avatar.getMana()));
	entradaVida.setText(String.valueOf(avatar.getVida()));
        String a = String.format("%2.2f", avatar.getAtaque());
        entradaAtaque.setText(a);
        String d = String.format("%2.2f", avatar.getDefensa());
        entradaDefensa.setText(d);
    }

    /**
     * Regresa el avatar del diálogo.
     * @return el avatar del diálogo.
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Define el verbo del botón de aceptar.
     * @param verbo el nuevo verbo del botón de aceptar.
     */
    public void setVerbo(String verbo) {
        botonAceptar.setText(verbo);
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaNombre.requestFocus();
    }

    /* Verifica que los cuatro campos sean válidos. */
    private void verificaAvatar() {
        boolean n = entradaNombre.esValida();
        boolean m = entradaMana.esValida();
        boolean v = entradaVida.esValida();
        boolean a = entradaAtaque.esValida();
        boolean d = entradaDefensa.esValida();
        botonAceptar.setDisable(!n || !m || !v || !a || !d);
    }

    /**
     * Verifica que el nivel de maná sea válido.
     * @param mana el maná a verificar.
     * @return <code>true</code> si el maná es válido; <code>false</code> en
     *         otro caso.
     */
    @Override protected boolean verificaMana(String mana) {
        return super.verificaMana(mana) &&
            this.mana >= 100 && this.mana <= 200;
    }

    /**
     * Verifica que el nivel de vida sea válido.
     * @param vida el vida a verificar.
     * @return <code>true</code> si el vida es válido; <code>false</code> en
     *         otro caso.
     */
    @Override protected boolean verificaVida(String vida) {
        return super.verificaVida(vida) &&
            this.vida >= 50 && this.vida <= 100;
    }

    /**
     * Verifica que el ataque sea válido.
     * @param ataque el ataque a verificar.
     * @return <code>true</code> si el ataque es válido; <code>false</code> en
     *         otro caso.
     */
    @Override protected boolean verificaAtaque(String ataque) {
        return super.verificaAtaque(ataque) &&
            this.ataque >= 300.0 && this.ataque <= 1000.0;
    }

    /**
     * Verifica que el defensa sea válido.
     * @param defensa el defensa a verificar.
     * @return <code>true</code> si el defensa es válido; <code>false</code> en
     *         otro caso.
     */
    @Override protected boolean verificaDefensa(String defensa) {
        return super.verificaDefensa(defensa) &&
            this.defensa >= 300.0 && this.defensa <= 1000.0;
    }
}
