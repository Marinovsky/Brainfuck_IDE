/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import data.Archivo;
import ui.Interfaz;

/**
 *
 * @author mau
 */
public class Central {
    public static Tree<Archivo> listaArchivos = new Tree();
    public static void main(String[] args){
        Interfaz interfaz = new Interfaz();
        
        /*
        for(int i=0; i<10000; i++) listaArchivos.insert(new TNode(i));
        long sT2=System.nanoTime();
        listaArchivos.get(0);
        long fT2=System.nanoTime();
        System.out.println("Tiempo en ns get : "+(fT2-sT2));
*/
    }
}