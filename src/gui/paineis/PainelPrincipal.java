/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.paineis;

import classes.Jogador;
import gui.janelas.JanelaPrincipal;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author arthur
 */
public class PainelPrincipal extends JPanel {

    public static final int JOGADOR = 0;
    public static final int ADVERSARIO = 1;
    
    private JanelaPrincipal pai;
    
    private JPanel painelPlacares;
    private JPanel painelTabuleiros;
    private JPanel painelMensagens;
    private JPanel painelNomes;
    
    JScrollPane sp1;
    JScrollPane sp2;
    
    private PainelPlacar[] placares;
    private PainelTabuleiro[] tabuleiros;
    
    private JLabel[] mensagens;
    private JLabel[] nomes;
    
    private Jogador jogador1;
    private Jogador jogador2;

    public PainelPrincipal(JanelaPrincipal pai, Jogador jogador1, Jogador jogador2) {
        this.setLayout(new java.awt.BorderLayout());
        this.pai = pai;
        
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;       
        
        this.construir();
    }
    
    
    private void construir(){
        this.painelTabuleiros = new JPanel();
        this.painelPlacares = new JPanel();
        this.painelMensagens = new JPanel();
        this.painelNomes = new JPanel();

        sp1 = new JScrollPane();
        //sp2 = new JScrollPane();

        painelPlacares.setLayout(new GridLayout(1, 2, 10, 0));
        this.add(painelPlacares, BorderLayout.NORTH);

        JPanel painelInterior = new JPanel();
        painelInterior.setLayout(new java.awt.BorderLayout());
        this.add(painelInterior, BorderLayout.CENTER);

        painelTabuleiros.setLayout(new GridLayout(1, 2, 10, 0));
        painelInterior.add(painelTabuleiros, BorderLayout.CENTER);

        painelMensagens.setLayout(new GridLayout(1, 2, 10, 0));
        painelInterior.add(painelMensagens, BorderLayout.SOUTH);

        painelNomes.setLayout(new GridLayout(1, 2, 10, 0));
        painelInterior.add(painelNomes, BorderLayout.NORTH);


        placares = new PainelPlacar[2];
        tabuleiros = new PainelTabuleiro[2];
        mensagens = new JLabel[2];
        nomes = new JLabel[2];

        tabuleiros[JOGADOR] = new PainelTabuleiro(pai, jogador1);
        //tabuleiros[ADVERSARIO] = new PainelTabuleiro(pai, jogador2);

        placares[JOGADOR] = new PainelPlacar(pai, jogador1);
        //placares[ADVERSARIO] = new PainelPlacar(pai, jogador2);


        for(int i = 0; i < 1; i++){
            mensagens[i] = new JLabel("Clique no navio para adiciona-lo ao tabuleiro.");
            mensagens[i].setBorder(new EmptyBorder(2, 5, 2, 5));
            mensagens[i].setFont(new Font("Verdana", Font.BOLD, 12));
            mensagens[i].setHorizontalAlignment(SwingConstants.CENTER);

            nomes[i] = new JLabel("Arthur Taborda");
            nomes[i].setBorder(new EmptyBorder(12, 5, 1, 5));
            nomes[i].setFont(new Font("Verdana", Font.BOLD, 18));
            nomes[i].setHorizontalAlignment(SwingConstants.CENTER);
        }

        sp1.getViewport().add(tabuleiros[JOGADOR]);
        painelTabuleiros.add(sp1);

        //sp2.getViewport().add(tabuleiros[ADVERSARIO]);
        //painelTabuleiros.add(sp2);

        painelPlacares.add(placares[JOGADOR]);
        //painelPlacares.add(placares[ADVERSARIO]);

        painelMensagens.add(mensagens[JOGADOR]);
        //painelMensagens.add(mensagens[ADVERSARIO]);

        painelNomes.add(nomes[JOGADOR]);
        //painelNomes.add(nomes[ADVERSARIO]);

        painelTabuleiros.setPreferredSize(new Dimension(tabuleiros[JOGADOR].getSize().width, tabuleiros[JOGADOR].getSize().height));
        painelPlacares.setPreferredSize(new Dimension(placares[0].getSize().width, placares[0].getSize().height));
    }
    

    public JLabel[] getMensagens() {
        return mensagens;
    }

    public void setMensagens(JLabel[] mensagens) {
        this.mensagens = mensagens;
    }

    public JanelaPrincipal getPai() {
        return pai;
    }

    public void setPai(JanelaPrincipal pai) {
        this.pai = pai;
    }

    public JPanel getPainelMensagens() {
        return painelMensagens;
    }

    public void setPainelMensagens(JPanel painelMensagens) {
        this.painelMensagens = painelMensagens;
    }

    public JPanel getPainelPlacares() {
        return painelPlacares;
    }

    public void setPainelPlacares(JPanel painelPlacares) {
        this.painelPlacares = painelPlacares;
    }

    public JPanel getPainelTabuleiros() {
        return painelTabuleiros;
    }

    public void setPainelTabuleiros(JPanel painelTabuleiros) {
        this.painelTabuleiros = painelTabuleiros;
    }

    public PainelPlacar[] getPlacares() {
        return placares;
    }

    public void setPlacares(PainelPlacar[] placares) {
        this.placares = placares;
    }

    public PainelTabuleiro[] getTabuleiros() {
        return tabuleiros;
    }

    public void setTabuleiros(PainelTabuleiro[] tabuleiros) {
        this.tabuleiros = tabuleiros;
    }

    public PainelTabuleiro getTabuleiroJogador() {
        return tabuleiros[JOGADOR];
    }

    public PainelTabuleiro getTabuleiroAdversario() {
        return tabuleiros[ADVERSARIO];
    }

    public PainelPlacar getPlacarJogador() {
        return placares[JOGADOR];
    }

    public PainelPlacar getPlacarAdversario() {
        return placares[ADVERSARIO];
    }

    public String getMensagemJogador() {
        return mensagens[JOGADOR].getText();
    }

    public String getMensagemAdversario() {
        return mensagens[ADVERSARIO].getText();
    }
}
