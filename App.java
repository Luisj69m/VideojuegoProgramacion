import javafx.application.Application;
import javafx.stage.Stage;
import controlador.Controlador;
/**
 * Autores:Daniel Moñino,Luis Marcano e Ivan Rubio
 * Licencia GPL V3.0
 * */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crea el controlador con la ventana principal y muestra la pantalla de splash
        Controlador controlador = new Controlador(primaryStage);
        controlador.mostrarSplash();
    }

    public static void main(String[] args) {
        // Lanza la aplicación JavaFX
        launch(args);
    }
}
