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
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 *
 * @author arthur
 */
public class ClientHandler implements IDataHandler, IConnectHandler {

    @Override
    public boolean onData(INonBlockingConnection inbc) {
        try {
            String message = inbc.readStringByDelimiter("\n");
            if(message.charAt(0) == '#'){
                message = message.substring(1);
                String[] users = message.split("~~");
                ArrayList<String> u = new ArrayList();
                u.addAll(Arrays.asList(users));
                for(int i = 0; i < u.size(); i++){
                    if(Cliente.getNome().equals(u.get(i))) u.remove(i);
                }
                
                //ClientMain.application.setUsersModel(u.toArray());
            } else {
                String[] m = message.split("~~");
                if(m.length == 3){
                    if(m[1].equals(Cliente.getNome()) || m[0].equals(Cliente.getNome())){
                        Cliente.displayMessage(m[0] + " to " + m[1] + ": " + m[2]);
                    }
                } else {
                    Cliente.displayMessage(m[0] + ": " + m[1]);
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
