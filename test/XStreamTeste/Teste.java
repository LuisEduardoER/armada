/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XStreamTeste;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 *
 * @author dolalima
 */
public class Teste {
    
    private static Player jogador;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        XStream xstream = new XStream();
        xstream.alias("Jogador", Player.class);
        
        System.out.println("Gera XML de uma classe.");
        jogador = new Player("Carlos Eduardo", "dolalima@gmail.com",180);
        
        String xml = xstream.toXML(jogador);
        System.out.println(xml);
        
        System.out.println("\nGera Classes de um XML.");
        Player newJogador = (Player) xstream.fromXML(xml);
        System.out.println("Jogador: "+ newJogador.getNome());
        System.out.println("email: " + newJogador.getEmail());
        System.out.println("altura: "+ newJogador.getAltuta()+ " cm");
        
        System.out.println("\nit Works");
        
        
        
        
        
        
    }
}
