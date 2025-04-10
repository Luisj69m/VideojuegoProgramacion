package modelo;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Escenario que representa un mapa de juego basado en un archivo de texto.
 * 
 * El archivo debe tener el siguiente formato:
 * - La primera línea indica las dimensiones del escenario en formato `ancho x alto` (Ej: `80x40`).
 * - Las siguientes líneas representan el mapa con espacios, obstáculos, etc.
 * 
 * Esta clase permite cargar y mostrar un escenario desde un archivo.
 * 
 * @author Luis José Marcano
 * @author Dani Moñino
 * @author Ivan Rubio
 */
public class Escenario {
    private int ancho;
    private int alto;
    private List<String> mapa;

    /**
     * Constructor que inicializa el escenario cargándolo desde un archivo.
     * 
     * @param rutaArchivo Ruta del archivo que contiene la configuración del escenario.
     */
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

        if (primeraLinea != null && primeraLinea.matches("\\d+x\\d+")) {
            // Es formato comprimido → usar GeneradorEscenario
            mapa = GeneradorEscenario.generarDesdeArchivo(rutaArchivo);
        } else {
            // Es formato tradicional (primera línea = dimensiones)
            if (primeraLinea == null) {
                throw new IOException("Archivo vacío");
            }

            // Leer dimensiones como antes
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
     * Muestra el escenario en la consola.
     */
    public void mostrarEscenario() {
        for (String linea : mapa) {
            System.out.println(linea);
        }
    }

    /**
     * Obtiene el ancho del escenario.
     * 
     * @return Ancho del escenario.
     */
    public int getAncho() { return ancho; }

    /**
     * Obtiene el alto del escenario.
     * 
     * @return Alto del escenario.
     */
    public int getAlto() { return alto; }

    /**
     * Obtiene la lista de líneas que representan el mapa del escenario.
     * 
     * @return Lista de líneas del mapa.
     */
    public List<String> getMapa() { return mapa; }
}
