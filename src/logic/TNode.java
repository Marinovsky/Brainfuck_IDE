
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import data.Archivo;

/**
 *
 * @author kjcar
 */
public class TNode{
    private Archivo archivo;
    private TNode right, left;
    private int key,height;
    
    public TNode(){
    }
    public TNode(int key){
        this.archivo = new Archivo();
        this.key=key;
        right=left=null;
        height=0;
    }
    public Archivo getArchivo(){
        return archivo;
    }
    public void setArchivo(Archivo archivo){
        this.archivo=archivo;
    }
    public int getKey(){
        return key;
    }
    public void setKey(int key){
        this.key = key;
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public TNode getLeft(){
        return left;
    }
    public TNode setLeft(TNode left){
        this.left = left;
        return this;
    }
    public TNode getRight(){
        return right;
    }
    public TNode setRight(TNode right){
        this.right = right;
        return this;
    }   
}