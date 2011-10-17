/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.paineis;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    
    private JRadioButton radioTiro1;
    private JRadioButton radioTiro2;
    private JRadioButton radioTiro3;
    private JRadioButton radioTiro4;
    private JRadioButton radioTiro5;
    
    public PainelInformacoes(){
        this.setSize(new Dimension(200,200));
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        
        labelTimer = new JLabel("00:45");
        labelTimer.setFont(new java.awt.Font("Droid Sans Fallback", 1, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.insets = new java.awt.Insets(7, 7, 7, 7);
        this.add(labelTimer, gbc);
        
        this.radioTiro1 = new JRadioButton();
        this.radioTiro2 = new JRadioButton();
        this.radioTiro3 = new JRadioButton();
        this.radioTiro4 = new JRadioButton();
        this.radioTiro5 = new JRadioButton();
        
        gbc.insets = new java.awt.Insets(1,1,1,1);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(radioTiro1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(radioTiro2, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(radioTiro3, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        this.add(radioTiro4, gbc);
        gbc.gridx = 4;
        gbc.gridy = 1;
        this.add(radioTiro5, gbc);

        botaoAtirar = new JButton("Atirar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(0, 5, 5, 5);
        this.add(botaoAtirar, gbc);
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.add(new PainelInformacoes());        
        frame.setSize(new Dimension(200,200));
        frame.setVisible(true);
    }
}
