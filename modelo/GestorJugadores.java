package modelo;

import java.io.*;

public class GestorJugadores {
    private static final String ARCHIVO = "jugador.dat";

    public static void guardarJugador(Jugador jugador) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            out.writeObject(jugador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Jugador cargarJugador() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (Jugador) in.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}