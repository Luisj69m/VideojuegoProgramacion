package vista;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia GPL V3.0
 */
public class VentanaInstrucciones {

    /**
     * Muestra una ventana modal con las instrucciones básicas del juego.
     */
    public void mostrar() {
        // Crea un nuevo Stage que bloquea la interacción con la ventana principal
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle("Instrucciones");

        // Contenedor con un Label que muestra las instrucciones en varias líneas
        VBox root = new VBox(
            new Label(
                "Instrucciones del juego:\n" +
                "- Mueve al jugador\n" +
                "- Gana puntos\n" +
                "- ¡Diviértete!"
            )
        );

        // Crea la escena con tamaño fijo y la asigna al Stage
        Scene escena = new Scene(root, 300, 150);
        ventana.setScene(escena);

        // Muestra la ventana y espera a que se cierre antes de continuar
        ventana.showAndWait();
    }
}
