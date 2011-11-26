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
    private int maxClient = 200;
    private boolean run = false;
    private boolean roomListFull;
    // Lista de Conexoẽs com Thread
    private RoomList roomList;
    private ClientList clientList;
    // Conexões
    private ServerSocket roomServer;
    private ServerSocket clientServer;
    
    

    public RoomListServer() {


        try {
            Log("Iniciando listas de salas e clientes.");
            this.roomServer = new ServerSocket(this.roomPort);
            this.clientServer = new ServerSocket(this.clientPort);
            Log("Inciando Thread de Conexão");
            this.clientList = new ClientList(this.clientServer,this.maxClient);
            this.roomList = new RoomList(this.roomServer,this.maxRoom);
            this.clientList.start();
            this.roomList.start();
            
            this.run = true;
            
            Log("Servidor de salas iniciado com sucesso.\n"
                    + "Aguargando Conexões:");

        } catch (IOException ex) {
            Logger.getLogger(RoomListServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void Log(String log){
        System.out.println(log);
    }
    
    public ServerSocket getClientServer() {
        return clientServer;
    }

    public void setClientServer(ServerSocket clientServer) {
        this.clientServer = clientServer;
    }

    public ServerSocket getRoomServer() {
        return roomServer;
    }

    public void setRoomServer(ServerSocket roomServer) {
        this.roomServer = roomServer;
    }

    public static void processRoomPackage(Package pack) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public static void proccesClientPackage(Package pack){
        throw new UnsupportedOperationException("Not yet implemented");
        
    }

    static void dropClient(int idClient){
        throw new UnsupportedOperationException("Not yet implemented");
        
    }
    
    static void dropRoom(int idRoom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean isRun() {
        return this.run;
    }
    
    public static void main(String[] args){
        RoomListServer s = new RoomListServer();
        
        while (s.isRun());
        
    }

    
    
    
}
