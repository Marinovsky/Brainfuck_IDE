
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Marinovsky
 * @param <T>
 */
public class Nodo<T>{
    private T data;
    private Nodo next;
        
    public Nodo(){
        this(null);
    }
    public Nodo(T data){
        this.data=data;
        next=null;
    }
    public T getData(){
        return data;
    }
    public Nodo getNext(){
        return next;
    }
    public void setData(T data){
        this.data=data;
    }
    public void setNext(Nodo next){
        this.next=next;
    }
    
}