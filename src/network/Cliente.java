/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.protocolo.Chat;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

/**
 *
 * @author dolalima
 */
public class Cliente {

    private static String nome;
    private INonBlockingConnection bc;
    private Chat chat;
    private Protocolo protocol = new Protocolo();

    public Cliente(String nome) {
        chat = new Chat(nome);
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
    
    public void sendData(String data) throws IOException{
        bc.write(data+"\n");
    }
    
    public void sendMessege(String destinatario, String msn){
        chat.setDestinatario(destinatario);
        chat.setMensagem(msn);
        try {
            sendData(protocol.chatToXml(chat));
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    public void onClose() throws IOException {
        Thread t = new Thread() {

            @Override
            public void start() {
                
                    sendMessege("","Desconectou-se");
                
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
