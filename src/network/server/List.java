/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

/**
 *
 * @author dolalima
 */
public abstract class List extends Thread {
    
    public abstract void waitConnection();
    public abstract void sendPackageto(int id,Package pack);
    public abstract int getFreeSlot();
    
    @Override
    public void run(){
        waitConnection();
    }
    
}
