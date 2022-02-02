package tictactoe;

public enum ModoJuego {
    HH("Humano vs Humano"), HC("Humano vs Computador"), 
    CC("Computador vs Computador");
    private final String string;
    
    private ModoJuego(String string){
        this.string = string;
    }
    @Override
    public String toString(){
        return string;
    }
}
