
import controlador.Controlador;
import modelo.Configuracion;
import modelo.Jugador;

public class App {
    public static void main(String[] args) {
        Configuracion.inicializar();

        Controlador controlador = new Controlador("escenarios/escenario1.txt");

        Jugador jugador = controlador.iniciarJugador();

        controlador.mostrarEscenario();

        controlador.iniciarJuegoInteractivo();
    }
}