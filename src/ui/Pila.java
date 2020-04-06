/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author mau
 */
public class Pila<T>{    
    private Nodo<T> top;
    
    public Pila(){
        top=null;
    }
    public void push(T item){
        Nodo<T> pnew = new Nodo<T>(item);
        pnew.setNext(top);
        top=pnew;
    }
    public T pop(){
        T temp = top.getData();
        top=top.getNext();
        return temp;
    }
    public boolean empty(){
        return top==null;
    }
    
    
}
