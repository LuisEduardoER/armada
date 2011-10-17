/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 *
 * @author Dola
 */
public class Cliente implements IDataHandler, IConnectHandler {

    @Override
    public boolean onData(INonBlockingConnection inbc) throws IOException, BufferUnderflowException, ClosedChannelException, MaxReadSizeExceededException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean onConnect(INonBlockingConnection inbc) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
