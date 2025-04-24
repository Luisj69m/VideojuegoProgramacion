import javafx.application.Application;
import javafx.stage.Stage;
import controlador.Controlador;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Controlador controlador = new Controlador(primaryStage);
        controlador.mostrarSplash();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

