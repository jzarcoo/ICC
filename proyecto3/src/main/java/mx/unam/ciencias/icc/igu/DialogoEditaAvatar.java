package mx.unam.ciencias.icc.igu;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.unam.ciencias.icc.Avatar;

/**
 * Clase para diálogos con formas para editar avatares.
 */
public class DialogoEditaAvatar extends Stage {

    /* Vista de la forma para agregar/editar avatares. */
    private static final String EDITA_AVATAR_FXML =
        "fxml/forma-edita-avatar.fxml";

    /* El controlador. */
    private ControladorFormaEditaAvatar controlador;

    /**
     * Define el estado inicial de un diálogo para avatar.
     * @param escenario el escenario al que el diálogo pertenece.
     * @param avatar el avatar; puede ser <code>null</code> para agregar
     *                   un avatar.
     * @throws IOException si no se puede cargar el archivo FXML.
     */
    public DialogoEditaAvatar(Stage escenario,
                                  Avatar avatar) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        FXMLLoader cargador =
            new FXMLLoader(cl.getResource(EDITA_AVATAR_FXML));
        AnchorPane cristal = (AnchorPane)cargador.load();

        if (avatar == null)
            setTitle("Agregar avatar");
        else
            setTitle("Editar avatar");
        initOwner(escenario);
        initModality(Modality.WINDOW_MODAL);
        Scene escena = new Scene(cristal);
        setScene(escena);

        controlador = cargador.getController();
        controlador.setEscenario(this);
        controlador.setAvatar(avatar);
        if (avatar == null)
            controlador.setVerbo("Agregar");
        else
            controlador.setVerbo("Actualizar");

        setOnShown(w -> controlador.defineFoco());
        setResizable(false);
    }

    /**
     * Nos dice si el usuario activó el botón de aceptar.
     * @return <code>true</code> si el usuario activó el botón de aceptar,
     *         <code>false</code> en otro caso.
     */
    public boolean isAceptado() {
        return controlador.isAceptado();
    }

    /**
     * Regresa el avatar del diálogo.
     * @return el avatar del diálogo.
     */
    public Avatar getAvatar() {
        return controlador.getAvatar();
    }
}
