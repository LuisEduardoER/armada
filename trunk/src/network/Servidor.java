/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

/**
 *
 * @author dolalima
 */
public abstract class Servidor {
    
    private static String nome;
    
    private static IServer srv;
    private static ServerHandler handler;

       
    public static void start(String nome){
        try {
            handler = new ServerHandler();
            srv = new Server(8090, handler);
            displayMessage("Esperando Conexão...");
            srv.start();
        } catch(IOException ioException){
            System.out.println("Erro ao cria o servidor.");
        }
        
        
    }
    
    public static String getNome() {
        return nome;
    }

    
    public void sendDataTo(String destinatario, String message) throws IOException {
        handler.sendMessageTo(getNome(), destinatario, message);
    }

    
    public void sendDataToAll(String message) throws IOException {
        handler.sendMessageToAll(getNome(), message);
    }

    
    public void onClose() throws IOException {
         try {
            sendDataToAll("Saiu");
            srv.close();
        } catch(IOException ex){
            System.out.println("Erro ao desconecta o servidor.");
        }
    }

    static void displayMessage(String messege) {
        System.out.println(messege);
    }
            
            
    
}
