package vista;

import controlador.Controlador;
import modelo.Jugador;
import modelo.Serializador;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * VistaInicio presenta la pantalla de inicio del juego.
 * <p>
 * Permite al usuario ingresar su nombre y, si es nuevo, su email para registrarse.
 * Valida los campos y establece el jugador en el controlador.
 * </p>
 * 
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia: GPL V3.0
 */
public class VistaInicio {
    /** Controlador principal de la aplicación. */
    private Controlador controlador;

    /**
     * Constructor de la vista de inicio.
     *
     * @param controlador instancia del controlador de la aplicación
     */
    public VistaInicio(Controlador controlador) {
        this.controlador = controlador;
    }

    /**
     * Muestra la interfaz de inicio en el Stage proporcionado.
     * Configura fondo, campos de texto y lógica de validación.
     *
     * @param ventana Stage principal donde se montará la escena
     */
    public void mostrar(Stage ventana) {
        // Inicia o reinicia la música de introducción
        controlador.startIntroMusic();

        // Carga la imagen de fondo desde recursos
        Image fondoImg = new Image(new File("resources/fondolobby.png").toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(
            fondoImg,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        StackPane root = new StackPane();
        root.setBackground(new Background(bgImage));

        // Contenedor vertical centrado para elementos de UI
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        // Etiqueta y campo de texto para el nombre de jugador
        Label lblNombre = new Label("Nombre:");
        lblNombre.setTextFill(Color.WHITE);
        lblNombre.setFont(Font.font("Arial Black", 14));
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ingrese su nombre");
        txtNombre.setMaxWidth(200);
        txtNombre.setStyle(
            "-fx-background-color: rgba(0,0,0,0.5);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-border-color: white;" +
            "-fx-prompt-text-fill: gray;"
        );

        // Etiqueta y campo de texto para email (solo si el usuario es nuevo)
        Label lblEmail = new Label("Email (si es nuevo):");
        lblEmail.setTextFill(Color.WHITE);
        lblEmail.setFont(Font.font("Arial Black", 14));
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("usuario@gmail.com");
        txtEmail.setMaxWidth(200);
        txtEmail.setStyle(
            "-fx-background-color: rgba(0,0,0,0.5);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-border-color: white;" +
            "-fx-prompt-text-fill: gray;"
        );

        // Botón para comenzar el juego con validación de campos
        Button btnComenzar = new Button("Comenzar Juego");
        btnComenzar.setFont(Font.font("Arial Black", 14));
        btnComenzar.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #ff5e5e, #880000);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-cursor: hand;"
        );
        btnComenzar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                mostrarAlerta("El nombre es obligatorio.");
                return;
            }
            // Intentar cargar jugador existente
            Jugador existente = Serializador.cargarJugador(nombre);
            if (existente != null) {
                // Jugador encontrado: establecer y mostrar juego
                controlador.establecerJugador(nombre, existente.getEmail());
                controlador.mostrarJuego();
            } else {
                // Jugador nuevo: validar email
                String email = txtEmail.getText().trim();
                if (email.isEmpty()) {
                    mostrarAlerta("Jugador no encontrado. Introduce el email para registrarte.");
                    return;
                }
                if (!email.endsWith("@gmail.com")) {
                    mostrarAlerta("El email debe terminar en @gmail.com.");
                    return;
                }
                controlador.establecerJugador(nombre, email);
                controlador.mostrarJuego();
            }
        });

        // Agregar elementos al layout y montar en el root
        layout.getChildren().addAll(lblNombre, txtNombre, lblEmail, txtEmail, btnComenzar);
        root.getChildren().add(layout);

        // Configurar y mostrar escena
        Scene escena = new Scene(root, 400, 300);
        ventana.setScene(escena);
        ventana.setTitle("Inicio del Juego");
        ventana.show();
    }

    /**
     * Muestra una alerta de advertencia con estilo personalizado.
     *
     * @param mensaje texto a mostrar en la alerta
     */
    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setContentText(mensaje);
        // Aplicar estilo semitransparente
        alerta.getDialogPane().setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white;");
        alerta.showAndWait();
    }
}
