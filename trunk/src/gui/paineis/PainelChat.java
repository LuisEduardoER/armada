/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.paineis;

import classes.Jogador;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author arthur
 */
public class PainelChat extends JPanel {

    private JScrollPane painelScroll;
    private JTextArea areaTexto;
    private JTextField campoMensagem;
    
    private Jogador ultimoEnviador;
    
    private Jogador jogador1;
    private Jogador jogador2;

    public PainelChat(Jogador jogador1, Jogador jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        
        this.construir();
    }
    
    private void construir(){
        painelScroll = new JScrollPane();
        areaTexto = new JTextArea();
        campoMensagem = new JTextField();
        
        this.campoMensagem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(evt.getKeyCode() == KeyEvent.VK_ENTER)
                    enviouMensagem();
            }
        });

        this.setLayout(new BorderLayout());

        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setColumns(15);
        areaTexto.setRows(5);
        painelScroll.setViewportView(areaTexto);

        this.add(painelScroll, BorderLayout.CENTER);
        this.add(campoMensagem, BorderLayout.SOUTH);
        
        System.out.println(this.getPreferredSize());
    }
    
    
    private void enviouMensagem(){
        String nome = this.jogador1.getNome() + " diz:\n";
        
        if(this.ultimoEnviador != null && this.ultimoEnviador.equals(jogador1))
            nome = "";
        
        this.areaTexto.append(nome + "   " + this.campoMensagem.getText() + "\n");
        this.campoMensagem.setText("");
        
        this.ultimoEnviador = jogador1;
    }
    

    public JTextArea getAreaTexto() {
        return areaTexto;
    }

    public JTextField getCampoMensagem() {
        return campoMensagem;
    }

    public JScrollPane getPainelScroll() {
        return painelScroll;
    }
}
