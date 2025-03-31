import controlador.Controlador;
import modelo.Configuracion;

public class App {
    public static void main(String[] args) {
        Configuracion.inicializar();
        Controlador controlador = new Controlador("escenarios/escenario1.txt");
        controlador.mostrarEscenario();
    }
}