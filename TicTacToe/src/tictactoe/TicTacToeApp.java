package tictactoe;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class TicTacToeApp extends Application{    
    
    private static Stage stage;
    private static Scene scene;
    public static TicTacToeGame partida;
    public static File archivo = new File("partida.dat");
    
    @Override
    public void init(){}
    
    @Override
    public void start(Stage s) throws Exception {
        scene = new Scene(loadFXML("ventanaInicio"));
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tres en Raya");
        stage.centerOnScreen();
        stage.show();
    }

    static void setScene(BorderPane bp){
        scene = new Scene(bp);
        stage.setScene(scene);
        stage.centerOnScreen();
    }
    
    static void setScene(String fxml){
        try {
            scene = new Scene(loadFXML(fxml));
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException ex) {
            System.out.println("No hay archivo");
        }
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApp.class.getResource("/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    
}
