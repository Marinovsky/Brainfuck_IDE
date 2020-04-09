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
import java.awt.event.InputEvent;
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
import javax.swing.KeyStroke;
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
    Color color1 = new Color(245,245,220);    //hueso
    Color color2 = new Color(120,0,0);      //rojo oscuro
    Color color3 = new Color(189,195,199);  //gris
    Color color4 = new Color(28, 28, 33);  //gris oscuro
    Color color5 = new Color(247, 175, 0);  //mostaza
    Color color6 = new Color(68, 48, 0);    //mostaza oscuro
    //Escalas frias para simbolos brainfuck
    Color color7 = new Color(15, 131, 58);    //verde menta
    Color color8 = new Color(1, 138, 108);    //verde menta
    Color color9 = new Color(1, 65, 127);    //verde menta
    Color color10 = new Color(48, 41, 121);    //verde menta
    Color color11 = new Color(80, 35, 128);    //verde menta
    Color color12 = new Color(120, 29, 125);    //verde menta
    
    Font fuente1 = new Font("Tahoma", Font.PLAIN, Py(40));
    StyledDocument doc;
    Style estilo;
    Pila<String[]> undo = new Pila<>();
    Pila<String[]> redo = new Pila<>();
    String[] temporal = new String[3];
    
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
        BarraMenus.setBackground(Color.WHITE);
        
        MenuArchivo=new JMenu("Archivo");
        BarraMenus.add(MenuArchivo);
        crearMenuArchivo();
        
        MenuCorrer=new JMenu("Correr");
        BarraMenus.add(MenuCorrer);
        
        MenuEditar=new JMenu("Editar");
        BarraMenus.add(MenuEditar);
        crearMenuEditar();
    }
    void crearMenuArchivo(){
        subMenuNuevo = new JMenuItem("Nuevo");
        subMenuNuevo.addActionListener(this);
        subMenuNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        MenuArchivo.add(subMenuNuevo);
        
        subMenuAbrir = new JMenuItem("Abrir");
        subMenuAbrir.addActionListener(this);
        subMenuAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        MenuArchivo.add(subMenuAbrir);
        
        subMenuGuardar = new JMenuItem("Guardar");
        subMenuGuardar.addActionListener(this);
        subMenuGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        MenuArchivo.add(subMenuGuardar);
    }
    void crearMenuEditar(){
        retroceder = new JMenuItem("Retroceder");
        retroceder.addActionListener(this);
        retroceder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        MenuEditar.add(retroceder);
        
        avanzar = new JMenuItem("Avanzar");
        avanzar.addActionListener(this);
        avanzar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        MenuEditar.add(avanzar);
    }
    
    //Paneles de la ventana
    void CrearPanelProgramacion (int x, int y, int ancho, int alto){
        PanelProgramacion = new JPanel();
        PanelProgramacion.setLayout(null);
        PanelProgramacion.setSize(ancho, alto);
        PanelProgramacion.setLocation(x, y);
        
        CuadroProgramacion = new JTextPane();
        doc = CuadroProgramacion.getStyledDocument();
        estilo = CuadroProgramacion.addStyle("", null);
        CuadroProgramacion.setBounds(0, Py(35), Px(1880), Py(575));
        CuadroProgramacion.setFont(fuente1);
        CuadroProgramacion.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroProgramacion.setBackground(color3);
        CuadroProgramacion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String simbolo = String.valueOf(e.getKeyChar());
                e.consume();
                if(simbolo.equals("<")||
                        simbolo.equals(">")||
                        simbolo.equals(".")||
                        simbolo.equals(",")||
                        simbolo.equals("+")|| 
                        simbolo.equals("-")|| 
                        simbolo.equals("[")|| 
                        simbolo.equals("]")|| 
                        simbolo.equals(";")||
                        simbolo.equals(":")||
                        simbolo.equals("#")||
                        simbolo.equals("$")){
                    imprimirColorSimbolo(simbolo, doc.getLength());
                    String[] temporal = new String[3];
                    temporal[0] = "e";
                    temporal[1] = simbolo;
                    temporal[2] = String.valueOf(CuadroProgramacion.getCaretPosition());
                    undo.push(temporal);
                    redo.reset();
                }
            }
            @Override
            public void keyPressed (KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE || e.getKeyCode()==KeyEvent.VK_DELETE){
                    if(CuadroProgramacion.getSelectedText()!=null){
                        String[] temporal = new String[3];
                        temporal[0] = "b1";
                        temporal[1] = CuadroProgramacion.getSelectedText();
                        temporal[2] = String.valueOf(CuadroProgramacion.getSelectionStart());
                        undo.push(temporal);
                    }else if(e.getKeyCode()==KeyEvent.VK_DELETE){
                        try{
                            String[] temporal = new String[3];
                            temporal[0] = "b1";
                            temporal[1] = doc.getText(CuadroProgramacion.getCaretPosition(), 1);
                            temporal[2] = String.valueOf(CuadroProgramacion.getCaretPosition());
                            undo.push(temporal);
                        }catch(BadLocationException b){}
                    }else {
                        try{
                            String[] temporal = new String[3];
                            temporal[0] = "b2";
                            temporal[1] = doc.getText(CuadroProgramacion.getCaretPosition()-1, 1);
                            temporal[2] = String.valueOf(CuadroProgramacion.getCaretPosition());
                            undo.push(temporal);
                        }catch(BadLocationException b){}
                    }
                }
            }
        });
        PanelProgramacion.add(CuadroProgramacion);
        
        etiqueta = new JLabel("Nuevo");
        etiqueta.setBounds(Px(10), 0, Px(150), Py(35));
        PanelProgramacion.add(etiqueta);
        
        Ventana.getContentPane().add(PanelProgramacion);
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
        
        Ventana.getContentPane().add(PanelEntrada);
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
        
        Ventana.getContentPane().add(PanelSalida);
    }
    
    /*funciones
    Retro(deshacer):
        - si caracter[0] es "e" entonces el usuario habia [escrito] deshacer será borrar
        - si caracter[0] es "b" entonces el usuario habia [borrado] deshacer será imprimir
        - guarda en pila "redo"
    Avanzar(rehacer):
        - si caracter[0] es "e" entonces el usuario habia [escrito] deshacer será imprimir
        - si caracter[0] es "b" entonces el usuario habia [borrado] deshacer será borrar
        - guarda en pila "undo"
    colorSimbolo:
        - cambia los colores a los simbolos e imprime en "CuadroProgramacion" */
    int Px(int pixeles){
        return resolucion.width*pixeles/1920;
    }
    int Py(int pixeles){
        return resolucion.height*pixeles/1080;
    }
    void subMenuNuevo(){
        Ventana.getContentPane().setLayout(null);
        CrearPanelProgramacion(Px(20), Py(20), Px(1880), Py(610));
        CrearPanelEntrada(Px(20), Py(650), Px(930), Py(340));
        CrearPanelSalida(Px(970), Py(650), Px(930), Py(340));
        Ventana.repaint();
    }
    void subMenuAbrir(){
        FileDialog fd= new FileDialog(Ventana, "Abrir", FileDialog.LOAD);
        fd.setVisible(true);
    }
    void Retro(){
        if(!undo.empty()){
            temporal = undo.pop();
            redo.push(temporal);
            switch (temporal[0]) {
                case "e":
                    try{
                        CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2])-1);
                        doc.remove(Integer.parseInt(temporal[2])-1, 1);
                    }catch(BadLocationException b){}
                    break;
                case "b1":
                    for(int i=0; i<temporal[1].length(); i++){
                        imprimirColorSimbolo(temporal[1].substring(i,i+1), Integer.parseInt(temporal[2])+i);
                    }
                    CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                    break;
                case "b2":
                    imprimirColorSimbolo(temporal[1], Integer.parseInt(temporal[2])-1);
                    CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                    break;
            }
        }else Toolkit.getDefaultToolkit().beep();
    }
    void Adelante(){
        if(!redo.empty()){
            temporal =redo.pop();
            undo.push(temporal);
            switch (temporal[0]) {
                case "e":
                    imprimirColorSimbolo(temporal[1], Integer.parseInt(temporal[2])-1);
                    CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                    break;
                case "b1":
                    try{
                        CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                        doc.remove(Integer.parseInt(temporal[2]), temporal[1].length());
                    }catch(BadLocationException b){}
                    break;
                case "b2":
                    try{
                        CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                        doc.remove(Integer.parseInt(temporal[2])-1, 1);
                    }catch(BadLocationException b){}
                    break;
            }
        }else Toolkit.getDefaultToolkit().beep();
    }
    void imprimirColorSimbolo(String simbolo, int ubicacion){
        try {
            switch (simbolo) {
                case "<":
                    StyleConstants.setForeground(estilo, color7);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case ">":
                    StyleConstants.setForeground(estilo, color7);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case ".":
                    StyleConstants.setForeground(estilo, color8);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case ",":
                    StyleConstants.setForeground(estilo, color8);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case "+":
                    StyleConstants.setForeground(estilo, color9);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case "-":
                    StyleConstants.setForeground(estilo, color9);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case "[":
                    StyleConstants.setForeground(estilo, color10);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case "]":
                    StyleConstants.setForeground(estilo, color10);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case ";":
                    StyleConstants.setForeground(estilo, color11);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case ":":
                    StyleConstants.setForeground(estilo, color11);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case "#":
                    StyleConstants.setForeground(estilo, color12);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
                case "$":
                    StyleConstants.setForeground(estilo, color12);
                    doc.insertString(ubicacion, simbolo, estilo);
                    break;
            }
        }catch (BadLocationException b){}
    }
    
    //Constructor
    public Interfaz(){
        crearVentana();
        Ventana.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
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