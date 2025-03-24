import java.io.File;

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

    }
}