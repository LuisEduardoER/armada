/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import classes.Tabuleiro;
import network.client.LocalGameClient;
import network.datapackage.Flag;
import network.datapackage.Package;
import network.server.LocalGameServer;

/**
 *
 * @author Maria Eduarda
 */
public class NetworkManager {

    private NetworkMode mode;
    
    private LocalGameClient connection;
    private static LocalGameServer LocalServer;
    
    private int gamePort = 2222;

    public void setMode(NetworkMode mode) {
        this.mode = mode;
    }

    public void connectOnIp(String address) {
        this.connection = new LocalGameClient(address);
    }

    public void CreateLocalServer() {
        this.LocalServer = new LocalGameServer();

    }
    public void sendRoomInfo(){
        Package pack = new Package();
        pack.setFlag(Flag.REQUEST_ROOM_INFO);
        
    }
    
    public void sendMessenger() {
    }

    public void sendTabuleiro(Tabuleiro table) {
    }

    public void sendAttack() {
    }
    
    private void sendPackage(Package pack){
        String xml = pack.toXML();
        //this.connection.sendData(xml);
        
    }

    public static void processClientPackage(Package pack) {
        switch (pack.getFlag()) {
            case DISCONECT:

            case LEAVE:
            //
            case REQUIRE_ROOM_LIST:
            case REQUEST_ROOM_LIST:
            //
            case REQUIRE_ROOM_INFO:
            case REQUEST_ROOM_INFO:
            //
            case REQUIRE_CLIENT_INFO:
            case REQUEST_CLIENT_INFO:
            //
            case ATTACK:
            case REQUEST_ATTACK:
            //
            case CHAT:
            case TIME:
            case TURN_TIME:
        }
    }

    public void processConnection() {
    }
}
