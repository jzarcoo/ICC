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
    }
    
    /**
     * Prueba unitaria para {@link BaseDeDatos#getNumRegistros}.
     */
    @Test public void testGetNumRegistros() {
        Assert.assertTrue(bdd.getNumRegistros() == 0);
        for (int i = 0; i < total; i++) {
            Avatar a = TestAvatar.avatarAleatorio();
            bdd.agregaRegistro(a);
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
        for (Avatar a : l)
            Assert.assertTrue(avatares[c++].equals(a));
        l.elimina(avatares[0]);
        Assert.assertFalse(l.equals(bdd.getRegistros()));
        Assert.assertFalse(l.getLongitud() == bdd.getNumRegistros());
    }
    
    /**
     * Prueba unitaria para {@link BaseDeDatos#agregaRegistro}.
     */
    @Test public void testAgregaRegistro() {
        for (int i = 0; i < total; i++) {
            Avatar a = TestAvatar.avatarAleatorio();
            Assert.assertFalse(bdd.getRegistros().contiene(a));
            bdd.agregaRegistro(a);
            Assert.assertTrue(bdd.getRegistros().contiene(a));
            Lista<Avatar> l = bdd.getRegistros();
            Assert.assertTrue(l.get(l.getLongitud() - 1).equals(a));
        }
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#eliminaRegistro}.
     */
    @Test public void testEliminaRegistro() {
        int ini = random.nextInt(200);
        for (int i = 0; i < total; i++) {
            Avatar a = TestAvatar.avatarAleatorio(ini + i);
            bdd.agregaRegistro(a);
        }
        while (bdd.getNumRegistros() > 0) {
            int i = random.nextInt(bdd.getNumRegistros());
            Avatar a = bdd.getRegistros().get(i);
            Assert.assertTrue(bdd.getRegistros().contiene(a));
            bdd.eliminaRegistro(a);
            Assert.assertFalse(bdd.getRegistros().contiene(a));
        }
    }
    
    /**
     * Prueba unitaria para {@link BaseDeDatos#limpia}.
     */
    @Test public void testLimpia() {
        for (int i = 0; i < total; i++) {
            Avatar a = TestAvatar.avatarAleatorio();
            bdd.agregaRegistro(a);
        }
        Lista<Avatar> registros = bdd.getRegistros();
        Assert.assertFalse(registros.esVacia());
        Assert.assertFalse(registros.getLongitud() == 0);
        bdd.limpia();
        registros = bdd.getRegistros();
        Assert.assertTrue(registros.esVacia());
        Assert.assertTrue(registros.getLongitud() == 0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#guarda}.
     */
    @Test public void testGuarda() {
        for (int i = 0; i < total; i++) {
            Avatar a = TestAvatar.avatarAleatorio();
            bdd.agregaRegistro(a);
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
            String al = String.format("%s\t%d\t%d\t%2.2f\t%2.2f\t%s",
                                      a.getNombre(),
                                      a.getMana(),
                                      a.getVida(),
                                      a.getAtaque(),
				      a.getDefensa(),
				      a.getHabilidades());
            Assert.assertTrue(lineas[c++].equals(al));
        }
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#carga}.
     */
    @Test public void testCarga() {
        int ini = random.nextInt(200);
        String entrada = "";
        Avatar[] avatares = new Avatar[total];
        for (int i = 0; i < total; i++) {
            avatares[i] = TestAvatar.avatarAleatorio(ini + i);
            entrada += String.format("%s\t%d\t%d\t%2.2f\t%2.2f\t%s\n",
                                     avatares[i].getNombre(),
                                     avatares[i].getMana(),
                                     avatares[i].getVida(),
                                     avatares[i].getAtaque(),
                                     avatares[i].getDefensa(),
                                     avatares[i].getHabilidades());
            bdd.agregaRegistro(avatares[i]);
        }
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
        for (Avatar a : l)
            Assert.assertTrue(avatares[c++].equals(a));
        entrada = String.format("%s\t%d\t%d\t%2.2f\t%2.2f\t%s\n",
				avatares[0].getNombre(),
				avatares[0].getMana(),
				avatares[0].getVida(),
				avatares[0].getAtaque(),
				avatares[0].getDefensa(),
				avatares[0].getHabilidades());
        entrada += " \n";
        entrada += String.format("%s\t%d\t%d\t%2.2f\t%2.2f\t%s\n",
				 avatares[1].getNombre(),
				 avatares[1].getMana(),
				 avatares[1].getVida(),
				 avatares[1].getAtaque(),
				 avatares[1].getDefensa(),
				 avatares[1].getHabilidades());
        try {
            StringReader srInt = new StringReader(entrada);
            BufferedReader in = new BufferedReader(srInt, 8192);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            Assert.fail();
        }
        Assert.assertTrue(bdd.getNumRegistros() == 1);
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
     * Prueba unitaria para {@link BaseDeDatosAvatares#buscaRegistros}.
     */
    @Test public void testBuscaRegistros() {
        Avatar[] avatares = new Avatar[total];
        int ini = 100 + random.nextInt(200);
        for (int i = 0; i < total; i++) {
            Avatar a =  new Avatar(String.valueOf(ini+i),
				   ini+i, ini+2*i,
				   (i * 10.0) / total, (i * 10.0) / total,
				   TestAvatar.habilidadesAleatorias());
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
            for (Avatar a : l)
                Assert.assertTrue(a.getNombre().indexOf(nombre) > -1);
            int n = nombre.length();
            String bn = nombre.substring(random.nextInt(2),
                                         2 + random.nextInt(n-2));
            l = bdd.buscaRegistros(CampoAvatar.NOMBRE, bn);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getNombre().indexOf(bn) > -1);

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

            String habilidad = avatar.getHabilidades().getPrimero();
            l = bdd.buscaRegistros(CampoAvatar.HABILIDADES, habilidad);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l)
                Assert.assertTrue(a.getHabilidades().contiene(habilidad));
            int h = habilidad.length();
            String bh = habilidad.substring(random.nextInt(2),
                                         2 + random.nextInt(h-2));
            l = bdd.buscaRegistros(CampoAvatar.HABILIDADES, bh);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(avatar));
            for (Avatar a : l){
		Lista<String> habilidades = a.getHabilidades();
		for(String s : habilidades)
		    if(habilidad.equals(s))
			Assert.assertTrue(s.indexOf(bh) > -1);
	    }
        }

        l = bdd.buscaRegistros(CampoAvatar.NOMBRE,
                                "xxx-nombre");
        Assert.assertTrue(l.esVacia());
	l = bdd.buscaRegistros(CampoAvatar.HABILIDADES,
                                "xxx-habilidad");
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
	l = bdd.buscaRegistros(CampoAvatar.HABILIDADES, "");
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
        l = bdd.buscaRegistros(CampoAvatar.HABILIDADES, null);
        Assert.assertTrue(l.esVacia());

        try {
            l = bdd.buscaRegistros(null, null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
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
	Assert.assertTrue(a.getHabilidades() == null);
    }

}
