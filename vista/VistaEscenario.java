package vista;

import modelo.Escenario;
import java.util.Scanner;

/**
 * Clase VistaEscenario.
 * Se encarga de gestionar la visualización del escenario y la interacción con el usuario.
 * @author Luis José Marcano
 * @author Dani Moñino
 * @author Ivan Rubio
 * Licencia: GPL V3.0

 */
public class VistaEscenario {

    /** Scanner para la entrada del usuario. */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Método estático para pedir una entrada del usuario.
     * 
     * @param mensaje Mensaje que se mostrará al usuario antes de la entrada.
     * @return La entrada del usuario como una cadena de texto.
     */
    public static String pedirEntrada(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    /**
     * Método para mostrar un mensaje en la consola.
     * 
     * @param mensaje Mensaje a mostrar en la consola.
     */
    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Método para mostrar el escenario en la consola.
     * 
     * @param escenario Objeto de la clase Escenario que representa el laberinto.
     */
    public void mostrar(Escenario escenario) {
        System.out.println("Laberinto (" + escenario.getAncho() + "x" + escenario.getAlto() + "):");
        escenario.mostrarEscenario();
    }