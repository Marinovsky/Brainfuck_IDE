/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headfuck;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author mau
 */
public class Function_File {
    GUI gui;
    String fileName;
    String fileAddress;
    
    Function_File(GUI gui){
        this.gui=gui;
    }
    void newFile(){
        gui.textArea.setText("");
        gui.window.setTitle("New");
        fileName=null;
        fileAddress=null;   
        
    }
    void open(){
        FileDialog fd= new FileDialog(gui.window, "Open", FileDialog.LOAD);
        fd.setVisible(true);
        
        if(fd.getFile()!=null){
            fileName=fd.getFile();
            fileAddress=fd.getDirectory();
            gui.window.setTitle(fileName);
            
        }
        
        try{
            
            BufferedReader br= new BufferedReader(new FileReader(fileAddress + fileName)); //You need the address to read the file
            
            gui.textArea.setText("");
            
            String line=null;
            
            while((line = br.readLine())!=null){
                gui.textArea.append(line + "\n");
            }
            br.close();
        }catch(Exception e){
            System.out.println("FILE NOT OPENED!!");
        }
    }
    
    void Save(){
        if(fileName==null){
            saveAs();
        }else{
            try{
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(gui.textArea.getText());
                gui.window.setTitle(fileName);
                fw.close();
            
            }catch(Exception e){
                System.out.println("SOMETHING WRONG!!");
            }
        }
    }
    void saveAs(){
        FileDialog fd = new FileDialog(gui.window, "Save", FileDialog.SAVE);
        fd.setVisible(true);
        
        if(fd.getFile()!=null){
            fileName=fd.getFile();
            fileAddress=fd.getDirectory();
            gui.window.setTitle(fileName);
             
        }
        
        try{
            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(gui.textArea.getText());
            fw.close();
            
        }catch(Exception e){
            System.out.println("SOMETHING WRONG!!");
        }
    }
    
    void Exit(){
        System.exit(0);
    }
    
}
