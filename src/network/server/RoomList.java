/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.connection.RoomListLocalGameConnection;

/**
 *
 * @author dolalima
 */
public class RoomList extends List {

    private RoomListLocalGameConnection[] RoomList;
    private ServerSocket server;
    private boolean run = false;

    public RoomList(ServerSocket server, int maxCoonection) {

        this.RoomList = new RoomListLocalGameConnection[maxCoonection];
        this.server = server;
        
    }

    @Override
    public int getFreeSlot() {
        int slot = -1;
        for (int i = 0; i < this.RoomList.length; i++) {
            if (this.RoomList[i] == null) {
                slot = i;
                break;
            }

        }
        return slot;
    }

    @Override
    public void waitConnection() {
        while (true) {
            try {
                int slot = this.getFreeSlot();
                RoomListLocalGameConnection room = new RoomListLocalGameConnection(
                        slot, this.server.accept());

                if (slot == -1) {
                    room.close();
                    System.out.println("Conexão com sala rejeitada, servidor cheio");
                } else {
                    room.start();
                    this.RoomList[slot] = room;
                    System.out.println("Sala conectado no slot: " + slot);
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
}
