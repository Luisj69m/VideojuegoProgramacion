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
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Leer la primera línea con las dimensiones del escenario
            String dimensiones = br.readLine();
            String[] partes = dimensiones.split("x");
            this.ancho = Integer.parseInt(partes[0]);
            this.alto = Integer.parseInt(partes[1]);

            // Leer el mapa línea por línea
            String linea;
            while ((linea = br.readLine()) != null) {
                mapa.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el escenario: " + e.getMessage());
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
