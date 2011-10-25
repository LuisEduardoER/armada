/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Protocolo;
import network.protocolo.Chat;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 *
 * @author arthur
 */
public class ClientHandler implements IDataHandler, IConnectHandler {
    
    private Protocolo protocol = new Protocolo();

    @Override
    public boolean onData(INonBlockingConnection inbc) {
        try {
            String message = inbc.readStringByDelimiter("\n");
            if (message.contains("ClassChat")){
                Chat chat = protocol.xmlToChat(message);
                if (chat.getDestinatario().equals("")||
                        chat.getDestinatario().equals(Cliente.getNome())){
                    Cliente.displayMessage(chat.getMensagem());                    
                }
            }

        } catch(IOException ex){
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public boolean onConnect(final INonBlockingConnection inbc) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
        Thread t = new Thread(){
            @Override
            public void start(){
                try {
                    try { Thread.sleep(250); } catch(InterruptedException ex){ }
                    inbc.write(Cliente.getNome() + "~~" + "Conectou-se\n");
                } catch(IOException ex){
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
        
        return true;
    }
}
