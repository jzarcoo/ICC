package mx.unam.ciencias.icc.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;
import mx.unam.ciencias.icc.CampoAvatar;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la enumeración {@link CampoAvatar}.
 */
public class TestCampoAvatar {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /**
     * Prueba unitaria para {@link CampoAvatar#toString}.
     */
    @Test public void testToString() {
        String s = CampoAvatar.NOMBRE.toString();
        Assert.assertTrue(s.equals("Nombre"));
        s = CampoAvatar.MANA.toString();
        Assert.assertTrue(s.equals("Nivel de maná"));
        s = CampoAvatar.VIDA.toString();
        Assert.assertTrue(s.equals("Nivel de vida"));
        s = CampoAvatar.ATAQUE.toString();
        Assert.assertTrue(s.equals("Nivel de ataque"));
        s = CampoAvatar.DEFENSA.toString();
        Assert.assertTrue(s.equals("Nivel de defensa"));
    }
}
