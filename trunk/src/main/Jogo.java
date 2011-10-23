/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.janelas.JanelaPrincipal;
import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import gui.janelas.JanelaSalas;
import gui.paineis.PainelPrincipal;

/**
 *
 * @author arthur
 */
public class Jogo {    
    
    private JanelaSalas janelaSalas;
    private JanelaPrincipal janelaPrincipal;
    
    private Jogador jogador1;
    private Jogador jogador2;
    
    private Navio navioSelecionado;

    public Jogo() {
        jogador1 = new Jogador();
        jogador2 = new Jogador();
        
                
        this.janelaSalas = new JanelaSalas();        
        this.janelaPrincipal = new JanelaPrincipal(jogador1, jogador2);
        
        //comente qual que não deve aparecer (para testes)
        //this.janelaSalas.setVisible(true);
        this.janelaPrincipal.setVisible(true);
    }
    
    public void selecionarNavio(Navio navio){
        this.navioSelecionado = navio;
    }

    public void selecionarPosicao(int[] pos) {
        if(this.navioSelecionado != null) {
            this.navioSelecionado.setPos(pos);
            this.navioSelecionado = null;
            this.janelaPrincipal.getTabuleiros()[PainelPrincipal.JOGADOR].atualizarPosicaoNavios();
        }
    }
    
    public void selecionarOrientacao(Orientacao orientacao){
        this.navioSelecionado.setOrientacao(orientacao);
    }
}
