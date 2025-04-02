package modelo;

import java.io.File;
import java.io.IOException;

/**
 * Clase Configuracion que gestiona la creación de archivos y directorios necesarios 
 * para la configuración del sistema.
 * 
 * Se encarga de crear:
 * - Un fichero de configuración (`configuracion.cfg`).
 * - Los directorios esenciales: `escenarios`, `jugadores` y `partidas`.
 * 
 * @author Luis José Marcano
 * @author Dani Moñino
 * @author Ivan Rubio
 */
public class Configuracion {
    private static final String RUTA_CONFIGURACION = "configuracion.cfg";
    private static final String[] DIRECTORIOS = {"escenarios", "jugadores", "partidas"};

    /**
     * Método para inicializar la configuración del sistema.
     * Crea el fichero de configuración y los directorios si no existen.
     */
    public static void inicializar() {
        crearFicheroConfiguracion();
        crearDirectorios();
    }

    /**
     * Crea el fichero de configuración si no existe.
     */
    private static void crearFicheroConfiguracion() {
        File configFile = new File(RUTA_CONFIGURACION);
        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()) {
                    System.out.println("Fichero de configuración creado: " + RUTA_CONFIGURACION);
                }
            } catch (IOException e) {
                System.out.println("Error al crear el fichero de configuración: " + e.getMessage());
            }
        } else {
            System.out.println("El fichero de configuración ya existe.");
        }
    }

    /**
     * Crea los directorios necesarios si no existen.
     */
    private static void crearDirectorios() {
        for (String dir : DIRECTORIOS) {
            File directory = new File(dir);
            if (!directory.exists() && directory.mkdir()) {
                System.out.println("Directorio creado: " + dir);
            }
        }
    }
}