
import controlador.Controlador;
import modelo.Configuracion;
import modelo.Jugador;

/**
 * Clase principal de la aplicación.
 * Se encarga de inicializar la configuración, crear el controlador y gestionar el jugador.
 * @author Luis José Marcano
 * @author Dani Moñino
 * @author Ivan Rubio
 * Licencia: GPL V3.0

 */
public class App {
    /**
     * Método principal que inicia la ejecución del programa.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Inicialización de la configuración (fichero de configuración y directorios)
        Configuracion.inicializar();

        // Crear el controlador con el archivo de escenario especificado
        Controlador controlador = new Controlador("escenarios/escenario1.txt");

        // Iniciar jugador (cargar o crear jugador)
        Jugador jugador = controlador.iniciarJugador();  // Se llama al método desde el controlador

        // Mostrar el escenario
        controlador.mostrarEscenario();
    }
}