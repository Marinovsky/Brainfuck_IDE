
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
public class TNodo{
    private Archivo archivo;
    private TNodo right, left;
    private int key,height;
    
    public TNodo(int key){
        this.archivo = new Archivo();
        this.key=key;
        right=left=null;
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
    public TNodo getLeft(){
        return left;
    }
    public TNodo setLeft(TNodo left){
        this.left = left;
        return this;
    }
    public TNodo getRight(){
        return right;
    }
    public TNodo setRight(TNodo right){
        this.right = right;
        return this;
    }   
}