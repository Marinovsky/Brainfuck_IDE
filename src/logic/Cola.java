/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author MAU
 */
public class Cola<T>{
    private Nodo<T> front, rear;
    int count;
    public Cola(){
        front=null;
        rear=null;
        count=0;
    }
    public void add(T item){
        Nodo<T> pnew = new Nodo<>(item);
        if(front==null){
            front=pnew;
            rear=pnew;
        }else{
            rear.setNext(pnew);
            rear=pnew;
        }
        count++;
    }
    public void pop(){
        if(front==null){
            System.out.println("The queue is empty");
        }else{
            front=front.getNext();
            count--;
        }
    }
    public T peek(){
        return front.getData();
    }
    public boolean isEmpty(){
        return count==0;
    }
    
}
