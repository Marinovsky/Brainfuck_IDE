/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import java.awt.Toolkit;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import logic.Pila;
import static ui.Interfaz.estilo7;
import logic.Set;
import static ui.Interfaz.colors;

/**
 *
 * @author kjcar
 */
public class Archivo {
    public Pila<Temporal> deshacer = new Pila<>();
    public Pila<Temporal> rehacer = new Pila<>();
    public Pila<Integer> stack= new Pila<>();
    public StyledDocument doc;
    public Document ent, sal;
    public String nombreArchivo, rutaDirectorio;
    public int[] memory = new int[256];
    public int pointer = 256/2;
    public static Set keywords;
    
    public Archivo(){
        // Ingresa los caracteres especiales de Brainfuck++
        keywords=new Set(13);
        keywords.Add('<');
        keywords.Add('>');
        keywords.Add('+');
        keywords.Add('-');
        keywords.Add('.');
        keywords.Add(',');
        keywords.Add('[');
        keywords.Add(']');
        keywords.Add(';');
        keywords.Add(':');
        keywords.Add('$');
        keywords.Add('#');
        keywords.Add('&');
        deshacer = rehacer = null;
        doc = null;
        ent = sal = null;
        nombreArchivo=rutaDirectorio=null;
    }
    public static int Hash_code(char s){
        if(s=='.'){
            return 8;
        }
        if(s=='-'){
            return 2;
        }
        if(s=='$'){
            return 12;
        }
        int p=1000007;
        int a=2;int b=253;
        int x=Integer.valueOf(s);
        int hash=((a*x+b)%p)%13;
        return hash;
    }
    
    public void clean(){
        memory = new int[256];
    }
    
    public void subMenuDeshacer(){
        /*Clasificacion
            1::escritura
            2::suprimir-escritura
            3::suprimir
            4::borrar(backspace)
            */
        if(!deshacer.empty()){
            Temporal temp = deshacer.pop();
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
            Temporal temp = rehacer.pop();
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
        try{
            // Comprueba si es un caracter especial de Brainfuck++
            if(keywords.Find(dato.charAt(0))){
                doc.insertString(ubicacion, dato, colors[Hash_code(dato.charAt(0))]);
            }else{
                doc.insertString(ubicacion, dato, estilo7);
            }
        }catch(BadLocationException b){}
        /*
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
                case "&":
                    doc.insertString(ubicacion, dato, estilo6);
                    break;
                default:
                    doc.insertString(ubicacion, dato, estilo7);
                    break;
            }
        }catch (BadLocationException b){}   
        */
    }
}