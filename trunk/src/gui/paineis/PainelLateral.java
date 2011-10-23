/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.paineis;

import classes.Jogador;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author arthur
 */
public class PainelLateral extends JPanel {
    
    private PainelInformacoes painelInfo;
    private PainelChat painelChat;
    
    private Jogador jogador1;
    private Jogador jogador2;
    
    public PainelLateral(Jogador jogador1, Jogador jogador2){
        this.setLayout(new BorderLayout());
        
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        
        this.painelInfo = new PainelInformacoes(this.jogador1);
        this.add(this.painelInfo, BorderLayout.NORTH);
        
        this.painelChat = new PainelChat(this.jogador1, this.jogador2);
        this.add(this.painelChat, BorderLayout.CENTER);
    }

    public PainelChat getPainelChat() {
        return painelChat;
    }

    public void setPainelChat(PainelChat painelChat) {
        this.painelChat = painelChat;
    }

    public PainelInformacoes getPainelInfo() {
        return painelInfo;
    }

    public void setPainelInfo(PainelInformacoes painelInfo) {
        this.painelInfo = painelInfo;
    }
}
