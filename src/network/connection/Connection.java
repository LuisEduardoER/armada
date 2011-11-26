/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dolalima
 */
public abstract class Connection extends Thread {

    // Vaiaveis de Conexão
    private Socket connection;
    private String ipAddress;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean conected;

    
    public void connect(String host, int port) {
        try {
            this.connection = new Socket(host, port);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void configTransferData() {

        
        try {
            this.setInput(new DataInputStream(this.getConnection().getInputStream()));
            this.setOutput(new DataOutputStream(this.getConnection().getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected abstract void listenConnection();

    
    @Override
    public void run(){
        while (this.isConected()){
            this.listenConnection();
        }
    }
        
    

    public boolean isConected() {
        return conected;
    }

    public void setConected(boolean conected) {
        this.conected = conected;
    }

    public final Socket getConnection() {
        return connection;
    }

    public final void setConnection(Socket connection) {
        this.connection = connection;
    }

    public DataInputStream getInput() {
        return input;
    }

    public final void setInput(DataInputStream input) {
        this.input = input;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public final void setOutput(DataOutputStream output) {
        this.output = output;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
