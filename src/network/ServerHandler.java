package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import main.ServerMain;
import org.xsocket.connection.IConnectExceptionHandler;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;

public class ServerHandler implements IDataHandler, IConnectHandler, IDisconnectHandler, IConnectExceptionHandler {

    private final List<INonBlockingConnection> connections = Collections.synchronizedList(new ArrayList<INonBlockingConnection>());
    private ArrayList<String> names = new ArrayList<String>();

    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException {
        String message = nbc.readStringByDelimiter("\n");

        if(message.trim().length() > 0 && message.contains("~~")){
            String[] m = message.split("~~");
            if(!names.contains(m[0])) {
                names.add(m[0]);
                updateUsuarios();
            }
            if(m.length == 2) {
                sendMessageToAll(m[0], m[1]);  
            } else {
                sendMessageTo(m[0], m[1], m[2]);                
            }
        }

        return true;
    }

    public void sendMessageTo(final String nome, final String dest, final String message) {
        try {
            synchronized(connections){
                Iterator<INonBlockingConnection> iter = connections.iterator();

                for(int i = 0; iter.hasNext(); i++){
                    final INonBlockingConnection nbConn = (INonBlockingConnection) iter.next();

                    if(nbConn.isOpen()){
                        Thread t = new Thread() {
                            @Override
                            public void start() {
                                try {
                                    if(message.charAt(0) == '#'){
                                        nbConn.write(message + "\n");
                                    } else {
                                        String d = "";
                                        if(!dest.equals("")) {
                                            d = dest + "~~";
                                        }
                                        nbConn.write(nome + "~~" + d + message + "\n");
                                    }
                                } catch(IOException ex){
                                }
                            }
                        };
                        t.start();

                    }
                }
            }
            String d = "";
            if(!dest.equals("")) d = " to " + dest;
            if(message.charAt(0) != '#' && (dest.equals(ServerMain.getServidor().getName()) || dest.equals("") ||
                    nome.equals(ServerMain.getServidor().getName()))){
                //ServerMain.getServidor().displayMessage(nome + d + ": " + message);
                
            }
        } catch(Exception ex){
            System.out.println("sendMessageToAll: " + ex.getMessage());
        }
    }
    
    public void sendMessageToAll(final String nome, final String message) {
        sendMessageTo(nome, "", message);
    }

    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException {
        connections.add(nbc);
        return true;
    }

    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        names.remove(connections.indexOf(nbc));
        connections.remove(nbc);
        updateUsuarios();
        return true;
    }
    
    private void updateUsuarios(){
        String n1 = "#All~~" + ServerMain.getServidor().getName();
        String n2 = "#All";
        for(int i = 0; i < names.size(); i++){
            n1 += "~~" + names.get(i);
            n2 += "~~" + names.get(i);
        }
        sendMessageToAll(ServerMain.getServidor().getName(), n1);
        n2 = n2.substring(1);
        String[] users = n2.split("~~");
        //ServerMain.getServidor().setUsersModel(users);        
    }

    @Override
    public boolean onConnectException(INonBlockingConnection inbc, IOException ioe) throws IOException {
        return true;
    }
}