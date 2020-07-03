/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author kjcar
 */
public class CNodo {
    private int data;
    private CNodo next;
        
    public CNodo(int data){
        this.data=data;
        next=null;
    }
    public int getData(){
        return data;
    }
    public CNodo getNext(){
        return next;
    }
    public void setData(int data){
        this.data=data;
    }
    public void setNext(CNodo next){
        this.next=next;
    }
}
