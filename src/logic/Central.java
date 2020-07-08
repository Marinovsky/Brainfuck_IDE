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
        /*
        long startTime=System.nanoTime();
        for(int i=0; i<10000000;i++){
            String temp=Integer.toString(i);
            switch(temp.charAt(temp.length()-1)){
                case '0':
                    break;
                case '1':
                    break;
                case '2':
                    break;
                case '3':
                    break;
                case '4':
                    break;
                case '5':
                    break;
                case '6':
                    break;
                case '7':
                    break;
                case '8':
                    break;
                case '9':
                    break;
            }
        }
        long endTime=System.nanoTime();
        long totalTime=endTime-startTime;
        System.out.println("Total time in ns: "+totalTime);
    */
    }
}