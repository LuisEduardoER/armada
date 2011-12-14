/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.datapackage.Package;
import network.server.LocalGameServer;

/**
 *
 * @author dolalima
 */
public class LocalGameClientConnection extends Connection {
    
    private LocalGameServer main;
    

    public LocalGameClientConnection(Socket connection) {
        
        this.setConnection(connection);
        this.configTransferData();
    }
    
    

    @Override
    protected void listenConnection() {
        Package pack = new Package();
        try {
            String data = this.getInput().readUTF();
            if (data.equals("")) {
                pack = pack.fromXml(data);
                main.processClientPackage(pack);
            }


        } catch (IOException ex) {
            Logger.getLogger(RoomListClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            this.interrupt();
        }
    }
    
    public void sendData(String data){
        try {
            this.getOutput().writeUTF(data);
        } catch (IOException ex) {
            Logger.getLogger(LocalGameClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
