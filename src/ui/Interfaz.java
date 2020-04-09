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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
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
    Color color7 = new Color(15, 131, 58);
    Color color8 = new Color(1, 138, 108);
    Color color9 = new Color(1, 65, 127);
    Color color10 = new Color(48, 41, 121);
    Color color11 = new Color(80, 35, 128);
    Color color12 = new Color(120, 29, 125);
    
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
        subMenuNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        MenuArchivo.add(subMenuNuevo);
        
        subMenuAbrir = new JMenuItem("Abrir");
        subMenuAbrir.addActionListener(this);
        subMenuAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        MenuArchivo.add(subMenuAbrir);
        
        subMenuGuardar = new JMenuItem("Guardar");
        subMenuGuardar.addActionListener(this);
        subMenuGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        MenuArchivo.add(subMenuGuardar);
    }
    void crearMenuEditar(){
        retroceder = new JMenuItem("Deshacer");
        retroceder.addActionListener(this);
        retroceder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        MenuEditar.add(retroceder);
        
        avanzar = new JMenuItem("Rehacer");
        avanzar.addActionListener(this);
        avanzar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
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
                if(simbolo.equals("\n") || simbolo.equals("\t")){
                    String[] temporal = new String[3];
                    temporal[0] = "e1";
                    temporal[1] = simbolo;
                    temporal[2] = String.valueOf(CuadroProgramacion.getCaretPosition()-1);
                    undo.push(temporal);
                    redo.reset();
                }else if(!simbolo.equals("\u0008") && !simbolo.equals("\u007F") && !simbolo.equals("\u001B") && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) == 0)){
                    if(CuadroProgramacion.getSelectedText()!=null){
                        String[] temporal = new String[3];
                        temporal[0] = "b1";
                        temporal[1] = CuadroProgramacion.getSelectedText();
                        temporal[2] = String.valueOf(CuadroProgramacion.getSelectionStart());
                        undo.push(temporal);
                    }
                    String[] temporal = new String[3];
                    if(CuadroProgramacion.getSelectedText()!=null){
                        eliminarSeleccion(CuadroProgramacion.getSelectionStart(), CuadroProgramacion.getSelectedText().length());
                        temporal[0] = "et";
                    }else
                        temporal[0] = "e1";
                    temporal[1] = simbolo;
                    temporal[2] = String.valueOf(CuadroProgramacion.getCaretPosition());
                    imprimirColorSimbolo(simbolo, CuadroProgramacion.getCaretPosition());
                    undo.push(temporal);
                    redo.reset();
                }
            }
            @Override
            public void keyPressed (KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE || e.getKeyCode()==KeyEvent.VK_DELETE || ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0))){
                    if(CuadroProgramacion.getSelectedText()!=null){
                        String[] temporal = new String[3];
                        temporal[0] = "b1";
                        temporal[1] = CuadroProgramacion.getSelectedText();
                        temporal[2] = String.valueOf(CuadroProgramacion.getSelectionStart());
                        undo.push(temporal);
                    }else if(e.getKeyCode()==KeyEvent.VK_DELETE && CuadroProgramacion.getCaretPosition()!=doc.getLength()){
                        try{
                            String[] temporal = new String[3];
                            temporal[0] = "b1";
                            temporal[1] = doc.getText(CuadroProgramacion.getCaretPosition(), 1);
                            temporal[2] = String.valueOf(CuadroProgramacion.getCaretPosition());
                            undo.push(temporal);
                        }catch(BadLocationException b){}
                    }else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE && CuadroProgramacion.getCaretPosition()!=0){
                        try{
                            String[] temporal = new String[3];
                            temporal[0] = "b2";
                            temporal[1] = doc.getText(CuadroProgramacion.getCaretPosition()-1, 1);
                            temporal[2] = String.valueOf(CuadroProgramacion.getCaretPosition());
                            undo.push(temporal);
                        }catch(BadLocationException b){}
                    }
                }
                if((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)){
                    if(CuadroProgramacion.getSelectedText()!=null){
                        try {
                            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                            String[] temporal1 = new String[3];
                            temporal1[0] = "b1";
                            temporal1[1] = CuadroProgramacion.getSelectedText();
                            temporal1[2] = String.valueOf(CuadroProgramacion.getSelectionStart());
                            undo.push(temporal1);
                            String[] temporal2 = new String[3];
                            temporal2[0] = "et";
                            temporal2[1] = (String) t.getTransferData(DataFlavor.stringFlavor);
                            temporal2[2] = temporal1[2];
                            undo.push(temporal2);
                            redo.reset();
                            e.consume();
                            eliminarSeleccion(CuadroProgramacion.getSelectionStart(), temporal1[1].length());
                            for(int i=0; i<temporal2[1].length(); i++)
                                imprimirColorSimbolo(temporal2[1].substring(i,i+1), Integer.parseInt(temporal2[2])+i);
                        } catch (UnsupportedFlavorException | IOException ex) {}
                    }else {
                        try {
                            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                            String[] temporal = new String[3];
                            temporal[0] = "e2";
                            temporal[1] = (String) t.getTransferData(DataFlavor.stringFlavor);
                            temporal[2] = String.valueOf(CuadroProgramacion.getCaretPosition());
                            undo.push(temporal);
                            redo.reset();
                            e.consume();
                            for(int i=0; i<temporal[1].length(); i++)
                                imprimirColorSimbolo(temporal[1].substring(i,i+1), Integer.parseInt(temporal[2])+i);
                        } catch (UnsupportedFlavorException | IOException ex) {}
                    }
                }
            }
            void eliminarSeleccion(int comienzo, int longitud){
                try{
                    doc.remove(comienzo, longitud);
                }catch(BadLocationException b){}
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
    
    //funciones
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
    void deshacer(){
        if(!undo.empty()){
            temporal = undo.pop();
            redo.push(temporal);
            switch (temporal[0]) {
                case "e1":
                    try{
                        CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                        doc.remove(Integer.parseInt(temporal[2]), 1);
                    }catch(BadLocationException b){}
                    break;
                case "e2":
                    try{
                        CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                        doc.remove(Integer.parseInt(temporal[2]), temporal[1].length());
                    }catch(BadLocationException b){}
                    break;
                case "et":
                    try{
                        CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                        doc.remove(Integer.parseInt(temporal[2]), temporal[1].length());
                        deshacer();
                    }catch(BadLocationException b){}
                    break;
                case "b1":
                    for(int i=0; i<temporal[1].length(); i++)
                        imprimirColorSimbolo(temporal[1].substring(i,i+1), Integer.parseInt(temporal[2])+i);
                    CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                    break;
                case "b2":
                    imprimirColorSimbolo(temporal[1], Integer.parseInt(temporal[2])-1);
                    CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                    break;
            }
        }else Toolkit.getDefaultToolkit().beep();
    }
    void rehacer(){
        if(!redo.empty()){
            temporal =redo.pop();
            undo.push(temporal);
            switch (temporal[0]) {
                case "e1":
                    imprimirColorSimbolo(temporal[1], Integer.parseInt(temporal[2]));
                    CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2])+1);
                    break;
                case "e2":
                    for(int i=0; i<temporal[1].length(); i++)
                        imprimirColorSimbolo(temporal[1].substring(i,i+1), Integer.parseInt(temporal[2])+i);
                    CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2])+temporal[1].length());
                    break;
                case "et":
                    for(int i=0; i<temporal[1].length(); i++)
                        imprimirColorSimbolo(temporal[1].substring(i,i+1), Integer.parseInt(temporal[2])+i);
                    CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2])+temporal[1].length());
                    break;
                case "b1":
                    try{
                        CuadroProgramacion.setCaretPosition(Integer.parseInt(temporal[2]));
                        doc.remove(Integer.parseInt(temporal[2]), temporal[1].length());
                    }catch(BadLocationException b){}
                    if(redo.peek()[0].equals("et"))
                        rehacer();
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
                default:
                    StyleConstants.setForeground(estilo, Color.BLACK);
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
            case "Deshacer":
                deshacer();
                break;
            case "Rehacer":
                rehacer();
                break;
        }
    }
}