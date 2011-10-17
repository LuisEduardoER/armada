/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.janelas.JanelaServidor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author dolalima
 */
public class ServerMain {

    private static JanelaServidor servidor;
    
    public static void main(String args[]) {
        servidor = new JanelaServidor(); // create server
        servidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        Thread t = new Thread(){
            @Override
            public void start(){
                try { 
                    while(true){
                        Thread.sleep(10000);
                        try {
                            servidor.sendDataToAll("Bem-vindo ao melhor servidor do mundo!");
                        } catch (IOException ex) {
                            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } 
                } catch(InterruptedException ex){ }                
            }
        };
        //t.start();
        servidor.setVisible(true);
    }
    
    public static JanelaServidor getServidor(){
        if(servidor == null)
            servidor = new JanelaServidor();
        return servidor;
    }
}
