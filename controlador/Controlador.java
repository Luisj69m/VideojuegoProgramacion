package controlador;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.*;
import vista.*;
import javafx.scene.layout.GridPane;  // Necesario para usar GridPane
import modelo.Escenario;  // Necesario para usar la clase Escenario




import java.io.File;
import java.util.List;

public class Controlador {
    private Stage ventana;
    private Jugador jugador;

    public Controlador(Stage ventana) {
        this.ventana = ventana;
    }

    public void mostrarSplash() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vista_splash.fxml"));
            Parent root = loader.load();

            VistaSplash vistaSplash = loader.getController();
            Scene escena = new Scene(root, 600, 400);
            ventana.setScene(escena);
            ventana.setTitle("Pantalla Splash");
            ventana.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vista_inicio.fxml"));
            VistaInicio vistaInicio = new VistaInicio(this);
            loader.setController(vistaInicio);
            Parent root = loader.load();
            Scene escena = new Scene(root, 800, 600);
            ventana.setScene(escena);
            ventana.setTitle("Inicio del Juego");
            ventana.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarJuego() {
    try {
        // Cargar el archivo de escenario
        Escenario escenario = new Escenario("recursos/escenarios/escenario1.txt"); // Asegúrate de tener el archivo en la ruta correcta
        List<String> mapa = escenario.getMapa();
        
        // Cargar la vista del juego
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vista_juego.fxml"));
        Parent root = loader.load();
        
        // Crear un GridPane para el escenario
        GridPane grid = escenario.generarEscenario();  // Genera el escenario con la lógica de dibujo

        // Añadimos el GridPane con el escenario al contenedor principal
        Parent contenedorJuego = (Parent) root;
        
        // Aquí agregamos el escenario generado
        if (contenedorJuego instanceof GridPane) {
            GridPane panelJuego = (GridPane) contenedorJuego;
            panelJuego.getChildren().add(grid);  // Agrega el escenario al panel principal
        }

        // Crear y mostrar la escena
        Scene escena = new Scene(root, 480, 520); // Ajustado al tamaño del laberinto
        ventana.setScene(escena);
        ventana.setTitle("Laberinto");
        ventana.show();
        root.requestFocus(); // Necesario para capturar teclas

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public void mostrarFin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vista_fin.fxml"));
            Parent root = loader.load();

            VistaFin vistaFin = loader.getController();

            Scene escena = new Scene(root, 800, 600);
            ventana.setScene(escena);
            ventana.setTitle("Fin del Juego");
            ventana.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void establecerJugador(String nombre, String email) {
        jugador = Serializador.cargarJugador(nombre);
        if (jugador == null) {
            jugador = new Jugador(nombre, email);
            Serializador.guardarJugador(jugador);
        }
    }

    public Jugador getJugador() {
        return jugador;
    }
}
