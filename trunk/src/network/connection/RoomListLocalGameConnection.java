/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Connection;
import network.datapackage.Package;
import network.server.RoomListServer;

/**
 *
 * @author dolalima
 */
public class RoomListLocalGameConnection extends Connection{
    
    private int idRoom;
    private String Address;
    
    
    
    public RoomListLocalGameConnection(int id, Socket connection){
        
        try {
            this.idRoom = id;
            


            // Estabelecer Fluxo de Dados
            this.setConnection(connection);
            this.setInput(new DataInputStream(this.getConnection().getInputStream()));
            this.setOutput(new DataOutputStream(this.getConnection().getOutputStream()));
            this.Address = this.getConnection().getInetAddress().toString();
            this.setConected(true);

            System.out.println("Client Conectado");
        } catch (IOException ex) {
            Logger.getLogger(RoomListClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro ao obter fluxo de dados do conexão:\n"
                + " Client do slot de conexão = " + this.idRoom);
        }
        
    }
    
    
    @Override
    protected void listenConnection() {
        
        Package pack = new Package();
        
        try {
            String data = this.getInput().readUTF();
            pack = pack.fromXml(data);
            
            RoomListServer.processClientPackage(pack);
            
        } catch (IOException ex) {
            Logger.getLogger(RoomListClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (this.isConected()){
            this.listenConnection();
        }
    }
    
}
