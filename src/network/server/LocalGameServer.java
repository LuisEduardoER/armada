/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.connection.LocalGameClientConnection;
import network.datapackage.Flag;
import network.datapackage.Package;

/**
 *
 * @author dolalima
 */
public class LocalGameServer {
    
    private int gamePort = 2000;
    
    private ServerSocket connection;
    private LocalGameClientConnection LocalPlayerConnection;
    private LocalGameClientConnection NetPlayerConnection;

    
    public LocalGameServer(){
        try {
            System.out.println("Iniciando Servidor de Jogo Local");
            this.connection = new ServerSocket(this.gamePort);
            
            System.out.println("Esperando Conexão do Jogador Local");
            this.LocalPlayerConnection = new LocalGameClientConnection(this.connection.accept());
            //Iniando Thread de Connnection
            this.LocalPlayerConnection.start();
            
            System.out.println("Recebendo dados do jogador local");
            this.getInfoFromLocalPlayer();
            
            System.out.println("Esperando conexão com Jogador da Rede");
            this.NetPlayerConnection = new LocalGameClientConnection(this.connection.accept());
            
        } catch (IOException ex) {
            Logger.getLogger(LocalGameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public void processClientPackage(Package pack) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void sendPackageToLocalPlayer(Package pack){
        String xml = pack.toXML();
        this.LocalPlayerConnection.sendData(xml);
    }
    
    public void senfPackageToNetPlayer(Package pack){
        String xml = pack.toXML();
        this.NetPlayerConnection.sendData(xml);
        
    }
    public void getInfoFromLocalPlayer(){
        Package pack = new Package();
        pack.setFlag(Flag.REQUIRE_ROOM_INFO);
        this.sendPackageToLocalPlayer(pack);
    }

    
        
    
}
