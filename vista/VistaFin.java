package vista;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import controlador.Controlador;
import modelo.Jugador;
import modelo.GestorBD;

/**
 * VistaFin muestra la pantalla de fin de juego con el Top 10 de puntuaciones.
 * <p>
 * Reproduce música de ambiente, muestra una lista de los mejores jugadores
 * y ofrece la opción de volver al inicio.
 * </p>
 * 
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia: GPL V3.0
 */
public class VistaFin {
    /** Controlador principal para manejar la navegación de vistas. */
    private Controlador controlador;

    /** Jugador que ha completado el juego (no se usa directamente en esta vista). */
    private Jugador jugador;

    /**
     * Constructor de la vista de fin de juego.
     *
     * @param controlador instancia del controlador de la aplicación
     * @param jugador jugador que ha finalizado la partida
     */
    public VistaFin(Controlador controlador, Jugador jugador) {
        this.controlador = controlador;
        this.jugador = jugador;
    }

    /**
     * Muestra la ventana final con música, fondo, overlay, lista Top 10 y botón de retorno.
     *
     * @param ventana Stage principal donde se montará la escena
     */
    public void mostrar(Stage ventana) {
        // ----- Música de fondo alto impacto -----
        Media introMusic = new Media(
            new File("resources/spooky.mp3").toURI().toString()
        );
        MediaPlayer introPlayer = new MediaPlayer(introMusic);
        introPlayer.setCycleCount(MediaPlayer.INDEFINITE); // reproducción en bucle
        introPlayer.setVolume(0.5);                       // volumen moderado
        introPlayer.play();                                // iniciar reproducción

        // ----- Configuración de fondo de imagen -----
        Image fondo = new Image("file:resources/fin.png");
        BackgroundImage bgImage = new BackgroundImage(
            fondo,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(
                BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true
            )
        );
        StackPane root = new StackPane();
        root.setBackground(new Background(bgImage));

        // ----- Overlay semitransparente rojizo -----
        Rectangle overlay = new Rectangle();
        overlay.widthProperty().bind(root.widthProperty());
        overlay.heightProperty().bind(root.heightProperty());
        overlay.setFill(new LinearGradient(
            0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(50, 0, 0, 0.7)),
            new Stop(1, Color.rgb(20, 0, 0, 0.7))
        ));

        // ----- Título Top 10 -----
        Label titulo = new Label("🏆 Top 10 Puntuaciones 🏆");
        titulo.setFont(Font.font("Arial Black", 30));
        titulo.setTextFill(Color.web("#ff4444"));
        titulo.setEffect(new DropShadow(10, Color.BLACK));

        // ----- Lista de puntuaciones -----
        ListView<String> lista = new ListView<>();
        lista.setPrefSize(400, 400);
        lista.setStyle(
            "-fx-control-inner-background: rgba(0,0,0,0.6);" +
            "-fx-font-family: 'Consolas';" +
            "-fx-font-size: 16px;" +
            "-fx-text-fill: #ff5555;"
        );
        lista.setEffect(new DropShadow(5, Color.web("#220000")));
        // Cargar datos Top 10 desde la base de datos
        java.util.List<Jugador> top10 = new GestorBD().obtenerTop10();
        for (int i = 0; i < top10.size(); i++) {
            Jugador j = top10.get(i);
            lista.getItems().add(
                (i + 1) + ". " + j.getNombre() + " - " + j.getPuntuacion()
            );
        }

        // ----- Botón de retorno al inicio -----
        Button volverBtn = new Button("Volver al inicio");
        volverBtn.setFont(Font.font("Arial Black", 18));
        volverBtn.setTextFill(Color.WHITE);
        volverBtn.setBackground(new Background(new BackgroundFill(
            new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#330000")),
                new Stop(1, Color.web("#000000"))
            ),
            new CornerRadii(10), new Insets(10)
        )));
        volverBtn.setEffect(new DropShadow(8, Color.BLACK));
        // Efecto de sombra al pasar el ratón
        volverBtn.setOnMouseEntered(e -> 
            volverBtn.setEffect(new DropShadow(15, Color.web("#ff0000")))
        );
        volverBtn.setOnMouseExited(e -> 
            volverBtn.setEffect(new DropShadow(8, Color.BLACK))
        );
        // Acción: mostrar vista de inicio
        volverBtn.setOnAction(e -> controlador.mostrarInicio());

        // ----- Organizar componentes en VBox -----
        VBox container = new VBox(20);
        container.getChildren().addAll(titulo, lista, volverBtn);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(40));

        // Añadir overlay y contenido sobre el fondo
        root.getChildren().addAll(overlay, container);

        // Configurar y mostrar escena
        Scene escena = new Scene(root, 800, 700);
        ventana.setScene(escena);
        ventana.setTitle("Fin del Juego");
        ventana.show();
    }
}
