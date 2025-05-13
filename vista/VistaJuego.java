package vista;

import controlador.Controlador;
import modelo.Jugador;
import modelo.GestorBD;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * VistaJuego gestiona la interfaz de juego del laberinto.
 * <p>
 * Carga un mapa desde un archivo de texto, dibuja el laberinto usando imágenes
 * y permite mover al jugador con teclas. Actualiza la puntuación y guarda
 * el resultado si alcanza la salida.
 * </p>
 * 
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia: GPL V3.0
 */
public class VistaJuego {
  
    private final Controlador controlador;
    private final Jugador jugador;
    private int puntuacion;
    private Label lblPuntuacion;
    private Label lblMensaje;
    private int[][] laberinto;
    private int jugadorFila;
    private int jugadorColumna;
    private ImageView[][] celdas;

    // Imágenes para diferentes tipos de celda
    private final Image jugadorImg = new Image("file:resources/jugador.png");
    private final Image paredImg   = new Image("file:resources/pared.jpg");
    private final Image caminoImg  = new Image("file:resources/camino.jpg");
    private final Image salidaImg  = new Image("file:resources/salida.jpg");

    /**
     * Construye la vista de juego.
     *
     * @param controlador instancia del controlador general
     * @param jugador instancia del jugador con datos de usuario
     */
    public VistaJuego(Controlador controlador, Jugador jugador) {
        this.controlador = controlador;
        this.jugador = jugador;
    }

    /**
     * Inicializa el laberinto desde un archivo y lanza la vista.
     * Coloca al jugador en la posición inicial (1,1) y asigna la puntuación base.
     *
     * @param ventana Stage principal para mostrar la escena
     * @param nombreArchivo ruta del archivo de texto del laberinto
     */
    public void mostrarConArchivo(Stage ventana, String nombreArchivo) {
        this.laberinto     = cargarLaberintoDesdeArchivo(nombreArchivo);
        if (this.laberinto == null) return; // salir si hay error
        this.jugadorFila    = 1;
        this.jugadorColumna = 1;
        this.puntuacion     = 100;
        mostrar(ventana);
    }

    /**
     * Lee el laberinto desde un archivo de texto con formato:
     * 
     * filasxcolumnas
     * conteoCarasTipo,conteoTipo...
     * ...
     * 
     * Soporta especificar la posición inicial con el tipo 'I'.
     *
     * @param ruta ruta al archivo de texto
     * @return matriz de enteros con el mapa, o null si hay error
     */
    private int[][] cargarLaberintoDesdeArchivo(String ruta) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(ruta));
            // Primera línea: dimensiones 'filasxcolumnas'
            String[] dim = lineas.get(0).split("x");
            int filas = Integer.parseInt(dim[0]);
            int cols  = Integer.parseInt(dim[1]);
            int[][] m = new int[filas][cols];
            // Iterar cada línea de mapa
            for (int i = 0; i < filas; i++) {
                String[] partes = lineas.get(i + 1).split(",");
                int col = 0;
                for (String p : partes) {
                    p = p.trim();
                    if (p.isEmpty()) continue;
                    // Último caracter define tipo: O=pared, F=salida, I=inicial, 0=camino
                    int count = Integer.parseInt(p.substring(0, p.length() - 1));
                    char tipo = p.charAt(p.length() - 1);
                    int valor;
                    switch (tipo) {
                        case 'O': valor = 1; break;
                        case 'F': valor = 2; break;
                        default:  valor = 0; break;
                    }
                    // Rellenar columnas con el mismo tipo
                    for (int j = 0; j < count && col < cols; j++) {
                        m[i][col] = valor;
                        // Marcar posición inicial si 'I'
                        if (tipo == 'I') {
                            jugadorFila = i;
                            jugadorColumna = col;
                        }
                        col++;
                    }
                }
            }
            return m;
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Monta la escena JavaFX: fondo, HUD, grid del laberinto y control de teclado.
     *
     * @param ventana Stage donde mostrar la escena
     */
    public void mostrar(Stage ventana) {
        // Configurar fondo con gradiente vertical cálido
        BorderPane root = new BorderPane();
        Stop[] stops = { new Stop(0, Color.web("#ff7f50")), new Stop(1, Color.web("#ffd1a4")) };
        root.setBackground(new Background(new BackgroundFill(
            new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops),
            CornerRadii.EMPTY, Insets.EMPTY
        )));

        // ----- HUD superior -----
        HBox hud = new HBox(20);
        hud.setAlignment(Pos.CENTER);
        hud.setPadding(new Insets(10));
        // Fondo oscuro degradado
        hud.setBackground(new Background(new BackgroundFill(
            new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#550000")), new Stop(1, Color.web("#110000"))
            ), new CornerRadii(5), Insets.EMPTY
        )));
        // Label de puntuación
        lblPuntuacion = new Label("Puntuación: " + puntuacion);
        lblPuntuacion.setFont(Font.font("Arial Black", 24));
        lblPuntuacion.setTextFill(Color.web("#ff4444"));
        lblPuntuacion.setEffect(new DropShadow(8, Color.BLACK));
        // Label de mensajes (crash, etc.)
        lblMensaje = new Label("");
        lblMensaje.setFont(Font.font("Arial Black", 20));
        lblMensaje.setTextFill(Color.web("#ff2222"));
        lblMensaje.setEffect(new DropShadow(8, Color.BLACK));
        hud.getChildren().addAll(lblPuntuacion, lblMensaje);
        root.setTop(hud);

        // ----- Grid del laberinto -----
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        celdas = new ImageView[laberinto.length][laberinto[0].length];
        // Crear ImageViews
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                celdas[i][j] = new ImageView();
                grid.add(celdas[i][j], j, i);
            }
        }
        // Ajuste dinámico de tamaño al cambiar ventana
        root.widthProperty().addListener((obs, oldV, newV) -> {
            double cellWidth = newV.doubleValue() / laberinto[0].length;
            for (ImageView[] row : celdas) for (ImageView iv : row) iv.setFitWidth(cellWidth);
        });
        root.heightProperty().addListener((obs, oldV, newV) -> {
            double cellHeight = (newV.doubleValue() - hud.getHeight()) / laberinto.length;
            for (ImageView[] row : celdas) for (ImageView iv : row) iv.setFitHeight(cellHeight);
        });
        // Inicializar imágenes según valor del laberinto
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                int v = laberinto[i][j];
                if (v == 1) celdas[i][j].setImage(paredImg);
                else if (v == 2) celdas[i][j].setImage(salidaImg);
                else celdas[i][j].setImage(caminoImg);
            }
        }
        // Colocar jugador en posición inicial
        celdas[jugadorFila][jugadorColumna].setImage(jugadorImg);
        root.setCenter(grid);

        // Mostrar escena
        Scene scene = new Scene(root, 800, 600);
        ventana.setScene(scene);
        ventana.setTitle("Guillem The Frutem");
        ventana.show();

        // Capturar teclas para movimiento
        scene.setOnKeyPressed(e -> {
            int nextRow = jugadorFila;
            int nextCol = jugadorColumna;
            if (e.getCode() == KeyCode.W) nextRow--;
            if (e.getCode() == KeyCode.S) nextRow++;
            if (e.getCode() == KeyCode.A) nextCol--;
            if (e.getCode() == KeyCode.D) nextCol++;
            moverJugador(nextRow, nextCol);
        });
    }

    /**
     * Maneja la lógica de movimiento del jugador:
     * - Choque con pared: resta puntos y muestra mensaje.
     * - Llegada a la salida: guarda en BD y muestra vista final.
     * - Movimiento válido: actualiza posición y limpia mensaje.
     *
     * @param nr nueva fila objetivo
     * @param nc nueva columna objetivo
     */
    private void moverJugador(int nr, int nc) {
        // Comprobar límites
        if (nr < 0 || nr >= laberinto.length || nc < 0 || nc >= laberinto[0].length) return;
        int valor = laberinto[nr][nc];
        // Pared
        if (valor == 1) {
            puntuacion = Math.max(0, puntuacion - 8);
            lblMensaje.setText("CRASH! -8");
            lblPuntuacion.setText("Puntuación: " + puntuacion);
            return;
        }
        // Salida alcanzada
        if (valor == 2) {
            jugador.setPuntuacion(puntuacion);
            new GestorBD().guardarSiTop10(jugador);
            new VistaFin(controlador, jugador)
                .mostrar((Stage) celdas[jugadorFila][jugadorColumna]
                    .getScene().getWindow());
            return;
        }
        // Movimiento a camino
        lblMensaje.setText("");
        celdas[jugadorFila][jugadorColumna].setImage(caminoImg);
        jugadorFila = nr;
        jugadorColumna = nc;
        celdas[nr][nc].setImage(jugadorImg);
    }
}
