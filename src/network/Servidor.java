/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import network.protocolo.Chat;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;

/**
 *
 * @author dolalima
 */
public abstract class Servidor {
    
    private static String nome;
    private static Chat chat;
    private static IServer srv;
    private static ServerHandler handler;
    private Protocolo protocol = new Protocolo();

       
    public static void start(String nome){
        chat = new Chat(nome);
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

    
    public void sendData( String data) throws IOException {
        
        handler.sendMessageTo(data);
    }

    /*
    public void sendDataToAll(String message) throws IOException {
        handler.sendMessageToAll(String message);
    }*/

    public void  sendMessege(String destinatario , String msn) throws IOException{
        chat.setDestinatario(destinatario);
        chat.setMensagem(msn);
        sendData(protocol.chatToXml(chat));
        
        
    }
    public void onClose() throws IOException {
         try {
            
            sendMessege("","Saiu");
            srv.close();
        } catch(IOException ex){
            System.out.println("Erro ao desconecta o servidor.");
        }
    }

    static void displayMessage(String messege) {
        System.out.println(messege);
    }
            
            
    
}
