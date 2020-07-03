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
public class Cola{
    private CNodo front, rear;
    int count;
    public Cola(){
        front=rear=null;
        count=0;
    }
    public void add(int item){
        CNodo pnew = new CNodo(item);
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
    public int peek(){
        return front.getData();
    }
    public boolean isEmpty(){
        return count==0;
    }
    
}
