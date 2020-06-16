/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author kjcar
 */
public class Temporal {
    private int clasificacion;
    private String dato;
    private int cursor;
    
    public void establecerClasificacion (int clasificacion){
        this.clasificacion = clasificacion;
    }
    
    public int verClasificacion (){
        return clasificacion;
    }
    
    public void establecerDato (String dato){
        this.dato = dato;
    }
    
    public String verDato (){
        return dato;
    }
    
    public int longitudDato (){
        return dato.length();
    }
    
    public void establecerCursor (int cursor){
        this.cursor = cursor;
    }
    
    public int verCursor (){
        return cursor;
    }
}
