package mx.unam.ciencias.icc.test;

import java.util.Random;
import mx.unam.ciencias.icc.CampoAvatar;
import mx.unam.ciencias.icc.Avatar;
import mx.unam.ciencias.icc.ExcepcionLineaInvalida;
import mx.unam.ciencias.icc.Lista;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link Avatar}.
 */
public class TestAvatar {
    
    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);
    
    /* Nombres. */
    private static final String[] NOMBRES = {
        "Arthenox", "Gandalf", "Merlin", "Dumbledore", "Athena",
        "Loki", "Horus", "Boggart", "Poseidon", "Typhon"
    };

    /* Habilidades. */
    private static final String[] HABILIDADES = {
        "Lanza hechizos", "Cura a tu equipo", "Envenena enemigos", "Electrocutar", "Bola de hielo",
        "Teletransporte", "Rayo de escarcha", "Agujero negro", "Desintegrar", "Ráfaga de fuego"
    };

    /* Generador de números aleatorios. */
    private static Random random;

    /**
     * Genera un nombre aleatorio.
     * @return un nombre aleatorio.
     */
    public static String nombreAleatorio() {
        int n = random.nextInt(NOMBRES.length);
        return NOMBRES[n];
    }

    /**
     * Genera habilidades aleatorias.
     * @return una lista de habilidades aleatoria.
     */
    public static Lista<String> habilidadesAleatorias() {
	Lista<String> lista = new Lista<String>();
	int maxLongitud = HABILIDADES.length;
        int numHabilidades = random.nextInt(maxLongitud-1)+1;
	for(int i=0; i< numHabilidades; i++){
	    int indice;
	    do{
		indice = random.nextInt(maxLongitud);
	    } while (lista.contiene(HABILIDADES[indice]));
	    lista.agregaFinal(HABILIDADES[indice]);
	}
        return lista;
    }
    
    /**
     * Genera un nivel de mana aleatorio.
     * @return un nivel de mana aleatorio.
     */
    public static int manaAleatorio() {
        return 50 + random.nextInt(70);
    }
    
    /**
     * Genera un nivel de vida aleatorio.
     * @return un nivel de vida aleatorio.
     */
    public static int vidaAleatoria() {
        return 3 + random.nextInt(10);
    }
    
    /**
     * Genera un nivel de ataque aleatorio.
     * @return un nivel de ataque aleatorio.
     */
    public static double ataqueAleatorio() {
        return (random.nextInt(100) / 10.0) + random.nextInt(200);
    }
    
    /**
     * Genera un nivel de defensa aleatorio.
     * @return un nivel de defensa aleatorio.
     */
    public static double defensaAleatoria() {
        return (random.nextInt(100) / 10.0) + random.nextInt(200);
    }
    
    /**
     * Genera un avatar aleatorio.
     * @return un avatar aleatorio.
     */
    public static Avatar avatarAleatorio() {
        return new Avatar(nombreAleatorio(),
    			  manaAleatorio(),
    			  vidaAleatoria(),
     			  ataqueAleatorio(),
    			  defensaAleatoria(),
			  habilidadesAleatorias());
    }
    
    /**
     * Genera un avatar aleatorio con un nivel de mana dado.
     * @param mana el nivel de mana del nuevo estudiante.
     * @return un avatar aleatorio.
     */
    public static Avatar avatarAleatorio(int mana) {
        return new Avatar(nombreAleatorio(),
    			  mana,
    			  vidaAleatoria(),
     			  ataqueAleatorio(),
    			  defensaAleatoria(),
			  habilidadesAleatorias());
    }
    
    /* El avatar. */
    private Avatar avatar;

    /**
     * Prueba unitaria para {@link
     * Avatar#Avatar(String,int,int, double, double, Lista<String>)}.
     */
    @Test public void testConstructor() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getNombre().equals(nombre));
        Assert.assertTrue(avatar.getMana() == mana);
	Assert.assertTrue(avatar.getVida() == vida);
	Assert.assertTrue(avatar.getAtaque() == ataque);
	Assert.assertTrue(avatar.getDefensa() == defensa);
        Assert.assertTrue(avatar.getHabilidades().equals(habilidades));
    }
    
    /**
     * Prueba unitaria para {@link Avatar#getNombre}.
     */
    @Test public void testGetNombre() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getNombre().equals(nombre));
        Assert.assertFalse(avatar.getNombre().equals(nombre + " X"));
    }

    /**
     * Prueba unitaria para {@link Avatar#setNombre}.
     */
    @Test public void testSetNombre() {
        String nombre = nombreAleatorio();
        String nuevoNombre = nombre + " X";
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getNombre().equals(nombre));
        Assert.assertFalse(avatar.getNombre().equals(nuevoNombre));
        avatar.setNombre(nuevoNombre);
        Assert.assertFalse(avatar.getNombre().equals(nombre));
        Assert.assertTrue(avatar.getNombre().equals(nuevoNombre));
    }
    
    /**
     * Prueba unitaria para {@link Avatar#getMana}.
     */
    @Test public void testGetMana() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
        Assert.assertTrue(avatar.getMana() == mana);
        Assert.assertFalse(avatar.getMana() == mana + 100);
    }
    

    /**
     * Prueba unitaria para {@link Avatar#setMana}.
     */
    @Test public void testSetMana() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
        int nuevoMana = mana + 100;
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getMana() == mana);
        Assert.assertFalse(avatar.getMana() == nuevoMana);
        avatar.setMana(nuevoMana);
        Assert.assertFalse(avatar.getMana() == mana);
        Assert.assertTrue(avatar.getMana() == nuevoMana);
    }
    
    /**
     * Prueba unitaria para {@link Avatar#getVida}.
     */
    @Test public void testGetVida() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
        Assert.assertTrue(avatar.getVida() == vida);
        Assert.assertFalse(avatar.getVida() == vida + 50);
    }
    

    /**
     * Prueba unitaria para {@link Avatar#setMana}.
     */
    @Test public void testSetVida() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        int nuevaVida = vida + 50;
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getVida() == vida);
        Assert.assertFalse(avatar.getVida() == nuevaVida);
        avatar.setVida(nuevaVida);
        Assert.assertFalse(avatar.getVida() == vida);
        Assert.assertTrue(avatar.getVida() == nuevaVida);
    }
    
    /**
     * Prueba unitaria para {@link Avatar#getAtaque}.
     */
    @Test public void testGetAtaque() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
        Assert.assertTrue(avatar.getAtaque() == ataque);
        Assert.assertFalse(avatar.getAtaque() == ataque + 1.0);
    }

    /**
     * Prueba unitaria para {@link Avatar#setAtaque}.
     */
    @Test public void testSetAtaque() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
        double nuevoAtaque = ataque + 1.0;
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getAtaque() == ataque);
        Assert.assertFalse(avatar.getAtaque() == nuevoAtaque);
        avatar.setAtaque(nuevoAtaque);
        Assert.assertFalse(avatar.getAtaque() == ataque);
        Assert.assertTrue(avatar.getAtaque() == nuevoAtaque);
    } 
    
    /**
     * Prueba unitaria para {@link Avatar#getDefensa}.
     */
    @Test public void testGetDefensa() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
        Assert.assertTrue(avatar.getDefensa() == defensa);
        Assert.assertFalse(avatar.getDefensa() == defensa + 1.0);
    }

    /**
     * Prueba unitaria para {@link Avatar#setDefensa}.
     */
    @Test public void testSetDefensa() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
        double nuevaDefensa = defensa + 1.0;
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getDefensa() == defensa);
        Assert.assertFalse(avatar.getDefensa() == nuevaDefensa);
        avatar.setDefensa(nuevaDefensa);
        Assert.assertFalse(avatar.getDefensa() == defensa);
        Assert.assertTrue(avatar.getDefensa() == nuevaDefensa);
    }
    
    /**
     * Prueba unitaria para {@link Avatar#getHabilidades}.
     */
    @Test public void testGetHabilidades() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	Lista<String> nuevasHabilidades = habilidadesAleatorias();
        nuevasHabilidades.agregaFinal("X");
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getHabilidades().equals(habilidades));
        Assert.assertFalse(avatar.getHabilidades().equals(nuevasHabilidades));
    }

    /**
     * Prueba unitaria para {@link Avatar#setHabilidades}.
     */
    @Test public void testSetHabilidades() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	Lista<String> nuevasHabilidades = habilidadesAleatorias();
        nuevasHabilidades.agregaFinal("X");
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertTrue(avatar.getHabilidades().equals(habilidades));
        Assert.assertFalse(avatar.getHabilidades().equals(nuevasHabilidades));
        avatar.setHabilidades(nuevasHabilidades);
        Assert.assertFalse(avatar.getHabilidades().equals(habilidades));
        Assert.assertTrue(avatar.getHabilidades().equals(nuevasHabilidades));
    }

    
    /**
     * Prueba unitaria para {@link Avatar#toString}.
     */
    @Test public void testToString() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
	StringBuffer sb = new StringBuffer();
	sb.append(String.format("Nombre      : %s\n", nombre));
        sb.append(String.format("Mana        : %d\n", mana));
	sb.append(String.format("Vida        : %d\n", vida));
	sb.append(String.format("Ataque      : %2.2f\n", ataque));
	sb.append(String.format("Defensa     : %2.2f\n", defensa));
	sb.append(String.format("Habilidades : %s\n", habilidades));
        String cadena = sb.toString();
        Assert.assertTrue(avatar.toString().equals(cadena));
        mana = 213;
        defensa = 100.99;
        avatar.setMana(mana);
        avatar.setDefensa(defensa);
	sb.setLength(0); 
	sb.append(String.format("Nombre      : %s\n", nombre));
        sb.append(String.format("Mana        : %d\n", mana));
	sb.append(String.format("Vida        : %d\n", vida));
	sb.append(String.format("Ataque      : %2.2f\n", ataque));
	sb.append(String.format("Defensa     : %2.2f\n", defensa));
	sb.append(String.format("Habilidades : %s\n", habilidades));
	cadena = sb.toString();
        Assert.assertTrue(avatar.toString().equals(cadena));
    }
    
    /**
     * Prueba unitaria para {@link Avatar#equals}.
     */
    @Test public void testEquals() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
        Avatar igual = new Avatar(new String(nombre),
                                          mana, vida, ataque, defensa, habilidades);
        Assert.assertTrue(avatar.equals(igual));
        String otroNombre = nombre + " Segundo";
        int otroMana = mana + 1;
        int otraVida = vida + 1;
	double otroAtaque = (ataque != 0.0) ? ataque / 10.0 : 10.0;
	double otraDefensa = (defensa != 0.0) ? defensa / 10.0 : 10.0;
	Lista<String> otrasHabilidades = habilidades.copia();
	otrasHabilidades.agregaFinal("Otra");
	Avatar distinto =
            new Avatar(otroNombre, mana, vida, ataque, defensa, habilidades);
	Assert.assertFalse(avatar.equals(distinto));
	distinto = new Avatar(nombre, otroMana, vida, ataque, defensa, habilidades);
        Assert.assertFalse(avatar.equals(distinto));
        distinto = new Avatar(nombre, mana, otraVida, ataque, defensa, habilidades);
        Assert.assertFalse(avatar.equals(distinto));
        distinto = new Avatar(nombre, mana, vida, otroAtaque, defensa, habilidades);
        Assert.assertFalse(avatar.equals(distinto));
        distinto = new Avatar(nombre, mana, vida, ataque, otraDefensa, habilidades);
        Assert.assertFalse(avatar.equals(distinto));
        distinto = new Avatar(nombre, mana, vida, ataque, defensa, otrasHabilidades);
	Assert.assertFalse(avatar.equals(distinto));
        distinto =
	    new Avatar(otroNombre, otroMana, otraVida, otroAtaque, otraDefensa, otrasHabilidades);
        Assert.assertFalse(avatar.equals(distinto));
        Assert.assertFalse(avatar.equals("Una cadena"));
        Assert.assertFalse(avatar.equals(null));
    }

    /**
     * Prueba unitaria para {@link Avatar#seria}.
     */
    @Test public void testSeria() {
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
        String linea = String.format("%s\t%d\t%d\t%2.2f\t%2.2f\t%s\n",
			     nombre, mana, vida, ataque, defensa, habilidades);
        Assert.assertTrue(avatar.seria().equals(linea));
    }

        /**
     * Prueba unitaria para {@link Avatar#deseria}.
     */
    @Test public void testDeseria() {
        avatar = new Avatar();
        String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
        String linea = String.format("%s\t%d\t%d\t%2.2f\t%2.2f\t%s\n",
			     nombre, mana, vida, ataque, defensa, habilidades);
        try {
            avatar.deseria(linea);
        } catch (ExcepcionLineaInvalida eli) {
            Assert.fail();
        }
	Assert.assertTrue(avatar.getNombre().equals(nombre));
        Assert.assertTrue(avatar.getMana() == mana);
	Assert.assertTrue(avatar.getVida() == vida);
	Assert.assertTrue(avatar.getAtaque() == ataque);
	Assert.assertTrue(avatar.getDefensa() == defensa);
        Assert.assertTrue(avatar.getHabilidades().equals(habilidades));
	String[] invalidas = {"", " ", "\t", "  ", "\t\t",
			      " \t", "\t ", "\n", "a\ta\ta",
			      "a\ta\ta\ta"};
	for(String l: invalidas){
	    try {
                avatar.deseria(l);
                Assert.fail();
            } catch (ExcepcionLineaInvalida eli) {}
	}
    }

    /**
     * Prueba unitaria para {@link Avatar#actualiza}.
     */
    @Test public void testActualiza() {
        avatar = new Avatar("A", 1, 1, 1, 1, null);
        Avatar a = new Avatar("B", 2, 2, 2, 2, new Lista<String>());
        Assert.assertFalse(avatar == a);
        Assert.assertFalse(avatar.equals(a));
        avatar.actualiza(a);
        Assert.assertFalse(avatar == a);
        Assert.assertTrue(avatar.equals(a));
        Assert.assertTrue(avatar.getNombre().equals("B"));
        try {
            avatar.actualiza(null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
    }
    
    /**
     * Prueba unitaria para {@link Avatar#casa}.
     */
    @Test public void testCasa() {
	String nombre = nombreAleatorio();
        int mana = manaAleatorio();
	int vida = vidaAleatoria();
        double ataque = ataqueAleatorio();
	double defensa = defensaAleatoria();
	Lista<String> habilidades = habilidadesAleatorias();
	avatar = new Avatar(nombre, mana, vida, ataque, defensa, habilidades);

        String n = avatar.getNombre();
        int m = avatar.getNombre().length();
        Assert.assertTrue(avatar.casa(CampoAvatar.NOMBRE, n));
        n = avatar.getNombre().substring(0, m/2);
        Assert.assertTrue(avatar.casa(CampoAvatar.NOMBRE, n));
        n = avatar.getNombre().substring(m/2, m);
        Assert.assertTrue(avatar.casa(CampoAvatar.NOMBRE, n));
        n = avatar.getNombre().substring(m/3, 2*(m/3));
        Assert.assertTrue(avatar.casa(CampoAvatar.NOMBRE, n));
        Assert.assertFalse(avatar.casa(CampoAvatar.NOMBRE, ""));
        Assert.assertFalse(avatar.casa(CampoAvatar.NOMBRE, "XXX"));
        Assert.assertFalse(avatar.casa(CampoAvatar.NOMBRE, 1000));
        Assert.assertFalse(avatar.casa(CampoAvatar.NOMBRE, null));

        int c = avatar.getMana();
        Assert.assertTrue(avatar.casa(CampoAvatar.MANA, c));
        c = avatar.getMana() - 100;
        Assert.assertTrue(avatar.casa(CampoAvatar.MANA, c));
        c = avatar.getMana() + 100;
        Assert.assertFalse(avatar.casa(CampoAvatar.MANA, c));
        Assert.assertFalse(avatar.casa(CampoAvatar.MANA, "XXX"));
        Assert.assertFalse(avatar.casa(CampoAvatar.MANA, null));

        int v = avatar.getVida();
        Assert.assertTrue(avatar.casa(CampoAvatar.VIDA, v));
        v = avatar.getVida() - 10;
        Assert.assertTrue(avatar.casa(CampoAvatar.VIDA, v));
        v = avatar.getVida() + 10;
        Assert.assertFalse(avatar.casa(CampoAvatar.VIDA, v));
        Assert.assertFalse(avatar.casa(CampoAvatar.VIDA, "XXX"));
        Assert.assertFalse(avatar.casa(CampoAvatar.VIDA, null));

        double a = avatar.getAtaque();
        Assert.assertTrue(avatar.casa(CampoAvatar.ATAQUE, a));
        a = avatar.getAtaque() - 25.0;
        Assert.assertTrue(avatar.casa(CampoAvatar.ATAQUE, a));
        a = avatar.getAtaque() + 25.0;
        Assert.assertFalse(avatar.casa(CampoAvatar.ATAQUE, a));
        Assert.assertFalse(avatar.casa(CampoAvatar.ATAQUE, "XXX"));
        Assert.assertFalse(avatar.casa(CampoAvatar.ATAQUE, null));

        double d = avatar.getDefensa();
        Assert.assertTrue(avatar.casa(CampoAvatar.DEFENSA, d));
        d = avatar.getDefensa() - 50.0;
        Assert.assertTrue(avatar.casa(CampoAvatar.DEFENSA, d));
        d = avatar.getDefensa() + 50.0;
        Assert.assertFalse(avatar.casa(CampoAvatar.DEFENSA, d));
        Assert.assertFalse(avatar.casa(CampoAvatar.DEFENSA, "XXX"));
        Assert.assertFalse(avatar.casa(CampoAvatar.DEFENSA, null));


        Lista<String> lista = avatar.getHabilidades();
	String h = lista.getPrimero();
	int l = h.length();
	Assert.assertTrue(avatar.casa(CampoAvatar.HABILIDADES, h));
	h = lista.getPrimero().substring(0, l/2);
	Assert.assertTrue(avatar.casa(CampoAvatar.HABILIDADES, h));
	h = lista.getPrimero().substring(l/2, l);
	Assert.assertTrue(avatar.casa(CampoAvatar.HABILIDADES, h));
	h = lista.getPrimero().substring(l/3, 2*(l/3));
	Assert.assertTrue(avatar.casa(CampoAvatar.HABILIDADES, h));
	Assert.assertFalse(avatar.casa(CampoAvatar.HABILIDADES, ""));
	Assert.assertFalse(avatar.casa(CampoAvatar.HABILIDADES, "XXX"));
	Assert.assertFalse(avatar.casa(CampoAvatar.HABILIDADES, 1000));
	Assert.assertFalse(avatar.casa(CampoAvatar.HABILIDADES, null));
	
        try {
            avatar.casa(null, null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
    }

    /* Inicializa el generador de números aleatorios. */
    static {
	random = new Random();
    }
}
