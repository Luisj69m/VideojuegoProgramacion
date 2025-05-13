package modelo;

import java.io.*;
import java.nio.file.*;

/**
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia GPL V3.0
 */
public class Serializador {
    // Nombre de la carpeta donde se almacenan los archivos de jugador
    private static final String CARPETA = "jugadores";

    // Bloque estático que se ejecuta al cargar la clase, asegurando que la carpeta existe
    static {
        new File(CARPETA).mkdir();
    }

    /**
     * Serializa y guarda el objeto Jugador en un archivo binario (.dat).
     * @param jugador instancia a guardar, su nombre define el nombre del archivo.
     */
    public static void guardarJugador(Jugador jugador) {
        // Ruta completa del archivo: jugadores/<nombre>.dat
        String ruta = CARPETA + "/" + jugador.getNombre() + ".dat";
        try (ObjectOutputStream out = new ObjectOutputStream(
                 new FileOutputStream(ruta))) {
            out.writeObject(jugador);
        } catch (IOException e) {
            e.printStackTrace(); // Manejo básico de errores de E/S
        }
    }

    /**
     * Carga un objeto Jugador desde su archivo binario.
     * @param nombre nombre del jugador (sin extensión) para localizar el archivo.
     * @return instancia de Jugador si existe; null si no existe o hay error.
     */
    public static Jugador cargarJugador(String nombre) {
        // Ruta del archivo que contiene la serialización del jugador
        File archivo = new File(CARPETA + "/" + nombre + ".dat");
        if (!archivo.exists()) {
            return null; // No hay datos guardados para ese jugador
        }

        try (ObjectInputStream in = new ObjectInputStream(
                 new FileInputStream(archivo))) {
            // Deserializa el objeto y lo convierte a Jugador
            return (Jugador) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejo de errores de deserialización
            return null;
        }
    }
}
