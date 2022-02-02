package tablero;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 * Tablero 3x3
 */
public class Tablero extends Parent{
    private  List<Celda> celdas = new ArrayList<>();

    public Tablero(Node root){
        this.getChildren().add(root);
    }
    
    public Tablero(){
        Pane root = new Pane();
        root.setPrefSize(600, 600);
        
        for(int i = 0; i < 3; i++){
            for(int j = 0; j<3; j++){
                Celda celda = new Celda();
                celda.setTranslateX(j*200);
                celda.setTranslateY(i*200);
                
                root.getChildren().add(celda);
                celdas.add(celda);
            }
        }
        this.getChildren().add(root);
    }
    

    
    /**
     * Retorna true si el tablero esta en un estado inicial
     * En caso contrario retorna false
     * @return 
     */
    public boolean isEmpty(){
        return celdas.stream().noneMatch(c -> (!c.isEmpty()));
    }
    
    /**
     * Retorna true si el tablero esta lleno
     * En caso contrario retorna false
     * @return 
     */
    public boolean isFull(){
        return celdas.stream().noneMatch(c -> (c.isEmpty()));
    }
    
    
    private int hallarP(Jugador jugador){
        int util = 0;
        for(int i = 0; i<7; i+=3){
            boolean libre = true;
            for(int j = 0; j<3; j++){
                if(!(celdas.get(i+j).isEmpty()||(celdas.get(i+j).getJugador().equals(jugador)))){
                    libre = false;
                }
            }
            if(libre){
                util++;
            }   
        }
        for(int j = 0; j<3; j++){
            boolean libre = true;
            for(int i = 0; i<7; i+=3){
                if(!(celdas.get(i+j).isEmpty()||(celdas.get(i+j).getJugador().equals(jugador)))){
                    libre = false;
                }
            }
            if(libre){
                util++;
            }   
        }
        boolean d1 = true;
        boolean d2 = true;
        for(int i=0; i<9; i+=4){
            if(!(celdas.get(i).isEmpty()||(celdas.get(i).getJugador().equals(jugador)))){
                d1=false;
            }
        }
        for(int i=2; i<7;i+=2){
            if(!(celdas.get(i).isEmpty()||(celdas.get(i).getJugador().equals(jugador)))){
                d1=false;
            }
        }
        if(d1) util++;
        if(d2) util++;
        return util;
    }
    
    public int utilidad(Jugador jugadorActual){
        int p1 = this.hallarP(jugadorActual);
        int p2 = 0;
        if(jugadorActual.equals(Jugador.X)){
            p2 = this.hallarP(Jugador.O);
        }else{
            p2=this.hallarP(Jugador.X);
        }
        return p1-p2;
    }
    
    public List<Celda> getCeldas(){
        return celdas;
    }
    public void setCeldas(List<Celda> celdas) {
        this.celdas = celdas;
    }
    
    public ArrayList<Tablero> jugadasDisponible(Jugador jugador){
        ArrayList<Tablero> jugadas = new ArrayList<>();
            for(int i=0; i < celdas.size();i++){
                if(celdas.get(i).isEmpty()){
                   Celda c = new Celda();
                   if(jugador==Jugador.O){
                       c.setJugador(Jugador.O);
                   }
                   else{
                       c.setJugador(Jugador.X);
                   }
                    List<Celda> listaceldas = new ArrayList<>();
                    int cont =0;
                    for(Celda cel: celdas){
                       if(cont==i){
                           listaceldas.add(c);
                       }else{
                           listaceldas.add(cel);
                       }
                       cont++;
                    }
                    Tablero newTablero = new Tablero();
                    newTablero.setCeldas(listaceldas);
                    jugadas.add(newTablero);
                }
            }
        return jugadas;
    }
}
