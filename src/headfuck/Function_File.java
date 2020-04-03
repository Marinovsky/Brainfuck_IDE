/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headfuck;

import java.awt.FileDialog;

/**
 *
 * @author mau
 */
public class Function_File {
    GUI gui;
    Function_File(GUI gui){
        this.gui=gui;
    }
    void newFile(){
        gui.textArea.setText("");
        gui.window.setTitle("New");
        
    }
    void open(){
        FileDialog fd= new FileDialog(gui.window, "Open", FileDialog.LOAD);
        fd.setVisible(true);
    }
    
}
