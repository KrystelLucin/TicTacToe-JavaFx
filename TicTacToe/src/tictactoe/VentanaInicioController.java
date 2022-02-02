package tictactoe;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 */
public class VentanaInicioController implements Initializable {
    
    @FXML
    private VBox vBox;
    @FXML
    private ComboBox<ModoJuego> cbModo;
    @FXML
    private Button btCargar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbModo.getItems().addAll(ModoJuego.HH,ModoJuego.HC,ModoJuego.CC);
    }    
    
    @FXML
    public void mostrarModo(ActionEvent event){
        vBox.setVisible(true);
    }
    
    @FXML
    public void continuar(ActionEvent event){
        if(cbModo.getValue()!=null){
            TicTacToeApp.partida = new TicTacToeGame(cbModo.getValue());
            TicTacToeApp.setScene(new VentanaSeleccion());
            
        } else {
            Label lbError = new Label("Ingrese un modo de juego");
            lbError.setTextFill(Color.RED);
            vBox.getChildren().add(lbError);
        }
    }
    
    @FXML
    public void cargarPartida(ActionEvent event){
        try {
            FileInputStream fis = new FileInputStream(TicTacToeApp.archivo);
            ObjectInputStream ois;
            while(fis.available()>0){
                ois = new ObjectInputStream(fis);
                TicTacToeApp.partida = (TicTacToeGame) ois.readObject();
                empezar();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //Ventana por si no carga la partida
        }
        
    }   
    
    private void empezar(){
        Button btGuardar = new Button("Guardar Partida");
            
        btGuardar.setOnMouseClicked(event -> {
            TicTacToeApp.partida.guardarPartida();
        });
            
        VBox vBoxB = new VBox();
        VBox vBoxL = new VBox();
        VBox vBoxR = new VBox();
                
        setVBox(vBoxL, vBoxR);
        vBoxB.getChildren().addAll(btGuardar);
        vBoxB.setAlignment(Pos.BASELINE_RIGHT);        
        vBoxB.setPadding(new Insets(10));
            
        BorderPane bp = new BorderPane();
        bp.setCenter(TicTacToeApp.partida.getTablero());
        bp.setLeft(vBoxL);
        bp.setRight(vBoxR);
        bp.setBottom(vBoxB);
        TicTacToeApp.setScene(bp);
            
        TicTacToeApp.partida.startGame();
    }
    
    private void setVBox(VBox vBoxL, VBox vBoxR){
        Label lbL = new Label();
        Label lbR = new Label();
        Label lb1 = new Label();
        Label lb2 = new Label();
        
        switch (TicTacToeApp.partida.getModo()) {
            case HH:
                lbL.setText("Jugador 1");
                lbR.setText("Jugador 2");
                break;
            case HC:
                lbL.setText("Jugador");
                lbR.setText("Computador");
                break;
            default:
                lbL.setText("Computador 1");
                lbR.setText("Computador 2");
                break;
        }
        
        switch(TicTacToeApp.partida.getJugador1()){
            case X:
                lb1.setText("X");
                lb2.setText("O");
                break;
            case O:
                lb1.setText("O");
                lb2.setText("X");
                break;
        }
        
        lbL.setFont(Font.font(40));
        lbR.setFont(Font.font(40));
        lb1.setFont(Font.font(72));
        lb2.setFont(Font.font(72));
        
        vBoxL.getChildren().addAll(lbL,lb1);
        vBoxR.getChildren().addAll(lbR,lb2);
        
        vBoxL.setAlignment(Pos.CENTER);
        vBoxR.setAlignment(Pos.CENTER);
        
        vBoxL.setPadding(new Insets(10));
        vBoxR.setPadding(new Insets(10));
    }
    
}
