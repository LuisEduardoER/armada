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
import network.server.RoomListServer;

/**
 *
 * @author dolalima
 */
public class RoomListClientConnection extends Connection {

    // Variaveis do Cliente
    private int idClient;
    private String Address;

    public RoomListClientConnection(int id, Socket connection) {


        this.idClient = id;


        // Estabelecer Fluxo de Dados
        this.setConnection(connection);
        this.configTransferData();
        this.Address = this.getConnection().getLocalAddress().getHostAddress();
        this.setConected(true);

        System.out.println("Client Conectado -> Endereço: "+this.getIpAddress());

    }

    @Override
    public void listenConnection() {
        Package pack = new Package();

        try {
            String data = this.getInput().readUTF();
            if (data.equals("")) {
                pack = pack.fromXml(data);
                RoomListServer.processClientPackage(pack);
            }


        } catch (IOException ex) {
            Logger.getLogger(RoomListClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            this.interrupt();
        }


        
    }

    
}
