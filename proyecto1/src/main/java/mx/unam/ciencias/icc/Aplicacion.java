package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase para aplicaciones de la base de datos de estudiantes.
 */
public class Aplicacion {
    
    /* Modo de la aplicación. */
    private enum Modo {
        /* Modo para guardar. */
        GUARDA(1),
        /* Modo para cargar. */
        CARGA(2);
	
        /* Código de terminación. */
        private int codigo;
	
        /* Constructor. */
        private Modo(int codigo) {
            this.codigo = codigo;
        }
	
        /* Regresa el código. */
        public int getCodigo() {
            return codigo;
        }

        /* Regresa el modo de la bandera. */
        public static Modo getModo(String bandera) {
            switch (bandera) {
            case "-g": return GUARDA;
            case "-c": return CARGA;
            default: throw new IllegalArgumentException(
                "Bandera inválida: " + bandera);
            }
        }
    }

    /* La base de datos. */
    private BaseDeDatosAvatares bdd;
    /* La ruta del archivo. */
    private String ruta;
    /* El modo de la aplicación. */
    private Modo modo;

    /**
    * Define el estado inicial de la aplicación.
     * @param bandera la bandera de modo.
     * @param ruta la ruta del archivo.
     * @throws IllegalArgumentException si la bandera no es "-r" o "-g".
     */
    public Aplicacion(String bandera, String ruta) {
        modo = Modo.getModo(bandera);
        this.ruta = ruta;
        bdd = new BaseDeDatosAvatares();
    }

    /**
     * Ejecuta la aplicación.
     */
     public void ejecuta() {
        switch (modo) {
        case GUARDA:
            guarda();
            break;
        case CARGA:
            carga();
            break;
        }
    }

    /* Modo de guarda de la aplicación. */
    private void guarda() {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        verificaSalida(sc);
        agregaAvatares(sc);
        sc.close();
        try {
            BufferedWriter out =
                new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(ruta)));
            bdd.guarda(out);
            out.close();
        } catch (IOException ioe) {
            System.err.printf("No pude guardar en el archivo \"%s\".\n",
                              ruta);
            System.exit(modo.getCodigo());
        }
    }

    /* Verifica que la salida no exista o le permite salir al usuario. */
    private void verificaSalida(Scanner sc) {
        File archivo = new File(ruta);
        if (archivo.exists()) {
            System.out.printf("El archivo \"%s\" ya existe y será " +
                              "reescrito.\n¿Desea continuar? (s/n): ", ruta);
            String r = sc.next();
            if (!r.equals("s")) {
                sc.close();
                System.exit(0);
            }
        }
    }

    /* Agrega avatares a la base de datos mientras el usuario lo desee. */
    private void agregaAvatares(Scanner sc) {
         System.out.println("\nDeje el nombre en blanco para " +
                            "parar la captura de avatares.");
        Avatar a = null;
        do {
            try {
                a = getAvatar(sc);
                if (a != null)
                    bdd.agregaRegistro(a);
            } catch (InputMismatchException ime) {
                System.err.printf("\nNúmero inválido. Se descartará " +
                                  "este avatar.\n");
                sc.next(); // Purgamos la última entrada del usuario.
                continue;
            }
        } while (a != null);
    }

    /* Obtiene un avatar de la línea de comandos. */
     private Avatar getAvatar(Scanner sc) {
        System.out.printf("\nNombre   : ");
        String nombre = sc.next();
        if (nombre.equals(""))
            return null;
        System.out.printf("Mana     : ");
        int mana = sc.nextInt();
        System.out.printf("Vida     : ");
        int vida = sc.nextInt();
        System.out.printf("Ataque   : ");
        double ataque = sc.nextDouble();
        System.out.printf("Defensa  : ");
        double defensa = sc.nextDouble();
	Lista<String> habilidades = getHabilidades(sc);
        return new Avatar(nombre, mana, vida, ataque, defensa, habilidades);
    }

    private Lista<String> getHabilidades(Scanner sc){
	Lista<String> l = new Lista<String>();
	String habilidad = "";
	int contador = 1;
	System.out.println("\nDeje la habilidad en blanco para " +
			   "parar la captura de habilidades.");
	do {
	    System.out.printf("Habilidad %d : ", contador++);
	    habilidad = sc.next();
	    if(!habilidad.isEmpty())
		l.agregaFinal(habilidad);
	} while (!habilidad.isEmpty());
	return l;
    }

    /* Modo de carga de la aplicación. */
    private void carga() {
        try {
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(ruta)));
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            System.err.printf("No pude cargar del archivo \"%s\".\n",
                              ruta);
            System.exit(modo.getCodigo());
        }
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        busca(sc);
        sc.close();
    }

    /* Hace la búsqueda. */
    private void busca(Scanner sc) {
        System.out.println("\nDeje el campo en blanco para " +
                           "parar la búsqueda de avatares.");
        String c = "X"; // Para entrar al while.
        while (!(c = getCampo(sc)).equals("")) {
            Lista<Avatar> l;
            try {
                l = getResultados(c, sc);
            } catch (ExcepcionOpcionInvalida epi) {
                System.out.printf("%s\n", epi.getMessage());
                continue;
            } catch (InputMismatchException ime) {
                System.out.printf("\nValor inválido. Búsqueda descartada.\n");
                sc.next(); // Purgamos la entrada.
                continue;
            }
	    imprimeResultados(sc, l);
        }
    }
    private void imprimeResultados(Scanner sc, Lista<Avatar> l){
	if (l.esVacia())
	    System.out.println("\nCero registros casan la búsqueda.\n");
	for (Avatar a : l)
	    System.out.printf("%s\n\n", a);
    }

    /* Regresa el campo. */
    private String getCampo(Scanner sc) {
	System.out.println("\n¿Por qué campo quiere buscar?");
	System.out.println("n. Nombre");
	System.out.println("m. Maná");
	System.out.println("v. Vida");
	System.out.println("a. Ataque");
	System.out.println("d. Defensa");
	System.out.println("h. Habilidades");
	System.out.print("Seleccione una opción: ");
        return sc.next();
    }

    /* Regresa los resultados de la búsqueda. */
    private Lista<Avatar> getResultados(String c, Scanner sc) {
        System.out.println();
        switch (c) {
        case "n": return bdd.buscaRegistros(CampoAvatar.NOMBRE,
                                            getValorNombre(sc));
        case "m": return bdd.buscaRegistros(CampoAvatar.MANA,
                                            getValorMana(sc));
        case "v": return bdd.buscaRegistros(CampoAvatar.VIDA,
                                            getValorVida(sc));
	case "a": return bdd.buscaRegistros(CampoAvatar.ATAQUE,
					    getValorAtaque(sc));
	case "d": return bdd.buscaRegistros(CampoAvatar.DEFENSA,
					    getValorDefensa(sc));
	case "h": return bdd.buscaRegistros(CampoAvatar.HABILIDADES,
					    getValorHabilidades(sc));
        default:
            String m = String.format("El campo '%s' es inválido.", c);
            throw new ExcepcionOpcionInvalida(m);
        }
    }

    /* Regresa el valor a buscar para el nombre. */
    private String getValorNombre(Scanner sc) {
        System.out.printf("El nombre debe contener: ");
        return sc.next();
    }

    /* Regresa el valor a buscar para el nivel de mana. */
    private Integer getValorMana(Scanner sc) {
        System.out.printf("El nivel de maná debe ser mayor o igual a: ");
        return Integer.valueOf(sc.nextInt());
    }

    /* Regresa el valor a buscar para el nivel de vida. */
    private Integer getValorVida(Scanner sc) {
        System.out.printf("El nivel de vida debe ser mayor o igual a: ");
        return Integer.valueOf(sc.nextInt());
    }

    /* Regresa el valor a buscar para el nivel de ataque. */
    private Double getValorAtaque(Scanner sc) {
        System.out.printf("El nivel de ataque debe ser mayor o igual a: ");
        return Double.valueOf(sc.nextDouble());
    }

    /* Regresa el valor a buscar para el nivel de defensa. */
    private Double getValorDefensa(Scanner sc) {
        System.out.printf("El nivel de defensa debe ser mayor o igual a: ");
        return Double.valueOf(sc.nextDouble());
    }
    
    /* Regresa el valor a buscar para las habilidades. */
    private String getValorHabilidades(Scanner sc) {
        System.out.printf("Las habilidades deben contener: ");
        return sc.next();
    }
}
