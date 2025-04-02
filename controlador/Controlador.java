package controlador;

import modelo.Escenario;
import modelo.Jugador;
import vista.VistaEscenario;

/**
 * Controlador que gestiona la interacción entre el modelo (Escenario, Jugador) y la vista (VistaEscenario).
 * Se encarga de mostrar el escenario y gestionar la carga o creación de jugadores.
 * 
 * @author Luis José Marcano
 * @author Dani Moñino
 * @author Ivan Rubio
 * Licencia: GPL V3.0
 */
    public class Controlador {
    private Escenario escenario;
    private VistaEscenario vista;

    /**
     * Constructor del controlador. Carga un escenario desde la ruta especificada.
     * 
     * @param rutaEscenario Ruta del archivo que contiene la configuración del escenario.
     */
    public Controlador(String rutaEscenario) {
        this.escenario = new Escenario(rutaEscenario);
        this.vista = new VistaEscenario();
    }

    /**
     * Muestra el escenario en la vista.
     */
    public void mostrarEscenario() {
        vista.mostrar(escenario);
    }

    /**
     * Método para gestionar la inicialización o carga del jugador.
     * 
     * - Pregunta al usuario su nombre de usuario.
     * - Si existe un archivo con ese nombre, carga los datos del jugador.
     * - Si no existe, solicita el email, crea un nuevo jugador y lo guarda.
     * 
     * @return Jugador cargado o creado.
     */
    public static Jugador iniciarJugador() {
        // Pedir al usuario el nombre
        String nombre = VistaEscenario.pedirEntrada("Introduce tu nombre de usuario: ");
        Jugador jugador = Jugador.cargar(nombre);

        if (jugador != null) {
            VistaEscenario.mostrarMensaje("¡Bienvenido de nuevo, " + jugador.getNombre() + "!");
            VistaEscenario.mostrarMensaje("Email registrado: " + jugador.getEmail());
        } else {
            // Si el jugador no existe, se le pide el correo y se guarda el jugador
            String email = VistaEscenario.pedirEntrada("No se encontró el usuario. Introduce tu email: ");
            jugador = new Jugador(nombre, email);
            jugador.guardar();
            VistaEscenario.mostrarMensaje("Nuevo usuario registrado con éxito.");
        }
    return jugador;
    }
}