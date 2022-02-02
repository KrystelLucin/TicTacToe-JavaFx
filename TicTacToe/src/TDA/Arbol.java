/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDA;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author andre
 */
public class Arbol<T> {
    private Nodo<T> root;
    private Comparator cmp;

    public Arbol(T content) {
        root = new Nodo<T>(content);
    }

    public Nodo<T> getRoot() {
        return root;
    }

    public Comparator getCmp() {
        return cmp;
    }

    public void setRoot(Nodo root) {
        this.root = root;
    }

    public void setCmp(Comparator cmp) {
        this.cmp = cmp;
    }
    
    public Nodo<T> max(ArrayList<Nodo<T>> hijos){
        Nodo<T> mayor = hijos.get(0);
        for(Nodo<T> hijo :hijos){
            if(cmp.compare(mayor, hijo)>=0){
                mayor=hijo;
            }
        }
        return mayor;
    }
    
    public Nodo<T> min(ArrayList<Nodo<T>> hijos){
        Nodo<T> mayor = hijos.get(0);
        for(Nodo<T> hijo :hijos){
            if(cmp.compare(mayor, hijo)<=0){
                mayor=hijo;
            }
        }
        return mayor;
    }
    

}
