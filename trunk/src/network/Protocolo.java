/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;


import com.thoughtworks.xstream.XStream;
import network.protocolo.*;

/**
 *
 * @author dolalima
 */
public class Protocolo {
    
    XStream xstream = new XStream();
    
    public Protocolo(){
        xstream.alias("ClassChat", Chat.class);
        xstream.alias("ClassJogador", Jogador.class);
    }
    
    
    public String chatToXml(Chat chat){
        
        String xml = xstream.toXML(chat).replace("\n", "");
        return xml;
    }
    
    public Chat xmlToChat(String xml){
        
        Chat chat = (Chat) xstream.fromXML(xml);
        return chat;
    }
    
    public String jogadorToXml(Jogador jogador){
        
        String xml = xstream.toXML(jogador).replace("\n", "");
        return xml;
    }
    
    public Jogador xmlToJogador(String xml){
        
        Jogador jogador = (Jogador) xstream.fromXML(xml);
        return jogador;
    }
    
}
