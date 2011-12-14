/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Jogo;
import network.connection.Connection;
import network.datapackage.Package;

/**
 *
 * @author dolalima
 */
public class LocalGameClient extends Connection {
    
    private Socket connection;
    private boolean host;
    private int gamePort = 2000;
    
    public LocalGameClient(String ip){
        try {
            this.connection = new Socket(ip,this.gamePort);
            this.configTransferData();
        } catch (UnknownHostException ex) {
            Logger.getLogger(LocalGameClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LocalGameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    protected void listenConnection() {
         Package pack = new Package();
        try {
            String data = this.getInput().readUTF();
            if (data.equals("")) {
                pack = pack.fromXml(data);
                Jogo.processClientPackage(pack);
            }


        } catch (IOException ex) {
            ex.printStackTrace();
            this.interrupt();
        }
    }
    
}
