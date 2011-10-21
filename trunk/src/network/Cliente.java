/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

/**
 *
 * @author dolalima
 */
public class Cliente {

    private static String nome;
    private INonBlockingConnection bc;

    public Cliente(String nome) {
        this.nome = nome;
        try {
            bc = new NonBlockingConnection("localhost", 8090, new ClientHandler());
        } catch (IOException ioException) {
            System.out.println("Erro ao Conecta");

        }
    }

    public static String getNome() {
        return nome;
    }

   
    public void sendDataTo(String destinatario, String message) throws IOException {
        bc.write(getNome() + "~~" + destinatario + "~~" + message + "\n");
    }

   
    public void sendDataToAll(String message) throws IOException {
        bc.write(getNome() + "~~" + message + "\n");
    }

    
    public void onClose() throws IOException {
        Thread t = new Thread() {

            @Override
            public void start() {
                try {
                    sendDataToAll("Desconectou-se");
                } catch (IOException ex) {
                }
            }
        };
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
        bc.close();

    }

    static void displayMessage(String messege) {
        System.out.println(messege);
    }
}
