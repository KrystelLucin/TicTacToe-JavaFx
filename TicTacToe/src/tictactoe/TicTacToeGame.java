package tictactoe;

import TDA.Arbol;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tablero.Celda;
import tablero.Jugador;
import tablero.Tablero;

/**
 * Juego tres en raya
 */
public class TicTacToeGame implements Serializable{
    
    private final ModoJuego modo;
    private final Tablero tablero;
    private Jugador primerTurno;
    private Jugador jugador1;
    
    public TicTacToeGame(ModoJuego modo){
        this.modo = modo;
        tablero = new Tablero();
    }    
    
    protected void setPrimerTurno(Jugador primerTurno){
        this.primerTurno = primerTurno;
    }
    
    protected void setJugador1(Jugador jugador1){
        this.jugador1 = jugador1;
    }
    
    public Jugador getJugador1(){
        return jugador1;
    }
    
    public ModoJuego getModo(){
        return modo;
    }
    
    public void hacerMovimientoHumano(Celda celda){        
        switch(modo){
            case HH:
                celda.setJugador(turnoJugador());
                break;
            case HC:
                if(jugador1 == turnoJugador()){
                    celda.setJugador(turnoJugador());
                    hacerMovimientoComputador();
                }
                break;
        }
        if(gameOver()){
            setGameOverScreen();
        }
    }
    
    public void hacerMovimientoComputador(){
        System.out.println(obtenerMovimiento());
        tablero.getCeldas().get(obtenerMovimiento()).setJugador(turnoJugador());
       
    }
    
    public void startGame() {
        while(!gameOver()){
            switch(modo){
                case CC:
                    hacerMovimientoComputador();
                    break;
                case HC:
                    if(primerTurno != jugador1)
                        hacerMovimientoComputador();
                default:
                    return;
            }
        }
    }
    
    /**
     * Retorna true si el tablero recibido es
     * un juego ya acabado, en caso contrario retorna false
     * @return 
     */
    public boolean gameOver(){
        return getGanador() != null || tablero.isFull();
    }
    
    /**
     * Retorna el jugador de quien es el turno
     * @return 
     */
    public Jugador turnoJugador(){
        int count_x = 0;
        int count_o = 0;
        
        if (tablero.isEmpty())
            return primerTurno;
        if (gameOver())
            return primerTurno;
        
        for (Celda c: tablero.getCeldas()){
            if (c.getJugador()== Jugador.X)
                    count_x += 1;
            if (c.getJugador()== Jugador.O)
                    count_o += 1;
        }
        
        if(primerTurno == Jugador.O){
            if (count_o > count_x)
                return Jugador.X;
            else
                return Jugador.O;
        }else{
            if (count_o >= count_x)
                return Jugador.X;
            else
                return Jugador.O;
        }
    }
    
    public Jugador getGanador(){
        //TODO: obtener el ganador
        List<Celda> celdas =tablero.getCeldas();
        //Recorrio horizontal
        for(int i =0; i<7;i=i+3){
            if(celdas.get(i).compareTo(celdas.get(i+1))==0 && 
                    celdas.get(i).compareTo(celdas.get(i+2))==0){
                return celdas.get(i).getJugador();
            }
        }
        //Recorrido vertical
        for(int i =0; i<3;i=i+1){
            if(celdas.get(i).compareTo(celdas.get(i+3))==0 && 
                    celdas.get(i).compareTo(celdas.get(i+6))==0){
                return celdas.get(i).getJugador();
            }
        }
        //Diagonal
        if((celdas.get(0).compareTo(celdas.get(4))==0 && celdas.get(0).compareTo(celdas.get(8))==0)
                ||(celdas.get(2).compareTo(celdas.get(4))==0 && celdas.get(4).compareTo(celdas.get(6))==0) ){
            return celdas.get(4).getJugador();
        }
        return null;
    }
    
    public void guardarPartida(){
        //TODO: Funcionalidad Opcional Guardar y reabrir partidas a medio jugar
        File archivo = new File("partida.dat");
        try (FileOutputStream fos = new FileOutputStream(archivo, false); 
                ObjectOutputStream oss = new ObjectOutputStream(fos)) {
                oss.writeObject(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Tablero getTablero(){
        return tablero;
    }
    
    public Jugador jugadorOpuesto(Jugador jugador){
        if(jugador.equals(Jugador.X)){
            return Jugador.O;
        }else{
            return Jugador.X;
        }
    }
    
    public int minimax(Arbol<Tablero> tableros, int profundidad, boolean maximizar){
        
        if(profundidad == 0 || this.gameOver()){
            return tableros.getRoot().getContent().utilidad(this.turnoJugador());
        }
        ArrayList<Arbol<Tablero>> hijos = tableros.getRoot().getHijos();
        if(maximizar){
            int maxUtil = Integer.MIN_VALUE;
            for(int i = 0; i<hijos.size();i++){
                int util = this.minimax(hijos.get(i), profundidad-1, false);
                maxUtil= Math.max(maxUtil, util);
            }
            return maxUtil;
        }else{
            int minUtil= Integer.MAX_VALUE;
            for(int i = 0; i<hijos.size();i++){
                int util = this.minimax(hijos.get(i), profundidad-1, true);
                minUtil= Math.min(minUtil, util);
            }
            return minUtil;
        }
    }
    public Tablero mejorOpcion(){
        Arbol<Tablero> tableros = hallarPosibilidades(this.tablero, this.turnoJugador()); 
        ArrayList<Arbol<Tablero>> hijos = tableros.getRoot().getHijos();
        int mejorUtil= Integer.MIN_VALUE;
        int mejorTablero=0;
        for(int i = 0; i<hijos.size();i++){
            int util = this.minimax(hijos.get(i), 2 , true);
            if(util>mejorUtil){
                mejorUtil=util;
                mejorTablero = i;
            }
        }
        return hijos.get(mejorTablero).getRoot().getContent();
    }
    public Arbol<Tablero> hallarPosibilidades(Tablero tablero, Jugador jugador){
        
        Arbol<Tablero> arbol = new Arbol(tablero);
        ArrayList<Tablero> tableros = tablero.jugadasDisponible(jugador);
        ArrayList<Arbol<Tablero>> posibles = new ArrayList();
        for(Tablero t : tableros){
            Arbol<Tablero> nuevo = new Arbol(t);
            posibles.add(nuevo);
        }
        arbol.getRoot().agregarHijos(posibles);
        for(Arbol<Tablero> a : posibles){
            ArrayList<Tablero> tableros2 = a.getRoot().getContent().jugadasDisponible(this.jugadorOpuesto(jugador));
            ArrayList<Arbol<Tablero>> posibles2 = new ArrayList();
            for(Tablero t : tableros2){
            Arbol<Tablero> nuevo = new Arbol(t);
            posibles2.add(nuevo);
            }
            a.getRoot().agregarHijos(posibles2);
        }
        return arbol;
    }
    
    private void setGameOverScreen(){
        BorderPane bp = new BorderPane();
        VBox vBox = new VBox();
        Label lbGameOver = new Label("Game Over");
        Label lbGanador = new Label();
        Button btVolver = new Button("Volver a Jugar");
        
        if(getGanador() == null)
            lbGanador.setText("EMPATE");
        else if (jugador1 == getGanador()){
            switch(modo){
                case HH:
                    lbGanador.setText("Jugador 1 gana");
                    break;
                case HC:
                    lbGanador.setText("Jugador gana");
                    break;
                case CC:
                    lbGanador.setText("Computador 2 gana");
                    break;
            }
            
        }else{
            switch(modo){
                case HH:
                    lbGanador.setText("Jugador 2 gana");
                    break;
                case HC:
                    lbGanador.setText("Computador gana");
                    break;
                case CC:
                    lbGanador.setText("Computador 1 gana");
                    break;
            }
        }
        
        lbGameOver.setFont(Font.font(40));
        lbGanador.setFont(Font.font(40));
        
        btVolver.setOnAction(event ->{
            TicTacToeApp.setScene("ventanaInicio");
        });
        
        vBox.getChildren().addAll(lbGameOver,lbGanador,btVolver);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        
        bp.setCenter(vBox);
        TicTacToeApp.setScene(bp);
    }
    
    public int obtenerMovimiento(){
        Tablero tablero2 = this.mejorOpcion();
        List<Celda> celdas1 = this.tablero.getCeldas();
        List<Celda> celdas2 = tablero2.getCeldas();
        for(int i=0; i<9; i++){
            if(!(celdas1.get(i).getJugador() == celdas2.get(i).getJugador())){
                return i;
            }
        }
        return -1;
    }


}
