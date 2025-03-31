package modelo;

import java.io.File;
import java.io.IOException;

public class Configuracion {
    private static final String RUTA_CONFIGURACION = "configuracion.cfg";
    private static final String[] DIRECTORIOS = {"escenarios", "jugadores", "partidas"};

    public static void inicializar() {
        crearFicheroConfiguracion();
        crearDirectorios();
    }

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

    private static void crearDirectorios() {
        for (String dir : DIRECTORIOS) {
            File directory = new File(dir);
            if (!directory.exists() && directory.mkdir()) {
                System.out.println("Directorio creado: " + dir);
            }
 		}
	}
}