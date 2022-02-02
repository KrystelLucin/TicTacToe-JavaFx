/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDA;

import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class Nodo<T> {
    private ArrayList<Arbol<T>> hijos = new ArrayList();
    //private Arbol<Nodo<T>> padre=null;
    private T content;

    public Nodo(T content) {
        this.content = content;
    }
    
    
    public void agregarHijo(T content){
        Nodo nuevoHijo = new Nodo(content);
       // nuevoHijo.setPadre(this);
    }
    
    public void agregarHijos(ArrayList<Arbol<T>> hijos){
        /*for(Arbol hijo : hijos){
            hijo.getRoot().setPadre(this);
        }*/
        this.hijos.addAll(hijos);
    }
    
    public boolean isLeaf(){
        return hijos.isEmpty();
    }

    public void setHijos(ArrayList<Arbol<T>> hijos) {
        this.hijos = hijos;
    }

    /*public void setPadre(Nodo<T> padre) {
        this.padre = padre;
    }*/

    public void setContent(T content) {
        this.content = content;
    }

    public ArrayList<Arbol<T>> getHijos() {
        return hijos;
    }

   /* public Nodo<T> getPadre() {
        return padre;
    }*/

    public T getContent() {
        return content;
    }
    
    
}
