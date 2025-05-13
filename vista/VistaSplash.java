package vista;

import controlador.Controlador;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Paths;
/**
 * Autores:Daniel Moñino,Luis Marcano e Ivan Rubio
 * Licencia GPL V3.0
 * */
public class VistaSplash {
    private Controlador controlador;
    private static final String VIDEO_PATH = Paths.get("resources/video.mp4").toUri().toString();

    public VistaSplash(Controlador controlador) {
        // Guarda la referencia al controlador para manejar la navegación
        this.controlador = controlador;
    }

    public void mostrar(Stage ventana) {
        // Crea y configura el MediaPlayer para reproducir el vídeo en bucle con audio
        Media media = new Media(VIDEO_PATH);
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE); // Repetir vídeo indefinidamente
        player.setVolume(1.0);                      // Volumen al máximo
        player.play();                              // Inicia la reproducción

        // Crea el MediaView que mostrará el vídeo como fondo
        MediaView mediaView = new MediaView(player);
        mediaView.setPreserveRatio(true);          // Mantiene la proporción original del vídeo
        // Hace que el vídeo se ajuste automáticamente al tamaño de la ventana
        mediaView.fitWidthProperty().bind(ventana.widthProperty());
        mediaView.fitHeightProperty().bind(ventana.heightProperty());

        // Prepara la etiqueta de texto que se mostrará sobre el vídeo
        Label label = new Label("¡Bienvenido a Guillem de Frutem!");
        label.setFont(Font.font("Arial Black", 28));
        label.setTextFill(Color.BLACK);

        // Usa StackPane para superponer el texto sobre el vídeo de fondo
        StackPane root = new StackPane(mediaView, label);
        StackPane.setAlignment(label, Pos.TOP_CENTER);           // Alinea el texto arriba
        StackPane.setMargin(label, new Insets(20, 0, 0, 0));    // Margen superior de 20px

        // Crea y muestra la escena con el contenido preparado
        Scene escena = new Scene(root, 600, 400);
        ventana.setScene(escena);
        ventana.setTitle("Pantalla Splash");
        ventana.show();

        // Configura una pausa de 9 segundos para pasar automáticamente a la siguiente vista
        PauseTransition espera = new PauseTransition(Duration.seconds(9));
        espera.setOnFinished(e -> {
            player.stop();                     // Detiene el vídeo antes de cambiar de vista
            controlador.mostrarInicio();      // Llama al controlador para mostrar la vista de inicio
        });
        espera.play();                       // Inicia la transición de espera

        // Permite al usuario hacer clic en cualquier parte para saltar el splash
        escena.setOnMouseClicked(e -> {
            player.stop();
            controlador.mostrarInicio();      // Navega inmediatamente a la vista de inicio
        });
    }
}
