package controlador;

import modelo.Escenario;
import vista.VistaEscenario;

public class Controlador {
    private Escenario escenario;
    private VistaEscenario vista;

    public Controlador(String rutaEscenario) {
        this.escenario = new Escenario(rutaEscenario);
        this.vista = new VistaEscenario();
    }

    public void mostrarEscenario() {
        vista.mostrar(escenario);
    }
}
