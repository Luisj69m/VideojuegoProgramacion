package vista;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import controlador.Controlador;

/**
 * VistaSeleccionEscenario se encarga de mostrar la pantalla de selección de escenario.
 * 
 * Carga los archivos de escenarios (.txt) desde la carpeta 'escenarios' y genera
 * un botón por cada archivo disponible. Aplica estilos visuales y maneja eventos
 * para iniciar el juego con el escenario seleccionado.
 * 
 * 
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia: GPL V3.0
 */
public class VistaSeleccionEscenario {
    /** Controlador principal de la aplicación. */
    private Controlador controlador;
    
    /** Vista del juego para pasar al iniciar la partida. */
    private VistaJuego vistaJuego;

    /**
     * Construye la vista de selección de escenario.
     *
     * @param controlador instancia del controlador de la aplicación
     * @param vistaJuego instancia de la vista de juego para usar posteriormente
     */
    public VistaSeleccionEscenario(Controlador controlador, VistaJuego vistaJuego) {
        this.controlador = controlador;
        this.vistaJuego = vistaJuego;
    }

    /**
     * Muestra la ventana de selección de escenario en el Stage proporcionado.
     *
     * @param ventana Stage principal donde se montará la escena
     */
    public void mostrar(Stage ventana) {
        // Inicia (o reinicia) la música de introducción
        controlador.startIntroMusic();

        // ----- Configuración del fondo -----
        // Carga la imagen de fondo desde recursos
        Image fondo = new Image("file:resources/escenario.png");
        BackgroundImage bgImage = new BackgroundImage(
            fondo,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        // Contenedor principal con el fondo aplicado
        BorderPane root = new BorderPane();
        root.setBackground(new Background(bgImage));

        // ----- Contenedor de botones -----
        HBox contenedor = new HBox(20);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setPadding(new Insets(40));
        // Fondo semitransparente con esquinas redondeadas
        contenedor.setBackground(new Background(new BackgroundFill(
            Color.color(0, 0, 0, 0.6), new CornerRadii(20), Insets.EMPTY
        )));

        // ----- Carga dinámica de archivos de escenarios -----
        File carpeta = new File("escenarios");
        // Filtra solo archivos .txt
        File[] archivos = carpeta.listFiles((d, name) -> name.endsWith(".txt"));
        if (archivos != null) {
            for (File archivo : archivos) {
                // Nombre del escenario sin extensión
                String nombre = archivo.getName().replace(".txt", "");
                // Crea el botón correspondiente
                Button btn = new Button(nombre);
                btn.setPrefSize(220, 80);
                btn.setFont(javafx.scene.text.Font.font("Arial Black", 18));
                btn.setTextFill(Color.web("#ff5555"));

                // Estilo de fondo en degradado horizontal
                LinearGradient gradient = new LinearGradient(
                    0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#550000")),
                    new Stop(1, Color.web("#110000"))
                );
                btn.setBackground(new Background(new BackgroundFill(
                    gradient,
                    new CornerRadii(10), Insets.EMPTY
                )));

                // Efectos de sombra para realce visual
                DropShadow sombraBase = new DropShadow(10, Color.BLACK);
                DropShadow sombraHover = new DropShadow(20, Color.RED);
                btn.setEffect(sombraBase);
                // Cambia el efecto al pasar el ratón
                btn.setOnMouseEntered(e -> btn.setEffect(sombraHover));
                btn.setOnMouseExited(e -> btn.setEffect(sombraBase));

                // Acción al hacer clic: inicia el juego con el archivo seleccionado
                btn.setOnAction(e -> 
                    controlador.iniciarJuegoConArchivo(ventana, archivo.getPath())
                );

                // Añade el botón al contenedor
                contenedor.getChildren().add(btn);
            }
        }

        // Centra el contenedor de botones en la ventana
        root.setCenter(contenedor);

        // ----- Configuración de la escena y muestra -----
        Scene escena = new Scene(root, 800, 600);
        ventana.setScene(escena);
        ventana.setTitle("Seleccionar Escenario");
        ventana.show();
    }
}
