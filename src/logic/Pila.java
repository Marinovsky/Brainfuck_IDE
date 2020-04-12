/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author mau
 * @param <T>
 */
public class Pila<T>{
    private Nodo<T> top;
    
    public Pila(){
        top=null;
    }
    public void push(T item){
        Nodo<T> pnew = new Nodo<>(item);
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
    public T peek(){
        return top.getData();
    }
    public void reset(){
        top=null;
    }
    public void print(){
        Nodo<T> q=top;
        System.out.print("List : ");
        while(q!=null){
            System.out.print(q.getData() + ", ");
            q=q.getNext();
        }
        System.out.println("");
    }   
}