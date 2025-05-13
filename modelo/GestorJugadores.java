package modelo;

import java.io.*;

/**
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia GPL V3.0
 */
public class GestorJugadores {
    private static final String ARCHIVO = "jugador.dat";  // Nombre del archivo de serialización

    /**
     * Serializa y guarda el objeto Jugador en el archivo fijo.
     * @param jugador Instancia de Jugador a guardar.
     */
    public static void guardarJugador(Jugador jugador) {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            out.writeObject(jugador);  // Serializa el objeto Jugador
        } catch (IOException e) {
            e.printStackTrace();      // Manejo básico de errores de E/S
        }
    }

    /**
     * Carga y deserializa el objeto Jugador desde el archivo.
     * @return Instancia de Jugador si existe; null en caso de error o si no existe.
     */
    public static Jugador cargarJugador() {
        try (ObjectInputStream in =
                 new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (Jugador) in.readObject();  // Deserializa y devuelve el objeto
        } catch (Exception e) {
            return null;  // Retorna null si no hay datos o falla la carga
        }
    }
}
