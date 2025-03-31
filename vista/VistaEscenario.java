VistaEscenario.java

package vista;

import modelo.Escenario;

public class VistaEscenario {
    public void mostrar(Escenario escenario) {
        System.out.println("Laberinto (" + escenario.getAncho() + "x" + escenario.getAlto() + "):");
        escenario.mostrarEscenario();
    }
}