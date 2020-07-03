/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import ui.Interfaz;

/**
 *
 * @author mau
 */
public class Central {
    public static Tree listaArchivos;
    public static void main(String[] args){
        listaArchivos = new Tree();
        Interfaz interfaz = new Interfaz();
    }
}