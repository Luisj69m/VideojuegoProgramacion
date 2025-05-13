package controlador;

import java.io.File;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import modelo.Jugador;
import modelo.Serializador;
import vista.*;

/**
 * Controlador es el punto central de navegación y lógica de la aplicación.
 * <p>
 * Gestiona la transición entre vistas (Splash, Inicio, Selección de Escenario,
 * Juego y Fin), controla la música de fondo (intro y juego), y maneja los datos
 * del jugador (carga, creación y puntuación).
 * </p>
 * 
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia: GPL V3.0
 */
public class Controlador {
    /** Stage principal donde se muestran todas las vistas. */
    private Stage ventana;

    /** Jugador actual con datos de nombre, email y puntuación. */
    private Jugador jugador;

    /** Vista de juego para reiniciar o configurar partida. */
    private VistaJuego vistaJuego;

    /** Reproductor de música introductoria. */
    private MediaPlayer introPlayer;

    /** Reproductor de música durante la partida. */
    private MediaPlayer gamePlayer;

    /**
     * Constructor: inicializa el controlador con el Stage principal.
     *
     * @param ventana Stage donde se mostrará la interfaz
     */
    public Controlador(Stage ventana) {
        this.ventana = ventana;
    }

    /**
     * Muestra la pantalla de splash al iniciar la aplicación.
     */
    public void mostrarSplash() {
        startIntroMusic();  // inicia música de introducción
        new VistaSplash(this).mostrar(ventana);
    }

    /**
     * Muestra la pantalla de inicio, donde el jugador ingresa nombre/email.
     */
    public void mostrarInicio() {
        new VistaInicio(this).mostrar(ventana);
    }

    /**
     * Configura y muestra la vista de selección de escenario tras iniciar juego.
     */
    public void mostrarJuego() {
        stopIntroMusic();   // detiene música de intro
        startGameMusic();   // inicia música de juego
        vistaJuego = new VistaJuego(this, jugador);
        new VistaSeleccionEscenario(this, vistaJuego).mostrar(ventana);
    }

    /**
     * Muestra la pantalla final con las puntuaciones Top 10.
     */
    public void mostrarFin() {
        stopGameMusic();    // detiene música de juego
        startIntroMusic();  // regresa a música de intro
        new VistaFin(this, jugador).mostrar(ventana);
    }

    /**
     * Establece el jugador actual, cargando de persistencia o creando uno nuevo.
     *
     * @param nombre nombre del jugador
     * @param email email del jugador (solo si es nuevo)
     */
    public void establecerJugador(String nombre, String email) {
        jugador = Serializador.cargarJugador(nombre);
        if (jugador == null) {
            // Si no existe, crear y guardar
            jugador = new Jugador(nombre, email);
            Serializador.guardarJugador(jugador);
        }
    }

    /**
     * @return el jugador actual en la sesión
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Penaliza al jugador restándole puntos (uso opcional).
     */
    public void penalizarPorColision() {
        if (jugador != null) {
            jugador.restarPuntos(500);
            System.out.println("¡Pum! Te has chocado con una pared. -500 puntos.");
        }
    }

    /**
     * Muestra la puntuación final por consola (uso de depuración).
     */
    public void mostrarPuntuacionFinal() {
        if (jugador != null) {
            System.out.println("Puntuación final: " + jugador.getPuntuacion());
        }
    }

    /**
     * Inicia el juego cargando un archivo de escenario específico.
     *
     * @param ventana Stage principal para la vista de juego
     * @param rutaArchivo ruta del archivo de texto del escenario
     */
    public void iniciarJuegoConArchivo(Stage ventana, String rutaArchivo) {
        stopIntroMusic();
        startGameMusic();
        vistaJuego = new VistaJuego(this, jugador);
        vistaJuego.mostrarConArchivo(ventana, rutaArchivo);
    }

    /**
     * Inicia la música de introducción en bucle con volumen moderado.
     */
    public void startIntroMusic() {
        if (gamePlayer != null) {
            gamePlayer.stop();
        }
        Media intro = new Media(
            new File("resources/spooky.mp3").toURI().toString()
        );
        introPlayer = new MediaPlayer(intro);
        introPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        introPlayer.setVolume(0.5);
        introPlayer.play();
    }

    /**
     * Detiene la música de introducción si está reproduciéndose.
     */
    public void stopIntroMusic() {
        if (introPlayer != null) {
            introPlayer.stop();
        }
    }

    /**
     * Inicia la música de juego en bucle con volumen ajustado.
     */
    public void startGameMusic() {
        if (introPlayer != null) {
            introPlayer.stop();
        }
        Media game = new Media(
            new File("resources/musjuego.mp3").toURI().toString()
        );
        gamePlayer = new MediaPlayer(game);
        gamePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        gamePlayer.setVolume(0.6);
        gamePlayer.play();
    }

    /**
     * Detiene la música de juego si está reproduciéndose.
     */
    public void stopGameMusic() {
        if (gamePlayer != null) {
            gamePlayer.stop();
        }
    }
}
