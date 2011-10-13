/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.JanelaCliente;
import javax.swing.JFrame;

/**
 *
 * @author dolalima
 */
public class ClienteMain {

    public static JanelaCliente application;

    public static void main(String args[]) {
        application = new JanelaCliente();
        application.setVisible(true);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
