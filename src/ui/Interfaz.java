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
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
/**
 *
 * @author kjcar
 */
public final class Interfaz implements ActionListener, DocumentListener{
    JFrame Ventana;
    JPanel PanelProgramacion, PanelEntrada, PanelSalida;
    JLabel etiqueta;
    JMenuBar BarraMenus;
    JMenu MenuArchivo, MenuCorrer, MenuEditar;
    JMenuItem subMenuNuevo, subMenuAbrir, subMenuGuardar, retroceder, avanzar, run;
    JTextField archivo;
    JButton salir, ingresar;
    public JTextPane CuadroProgramacion;
    Document doc2;
    JTextArea CuadroEntrada, CuadroSalida;
    boolean redoed=false;
    //JScrollPane scrollPane;
    Dimension resolucion = Toolkit.getDefaultToolkit().getScreenSize();
    Color color1 = new Color(255,43,43);    //rojo
    Color color2 = new Color(120,0,0);      //rojo oscuro
    Color color3 = new Color(189,195,199);  //gris
    Color color4 = new Color(98, 99, 100);  //gris oscuro
    Color color5 = new Color(247, 175, 0);  //mostaza
    Color color6 = new Color(68, 48, 0);    //mostaza oscuro
    Font fuente1 = new Font("Console", Font.PLAIN, Py(40));
    StyledDocument doc;
    Style estilo;
    public Pila<String> undo = new Pila<>();
    public Pila<String> redo = new Pila<>();
    
    //ventana
    void crearVentana(){
        Ventana = new JFrame("Brainfuck IDE");
        Ventana.setMinimumSize(new Dimension(Px(1440), Py(810)));
        Ventana.setResizable(true);
        Ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        undo.push("");
        
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
        crearMenuCorrer();
        
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
    void crearMenuCorrer(){
        run = new JMenuItem("Correr");
        run.addActionListener(this);
        run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        MenuCorrer.add(run);
    }
    
    //Paneles de la ventana
    void CrearPanelProgramacion (int x, int y, int ancho, int alto){
        PanelProgramacion = new JPanel();
        PanelProgramacion.setLayout(null);
        PanelProgramacion.setSize(ancho, alto);
        PanelProgramacion.setLocation(x, y);
        
        CuadroProgramacion = new JTextPane();
        doc = CuadroProgramacion.getStyledDocument();
        doc2 = CuadroProgramacion.getDocument();
        doc2.addDocumentListener(this);
        estilo = CuadroProgramacion.addStyle(null, null);
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
                        simbolo.equals("+")|| 
                        simbolo.equals("-")|| 
                        simbolo.equals("[")|| 
                        simbolo.equals("]")|| 
                        simbolo.equals(";")||
                        simbolo.equals(":")){
                    imprimirColorSimbolo(simbolo);
                    redo.reset();
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
            redo.push(undo.peek());
            redoed=true;
            undo.pop();
            CuadroProgramacion.setText(undo.peek());
        }else Toolkit.getDefaultToolkit().beep();
    }
    void Adelante(){
        if(!redo.empty()){
            //undo.push(redo.pop());
            CuadroProgramacion.setText(redo.pop());
        }else Toolkit.getDefaultToolkit().beep();
    }
    
    void compilar(){
        if(CuadroEntrada.getText().equals("")){
            CuadroSalida.setText(compilador(CuadroProgramacion.getText(), "1"));
        }else{
            CuadroSalida.setText(compilador(CuadroProgramacion.getText(), CuadroEntrada.getText()));
        }
    }
    
    void imprimirColorSimbolo(String simbolo){
        try {
            switch (simbolo) {
                case "<":
                    StyleConstants.setForeground(estilo, Color.black);
                    doc.insertString(doc.getLength(), simbolo, estilo);
                    break;
                case ">":
                    StyleConstants.setForeground(estilo, Color.black);
                    doc.insertString(doc.getLength(), simbolo, estilo);
                    break;
                case "+":
                    StyleConstants.setForeground(estilo, Color.black);
                    doc.insertString(doc.getLength(), simbolo, estilo);
                    break;
                case "-":
                    StyleConstants.setForeground(estilo, Color.black);
                    doc.insertString(doc.getLength(), simbolo, estilo);
                    break;
                case "[":
                    StyleConstants.setForeground(estilo, Color.black);
                    doc.insertString(doc.getLength(), simbolo, estilo);
                    break;
                case "]":
                    StyleConstants.setForeground(estilo, Color.black);
                    doc.insertString(doc.getLength(), simbolo, estilo);
                    break;
                case ";":
                    StyleConstants.setForeground(estilo, Color.black);
                    doc.insertString(doc.getLength(), simbolo, estilo);
                    break;
                case ":":
                    StyleConstants.setForeground(estilo, Color.black);
                    doc.insertString(doc.getLength(), simbolo, estilo);
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
            case "Correr":
                compilar();
                break;      
        }
    }

    @Override
    public void insertUpdate(DocumentEvent d) {
        System.out.println("INSERT");
        if(!undo.empty()){
            if(redoed==false){
                undo.push(CuadroProgramacion.getText());
            }
        }else{
           undo.push(CuadroProgramacion.getText()); 
        }
        redoed=false;
        undo.print();
        redo.print();
    }

    @Override
    public void removeUpdate(DocumentEvent d) {
        System.out.println("REMOVE");
        if(redoed==false){
            undo.push(CuadroProgramacion.getText());
        }
        undo.print();
        redo.print();
    }

    @Override
    public void changedUpdate(DocumentEvent d) {
        System.out.println("CHANGE");
        undo.push(CuadroProgramacion.getText());
    }
    
    
    String compilador(String a, String input){
        String program = a;
        
        String salida="";
        String[] tokens=input.split(" ");
        int[] data = new int[tokens.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = Integer.valueOf(tokens[i]);
        }

        int[] memory = new int[256];
        //place initial pointer to a memory cell to the middle of the memory
        int pointer = 256 / 2;
        //pointer to the input data
        int dataPointer = 0;

        char[] commands = program.toCharArray();
        for (int i = 0; i < commands.length; i++) {
            switch (commands[i]) {
                case '+':
                    memory[pointer]++;
                    break;
                case '-':
                    memory[pointer]--;
                    break;
                case '>':
                    pointer++;
                    break;
                case '<':
                    pointer--;
                    break;
                case '[':
                    if(memory[pointer] == 0) {
                        int depth = 0;
                        for (int j = i; j < commands.length; j++) {
                            if(commands[j] == '[')
                                depth++;
                            else if(commands[j] == ']')
                                depth--;
                            if(depth == 0) {
                                i = j;
                                break;
                            }
                        }
                    }
                    break;
                case ']':
                    int depth = 0;
                    for (int j = i; j >= 0; j--) {
                        if(commands[j] == ']')
                            depth++;
                        else if(commands[j] == '[')
                            depth--;
                        if(depth == 0) {
                            i = j - 1;
                            break;
                        }
                    }
                    break;
                case ':':
                    //System.out.print(memory[pointer] + " ");
                    salida+= String.valueOf(memory[pointer])+" ";
                    break;
                case ';':
                    memory[pointer] = data[dataPointer];
                    dataPointer++;
                    break;
                default:
                    break;
            }

        }
        return salida;
    }
}