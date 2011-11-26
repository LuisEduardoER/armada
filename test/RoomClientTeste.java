
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.connection.Connection;
import network.datapackage.Flag;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dolalima
 */
public class RoomClientTeste extends Connection {
    
    
    private int port = 6001;
    private String host = "localhost";
    
    public RoomClientTeste(){
        
        this.connect(this.host, this.port);
        this.configTransferData();
        
        System.out.println(" Client conectado");
        
        
    }
            
    
    
    public static void main(String[] arg){
        
        RoomClientTeste client = new RoomClientTeste();
        
        
        
        
        
    }

    @Override
    protected void listenConnection() {
        
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
