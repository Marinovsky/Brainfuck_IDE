/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import java.awt.Toolkit;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import logic.Pila;
import static ui.Interfaz.estilo1;
import static ui.Interfaz.estilo2;
import static ui.Interfaz.estilo3;
import static ui.Interfaz.estilo4;
import static ui.Interfaz.estilo5;
import static ui.Interfaz.estilo6;
import static ui.Interfaz.estilo7;

/**
 *
 * @author kjcar
 */
public class Archivo {
    
    public Archivo left, right;
    public int key;
    public int height;
    public Pila<Temporal> deshacer = new Pila<>();
    public Pila<Temporal> rehacer = new Pila<>();
    public Temporal temp = new Temporal();
    public StyledDocument doc = new JTextPane().getStyledDocument();
    public String nombreArchivo, rutaDirectorio;
    
    public Archivo(){
        left = null;
        right = null;
        key = 0;
        height = 0;
    }
    
    public Archivo(int n){
        left = null;
        right = null;
        key = n;
        height = 0;
    }
    
    public void name(String nombre){
        nombreArchivo = nombre;
    }
    
    public void subMenuDeshacer(){
        /*Clasificacion
            1::escritura
            2::suprimir-escritura
            3::suprimir
            4::borrar(backspace)
            */
        if(!deshacer.empty()){
            temp = deshacer.pop();
            rehacer.push(temp);
            switch (temp.verClasificacion()) {
                case 1:
                case 2:
                    ui.Interfaz.CuadroProgramacion.setCaretPosition(temp.verCursor());
                    eliminarTexto(temp.verCursor(),temp.longitudDato());
                    if (temp.verClasificacion()==2)
                        subMenuDeshacer();
                    break;
                case 3:
                    for(int i=0; i<temp.longitudDato(); i++)
                        imprimirColor(temp.verDato().substring(i,i+1), temp.verCursor()+i);
                    ui.Interfaz.CuadroProgramacion.setCaretPosition(temp.verCursor());
                    break;
                case 4:
                    imprimirColor(temp.verDato(), temp.verCursor()-1);
                    ui.Interfaz.CuadroProgramacion.setCaretPosition(temp.verCursor());
                    break;
            }
        }else Toolkit.getDefaultToolkit().beep();
    }
    
    public void subMenuRehacer(){
        /*Clasificacion
            1::escritura
            2::suprimir-escritura
            3::suprimir
            4::borrar(backspace)
            */
        if(!rehacer.empty()){
            temp = rehacer.pop();
            deshacer.push(temp);
            switch (temp.verClasificacion()) {
                case 1:
                case 2:
                    for(int i=0; i<temp.longitudDato(); i++)
                        imprimirColor(temp.verDato().substring(i,i+1), temp.verCursor()+i);
                    ui.Interfaz.CuadroProgramacion.setCaretPosition(temp.verCursor()+temp.longitudDato());
                    break;
                case 3:
                    ui.Interfaz.CuadroProgramacion.setCaretPosition(temp.verCursor());
                    eliminarTexto(temp.verCursor(), temp.longitudDato());
                    if(!rehacer.empty() && rehacer.peek().verClasificacion()==2)
                        subMenuRehacer();
                    break;
                case 4:
                    ui.Interfaz.CuadroProgramacion.setCaretPosition(temp.verCursor());
                    eliminarTexto(temp.verCursor()-1, temp.longitudDato());
                    break;
            }
        }else Toolkit.getDefaultToolkit().beep();
    }  
    
    public void eliminarTexto(int comienzo, int longitud){
        try{
            doc.remove(comienzo, longitud);
        }catch(BadLocationException b){}
    }
    
    public void imprimirColor(String dato, int ubicacion){
        try {
            switch (dato) {
                case "<":
                case ">":
                    doc.insertString(ubicacion, dato, estilo1);
                    break;
                case ".":
                case ",":
                    doc.insertString(ubicacion, dato, estilo2);
                    break;
                case "+":
                case "-":
                    doc.insertString(ubicacion, dato, estilo3);
                    break;
                case "[":
                case "]":
                    doc.insertString(ubicacion, dato, estilo4);
                    break;
                case ";":
                case ":":
                    doc.insertString(ubicacion, dato, estilo5);
                    break;
                case "#":
                case "$":
                    doc.insertString(ubicacion, dato, estilo6);
                    break;
                default:
                    doc.insertString(ubicacion, dato, estilo7);
                    break;
            }
        }catch (BadLocationException b){}
    }
}