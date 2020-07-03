/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
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
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import logic.Pila;
import logic.TNodo;
/**
 *
 * @author kjcar
 */
public final class Interfaz implements ActionListener{
    JFrame Ventana=new JFrame("Brainfuck++ IDE");
    JPanel PanelProgramacion, PanelEntrada, PanelSalida;
    JLabel etiqueta;
    JMenuBar BarraMenus, BarraArchivos= new JMenuBar();
    JMenu MenuArchivo, MenuProyecto, MenuEditar, Archivo;
    JMenuItem subMenuNuevo, subMenuAbrir, subMenuGuardar, subMenuGuardarComo, subMenuCerrar, subMenuDeshacer, subMenuRehacer, subMenuCorrer;
    public static JTextPane CuadroProgramacion;
    JTextArea CuadroEntrada , CuadroSalida;
    //JScrollPane scrollPane;
    final Dimension resolucion = Toolkit.getDefaultToolkit().getScreenSize();
    final Color color0 = new Color(113, 118, 193 );//bordes
    final Color color1 = new Color(32, 34, 37);// Background color
    final Color color2 = new Color(126, 131, 216);//Menu color
    final Color color3 = new Color(55, 57, 63);//Color Panel de programación
    final Color color4 = new Color(51, 56, 57);//Color borde del panel de programación
    //Escalas frias para simbolos brainfuck
    final Color color7 = new Color(212, 36, 50);//Rojo
    final Color color8 = new Color(62, 125, 227);//azul
    final Color color9 = new Color(89, 184, 146);//cian
    final Color color10 = new Color(225, 83, 20);//Naranja
    final Color color11 = new Color(232, 192, 70);//Amaraillo
    final Color color12 = new Color(183, 56, 156);//Morado
    final Font fuente1 = new Font("Consolas", Font.PLAIN, Py(40));
    public static Style estilo1, estilo2, estilo3, estilo4, estilo5, estilo6, estilo7;
    int posicionLista, tamañoArbol=0;
    final String extension=".bfck";
    FileDialog fd;
    public static Style colors[];
    //compilador
    public Cola data;
    
    //ventana
    void crearVentana(){
        Ventana.setMinimumSize(new Dimension(Px(1440), Py(810)));
        Ventana.setResizable(true);
        Ventana.getContentPane().setBackground(color1);
        Ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        crearBarraMenus();
        Ventana.setJMenuBar(BarraMenus);
    }
    void crearBarraMenus(){
        BarraMenus= new JMenuBar();
        BarraMenus.setBackground(color0);
        BarraMenus.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        
        MenuArchivo=new JMenu("Archivo");
        BarraMenus.add(MenuArchivo);
        MenuArchivo.setForeground(Color.WHITE);
        crearMenuArchivo();
        
        MenuProyecto=new JMenu("Proyecto");
        BarraMenus.add(MenuProyecto);
        MenuProyecto.setForeground(Color.WHITE);
        crearMenuProyecto();
        
        MenuEditar=new JMenu("Editar");
        BarraMenus.add(MenuEditar);
        MenuEditar.setForeground(Color.WHITE);
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
        subMenuCerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
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
       subMenuCorrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
       MenuProyecto.add(subMenuCorrer);
    }
    void actualizarBarraArchivos(){
        BarraArchivos.removeAll();
        BarraArchivos.setBackground(color1);
        BarraArchivos.setBorder(BorderFactory.createLineBorder(color3, Px(4), true));
        if(tamañoArbol<13)
            for(int i=0;i<tamañoArbol; i++){
                Archivo=new JMenu(listaArchivos.get(i).getArchivo().nombreArchivo);
                Archivo.setForeground(Color.WHITE);
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
        else
            for(int i=tamañoArbol-13;i<tamañoArbol; i++){
                Archivo=new JMenu(listaArchivos.get(i).getArchivo().nombreArchivo);
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
        Archivo.setForeground(Color.WHITE);
        Archivo.setBackground(color2);
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
        Archivo.setBackground(color2);
        Archivo.setForeground(Color.WHITE);
        Archivo.setBorder(BorderFactory.createLineBorder(color3, Px(3), true));
        Archivo.setEnabled(false);
        Archivo.setOpaque(true);
        BarraArchivos.add(Archivo);
        
        Archivo=new JMenu(">");
        Archivo.setBackground(color2);
        Archivo.setForeground(Color.WHITE);
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
        PanelProgramacion = new JPanel();
        PanelProgramacion.setLayout(null);
        PanelProgramacion.setSize(ancho, alto);
        PanelProgramacion.setLocation(x, y);
        
        actualizarBarraArchivos();
        
        listaArchivos.get(posicionLista).getArchivo().doc = CuadroProgramacion.getStyledDocument();
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
        StyleConstants.setForeground(estilo7, Color.WHITE);
        CuadroProgramacion.setBounds(0, Py(35), Px(1880), Py(575));
        CuadroProgramacion.setFont(fuente1);
        CuadroProgramacion.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroProgramacion.setBackground(color3);
        //Instancia el arreglo de estilos para el Syntax Highlighting
        colors=new Style[]{estilo1,estilo3,estilo3,estilo2,estilo6, estilo5,estilo4,estilo5,estilo2,estilo1, estilo4, estilo6, estilo6};
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
                    listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp);
                    listaArchivos.get(posicionLista).getArchivo().rehacer.reset();
                }else if(!simbolo.equals("\u0008") && !simbolo.equals("\u007F") && !simbolo.equals("\u001B") && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) == 0)){
                    if(CuadroProgramacion.getSelectedText()!=null){
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(3);
                        temp.establecerDato(CuadroProgramacion.getSelectedText());
                        temp.establecerCursor(CuadroProgramacion.getSelectionStart());
                        listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp);
                    }
                    Temporal temp = new Temporal();
                    if(CuadroProgramacion.getSelectedText()!=null){
                        listaArchivos.get(posicionLista).getArchivo().eliminarTexto(CuadroProgramacion.getSelectionStart(), CuadroProgramacion.getSelectedText().length());
                        temp.establecerClasificacion(2);
                    }else
                        temp.establecerClasificacion(1);
                    temp.establecerDato(simbolo);
                    temp.establecerCursor(CuadroProgramacion.getCaretPosition());
                    listaArchivos.get(posicionLista).getArchivo().imprimirColor(simbolo, CuadroProgramacion.getCaretPosition());
                    listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp);
                    listaArchivos.get(posicionLista).getArchivo().rehacer.reset();
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
                        listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp);
                    }else if(e.getKeyCode()==KeyEvent.VK_DELETE && CuadroProgramacion.getCaretPosition()!=listaArchivos.get(posicionLista).getArchivo().doc.getLength()){
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(3);
                        temp.establecerDato(leerDato(CuadroProgramacion.getCaretPosition()));
                        temp.establecerCursor(CuadroProgramacion.getCaretPosition());
                        listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp);
                    }else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE && CuadroProgramacion.getCaretPosition()!=0){
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(4);
                        temp.establecerDato(leerDato(CuadroProgramacion.getCaretPosition()-1));
                        temp.establecerCursor(CuadroProgramacion.getCaretPosition());
                        listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp);
                    }
                }
                if((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)){
                    e.consume();
                    if(CuadroProgramacion.getSelectedText()!=null){
                        Temporal temp1 = new Temporal();
                        temp1.establecerClasificacion(3);
                        temp1.establecerDato(CuadroProgramacion.getSelectedText());
                        temp1.establecerCursor(CuadroProgramacion.getSelectionStart());
                        listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp1);
                        Temporal temp2 = new Temporal();
                        temp2.establecerClasificacion(2);
                        temp2.establecerDato(leerPortapapeles());
                        temp2.establecerCursor(CuadroProgramacion.getSelectionStart());
                        listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp2);
                        listaArchivos.get(posicionLista).getArchivo().rehacer.reset();
                        listaArchivos.get(posicionLista).getArchivo().eliminarTexto(CuadroProgramacion.getSelectionStart(), temp1.longitudDato());
                        for(int i=0; i<temp2.longitudDato(); i++)
                            listaArchivos.get(posicionLista).getArchivo().imprimirColor(temp2.verDato().substring(i,i+1), temp2.verCursor()+i);
                    }else {
                        Temporal temp = new Temporal();
                        temp.establecerClasificacion(1);
                        temp.establecerDato(leerPortapapeles());
                        temp.establecerCursor(CuadroProgramacion.getCaretPosition());
                        listaArchivos.get(posicionLista).getArchivo().deshacer.push(temp);
                        listaArchivos.get(posicionLista).getArchivo().rehacer.reset();
                        for(int i=0; i<temp.longitudDato(); i++)
                            listaArchivos.get(posicionLista).getArchivo().imprimirColor(temp.verDato().substring(i,i+1), temp.verCursor()+i);
                    }
                }
            }
            String leerDato (int posicion){
                try {
                    return listaArchivos.get(posicionLista).getArchivo().doc.getText(posicion, 1);
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
        PanelEntrada.setBackground(color2);
        
        listaArchivos.get(posicionLista).getArchivo().ent = CuadroEntrada.getDocument();
        CuadroEntrada.setBounds(0, Py(35), Px(930), Py(305));
        CuadroEntrada.setFont(fuente1);
        CuadroEntrada.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroEntrada.setBackground(color3);
        CuadroEntrada.setForeground(Color.WHITE);
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
        PanelSalida.setBackground(color2);
        
        listaArchivos.get(posicionLista).getArchivo().sal = CuadroSalida.getDocument();
        CuadroSalida.setEditable(false);
        CuadroSalida.setFont(fuente1);
        CuadroSalida.setBounds(0, Py(35), Px(930), Py(305));
        CuadroSalida.setBorder(BorderFactory.createLineBorder(color4, Px(4), true));
        CuadroSalida.setBackground(color3);
        CuadroSalida.setForeground(Color.WHITE);
        PanelSalida.add(CuadroSalida);
        
        etiqueta = new JLabel("Salida");
        etiqueta.setBounds(Px(10), 0, Px(150), Py(35));
        PanelSalida.add(etiqueta);
        
        Ventana.getContentPane().add(PanelSalida);
    }
    void subMenuNuevo(){
        posicionLista=tamañoArbol;
        listaArchivos.insert(new TNodo(posicionLista));
        tamañoArbol++;
        listaArchivos.get(posicionLista).getArchivo().doc = new JTextPane().getStyledDocument();
        listaArchivos.get(posicionLista).getArchivo().ent = new JTextArea().getDocument();
        listaArchivos.get(posicionLista).getArchivo().sal = new JTextArea().getDocument();
        listaArchivos.get(posicionLista).getArchivo().deshacer = new Pila();
        listaArchivos.get(posicionLista).getArchivo().rehacer = new Pila();
        subMenuCorrer.setEnabled(true);
        subMenuGuardar.setEnabled(true);
        subMenuGuardarComo.setEnabled(true);
        subMenuCerrar.setEnabled(true);
        subMenuDeshacer.setEnabled(true);
        subMenuRehacer.setEnabled(true);
        Ventana.getContentPane().setLayout(null);
        if(tamañoArbol==1){
            Ventana.getContentPane().setLayout(null);
            listaArchivos.get(posicionLista).getArchivo().nombreArchivo = "Nuevo1";
            CuadroProgramacion = new JTextPane();
            CuadroEntrada = new JTextArea();
            CuadroSalida = new JTextArea();
            CrearPanelProgramacion(Px(20), Py(20), Px(1880), Py(610));
            CrearPanelEntrada(Px(20), Py(650), Px(930), Py(340));
            CrearPanelSalida(Px(970), Py(650), Px(930), Py(340));
            CuadroProgramacion.requestFocus();
            Ventana.repaint();
        }else{
            listaArchivos.get(posicionLista).getArchivo().nombreArchivo = "Nuevo"+(Integer.parseInt(listaArchivos.get(tamañoArbol-2).getArchivo().nombreArchivo.replace("Nuevo",""))+1);
        }
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
            posicionLista=tamañoArbol;
            listaArchivos.insert(new TNodo(posicionLista));
            tamañoArbol++;
            listaArchivos.get(posicionLista).getArchivo().nombreArchivo = fd.getFile();
            listaArchivos.get(posicionLista).getArchivo().nombreArchivo=listaArchivos.get(posicionLista).getArchivo().nombreArchivo.replace(extension, "");
            listaArchivos.get(posicionLista).getArchivo().rutaDirectorio = fd.getDirectory();
            try{
                FileInputStream in = new FileInputStream(listaArchivos.get(posicionLista).getArchivo().rutaDirectorio+listaArchivos.get(posicionLista).getArchivo().nombreArchivo+extension);
                ObjectInputStream ois = new ObjectInputStream(in);
                if(tamañoArbol==1){
                    Ventana.getContentPane().setLayout(null);
                    CuadroProgramacion = new JTextPane();
                    CuadroEntrada = new JTextArea();
                    CuadroSalida = new JTextArea();
                    CrearPanelProgramacion(Px(20), Py(20), Px(1880), Py(610));
                    CrearPanelEntrada(Px(20), Py(650), Px(930), Py(340));
                    CrearPanelSalida(Px(970), Py(650), Px(930), Py(340));
                    Ventana.repaint();
                }
                listaArchivos.get(posicionLista).getArchivo().doc = new JTextPane().getStyledDocument();
                listaArchivos.get(posicionLista).getArchivo().ent = new JTextArea().getDocument();
                listaArchivos.get(posicionLista).getArchivo().sal = new JTextArea().getDocument();
                listaArchivos.get(posicionLista).getArchivo().deshacer = new Pila();
                listaArchivos.get(posicionLista).getArchivo().rehacer = new Pila();
                listaArchivos.get(posicionLista).getArchivo().doc=(StyledDocument)(ois.readObject()); 
                actualizarVentana();
            }catch(IOException | ClassNotFoundException e){}
        }  
    }
    void subMenuGuardar(){
        if(listaArchivos.get(posicionLista).getArchivo().rutaDirectorio!=null){
            try{
                FileOutputStream out = new FileOutputStream(listaArchivos.get(posicionLista).getArchivo().rutaDirectorio+listaArchivos.get(posicionLista).getArchivo().nombreArchivo+extension);
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(CuadroProgramacion.getStyledDocument());
                oos.flush();
            }catch(IOException e){}
        }else
            subMenuGuardarComo();
    }
    void subMenuGuardarComo(){
        fd = new FileDialog(Ventana,"Guardar como",FileDialog.SAVE);
        fd.setFile(listaArchivos.get(posicionLista).getArchivo().nombreArchivo);
        fd.setVisible(true);
        try{
            if(fd.getFile()!=null){
                listaArchivos.get(posicionLista).getArchivo().nombreArchivo = fd.getFile();
                listaArchivos.get(posicionLista).getArchivo().nombreArchivo = listaArchivos.get(posicionLista).getArchivo().nombreArchivo.replace(extension, "");
                listaArchivos.get(posicionLista).getArchivo().rutaDirectorio = fd.getDirectory();
                actualizarVentana();
                FileOutputStream out = new FileOutputStream(listaArchivos.get(posicionLista).getArchivo().rutaDirectorio+listaArchivos.get(posicionLista).getArchivo().nombreArchivo+extension);
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(CuadroProgramacion.getStyledDocument());
                oos.flush();
            }
        }catch(IOException e){}
    }
    void subMenuCerrar(){
        if(tamañoArbol>1){
            listaArchivos.remove(posicionLista);
            tamañoArbol--;
            if(!(posicionLista<tamañoArbol))posicionLista=tamañoArbol-1;
            actualizarVentana();
        }else{
            tamañoArbol=0;
            subMenuCorrer.setEnabled(false);
            subMenuGuardar.setEnabled(false);
            subMenuGuardarComo.setEnabled(false);
            subMenuCerrar.setEnabled(false);
            subMenuDeshacer.setEnabled(false);
            subMenuRehacer.setEnabled(false);
            Ventana.remove(PanelProgramacion);
            Ventana.remove(PanelEntrada);
            Ventana.remove(PanelSalida);
            Ventana.getContentPane().setLayout(null);
            Ventana.repaint();
        }
    }
    void MenuCorrer() throws UnsupportedEncodingException{
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
        CuadroProgramacion.setDocument(listaArchivos.get(posicionLista).getArchivo().doc);
        CuadroProgramacion.requestFocus();
        CuadroEntrada.setDocument(listaArchivos.get(posicionLista).getArchivo().ent);
        CuadroSalida.setDocument(listaArchivos.get(posicionLista).getArchivo().sal);
        actualizarBarraArchivos();
        PanelProgramacion.repaint();
    }
    String compilador(String a, String input) throws UnsupportedEncodingException{
        String program = a;
        
        String salida="";
        String[] tokens=input.split(" ");
        //int[] data = new int[tokens.length];
        data= new Cola();
        for(int i = 0; i < tokens.length; i++) {
            data.add(Integer.valueOf(tokens[i]));
        }

        byte bytes;
        int bytetodec;
        //pointer to the input data
        //int dataPointer = 0;

        char[] commands = program.toCharArray();
        for (int i = 0; i < commands.length; i++) {
            switch (commands[i]) {
                case '+':
                    listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer]++;
                    break;
                case '-':
                    listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer]--;
                    break;
                case '>':
                    listaArchivos.get(posicionLista).getArchivo().pointer++;
                    break;
                case '<':
                    listaArchivos.get(posicionLista).getArchivo().pointer--;
                    break;
                case '[':
                    if(listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer] == 0) {
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
                    salida+= String.valueOf(listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer])+" ";
                    break;
                case ';':
                    listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer] = data.peek();
                    data.pop();
                    break;
                case '.':                  
                    bytes = (byte)listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer];
                    salida+= (char)bytes;
                    break;
                case ',':                  
                    bytetodec  = btoDec(String.valueOf(data.peek()));
                    listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer] = bytetodec;
                    data.pop();
                    break;
                case '&':
                    listaArchivos.get(posicionLista).getArchivo().clean();
                    break;
                case '#':
                    listaArchivos.get(posicionLista).getArchivo().stack.push(listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer]);
                    break;
                case '$':
                    int poptocell = listaArchivos.get(posicionLista).getArchivo().stack.pop();
                    listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer] = poptocell;
                    break;
                default:
                    break;
            }
                //listaArchivos.get(posicionLista).getArchivo().memory[listaArchivos.get(posicionLista).getArchivo().pointer]
        }
        return salida;
    }
    
    public int btoDec(String binario) {
        int bite;
        int factor;
        int temp = 0;
        for(int i=0;i<binario.length();i++){
            bite = Integer.valueOf(binario.substring(i,i+1));
            factor = (int) Math.pow(2,(binario.length()-i-1));
            temp += bite*factor;
                    }
        return temp;
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
                listaArchivos.get(posicionLista).getArchivo().subMenuDeshacer();
                break;
            case "Rehacer":
                listaArchivos.get(posicionLista).getArchivo().subMenuRehacer();
                break;
            case "Nuevo":
                subMenuNuevo();
                break;
            case "Abrir":
                subMenuAbrir();
                break;
            case "Correr":
                {
                try {
                    MenuCorrer();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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