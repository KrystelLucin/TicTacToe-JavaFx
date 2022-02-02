package tictactoe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tablero.Jugador;

public class VentanaSeleccion extends BorderPane{
    
    private final VBox vBoxL;
    private final VBox vBoxR;
    private final VBox vBoxC;
    private final VBox vBoxB;
    
    private final Label lb1;
    private final Label lb2;
    
    private ComboBox<Jugador> cbTurno;
    
    public VentanaSeleccion(){
        vBoxL = new VBox();
        vBoxR = new VBox();
        vBoxC = new VBox();
        vBoxB = new VBox();
        lb1 = new Label("X");
        lb2 = new Label("O");
        setSides();
        setMiddle();
        setDown();
    }
    
    private void setSides(){
        Label lbL = new Label();
        Label lbR = new Label();
        
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
        
        this.setLeft(vBoxL);
        this.setRight(vBoxR);
    }

    private void setMiddle(){
        Button btCambiar = new Button("Cambiar") ;
        Label lbVS = new Label("VS");
        
        btCambiar.setOnMouseClicked(event ->{
            cambiar();
        });
        
        lbVS.setFont(Font.font(40));
        vBoxC.getChildren().addAll(btCambiar,lbVS);
        vBoxC.setAlignment(Pos.CENTER);
        vBoxC.setPadding(new Insets(10));
        
        this.setCenter(vBoxC);
    }
    
    private void setDown(){
        Label lbTurno = new Label("Quien empieza?");
        Button btEmpezar = new Button("Empezar");
        cbTurno = new ComboBox<>();
        
        cbTurno.getItems().addAll(Jugador.X,Jugador.O);
        
        btEmpezar.setOnMouseClicked(event ->{
            empezar();
        });
        
        vBoxB.getChildren().addAll(lbTurno,cbTurno,btEmpezar);
        vBoxB.setAlignment(Pos.CENTER);        
        vBoxB.setPadding(new Insets(10));
        this.setBottom(vBoxB);
    }
    
    private void cambiar(){
        switch(lb1.getText()){
            case "X":
                lb1.setText("O");
                lb2.setText("X");
                break;
            case "O":
                lb1.setText("X");
                lb2.setText("O");
                break;
        }
    }
    
    private Jugador getJugador1(){
        switch(lb1.getText()){
            case "X":
                return Jugador.X;
            case "O":
                return Jugador.O;
            default:
                return null;
        }
    }
    
    private void empezar(){
        if(cbTurno.getValue()==null){
            Label lbError = new Label("Ingrese un modo de juego");
            lbError.setTextFill(Color.RED);
            vBoxB.getChildren().add(lbError);
        } else {
            TicTacToeApp.partida.setPrimerTurno(cbTurno.getValue());            
            TicTacToeApp.partida.setJugador1(getJugador1());
            
            Button btGuardar = new Button("Guardar Partida");
            
            btGuardar.setOnMouseClicked(event -> {
                TicTacToeApp.partida.guardarPartida();
            });
            
            VBox vBox = new VBox();
            vBox.getChildren().addAll(btGuardar);
            vBox.setAlignment(Pos.BASELINE_RIGHT);        
            vBox.setPadding(new Insets(10));
            
            BorderPane bp = new BorderPane();
            bp.setCenter(TicTacToeApp.partida.getTablero());
            bp.setLeft(vBoxL);
            bp.setRight(vBoxR);
            bp.setBottom(vBox);
            TicTacToeApp.setScene(bp);
            
            TicTacToeApp.partida.startGame();
        }
    }
    
}
