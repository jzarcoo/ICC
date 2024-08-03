package mx.unam.ciencias.icc.igu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import mx.unam.ciencias.icc.CampoAvatar;

/**
 * Clase para el controlador del contenido del diálogo para buscar avatares.
 */
public class ControladorFormaBuscaAvatares
    extends ControladorFormaAvatar {

    /* El combo del campo. */
    @FXML private ComboBox<CampoAvatar> opcionesCampo;
    /* El campo de texto para el valor. */
    @FXML private EntradaVerificable entradaValor;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaValor.setVerificador(s -> verificaValor(s));
        entradaValor.textProperty().addListener(
            (o, v, n) -> revisaValor(null));
    }

    /* Revisa el valor después de un cambio. */
    @FXML private void revisaValor(ActionEvent evento) {
        Tooltip.install(entradaValor, getTooltip());
        botonAceptar.setDisable(!entradaValor.esValida());
    }

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        aceptado = true;
        escenario.close();
    }

    /* Verifica el valor. */
    private boolean verificaValor(String valor) {
        switch (opcionesCampo.getValue()) {
        case NOMBRE:   return verificaNombre(valor);
        case MANA:   return verificaMana(valor);
        case VIDA: return verificaVida(valor);
        case ATAQUE:     return verificaAtaque(valor);
        case DEFENSA:     return verificaDefensa(valor);
        default:       return false; // No puede ocurrir.
        }
    }

    /* Obtiene la pista. */
    private Tooltip getTooltip() {
        String m = "";
        switch (opcionesCampo.getValue()) {
        case NOMBRE:
            m = "Buscar por nombre necesita al menos un carácter";
            break;
        case MANA:
            m = "Buscar por nivel de maná necesita un número entre 100 y 200";
            break;
        case VIDA:
            m = "Buscar por nivel de vida necesita un número entre 50 y 100";
            break;
        case ATAQUE:
            m = "Buscar por nivel de ataque necesita un número entre 300.0 y 1000.0";
            break;
        case DEFENSA:
            m = "Buscar por nivel de defensa necesita un número entre 300.0 y 1000.0";
            break;
        }
        return new Tooltip(m);
    }

    /**
     * Regresa el valor ingresado.
     * @return el valor ingresado.
     */
    public Object getValor() {
        switch (opcionesCampo.getValue()) {
        case NOMBRE :
	    return entradaValor.getText();
	case MANA :
	    return Integer.parseInt(entradaValor.getText ());
	case VIDA :
	    return Integer.parseInt(entradaValor.getText());
	case ATAQUE :
	    return Double.parseDouble(entradaValor.getText());
	case DEFENSA :
	    return Double.parseDouble(entradaValor.getText());
        default:       return entradaValor.getText(); // No puede ocurrir.
        }
    }

    /**
     * Regresa el campo seleccionado.
     * @return el campo seleccionado.
     */
    public CampoAvatar getCampo() {
        return opcionesCampo.getValue();
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaValor.requestFocus();
    }
}
