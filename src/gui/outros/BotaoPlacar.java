/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.outros;

import classes.Navio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author arthur
 */
public class BotaoPlacar extends JButton {
    
    public static final int LARGURA = 165;
    public static final int ALTURA = 51;    
    
    private ArrayList<Navio> navios;
    
    private JLabel nome;
    private JLabel qtdeNavios;
    private JLabel icone;

    public BotaoPlacar(Navio navio) {
        this.navios = new ArrayList<Navio>();
        this.navios.add(navio);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        
        nome = new JLabel(navio.getNome());
        nome.setHorizontalAlignment(SwingConstants.CENTER);
        
        qtdeNavios = new JLabel(String.valueOf(navios.size()));
        qtdeNavios.setFont(new Font("Droid Sans Fallback", Font.BOLD, 14));
        qtdeNavios.setVerticalAlignment(SwingConstants.TOP);
        qtdeNavios.setHorizontalAlignment(SwingConstants.CENTER);
        qtdeNavios.setForeground(Color.blue);
        
        icone = new JLabel(new ImageIcon(getClass().getResource(navio.getCaminhoImagens() + Navio.VIVO)));
        
        this.add(BorderLayout.CENTER, icone);
        this.add(BorderLayout.WEST, qtdeNavios);
        this.add(BorderLayout.SOUTH, nome);

        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);
    }

    public Navio getNavio(int pos) {
        return navios.get(pos);
    }
    
    public void adicionarNavio(Navio navio){
        this.atualizar();
        this.navios.add(navio);
    }
    
    public void atualizar(){
        int qtde = this.getQtdeNaviosSemPosicao();
        if(qtde == 0){
            this.qtdeNavios.setForeground(Color.red);
        }
        this.qtdeNavios.setText(String.valueOf(this.getQtdeNaviosSemPosicao()));
    }
    
    public Navio getNavioSemPosicao(){
        for(int i = 0; i < navios.size(); i++){
            if(this.navios.get(i).getPos() == null){
                return this.navios.get(i);
            }
        }
        
        return null;
    }
    
    public int getQtdeNaviosSemPosicao(){
        int cont = 0;
        for(int i = 0; i < navios.size(); i++){
            if(this.navios.get(i).getPos() == null){
                cont++;
            }
        }
        
        return cont;
    }
}
