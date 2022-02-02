package tablero;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tictactoe.TicTacToeApp;

public class Celda extends StackPane implements Comparable<Celda>{
    private final Text text = new Text();
    
    public Celda(){
        Rectangle border = new Rectangle(200,200);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        
        text.setFont(Font.font(72));
        
        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);
        
        setOnMouseClicked(event ->{
            if(this.isEmpty())
                TicTacToeApp.partida.hacerMovimientoHumano(this);
        });
    }
    
    public void setJugador(Jugador s){
        text.setText(s.toString());
    }
    
    public Jugador getJugador(){
        switch(text.getText()){
            case "X":
                return Jugador.X;
            case "O":
                return Jugador.O;
            default:
                return null;
        }
    }
    
    public boolean isEmpty(){
        return getJugador()==null;
    }

    @Override
    public int compareTo(Celda c) {
        if(this.getJugador()==c.getJugador()){
            return 0;
        }else{
            return -1;
        }
    }
    
    
    
}
