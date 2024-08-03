package mx.unam.ciencias.icc.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;
import mx.unam.ciencias.icc.BaseDeDatos;
import mx.unam.ciencias.icc.BaseDeDatosAvatares;
import mx.unam.ciencias.icc.CampoAvatar;
import mx.unam.ciencias.icc.Avatar;
import mx.unam.ciencias.icc.EscuchaBaseDeDatos;
import mx.unam.ciencias.icc.EventoBaseDeDatos;
import mx.unam.ciencias.icc.Lista;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link BaseDeDatosAvatares}.
 */
public class TestBaseDeDatosAvatares {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /* Generador de números aleatorios. */
    private Random random;
    /* Base de datos de avatares. */
    private BaseDeDatosAvatares bdd;
    /* Número total de avatares. */
    private int total;

    /**
     * Crea un generador de números aleatorios para cada prueba y una base de
     * datos de avatares.
     */
    public TestBaseDeDatosAvatares() {
        random = new Random();
        bdd = new BaseDeDatosAvatares();
        total = 2 + random.nextInt(100);
    }

    /**
     * Prueba unitaria para {@link
     * BaseDeDatosAvatares#BaseDeDatosAvatares}.
     */
    @Test public void testConstructor() {
        Lista<Avatar> avatares = bdd.getRegistros();
        Assert.assertFalse(avatares == null);
        Assert.assertTrue(avatares.getLongitud() == 0);
        Assert.assertTrue(bdd.getNumRegistros() == 0);
        bdd.agregaEscucha((e, r1, r2) -> {});
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#getNumRegistros}.
     */
    @Test public void testGetNumRegistros() {
        Assert.assertTrue(bdd.getNumRegistros() == 0);
        for (int i = 0; i < total; i++) {
            Avatar e = TestAvatar.avatarAleatorio();
            bdd.agregaRegistro(e);
            Assert.assertTrue(bdd.getNumRegistros() == i+1);
        }
        Assert.assertTrue(bdd.getNumRegistros() == total);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#getRegistros}.
     */
    @Test public void testGetRegistros() {
        Lista<Avatar> l = bdd.getRegistros();
        Lista<Avatar> r = bdd.getRegistros();
        Assert.assertTrue(l.equals(r));
        Assert.assertFalse(l == r);
        Avatar[] avatares = new Avatar[total];
        for (int i = 0; i < total; i++) {
            avatares[i] = TestAvatar.avatarAleatorio();
            bdd.agregaRegistro(avatares[i]);
        }
        l = bdd.getRegistros();
        int c = 0;
        for (Avatar e : l)
            Assert.assertTrue(avatares[c++].equals(e));
        l.elimina(avatares[0]);
        Assert.assertFalse(l.equals(bdd.getRegistros()));
        Assert.assertFalse(l.getLongitud() == bdd.getNumRegistros());
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#agregaRegistro}.
     */
    @Test public void testAgregaRegistro() {
        for (int i = 0; i < total; i++) {
            Avatar e = TestAvatar.avatarAleatorio();
            Assert.assertFalse(bdd.getRegistros().contiene(e));
            bdd.agregaRegistro(e);
            Assert.assertTrue(bdd.getRegistros().contiene(e));
            Lista<Avatar> l = bdd.getRegistros();
            Assert.assertTrue(l.get(l.getLongitud() - 1).equals(e));
        }
        boolean[] llamado =  { false };
        bdd.agregaEscucha((e, r1, r2) -> {
                Assert.assertTrue(e == EventoBaseDeDatos.REGISTRO_AGREGADO);
                Assert.assertTrue(r1.equals(new Avatar("A", 1, 1, 1, 1)));
                Assert.assertTrue(r2 == null);
                llamado[0] = true;
            });
        bdd.agregaRegistro(new Avatar("A", 1, 1, 1, 1));
        Assert.assertTrue(llamado[0]);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#eliminaRegistro}.
     */
    @Test public void testEliminaRegistro() {
        int ini = random.nextInt(1000000);
        for (int i = 0; i < total; i++) {
            Avatar e = TestAvatar.avatarAleatorio(ini + i);
            bdd.agregaRegistro(e);
        }
        while (bdd.getNumRegistros() > 0) {
            int i = random.nextInt(bdd.getNumRegistros());
            Avatar e = bdd.getRegistros().get(i);
            Assert.assertTrue(bdd.getRegistros().contiene(e));
            bdd.eliminaRegistro(e);
            Assert.assertFalse(bdd.getRegistros().contiene(e));
        }
        boolean[] llamado = { false };
        Avatar avatar = new Avatar("A", 1, 1, 1, 1);
        bdd.agregaRegistro(avatar);
        bdd.agregaEscucha((e, r1, r2) -> {
                Assert.assertTrue(e == EventoBaseDeDatos.REGISTRO_ELIMINADO);
                Assert.assertTrue(r1.equals(new Avatar("A", 1, 1, 1, 1)));
                Assert.assertTrue(r2 == null);
                llamado[0] = true;
            });
        bdd.eliminaRegistro(avatar);
        Assert.assertTrue(llamado[0]);
        bdd = new BaseDeDatosAvatares();
        llamado[0] = false;
        bdd.agregaEscucha((e, r1, r2) -> {
                Assert.assertTrue(e == EventoBaseDeDatos.REGISTRO_ELIMINADO);
                Assert.assertTrue(r1.equals(new Avatar("A", 1, 1, 1, 1)));
                Assert.assertTrue(r2 == null);
                llamado[0] = true;
            });
        bdd.eliminaRegistro(avatar);
        Assert.assertTrue(llamado[0]);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#modificaRegistro}.
     */
    @Test public void testModificaRegistro() {
        for (int i = 0; i < total; i++) {
            Avatar e = TestAvatar.avatarAleatorio(total + i);
            Assert.assertFalse(bdd.getRegistros().contiene(e));
            bdd.agregaRegistro(e);
            Assert.assertTrue(bdd.getRegistros().contiene(e));
            Lista<Avatar> l = bdd.getRegistros();
            Assert.assertTrue(l.get(l.getLongitud() - 1).equals(e));
        }
        Avatar a = new Avatar("A", 1, 1, 1, 1);
        Avatar b = new Avatar("B", 2, 2, 2, 2);
        bdd.agregaRegistro(a);
        boolean[] llamado = { false };
        bdd.agregaEscucha((e, r1, r2) -> {
                Assert.assertTrue(e == EventoBaseDeDatos.REGISTRO_MODIFICADO);
                Assert.assertTrue(r1.equals(new Avatar("A", 1, 1, 1, 1)));
                Assert.assertTrue(r2.equals(new Avatar("B", 2, 2, 2, 2)));
                llamado[0] = true;
            });
        Avatar c = new Avatar("A", 1, 1, 1, 1);
        bdd.modificaRegistro(c, b);
        Assert.assertTrue(llamado[0]);
        Assert.assertTrue(c.equals(new Avatar("A", 1, 1, 1, 1)));
        Assert.assertTrue(b.equals(new Avatar("B", 2, 2, 2, 2)));
        int ca = 0, cb = 0;
        for (Avatar e : bdd.getRegistros()) {
            ca += e.equals(c) ? 1 : 0;
            cb += e.equals(b) ? 1 : 0;
        }
        Assert.assertTrue(ca == 0);
        Assert.assertTrue(cb == 1);
        bdd = new BaseDeDatosAvatares();
        a = new Avatar("A", 1, 1, 1, 1);
        b = new Avatar("B", 2, 2, 2, 2);
        bdd.agregaRegistro(a);
        bdd.agregaEscucha((e, r1, r2) -> {
                Assert.assertTrue(e == EventoBaseDeDatos.REGISTRO_MODIFICADO);
                Assert.assertTrue(r1.equals(new Avatar("A", 1, 1, 1, 1)));
                Assert.assertTrue(r2.equals(new Avatar("B", 2, 2, 2, 2)));
                llamado[0] = true;
            });
        bdd.modificaRegistro(a, b);
        Assert.assertTrue(a.equals(b));
        Assert.assertTrue(llamado[0]);
        bdd = new BaseDeDatosAvatares();
        llamado[0] = false;
        bdd.agregaEscucha((e, r1, r2) -> {
                Assert.assertTrue(e == EventoBaseDeDatos.REGISTRO_MODIFICADO);
                llamado[0] = true;
            });
        bdd.modificaRegistro(new Avatar("A", 1, 1, 1, 1),
                             new Avatar("B", 2, 2, 2, 2));
        Assert.assertFalse(llamado[0]);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#limpia}.
     */
    @Test public void testLimpia() {
        for (int i = 0; i < total; i++) {
            Avatar e = TestAvatar.avatarAleatorio();
            bdd.agregaRegistro(e);
        }
        boolean[] llamado = { false };
        bdd.agregaEscucha((e, r1, r2) -> {
                Assert.assertTrue(e == EventoBaseDeDatos.BASE_LIMPIADA);
                Assert.assertTrue(r1 == null);
                Assert.assertTrue(r2 == null);
                llamado[0] = true;
            });
        Lista<Avatar> registros = bdd.getRegistros();
        Assert.assertFalse(registros.esVacia());
        Assert.assertFalse(registros.getLongitud() == 0);
        bdd.limpia();
        registros = bdd.getRegistros();
        Assert.assertTrue(registros.esVacia());
        Assert.assertTrue(registros.getLongitud() == 0);
        Assert.assertTrue(llamado[0]);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#guarda}.
     */
    @Test public void testGuarda() {
        for (int i = 0; i < total; i++) {
            Avatar e = TestAvatar.avatarAleatorio();
            bdd.agregaRegistro(e);
        }
        String guardado = "";
        try {
            StringWriter swOut = new StringWriter();
            BufferedWriter out = new BufferedWriter(swOut);
            bdd.guarda(out);
            out.close();
            guardado = swOut.toString();
        } catch (IOException ioe) {
            Assert.fail();
        }
        String[] lineas = guardado.split("\n");
        Assert.assertTrue(lineas.length == total);
        Lista<Avatar> l = bdd.getRegistros();
        int c = 0;
        for (Avatar a : l) {
            String al = String.format("%s\t%d\t%d\t%2.2f\t%2.2f",
                                      a.getNombre(),
                                      a.getMana(),
                                      a.getVida(),
                                      a.getAtaque(),
				      a.getDefensa());
            Assert.assertTrue(lineas[c++].equals(al));
        }
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#carga}.
     */
    @Test public void testCarga() {
        int ini = random.nextInt(1000000);
        String entrada = "";
        Avatar[] avatares = new Avatar[total];
        for (int i = 0; i < total; i++) {
            avatares[i] = TestAvatar.avatarAleatorio(ini + i);
            entrada += String.format("%s\t%d\t%d\t%2.2f\t%2.2f\n",
                                     avatares[i].getNombre(),
                                     avatares[i].getMana(),
                                     avatares[i].getVida(),
                                     avatares[i].getAtaque(),
                                     avatares[i].getDefensa());
            bdd.agregaRegistro(avatares[i]);
        }
        int[] contador = { 0 };
        boolean[] llamado = { false };
        bdd.agregaEscucha((e, r1, r2) -> {
                if (e == EventoBaseDeDatos.BASE_LIMPIADA)
                    llamado[0] = true;
                if (e == EventoBaseDeDatos.REGISTRO_AGREGADO) {
                    contador[0] ++;
                    Assert.assertTrue(r1 != null);
                    Assert.assertTrue(r2 == null);
                }
            });
        try {
            StringReader srInt = new StringReader(entrada);
            BufferedReader in = new BufferedReader(srInt, 8192);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            Assert.fail();
        }
        Lista<Avatar> l = bdd.getRegistros();
        Assert.assertTrue(l.getLongitud() == total);
        int c = 0;
        for (Avatar e : l)
            Assert.assertTrue(avatares[c++].equals(e));
        Assert.assertTrue(llamado[0]);
        Assert.assertTrue(contador[0] == total);
        contador[0] = 0;
        llamado[0] = false;
        entrada = String.format("%s\t%d\t%d\t%2.2f\t%2.2f\n",
				avatares[0].getNombre(),
				avatares[0].getMana(),
				avatares[0].getVida(),
				avatares[0].getAtaque(),
				avatares[0].getDefensa());
        entrada += " \n";
        entrada += String.format("%s\t%d\t%d\t%2.2f\t%2.2f\n",
				 avatares[1].getNombre(),
				 avatares[1].getMana(),
				 avatares[1].getVida(),
				 avatares[1].getAtaque(),
				 avatares[1].getDefensa());
        try {
            StringReader srInt = new StringReader(entrada);
            BufferedReader in = new BufferedReader(srInt, 8192);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            Assert.fail();
        }
        Assert.assertTrue(bdd.getNumRegistros() == 1);
        Assert.assertTrue(llamado[0]);
        Assert.assertTrue(contador[0] == 1);
        entrada = "";
        try {
            StringReader srInt = new StringReader(entrada);
            BufferedReader in = new BufferedReader(srInt, 8192);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            Assert.fail();
        }
        Assert.assertTrue(bdd.getNumRegistros() == 0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatosAvatares#creaRegistro}.
     */
    @Test public void testCreaRegistro() {
        Avatar a = bdd.creaRegistro();
        Assert.assertTrue(a.getNombre() == null);
        Assert.assertTrue(a.getMana() == 0);
        Assert.assertTrue(a.getVida() == 0);
        Assert.assertTrue(a.getAtaque() == 0.0);
	Assert.assertTrue(a.getDefensa() == 0.0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatosAvatares#buscaRegistros}.
     */
    @Test public void testBuscaRegistros() {
        Avatar[] avatares = new Avatar[total];
	int ini = 1000000 + random.nextInt(999999);
        for (int i = 0; i < total; i++) {
            Avatar a =  new Avatar(String.valueOf(ini+i),
				   ini+i, i,
				   (i * 10.0) / total, (i * 10.0) / total);

            avatares[i] = a;
            bdd.agregaRegistro(a);
        }

        Avatar avatar;
        Lista<Avatar> l;
        int i;

        for (int k = 0; k < total/10 + 3; k++) {
            i = random.nextInt(total);
            avatar = avatares[i];

            String nombre = avatar.getNombre();
            l = bdd.buscaRegistros(CampoAvatar.NOMBRE, nombre);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar e : l)
                Assert.assertTrue(e.getNombre().indexOf(nombre) > -1);
            int n = nombre.length();
            String bn = nombre.substring(random.nextInt(2),
                                         2 + random.nextInt(n-2));
            l = bdd.buscaRegistros(CampoAvatar.NOMBRE, bn);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar e : l)
                Assert.assertTrue(e.getNombre().indexOf(bn) > -1);

            int mana = avatar.getMana();
            l = bdd.buscaRegistros(CampoAvatar.MANA, mana);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getMana() >= mana);
            int bm = mana - 10;
            l = bdd.buscaRegistros(CampoAvatar.MANA, bm);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getMana() >= bm);

            int vida = avatar.getVida();
            l = bdd.buscaRegistros(CampoAvatar.VIDA, vida);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getVida() >= vida);
            int bv = vida - 10;
            l = bdd.buscaRegistros(CampoAvatar.VIDA, bv);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getVida() >= bv);

            double ataque = avatar.getAtaque();
            l = bdd.buscaRegistros(CampoAvatar.ATAQUE, ataque);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getAtaque() >= ataque);
            double ba = ataque - 5.0;
            l = bdd.buscaRegistros(CampoAvatar.ATAQUE, ba);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getAtaque() >= ba);

            double defensa = avatar.getDefensa();
            l = bdd.buscaRegistros(CampoAvatar.DEFENSA, defensa);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getDefensa() >= defensa);
            double bd = defensa - 5.0;
            l = bdd.buscaRegistros(CampoAvatar.DEFENSA, bd);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getDefensa() >= bd);
        }

        l = bdd.buscaRegistros(CampoAvatar.NOMBRE,
                               "xxx-nombre");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.MANA, 9123456);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.VIDA, 127);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.ATAQUE, 97.12);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.DEFENSA, 98.13);
        Assert.assertTrue(l.esVacia());

        l = bdd.buscaRegistros(CampoAvatar.NOMBRE, "");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.MANA, Integer.MAX_VALUE);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.VIDA, Integer.MAX_VALUE);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.ATAQUE, Double.MAX_VALUE);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.DEFENSA, Double.MAX_VALUE);
        Assert.assertTrue(l.esVacia());

        l = bdd.buscaRegistros(CampoAvatar.NOMBRE, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.MANA, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.VIDA, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.ATAQUE, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoAvatar.DEFENSA, null);
        Assert.assertTrue(l.esVacia());

        try {
            l = bdd.buscaRegistros(null, null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#agregaEscucha}.
     */
    @Test public void testAgregaEscucha() {
        int[] c = new int[total];
        for (int i = 0; i < total; i++) {
            final int j = i;
            bdd.agregaEscucha((e, r1, r2) -> c[j]++);
        }
        bdd.agregaRegistro(new Avatar("A", 1, 1, 1, 1));
        for (int i = 0; i < total; i++)
            Assert.assertTrue(c[i] == 1);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#eliminaEscucha}.
     */
    @Test public void testEliminaEscucha() {
        int[] c = new int[total];
        Lista<EscuchaBaseDeDatos<Avatar>> escuchas =
            new Lista<EscuchaBaseDeDatos<Avatar>>();
        for (int i = 0; i < total; i++) {
            final int j = i;
            EscuchaBaseDeDatos<Avatar> escucha = (e, r1, r2) -> c[j]++;
            bdd.agregaEscucha(escucha);
            escuchas.agregaFinal(escucha);
        }
        int i = 0;
        while (!escuchas.esVacia()) {
            bdd.agregaRegistro(TestAvatar.avatarAleatorio(i++));
            EscuchaBaseDeDatos<Avatar> escucha = escuchas.eliminaPrimero();
            bdd.eliminaEscucha(escucha);
        }
        for (i = 0; i < total; i++)
            Assert.assertTrue(c[i] == i + 1);
    }
}
