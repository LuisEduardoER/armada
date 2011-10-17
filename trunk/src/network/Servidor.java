/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectExceptionHandler;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 *
 * @author Dola
 */
public class Servidor implements IDataHandler, IConnectHandler,
        IDisconnectHandler, IConnectExceptionHandler {

    private final List<INonBlockingConnection> conexoes =
            Collections.synchronizedList(new ArrayList<INonBlockingConnection>());
    private ArrayList<String> jogadores = new ArrayList<String>();
    private String NomeDaSala;
    private int porta;
    private int maxConexao;

    public Servidor(String hostname, int port, int maxConn){
        NomeDaSala = hostname;
        porta = port;
        maxConexao = maxConn;
                                
    }

    public String getNomeDaSala() {
        return NomeDaSala;
    }

    
    public int getMaxConexao() {
        return maxConexao;
    }

    
    public int getPorta() {
        return porta;
    }

    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException {
        String mensagem = nbc.readStringByDelimiter("~~");
        if (mensagem.trim().length() > 0 && mensagem.contains("~~")) {
            String[] m = mensagem.split("~~");
            if (!jogadores.contains(m[0])) {
                jogadores.add(m[0]);
                atualizarJogadores();
            }
            if (m.length == 2) {
                enviaMensagemParaTodos(m[0], m[1]);
            } else {
                enviaMensagemPara(m[0], m[1], m[2]);
            }
        }

        return true;

    }

    public void enviaMensagemParaTodos(String jogador, String mensagem) {
        enviaMensagemPara(jogador,"",mensagem);
    }

    public void enviaMensagemPara(final String jogador, final String destinatario, final String mensagem) {
        try {
            synchronized (conexoes) {
                Iterator<INonBlockingConnection> iterador =
                        conexoes.iterator();

                for (int i = 1; iterador.hasNext(); i++) {
                    final INonBlockingConnection nbConn =
                            (INonBlockingConnection) iterador.next();

                    if (nbConn.isOpen()) {
                        Thread thread = new Thread() {

                            @Override
                            public void start() {
                                try {
                                    if (mensagem.charAt(0) == '#') {
                                        nbConn.write(mensagem + "\n");
                                    } else {
                                        String d = "";
                                        if (!destinatario.equals("")) {
                                            d = destinatario + "~~";
                                        }
                                        nbConn.write(jogador + "~~" + d + mensagem + "\n");
                                    }
                                } catch (IOException ex) {
                                }
                            }
                        };
                        thread.start();

                    }
                }

            }
            String d ="";
            if (!destinatario.equals("")) d = " to " + destinatario;
        } catch (Exception ex){
             System.out.print("enviaMensageemParaTodos: "+ ex.getMessage());
        }
    }

    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException{
        conexoes.add(nbc);
        return true;
    }

    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        jogadores.remove(conexoes.indexOf(nbc));
        conexoes.remove(nbc);
        atualizarJogadores();
        return true;
    }

    @Override
    public boolean onConnectException(INonBlockingConnection inbc, IOException ioe) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void atualizarJogadores() {
        String n1 = "#All~~" + getNomeDaSala();
        String n2 = "#All";
        for(int i = 0; i < jogadores.size(); i++){
            n1 += "~~" + jogadores.get(i);
            n2 += "~~" + jogadores.get(i);
        }
        enviaMensagemParaTodos(getNomeDaSala(), n1);
        n2 = n2.substring(1);
        String[] users = n2.split("~~");
         
    }
}
