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
        System.out.println("> : "+Hash_code('>'));
        System.out.println("< : "+Hash_code('<'));
        System.out.println("+ : "+Hash_code('+'));
        System.out.println("- : "+Hash_code('-'));
        System.out.println(". : "+Hash_code('.'));
        System.out.println(", : "+Hash_code(','));
        System.out.println("[ : "+Hash_code('['));
        System.out.println("] : "+Hash_code(']'));
        System.out.println("; : "+Hash_code(';'));
        System.out.println(": : "+Hash_code(':'));
        System.out.println("& : "+Hash_code('&'));
        System.out.println("$ : "+Hash_code('$'));
        System.out.println("# : "+Hash_code('#'));
        */
    }
}