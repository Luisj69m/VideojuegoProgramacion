import java.io.File;
import java.io.IOException;

public class VideojuegoProgramacion {
      public static void main(String[] args) {
        String configFilePath = "configuracion.cfg";
        String[] directorios = {"escenarios", "jugadores", "partidas"};

        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            try {
                
                if (configFile.createNewFile()) {
                    System.out.println("Fichero de configuración creado: " + configFilePath);
                } else {
                    System.out.println("No se pudo crear el fichero de configuración.");
                }
            for (String dir : directorios) {
                    File directory = new File(dir);
                    if (!directory.exists()) {
                        if (directory.mkdir()) {
                            System.out.println("Directorio creado: " + dir);
                        } else {
                            System.out.println("No se pudo crear el directorio: " + dir);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al crear el fichero de configuración: " + e.getMessage());
            }
        } else {
            System.out.println("El fichero de configuración ya existe. No se realizan cambios.");
        }

    }
}