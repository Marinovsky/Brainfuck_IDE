/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Objects;
import javax.swing.text.Style;

/**
 *
 * @author MAU
 */
public class Hash_Table {
    public Pila<Hash_Node> arr[];
    public static int size;
    public Hash_Table(int size){
        arr=new Pila[size];
        this.size=size;
        for(int i=0; i<size;i++){
            Pila<Hash_Node> temp=new Pila();
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
    public void Add(Character item, Style c){
        Hash_Node temp =new Hash_Node(item, c);
        Pila<Hash_Node> L=arr[Hash_code(item)];
        Nodo<Hash_Node> cab=L.head();
        boolean isIn=true;
        if(cab==null){
            L.push(temp);
        }else{
            while(cab!=null){
                if(cab.getData().letra==item){
                    isIn=false;
                    break;
                }
                cab=cab.getNext();
            }
            if(isIn==true){
                L.push(temp);
            }
        }
    }
    public Style Find(Character item){
        Pila<Hash_Node> L=arr[Hash_code(item)];
        Nodo<Hash_Node> cab=L.head();
        while(cab!=null){
            if(cab.getData().letra==item){
                break;
            }
            cab=cab.getNext();
        }
        
        return cab.getData().color;

    }
}
