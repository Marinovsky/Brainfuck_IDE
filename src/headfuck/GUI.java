/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headfuck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author mau
 */
public class GUI implements ActionListener{

    /**
     * @param args the command line arguments
     */
    JFrame window;
    JPanel textPanel;
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem iNew, iOpen, iSave, iSaveAs, iExit;
    
    Function_File file = new Function_File(this);
    
    public static void main(String[] args) {
        // TODO code application logic here
        new GUI();
    }
    public GUI(){
        createWindow();
        createTextArea();
        createMenuBar();
        
        window.setVisible(true);
        textPanel.setVisible(true);
        
    }
    public void createWindow(){
        window=new JFrame("Brainfuck IDE");
        window.setSize(700, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void createTextArea(){
        textPanel= new JPanel();
        Border outerBorder = BorderFactory.createEtchedBorder();
        Border innerBorder = BorderFactory.createEmptyBorder(30, 5, 5, 300);
        textPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        
        
        textArea= new JTextArea(10,20);
        textArea.setFont(new Font("Ubuntu",Font.BOLD,20));
        textArea.setForeground(Color.GREEN);
        window.add(textPanel);
        
        scrollPane= new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        textPanel.add(scrollPane);
        window.add(textPanel);
    }
    public void createMenuBar(){
        menuBar= new JMenuBar();
        window.setJMenuBar(menuBar);
        
        menuFile=new JMenu("File");
        menuBar.add(menuFile);
        createFileMenu();
        
    }
    public void createFileMenu(){
        iNew = new JMenuItem("New");
        iNew.addActionListener(this);
        iNew.setActionCommand("New");
        menuFile.add(iNew);
        
        iOpen = new JMenuItem("Open");
        iOpen.addActionListener(this);
        iOpen.setActionCommand("Open");
        menuFile.add(iOpen);
        
        iSave = new JMenuItem("Save");
        menuFile.add(iSave);
        
        iSaveAs = new JMenuItem("Save as");
        menuFile.add(iSaveAs);
        
        iExit = new JMenuItem("Exit");
        menuFile.add(iExit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch(command){
            case "New": file.newFile(); break;
            case "Open": file.open(); break;
        }
    }  
}
