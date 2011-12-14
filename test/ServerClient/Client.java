/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerClient;

import network.client.LocalGameClient;

/**
 *
 * @author Maria Eduarda
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LocalGameClient client = new LocalGameClient("127.0.0.1");
    }
}
