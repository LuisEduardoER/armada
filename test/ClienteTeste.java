
import java.io.IOException;
import java.util.Scanner;
import network.Cliente;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dolalima
 */
public class ClienteTeste {
    private static Scanner input = new Scanner(System.in);
    private static String mm = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Cliente c = new Cliente("Dola");
        
        
        while (!mm.equals("sair")){
            mm = input.nextLine();
            if (mm.length()>0){
                c.sendMessege("",mm);
            } 
        }
        c.onClose();
    }
}
