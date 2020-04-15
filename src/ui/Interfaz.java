/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import data.Archivo;
import data.Temporal;
import java.awt.BorderLayout;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import static logic.Central.listaArchivos;
import logic.Cola;
/**
 *
 * @author kjcar
 */
public final class Interfaz implements ActionListener{
    JFrame Ventana=new JFrame("Brainfuck++ IDE");
    JPanel PanelProgramacion=new JPanel(), PanelEntrada, PanelSalida;
    JLabel etiqueta;
    JMenuBar BarraMenus, BarraArchivos= new JMenuBar();
    JMenu MenuArchivo, MenuProyecto, MenuEditar, Archivo;
    JMenuItem subMenuNuevo, subMenuAbrir, subMenuGuardar, subMenuGuardarComo, subMenuCerrar, subMenuDeshacer, subMenuRehacer, subMenuCorrer;
    public static JTextPane CuadroProgramacion = new JTextPane();
    JTextArea CuadroEntrada, CuadroSalida;
    //JScrollPane scrollPane;
    final Dimension resolucion = Toolkit.getDefaultToolkit().getScreenSize();
    final Color color2 = new Color(238,238,238);
    final Color color3 = new Color(189,195,199);
    final Color color4 = new Color(28, 28, 33);
    //Escalas frias para simbolos brainfuck
    final Color color7 = new Color(15, 131, 58);
    final Color color8 = new Color(1, 138, 108);
    final Color color9 = new Color(1, 65, 127);
    final Color color10 = new Color(48, 41, 121);
    final Color color11 = new Color(80, 35, 128);
    final Color color12 = new Color(120, 29, 125);
    final Font fuente1 = new Font("Tahoma", Font.PLAIN, Py(40));
    public static Style estilo1, estilo2, estilo3, estilo4, estilo5, estilo6, estilo7;
    int posicionLista;
    final String extension=".bfck";
    FileDialog fd;
    //compilador
    public Cola<Integer> data;
    
    //ventana
    void crearVentana(){
        Ventana.setMinimumSize(new Dimension(Px(1440), Py(810)));
        Ventana.setResizable(true);
        Ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        crearBarraMenus();
        Ventana.setJMenuBar(BarraMenus);
    }
    void crearBarraMenus(){
        BarraMenus= new JMenuBar();
        BarraMenus.setBackground(color2);
        
        MenuArchivo=new JMenu("Archivo");
        BarraMenus.add(MenuArchivo);
        crearMenuArchivo();
        
        MenuProyecto=new JMenu("Proyecto");
        BarraMenus.add(MenuProyecto);
        crearMenuProyecto();
        
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
        subMenuGuardar.setEnabled(false);
        subMenuGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        MenuArchivo.add(subMenuGuardar);
        
        subMenuGuardarComo = new JMenuItem("Guardar como");
        subMenuGuardarComo.addActionListener(this);
        subMenuGuardarComo.setEnabled(false);
        MenuArchivo.add(subMenuGuardarComo);
        
        subMenuCerrar = new JMenuItem("Cerrar");
        subMenuCerrar.addActionListener(this);
        subMenuCerrar.setEnabled(false);
        MenuArchivo.add(subMenuCerrar);
    }
    void crearMenuEditar(){
        subMenuDeshacer = new JMenuItem("Deshacer");
        subMenuDeshacer.addActionListener(this);
        subMenuDeshacer.setEnabled(false);
        subMenuDeshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        MenuEditar.add(subMenuDeshacer);
        
        subMenuRehacer = new JMenuItem("Rehacer");
        subMenuRehacer.addActionListener(this);
        subMenuRehacer.setEnabled(false);
        subMenuRehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        MenuEditar.add(subMenuRehacer);
    }
    void crearMenuProyecto(){
       subMenuCorrer = new JMenuItem("Correr");
       subMenuCorrer.addActionListener(this);
       subMenuCorrer.setEnabled(false);
       subMenuCorrer.setMnemonic(KeyEvent.VK_F5);
       MenuProyecto.add(subMenuCorrer);
    }
    void actualizarBarraArchivos(){
        BarraArchivos.removeAll();
        BarraArchivos.setBackground(color2);
        
        for(int i=0;i<listaArchivos.size(); i++){
            Archivo=new JMenu(listaArchivos.get(i).nombreArchivo);
            int o = i;
            Archivo.setOpaque(true);
            Archivo.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    posicionLista=o;
                    actualizarVentana();
                }
            });
            if(i==posicionLista)
                Archivo.setBackground(color3);
            else
                Archivo.setBackground(color2);
            BarraArchivos.add(Archivo);
        }
        Archivo=new JMenu("+");
        Archivo.setBorder(BorderFactory.createLineBorder(color3, Px(3), true));
        Archivo.setOpaque(true);
        Archivo.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    subMenuNuevo();
                }
            });
        BarraArchivos.add(Archivo);
        
        BarraArchivos.add(Box.createHorizontalGlue());
        
        Archivo=new JMenu("<");
        Archivo.setBorder(BorderFactory.createLineBorder(color3, Px(3), true));
        Archivo.setEnabled(false);
        Archivo.setOpaque(true);
        BarraArchivos.add(Archivo);
        
        Archivo=new JMenu(">");
        Archivo.setBorder(BorderFactory.createLineBorder(color3, Px(3), true));
        Archivo.setEnabled(false);
        Archivo.setOpaque(true);
        BarraArchivos.add(Archivo);
        
        BarraArchivos.setFocusable(false);
        
        PanelProgramacion.setLayout(new BorderLayout());
        PanelProgramacion.add(BarraArchivos, BorderLayout.NORTH);
    }
    
    //Paneles de la ventana
    void CrearPanelProgramacion (int x, int y, int ancho, int alto){
        PanelProgramacion.removeAll();
        
        PanelProgramacion.setSize(ancho, alto);
        PanelProgramacion.setLocation(x, y);
        
        actualizarBarraArchivos();
        
        listaArchivos.get(posicionLista).doc = CuadroProgramacion.getStyledDocument();
        estilo1 = CuadroProgramacion.addStyle("estilo1", null);
        estilo2 = CuadroProgramacion.addStyle("estilo2", null);
        estilo3 = CuadroProgramacion.addStyle("estilo3", null);
        estilo4 = CuadroProgramacion.addStyle("estilo4", null);
        estilo5 = CuadroProgramacion.addStyle("estilo5", null);
        estilo6 = CuadroProgramacion.addStyle("estilo6", null);
        estilo7 = CuadroProgramacion.addStyle("estilo7", null);
        StyleConstants.setForeground(estilo1, color7);
        StyleConstants.setForeground(estilo2, color8);
        StyleConstants.setForeground(estilo3, color9);
        StyleConstants.setForeground(estilo4, color10);
        StyleConstants.setForeground(estilo5, color11);
        StyleConstants.setForeground(estilo6, color12);
        StyleConstants.setForeground(estilo7, Color.BLACK);
        CuadroProgramacion.setBounds(0, Py(35), Px(1880), Py(575));
        CuadroProgramacion.setFont(fuente1);
        CuadroProgramacion.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroProgramacion.setBackground(color3);
        CuadroProgramacion.addKeyListener(new KeyAdapter() {
            /*Clasificacion
            1::escritura
            2::suprimir-escritura
            3::suprimir
            4::borrar(backspace)
            */
            @Override
            public void keyTyped(KeyEvent e) {
                String simbolo = String.valueOf(e.getKeyChar());
                e.consume();
                if(simbolo.equals("\n") || simbolo.equals("\t")){
                    Temporal temp = new Temporal();
                    temp.establecerClasificacion(1);
                    temp.establecerDato(simbolo);
                    temp.establecerCursor(CuadroProgramacion.getCaretPosition()-1);
                    listaArchivos.get(posicionLista).deshacer.push(temp);
                    listaArchivos.get(posicionLista).rehacer.reset();
                }else if(!simbolo.equals("\u0008") && !simbolo.equals("\u007F") && !simbolo.equals("\u001B") && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) == 0)){
                    if(CuadroProgramacion.getSelectedText()!=null){
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(3);
                        temp.establecerDato(CuadroProgramacion.getSelectedText());
                        temp.establecerCursor(CuadroProgramacion.getSelectionStart());
                        listaArchivos.get(posicionLista).deshacer.push(temp);
                    }
                    Temporal temp = new Temporal();
                    if(CuadroProgramacion.getSelectedText()!=null){
                        listaArchivos.get(posicionLista).eliminarTexto(CuadroProgramacion.getSelectionStart(), CuadroProgramacion.getSelectedText().length());
                        temp.establecerClasificacion(2);
                    }else
                        temp.establecerClasificacion(1);
                    temp.establecerDato(simbolo);
                    temp.establecerCursor(CuadroProgramacion.getCaretPosition());
                    listaArchivos.get(posicionLista).imprimirColor(simbolo, CuadroProgramacion.getCaretPosition());
                    listaArchivos.get(posicionLista).deshacer.push(temp);
                    listaArchivos.get(posicionLista).rehacer.reset();
                }
            }
            @Override
            public void keyPressed (KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE || e.getKeyCode()==KeyEvent.VK_DELETE || ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0))){
                    if(CuadroProgramacion.getSelectedText()!=null){
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(3);
                        temp.establecerDato(CuadroProgramacion.getSelectedText());
                        temp.establecerCursor(CuadroProgramacion.getSelectionStart());
                        listaArchivos.get(posicionLista).deshacer.push(temp);
                    }else if(e.getKeyCode()==KeyEvent.VK_DELETE && CuadroProgramacion.getCaretPosition()!=listaArchivos.get(posicionLista).doc.getLength()){
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(3);
                        temp.establecerDato(leerDato(CuadroProgramacion.getCaretPosition()));
                        temp.establecerCursor(CuadroProgramacion.getCaretPosition());
                        listaArchivos.get(posicionLista).deshacer.push(temp);
                    }else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE && CuadroProgramacion.getCaretPosition()!=0){
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(4);
                        temp.establecerDato(leerDato(CuadroProgramacion.getCaretPosition()-1));
                        temp.establecerCursor(CuadroProgramacion.getCaretPosition());
                        listaArchivos.get(posicionLista).deshacer.push(temp);
                    }
                }
                if((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)){
                    e.consume();
                    if(CuadroProgramacion.getSelectedText()!=null){
                        Temporal temp1 = new Temporal();
                        temp1.establecerClasificacion(3);
                        temp1.establecerDato(CuadroProgramacion.getSelectedText());
                        temp1.establecerCursor(CuadroProgramacion.getSelectionStart());
                        listaArchivos.get(posicionLista).deshacer.push(temp1);
                        Temporal temp2 = new Temporal();
                        temp2.establecerClasificacion(2);
                        temp2.establecerDato(leerPortapapeles());
                        temp2.establecerCursor(CuadroProgramacion.getSelectionStart());
                        listaArchivos.get(posicionLista).deshacer.push(temp2);
                        listaArchivos.get(posicionLista).rehacer.reset();
                        listaArchivos.get(posicionLista).eliminarTexto(CuadroProgramacion.getSelectionStart(), temp1.longitudDato());
                        for(int i=0; i<temp2.longitudDato(); i++)
                            listaArchivos.get(posicionLista).imprimirColor(temp2.verDato().substring(i,i+1), temp2.verCursor()+i);
                    }else {
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(1);
                        temp.establecerDato(leerPortapapeles());
                        temp.establecerCursor(CuadroProgramacion.getCaretPosition());
                        listaArchivos.get(posicionLista).deshacer.push(temp);
                        listaArchivos.get(posicionLista).rehacer.reset();
                        for(int i=0; i<temp.longitudDato(); i++)
                            listaArchivos.get(posicionLista).imprimirColor(temp.verDato().substring(i,i+1), temp.verCursor()+i);
                    }
                }
            }
            String leerDato (int posicion){
                try {
                    return listaArchivos.get(posicionLista).doc.getText(posicion, 1);
                } catch (BadLocationException ex) {
                    return null;
                }
            }
            String leerPortapapeles (){
                try {
                    Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                    return (String) t.getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException | IOException ex) {
                    return null;
                }
            }
        });
        PanelProgramacion.add(CuadroProgramacion);
        
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
    void subMenuNuevo(){
        posicionLista=listaArchivos.size();
        listaArchivos.add(new Archivo());
        listaArchivos.get(posicionLista).nombreArchivo = "Nuevo";
        subMenuCorrer.setEnabled(true);
        subMenuGuardar.setEnabled(true);
        subMenuGuardarComo.setEnabled(true);
        subMenuCerrar.setEnabled(true);
        subMenuDeshacer.setEnabled(true);
        subMenuRehacer.setEnabled(true);
        Ventana.getContentPane().setLayout(null);
        if(listaArchivos.size()==1){
            CrearPanelProgramacion(Px(20), Py(20), Px(1880), Py(610));
            CrearPanelEntrada(Px(20), Py(650), Px(930), Py(340));
            CrearPanelSalida(Px(970), Py(650), Px(930), Py(340));
            Ventana.repaint();
        }else
            actualizarVentana();
    }
    void subMenuAbrir(){
        fd = new FileDialog(Ventana, "Abrir", FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile()!=null && fd.getFile().endsWith(extension)){
            subMenuCorrer.setEnabled(true);
            subMenuGuardar.setEnabled(true);
            subMenuGuardarComo.setEnabled(true);
            subMenuCerrar.setEnabled(true);
            subMenuDeshacer.setEnabled(true);
            subMenuRehacer.setEnabled(true);
            posicionLista=listaArchivos.size();
            listaArchivos.add(new Archivo());
            listaArchivos.get(posicionLista).nombreArchivo = fd.getFile();
            listaArchivos.get(posicionLista).nombreArchivo=listaArchivos.get(posicionLista).nombreArchivo.replace(extension, "");
            listaArchivos.get(posicionLista).rutaDirectorio = fd.getDirectory();
            try{
                FileInputStream in = new FileInputStream(listaArchivos.get(posicionLista).rutaDirectorio+listaArchivos.get(posicionLista).nombreArchivo+extension);
                ObjectInputStream ois = new ObjectInputStream(in);
                if(listaArchivos.size()==1){
                    Ventana.getContentPane().setLayout(null);
                    CrearPanelProgramacion(Px(20), Py(20), Px(1880), Py(610));
                    CrearPanelEntrada(Px(20), Py(650), Px(930), Py(340));
                    CrearPanelSalida(Px(970), Py(650), Px(930), Py(340));
                    Ventana.repaint();
                }
                listaArchivos.get(posicionLista).doc=(StyledDocument)(ois.readObject()); 
                actualizarVentana();
            }catch(IOException | ClassNotFoundException e){}
        }  
    }
    void subMenuGuardar(){
        if(listaArchivos.get(posicionLista).rutaDirectorio!=null){
            try{
                FileOutputStream out = new FileOutputStream(listaArchivos.get(posicionLista).rutaDirectorio+listaArchivos.get(posicionLista).nombreArchivo+extension);
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(CuadroProgramacion.getStyledDocument());
                oos.flush();
            }catch(IOException e){}
        }else
            subMenuGuardarComo();
    }
    void subMenuGuardarComo(){
        fd = new FileDialog(Ventana,"Guardar como",FileDialog.SAVE);
        fd.setFile(listaArchivos.get(posicionLista).nombreArchivo);
        fd.setVisible(true);
        try{
            if(fd.getFile()!=null){
                listaArchivos.get(posicionLista).nombreArchivo = fd.getFile();
                listaArchivos.get(posicionLista).nombreArchivo = listaArchivos.get(posicionLista).nombreArchivo.replace(extension, "");
                listaArchivos.get(posicionLista).rutaDirectorio = fd.getDirectory();
                FileOutputStream out = new FileOutputStream(listaArchivos.get(posicionLista).rutaDirectorio+listaArchivos.get(posicionLista).nombreArchivo+extension);
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(CuadroProgramacion.getStyledDocument());
                oos.flush();
            }
        }catch(IOException e){}
    }
    void subMenuCerrar(){
        if(!listaArchivos.isEmpty()){
            listaArchivos.remove(posicionLista);
            posicionLista=listaArchivos.size()-1;
        }
        if(posicionLista<0){
            Ventana.remove(PanelProgramacion);
            Ventana.remove(PanelEntrada);
            Ventana.remove(PanelSalida);
            Ventana.repaint();
        }else
            actualizarVentana();
    }
    void MenuCorrer(){
        if(CuadroEntrada.getText().equals("")){
            CuadroSalida.setText(compilador(CuadroProgramacion.getText(), "1"));
        }else{
            CuadroSalida.setText(compilador(CuadroProgramacion.getText(), CuadroEntrada.getText()));
        }
    }
    
    //funciones
    int Px(int pixeles){
        return resolucion.width*pixeles/1920;
    }
    int Py(int pixeles){
        return resolucion.height*pixeles/1080;
    }
    void actualizarVentana(){
        CuadroProgramacion.setDocument(listaArchivos.get(posicionLista).doc);
        CuadroProgramacion.requestFocus();
        actualizarBarraArchivos();
    }
    String compilador(String a, String input){
        String program = a;
        
        String salida="";
        String[] tokens=input.split(" ");
        //int[] data = new int[tokens.length];
        data= new Cola<>();
        for (int i = 0; i < tokens.length; i++) {
            data.add(Integer.valueOf(tokens[i]));
        }

        int[] memory = new int[256];
        //place initial pointer to a memory cell to the middle of the memory
        int pointer = 256 / 2;
        //pointer to the input data
        //int dataPointer = 0;

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
                    memory[pointer] = data.peek();
                    data.pop();
                    break;
                default:
                    break;
            }

        }
        return salida;
    }
    
    //Constructor
    public Interfaz(){
        crearVentana();
        Ventana.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Deshacer":
                listaArchivos.get(posicionLista).subMenuDeshacer();
                break;
            case "Rehacer":
                listaArchivos.get(posicionLista).subMenuRehacer();
                break;
            case "Nuevo":
                subMenuNuevo();
                break;
            case "Abrir":
                subMenuAbrir();
                break;
            case "Correr":
                MenuCorrer();
                break;
            case "Guardar":
                subMenuGuardar();
                break;
            case "Guardar como":
                subMenuGuardarComo();
                break;
            case "Cerrar":
                subMenuCerrar();
                break;
        }
    }
}