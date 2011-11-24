/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.connection.RoomListClientConnection;
import network.connection.RoomListLocalGameConnection;
import network.datapackage.Package;


/**
 *
 * @author dolalima
 */
public class RoomListServer {

    public static void processClientPackage(network.datapackage.Package pack) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    

    private int clientPort = 6001;
    private int roomPort = 6000;
    private int maxRoom = 200;
    private boolean run;
    private boolean roomListFull;
    // Lista de Conexoẽs
    private ArrayList<RoomListLocalGameConnection> roomList;
    private ArrayList<RoomListClientConnection> clientList;
    // Conexões
    private ServerSocket roomServer;
    private ServerSocket clientServer;
    // Thread de Conexão
    private Thread roomListen;
    private Thread clientListen;

    public RoomListServer() {
    }

    private void starClientListen() {
        while (this.isRun()) {

            try {

                RoomListLocalGameConnection client = new RoomListLocalGameConnection(
                        this.clientList.size(), this.clientServer.accept());
                client.start();
                this.roomList.add(client);

            } catch (IOException ex) {
                Logger.getLogger(RoomListServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void startRoomListen() {
        while (this.isRun()) {

            try {

                RoomListClientConnection client = new RoomListClientConnection(
                        this.clientList.size(), this.clientServer.accept());
                client.start();
                this.clientList.add(client);

            } catch (IOException ex) {
                Logger.getLogger(RoomListServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    
    
    
    public static void processRoomPackage(Package pack){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static void processInputData(String input) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void processOutputData(String output) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static void dropRoom(int idRoom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean isRun() {
        return this.run;
    }
}
