/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.connection.RoomListClientConnection;

/**
 *
 * @author dolalima
 */
public class ClientList extends List {
    
    private RoomListClientConnection[] clientList;
    private ServerSocket server;
    private boolean run = false;
    
    public ClientList(ServerSocket server, int maxCoonection){
        
        this.clientList = new RoomListClientConnection[maxCoonection];
        this.server = server;
        
        
    }

    @Override
    public void waitConnection() {
        while (true) {
            try {
                int slot = this.getFreeSlot();
                RoomListClientConnection client = new RoomListClientConnection(
                        slot, this.server.accept());

                if (slot == -1) {
                    client.close();
                    System.out.println("Conexão com cliente rejeitada, servidor cheio");
                } else {
                    client.start();
                    this.clientList[slot] = client;
                    System.out.println("Cliente conectado no slot: " + slot);
                }

            } catch (IOException ex) {
                Logger.getLogger(RoomListServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @Override
    public void sendPackageto(int id, Package pack) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getFreeSlot() {
        int slot = -1;
        for (int i = 0; i < this.clientList.length; i++) {
            if (this.clientList[i] == null) {
                slot = i;
                break;
            }

        }
        return slot;
    }
}
