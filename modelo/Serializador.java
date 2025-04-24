package modelo;

import java.io.*;
import java.nio.file.*;

public class Serializador {
    private static final String CARPETA = "jugadores";

    static {
        new File(CARPETA).mkdir();
    }

    public static void guardarJugador(Jugador jugador) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(CARPETA + "/" + jugador.getNombre() + ".dat"))) {
            out.writeObject(jugador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Jugador cargarJugador(String nombre) {
        File archivo = new File(CARPETA + "/" + nombre + ".dat");
        if (!archivo.exists()) return null;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Jugador) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}