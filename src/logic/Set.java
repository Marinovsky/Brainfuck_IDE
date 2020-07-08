/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import logic.Pila;

/**
 *
 * @author MAU
 */
public class Set{
    public Pila<Character> arr[];
    public static int size;
    public Set(int size){
        arr=new Pila[size];
        this.size=size;
        for(int i=0; i<size;i++){
            Pila<Character> temp=new Pila();
            arr[i]=temp;
        }
    }
    public static int Hash_code(Character s){
        int p=1000007;
        int a=2;int b=253;
        int x=Integer.valueOf(s);
        int hash=((a*x+b)%p)%size;
        return hash;
    }
    public void Add(Character item){
        Pila<Character> L=arr[Hash_code(item)];
        if(L.search(item)==false){
            L.push(item);
        }
    }
    public boolean Find(Character item){
        Pila<Character> L=arr[Hash_code(item)];
        return L.search(item);
    }    
}
