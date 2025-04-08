package controlador;

import modelo.Escenario;
import modelo.Jugador;

import java.io.*;
import java.util.*;

public class Controlador {
    private Escenario escenario;
    private int jugadorX = 1;
    private int jugadorY = 1;
    private Jugador jugador;

    public Controlador(String rutaEscenario) {
        this.escenario = new Escenario(rutaEscenario);
    }

    public Jugador iniciarJugador() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce tu nombre de jugador: ");
        String nombre = scanner.nextLine().trim();

        File archivo = new File("jugadores/" + nombre + ".dat");
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                jugador = (Jugador) ois.readObject();
                System.out.println("Bienvenido de nuevo, " + jugador.getNombre() + "!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al cargar el jugador. Se creará uno nuevo.");
            }
        }

        if (jugador == null) {
            System.out.print("Introduce tu email: ");
            String email = scanner.nextLine().trim();
            jugador = new Jugador(nombre, email);

            try {
                new File("jugadores").mkdirs();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
                oos.writeObject(jugador);
                oos.close();
                System.out.println("Jugador guardado exitosamente.");
            } catch (IOException e) {
                System.out.println("No se pudo guardar el jugador.");
            }
        }

        return jugador;
    }

    public void mostrarEscenario() {
        for (String linea : escenario.getMapa()) {
            System.out.println(linea);
        }
    }

    public void iniciarJuegoInteractivo() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            limpiarPantalla();
            mostrarEscenarioConJugador();

            System.out.print("WASD> ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()) continue;

            char tecla = input.charAt(0);
            int nuevaX = jugadorX;
            int nuevaY = jugadorY;

            switch (tecla) {
                case 'W' -> nuevaY--;
                case 'S' -> nuevaY++;
                case 'A' -> nuevaX--;
                case 'D' -> nuevaX++;
                case 'Q' -> {
                    System.out.println("Saliendo del juego...");
                    return;
                }
                default -> {
                    continue;
                }
            }

            if (puedeMoverA(nuevaX, nuevaY)) {
                jugadorX = nuevaX;
                jugadorY = nuevaY;
            } else {
                // Mostrar mensaje "ouch!" sobre el jugador
                escenario.getMapa().set(jugadorY - 1, insertarTexto(escenario.getMapa().get(jugadorY - 1), jugadorX, "ouch!"));
            }
        }
    }

    private void mostrarEscenarioConJugador() {
        List<String> mapa = new ArrayList<>(escenario.getMapa());

        for (int y = 0; y < mapa.size(); y++) {
            String linea = mapa.get(y);
            if (y == jugadorY) {
                linea = insertarTexto(linea, jugadorX, "O");
            }
            System.out.println(linea);
        }
    }

    private String insertarTexto(String linea, int x, String texto) {
        StringBuilder sb = new StringBuilder(linea);
        for (int i = 0; i < texto.length(); i++) {
            int pos = x + i;
            if (pos < sb.length()) {
                sb.setCharAt(pos, texto.charAt(i));
            }
        }
        return sb.toString();
    }

    private boolean puedeMoverA(int x, int y) {
        List<String> mapa = escenario.getMapa();

        if (y < 0 || y >= mapa.size()) return false;

        String linea = mapa.get(y);
        if (x < 0 || x >= linea.length()) return false;

        return linea.charAt(x) != 'x';
    }

    private void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
