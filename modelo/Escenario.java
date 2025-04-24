package modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Escenario que representa un mapa de juego basado en un archivo de texto.
 * El archivo debe tener el siguiente formato:
 * - La primera línea indica las dimensiones del escenario en formato `ancho x alto` (Ej: `80x40`).
 * - Las siguientes líneas representan el mapa con espacios, obstáculos, etc.
 */
public class Escenario {
    private int ancho;
    private int alto;
    private List<String> mapa;
    private static final String RUTA_OBSTACULO = "/resources/pared.jpg";  // Ruta de la imagen del obstáculo
    private static final String RUTA_VACIO = "/resources/camino.jpg";  // Ruta de la imagen para los espacios vacíos

    public Escenario(String rutaArchivo) {
        this.mapa = new ArrayList<>();
        cargarEscenario(rutaArchivo);
    }

    /**
     * Carga el escenario desde el archivo especificado.
     *
     * @param rutaArchivo Ruta del archivo del escenario.
     */
    private void cargarEscenario(String rutaArchivo) {
        mapa.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String primeraLinea = br.readLine();

            // Parseo del archivo
            if (primeraLinea != null && primeraLinea.matches("\\d+x\\d+")) {
                String[] dimensiones = primeraLinea.split("x");
                this.ancho = Integer.parseInt(dimensiones[0]);
                this.alto = Integer.parseInt(dimensiones[1]);

                String linea;
                while ((linea = br.readLine()) != null) {
                    mapa.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el escenario: " + e.getMessage());
        }
    }

    /**
     * Genera el escenario en un GridPane de JavaFX.
     * 
     * @return El GridPane con el escenario representado.
     */
    public GridPane generarEscenario() {
        GridPane grid = new GridPane();
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                String celda = mapa.get(i).substring(j, j + 1);
                ImageView imagen = new ImageView();

                if (celda.equals("X")) {
                    // Obstáculo
                    imagen.setImage(new Image(RUTA_OBSTACULO));
                } else if (celda.equals("O")) {
                    // Espacio vacío
                    imagen.setImage(new Image(RUTA_VACIO));
                }

                // Ajustamos el tamaño de la imagen
                imagen.setFitWidth(50);  // Ajustar el tamaño de la imagen según sea necesario
                imagen.setFitHeight(50);

                // Colocamos la imagen en el GridPane
                grid.add(imagen, j, i);
            }
        }
        return grid;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public List<String> getMapa() {
        return mapa;
    }
}
