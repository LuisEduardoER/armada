/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.paineis;

import classes.Jogador;
import gui.janelas.JanelaPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import main.Jogo;

/**
 *
 * @author arthur
 */
public class PainelPrincipal extends JPanel {

    private static final int LOCAL = 0;
    private static final int ADVERSARIO = 1;
    
    private JanelaPrincipal pai;
    
    private JPanel painelPlacares;
    private JPanel painelTabuleiros;
    private JPanel painelMensagens;
    private JPanel painelNomes;
    
    private JScrollPane sp1;
    private JScrollPane sp2;
    
    private PainelPlacar[] placares;
    private PainelTabuleiro[] tabuleiros;
    
    private JLabel[] mensagens;
    private JLabel[] nomes;
    
    private Jogador[] jogadores;

    public PainelPrincipal(JanelaPrincipal pai, Jogador jogadorLocal, Jogador jogadorAdversario) {
        this.setLayout(new java.awt.BorderLayout());
        this.pai = pai;
        
        this.jogadores = new Jogador[2];
        
        this.jogadores[LOCAL] = jogadorLocal;
        this.jogadores[ADVERSARIO] = jogadorAdversario;       
        
        this.construir();
    }
    
    
    private void construir(){
        this.painelTabuleiros = new JPanel();
        this.painelPlacares = new JPanel();
        this.painelMensagens = new JPanel();
        this.painelNomes = new JPanel();

        sp1 = new JScrollPane();

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

        tabuleiros[LOCAL] = new PainelTabuleiro(pai, jogadores[LOCAL]);
        tabuleiros[ADVERSARIO] = new PainelTabuleiro(pai, jogadores[ADVERSARIO]);

        placares[LOCAL] = new PainelPlacar(pai, jogadores[LOCAL]);
        placares[ADVERSARIO] = new PainelPlacar(pai, jogadores[ADVERSARIO]);


        for(int i = 0; i < 2; i++){
            mensagens[i] = new JLabel("Clique no navio para adiciona-lo ao tabuleiro.");
            mensagens[i].setBorder(new EmptyBorder(2, 5, 2, 5));
            mensagens[i].setFont(new Font("Verdana", Font.BOLD, 12));
            mensagens[i].setHorizontalAlignment(SwingConstants.CENTER);

            nomes[i] = new JLabel(jogadores[i].getNome());
            nomes[i].setBorder(new EmptyBorder(12, 5, 1, 5));
            nomes[i].setFont(new Font("Verdana", Font.BOLD, 18));
            nomes[i].setHorizontalAlignment(SwingConstants.CENTER);
        }

        sp1.getViewport().add(tabuleiros[LOCAL]);
        painelTabuleiros.add(sp1);

        painelPlacares.add(placares[LOCAL]);
        painelMensagens.add(mensagens[LOCAL]);
        painelNomes.add(nomes[LOCAL]);

        painelTabuleiros.setPreferredSize(new Dimension(tabuleiros[LOCAL].getSize().width, tabuleiros[LOCAL].getSize().height));
        painelPlacares.setPreferredSize(new Dimension(placares[0].getSize().width, placares[0].getSize().height));
    }
    
    
    public void atualizarNomeTurno(){
        
        String ini = "";
        String fin = "";
        Color cor = Color.black;
        
        for(int i = 0; i < 2; i++){            
            if((Jogo.turno % 2 == 1 && jogadores[i].comecaJogando()) ||
               (Jogo.turno % 2 == 0 && !jogadores[i].comecaJogando())){
                cor = Color.RED;
                ini = ">>>>";
                fin = "<<<<";
            } else {
                cor = Color.black;
                ini = "";
                fin = "";
            }
            
            nomes[i].setForeground(cor);
            nomes[i].setText(ini + jogadores[i].getNome() + fin);
        }
    }
    
    
    public void ativarModoJogo(){
        sp2 = new JScrollPane();        
        sp2.getViewport().add(tabuleiros[ADVERSARIO]);
        
        painelTabuleiros.add(sp2);
        painelPlacares.add(placares[ADVERSARIO]);
        painelMensagens.add(mensagens[ADVERSARIO]);
        painelNomes.add(nomes[ADVERSARIO]);
        
        placares[LOCAL].atualizar();
        placares[ADVERSARIO].atualizar();
        
        //tabuleiros[ADVERSARIO].atualizarPosicaoNavios();
        
        this.updateUI();
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
        return tabuleiros[LOCAL];
    }

    public PainelTabuleiro getTabuleiroAdversario() {
        return tabuleiros[ADVERSARIO];
    }

    public PainelPlacar getPlacarJogador() {
        return placares[LOCAL];
    }

    public PainelPlacar getPlacarAdversario() {
        return placares[ADVERSARIO];
    }

    public String getMensagemJogador() {
        return mensagens[LOCAL].getText();
    }

    public String getMensagemAdversario() {
        return mensagens[ADVERSARIO].getText();
    }
}
