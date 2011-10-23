/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.paineis;

import classes.Jogador;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author arthur
 */
public class PainelInformacoes extends JPanel{
    
    private JLabel labelTimer;
    
    private JButton botaoAtirar;
    private JButton botaoPreparar;
    
    private ArrayList<JRadioButton> tiros;   
    
    private Jogador jogador;
    
    public PainelInformacoes(Jogador jogador){   
        this.jogador = jogador;
        
        this.ativarModoPreparo();
        //this.ativarModoJogo();
    }
    
    
    private void ativarModoPreparo(){        
        this.setLayout(new BorderLayout());
        
        this.botaoPreparar = new JButton("<html><center>ESTOU<p>PRONTO<center></html>");
        this.botaoPreparar.setFont(new Font("Verdana", Font.BOLD, 30));
        this.botaoPreparar.setVisible(false);
        
        this.add(botaoPreparar, BorderLayout.CENTER);
    }
    
    
    private void ativarModoJogo(){
        this.setSize(new Dimension(200,200));
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        
        labelTimer = new JLabel("00:45");
        labelTimer.setFont(new Font("Droid Sans Fallback", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = this.jogador.getNavios().length;
        gbc.insets = new Insets(7, 7, 7, 7);
        this.add(labelTimer, gbc);
        
        this.tiros = new ArrayList<JRadioButton>();        
        gbc.insets = new java.awt.Insets(1,1,1,1);
        gbc.gridwidth = 1;
        
        int x = 0;
        int y = 1;
        for(int i = 0; i < this.jogador.getNavios().length; i++){
            tiros.add(new JRadioButton());
            tiros.get(i).setSelected(true);
            gbc.gridx = x;
            gbc.gridy = y;
            this.add(tiros.get(i), gbc);
            if(x == 5){
                x = 0;
                y++;
            } else {
                x++;
            }     
        }

        botaoAtirar = new JButton("Atirar");
        gbc.gridx = 0;
        gbc.gridy = y + 1;
        gbc.gridwidth = this.jogador.getNavios().length;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(0, 5, 5, 5);
        this.add(botaoAtirar, gbc);
    }

    public JButton getBotaoAtirar() {
        return botaoAtirar;
    }

    public JButton getBotaoPreparar() {
        return botaoPreparar;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public JLabel getLabelTimer() {
        return labelTimer;
    }

    public ArrayList<JRadioButton> getTiros() {
        return tiros;
    }
    
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.add(new PainelInformacoes(new Jogador()));        
        frame.setSize(new Dimension(200,200));
        frame.setVisible(true);
    }
}
