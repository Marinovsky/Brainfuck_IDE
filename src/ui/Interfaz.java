/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
/**
 *
 * @author kjcar
 */
public final class Interfaz implements ActionListener{
    JFrame Ventana;
    JPanel PanelProgramacion, PanelEntrada, PanelSalida;
    JLabel etiqueta;
    JMenuBar BarraMenus;
    JMenu MenuArchivo, MenuCorrer, MenuEditar;
    JMenuItem subMenuNuevo, subMenuAbrir, subMenuGuardar, retroceder, avanzar;
    JTextField archivo;
    JButton salir, ingresar;
    JTextPane CuadroProgramacion;
    JTextArea CuadroEntrada, CuadroSalida;
    //JScrollPane scrollPane;
    Dimension resolucion = Toolkit.getDefaultToolkit().getScreenSize();
    Color color1 = new Color(255,43,43);    //rojo
    Color color2 = new Color(120,0,0);      //rojo oscuro
    Color color3 = new Color(189,195,199);  //gris
    Color color4 = new Color(98, 99, 100);  //gris oscuro
    Color color5 = new Color(247, 175, 0);  //mostaza
    Color color6 = new Color(68, 48, 0);    //mostaza oscuro
    Font fuente1 = new Font("Console", Font.PLAIN, Py(40));
    Pila<String> undo = new Pila<String>();
    Pila<String> redo = new Pila<String>();
    
    //ventana
    void crearVentana(){
        Ventana = new JFrame("Brainfuck IDE");
        Ventana.setMinimumSize(new Dimension(Px(1440), Py(810)));
        Ventana.setResizable(true);
        Ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        crearBarraMenus();
        Ventana.setJMenuBar(BarraMenus);
        
    }
    void crearBarraMenus(){
        BarraMenus= new JMenuBar();
        MenuArchivo=new JMenu("Archivo");
        BarraMenus.add(MenuArchivo);
        MenuCorrer=new JMenu("Correr");
        BarraMenus.add(MenuCorrer);
        
        MenuEditar=new JMenu("Editar");
        MenuEditar.addActionListener(this);
        BarraMenus.add(MenuEditar);
        
        BarraMenus.setBackground(Color.WHITE);
        crearMenuArchivo();
        crearMenuEditar();
    }
    void crearMenuArchivo(){
        subMenuNuevo = new JMenuItem("Nuevo");
        subMenuNuevo.addActionListener(this);
        MenuArchivo.add(subMenuNuevo);
        
        subMenuAbrir = new JMenuItem("Abrir");
        subMenuAbrir.addActionListener(this);
        MenuArchivo.add(subMenuAbrir);
        
        subMenuGuardar = new JMenuItem("Guardar");
        subMenuGuardar.addActionListener(this);
        MenuArchivo.add(subMenuGuardar);
    }
    
    void crearMenuEditar(){
        retroceder = new JMenuItem("Retroceder");
        retroceder.addActionListener(this);
        MenuEditar.add(retroceder);
        
        avanzar = new JMenuItem("Avanzar");
        avanzar.addActionListener(this);
        MenuEditar.add(avanzar);
    }
    
    //Paneles de la ventana
    void CrearPanelProgramacion (int x, int y, int ancho, int alto){
        PanelProgramacion = new JPanel();
        PanelProgramacion.setLayout(null);
        PanelProgramacion.setSize(ancho, alto);
        PanelProgramacion.setLocation(x, y);
        
        CuadroProgramacion = new JTextPane();
        StyledDocument doc = CuadroProgramacion.getStyledDocument();
        Style style = CuadroProgramacion.addStyle("", null);
        CuadroProgramacion.setBounds(0, Py(35), Px(1880), Py(575));
        CuadroProgramacion.setFont(fuente1);
        CuadroProgramacion.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroProgramacion.setBackground(color3);
        CuadroProgramacion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                switch (c) {
                    case '<':
                        StyleConstants.setForeground(style, Color.black);
                        try {
                            doc.insertString(doc.getLength(), "<",style); 
                            doc.remove(doc.getLength()-1, 1);
                            undo.push(String.valueOf(c));
                        }
                        catch (BadLocationException b){}
                        break;
                    case '>':
                        StyleConstants.setForeground(style, Color.black);
                        try { doc.insertString(doc.getLength(), ">",style);
                              doc.remove(doc.getLength()-1, 1);
                              undo.push(String.valueOf(c));
                        }
                        catch (BadLocationException b){}
                        break;
                    case '.':
                        StyleConstants.setForeground(style, Color.black);
                        try { doc.insertString(doc.getLength(), ".",style); doc.remove(doc.getLength()-1, 1);undo.push(String.valueOf(c));}
                        catch (BadLocationException b){}
                        break;
                    case '+':
                        StyleConstants.setForeground(style, Color.black);
                        try { doc.insertString(doc.getLength(), "+",style); doc.remove(doc.getLength()-1, 1);undo.push(String.valueOf(c));}
                        catch (BadLocationException b){}
                        break;
                    case '-':
                        StyleConstants.setForeground(style, Color.black);
                        try { doc.insertString(doc.getLength(), "-",style); doc.remove(doc.getLength()-1, 1);undo.push(String.valueOf(c));}
                        catch (BadLocationException b){}
                        break;
                    case '[':
                        StyleConstants.setForeground(style, Color.black);
                        try { doc.insertString(doc.getLength(), "[",style); doc.remove(doc.getLength()-1, 1);undo.push(String.valueOf(c));}
                        catch (BadLocationException b){}
                        break;
                    case ']':
                        StyleConstants.setForeground(style, Color.black);
                        try { doc.insertString(doc.getLength(), "]",style); doc.remove(doc.getLength()-1, 1);undo.push(String.valueOf(c));}
                        catch (BadLocationException b){}
                        break;
                    case ';':
                        StyleConstants.setForeground(style, Color.black);
                        try { doc.insertString(doc.getLength(), ";",style); doc.remove(doc.getLength()-1, 1);undo.push(String.valueOf(c));}
                        catch (BadLocationException b){}
                        break;
                    case ':':
                        StyleConstants.setForeground(style, Color.black);
                        try { doc.insertString(doc.getLength(), ":",style); doc.remove(doc.getLength()-1, 1);undo.push(String.valueOf(c));}
                        catch (BadLocationException b){}
                        break;
                    default:
                        e.consume();
                }
            }
        });
        PanelProgramacion.add(CuadroProgramacion);
        
        etiqueta = new JLabel("Nuevo");
        etiqueta.setBounds(Px(10), 0, Px(150), Py(35));
        PanelProgramacion.add(etiqueta);
    }
    void CrearPanelEntrada (int x, int y, int ancho, int alto){
        PanelEntrada = new JPanel();
        PanelEntrada.setLayout(null);
        PanelEntrada.setSize(ancho, alto);
        PanelEntrada.setLocation(x, y);
        
        CuadroEntrada = new JTextArea();
        CuadroEntrada.setBounds(0, Py(35), Px(930), Py(305));
        CuadroEntrada.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroEntrada.setBackground(color3);
        PanelEntrada.add(CuadroEntrada);
        
        etiqueta = new JLabel("Entrada");
        etiqueta.setBounds(Px(10), 0, Px(150), Py(35));
        PanelEntrada.add(etiqueta);
    }
    void CrearPanelSalida (int x, int y, int ancho, int alto){
        PanelSalida = new JPanel();
        PanelSalida.setLayout(null);
        PanelSalida.setSize(ancho, alto);
        PanelSalida.setLocation(x, y);
        
        CuadroSalida = new JTextArea();
        CuadroSalida.setEditable(false);
        CuadroSalida.setBounds(0, Py(35), Px(930), Py(305));
        CuadroSalida.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroSalida.setBackground(color3);
        PanelSalida.add(CuadroSalida);
        
        etiqueta = new JLabel("Salida");
        etiqueta.setBounds(Px(10), 0, Px(150), Py(35));
        PanelSalida.add(etiqueta);
    }
    
    //funciones
    void subMenuNuevo(){
        Ventana.getContentPane().setLayout(null);
        CrearPanelProgramacion(Px(20), Py(20), Px(1880), Py(610));
        Ventana.getContentPane().add(PanelProgramacion);
        CrearPanelEntrada(Px(20), Py(650), Px(930), Py(340));
        Ventana.getContentPane().add(PanelEntrada);
        CrearPanelSalida(Px(970), Py(650), Px(930), Py(340));
        Ventana.getContentPane().add(PanelSalida);
        Ventana.repaint();
    }
    void subMenuAbrir(){
        FileDialog fd= new FileDialog(Ventana, "Abrir", FileDialog.LOAD);
        fd.setVisible(true);
    }
    void Retro(){
        StyledDocument doc = CuadroProgramacion.getStyledDocument();
        Style style = CuadroProgramacion.addStyle("", null);
        CuadroProgramacion.setBounds(0, Py(35), Px(1880), Py(575));
        CuadroProgramacion.setFont(fuente1);
        CuadroProgramacion.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroProgramacion.setBackground(color3);
        redo.push(undo.pop());
        try{
            doc.remove(doc.getLength()-1, 1);
        }catch(BadLocationException b){};
    }
    void Adelante(){
        StyledDocument doc = CuadroProgramacion.getStyledDocument();
        Style style = CuadroProgramacion.addStyle("", null);
        CuadroProgramacion.setBounds(0, Py(35), Px(1880), Py(575));
        CuadroProgramacion.setFont(fuente1);
        CuadroProgramacion.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroProgramacion.setBackground(color3);
        try{
            doc.insertString(doc.getLength(), redo.pop(), style);
        }catch(BadLocationException b){};
    }
    int Px(int pixeles){
        return resolucion.width*pixeles/1920;
    }
    int Py(int pixeles){
        return resolucion.height*pixeles/1080;
    }
    
    //Constructor
    public Interfaz(){
        crearVentana();
        Ventana.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "Nuevo": 
                subMenuNuevo();
                break;
            case "Abrir": 
                subMenuAbrir();
                break;
            case "Retroceder":
                Retro();
                break;
            case "Avanzar":
                Adelante();
                break;
        }
    }
}