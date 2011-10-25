package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
//import main.DB;
import network.protocolo.Chat;
import network.protocolo.Jogador;
import org.xsocket.connection.IConnectExceptionHandler;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;


public class ServerHandler implements IDataHandler, IConnectHandler, IDisconnectHandler, IConnectExceptionHandler {

    private final List<INonBlockingConnection> connections = Collections.synchronizedList(new ArrayList<INonBlockingConnection>());
    private ArrayList<String> jogadoresOnline = new ArrayList<String>();
    private  Protocolo protocolo = new Protocolo();

    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException {
        String message = nbc.readStringByDelimiter("\n");
        
        if (message.contains("</ClassChat>")){
            Chat chat = protocolo.xmlToChat(message);
            if (!jogadoresOnline.contains(chat.getRemetente())){
                jogadoresOnline.add(chat.getRemetente());
            }
            if (chat.getDestinatario().equals("")){
                sendMessageTo(protocolo.chatToXml(chat));
            }
            String d = "";
            if(!chat.getDestinatario().equals("")) d = " to " + chat.getDestinatario();
            if(message.charAt(0) != '#' && (chat.getDestinatario().equals(Servidor.getNome()) || chat.getDestinatario().equals("") ||
                    chat.getRemetente().equals(Servidor.getNome())))
                Servidor.displayMessage(chat.getRemetente() + chat.getDestinatario() + ": " + chat.getMensagem());
        }
        
        if (message.contains("</ClassJogador>")){
            Jogador jogador = protocolo.xmlToJogador(message);
            if (!jogador.isOnline()){
                jogadoresOnline.remove(jogador.getNome());
            }
        }

        /*if(message.trim().length() > 0 && message.contains("~~")){
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
        }*/

        return true;
    }

    public void sendMessageTo(final String xml) {
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
                                    nbConn.write(xml+"\n");
                                    /*
                                    if(message.charAt(0) == '#'){
                                        nbConn.write(message + "\n");
                                    } else {
                                        String d = "";
                                        if(!dest.equals("")) {
                                            d = dest + "~~";
                                        }
                                        nbConn.write(nome + "~~" + d + message + "\n");
                                    }*/
                                } catch(IOException ex){
                                }
                            }
                        };
                        t.start();

                    }
                }
            }/*
            String d = "";
            if(!dest.equals("")) d = " to " + dest;
            if(message.charAt(0) != '#' && (dest.equals(Servidor.getNome()) || dest.equals("") ||
                    nome.equals(Servidor.getNome())))
                Servidor.displayMessage(nome + d + ": " + message);*/
        } catch(Exception ex){
            System.out.println("sendMessageToAll: " + ex.getMessage());
        }
    }
    
    /*public void sendMessageToAll(final String nome, final String message) {
        sendMessageTo(nome, "", message);
    }*/

    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException {
        //DB.inserirLog(nbc.getRemoteAddress().getHostAddress());
        connections.add(nbc);
        return true;
    }

    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        jogadoresOnline.remove(connections.indexOf(nbc));
        connections.remove(nbc);
        updateUsuarios();
        return true;
    }
    
    private void updateUsuarios(){
        String n1 = "#All~~" + Servidor.getNome();
        String n2 = "#All";
        for(int i = 0; i < jogadoresOnline.size(); i++){
            n1 += "~~" + jogadoresOnline.get(i);
            n2 += "~~" + jogadoresOnline.get(i);
        }
        //sendMessageTo(Servidor.getNome(), n1);
        n2 = n2.substring(1);
        String[] users = n2.split("~~");
        
        //Transfroma isso em metodo 
        //ServerMain.getServidor().setUsersModel(users);        
    }

    @Override
    public boolean onConnectException(INonBlockingConnection inbc, IOException ioe) throws IOException {
        //DB.inserirBlacklist(inbc.getRemoteAddress().getHostAddress());
        return true;
    }
}