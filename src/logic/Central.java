/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import data.Archivo;
import java.util.LinkedList;
import ui.Interfaz;

/**
 *
 * @author mau
 */
public class Central {
    public static LinkedList<Archivo> listaArchivos = new LinkedList<>();
    public static void main(String[] args){
        Interfaz interfaz = new Interfaz();
    }
}
