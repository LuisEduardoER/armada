/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.janelas.JanelaPrincipal;
import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import classes.TipoJogador;
import gui.janelas.JanelaSalas;

/**
 *
 * @author arthur
 */
public class Jogo {    
    
    private JanelaSalas janelaSalas;
    private JanelaPrincipal janelaPrincipal;
    
    private Jogador jogadorLocal;
    private Jogador jogadorAdversario;
    
    private Navio navioSelecionado;
    
    public static int turno;
    public static boolean modoPreparacao;

    public Jogo() {
        this.jogadorLocal = new Jogador(TipoJogador.LOCAL);
        this.jogadorAdversario = new Jogador(TipoJogador.ADVERSARIO);     
        
        Jogo.turno = 1;
        Jogo.modoPreparacao = true;   
                
        this.janelaSalas = new JanelaSalas();        
        this.janelaPrincipal = new JanelaPrincipal(jogadorLocal, jogadorAdversario);
        
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
            this.janelaPrincipal.getTabuleiros()[0].atualizarPosicaoNavios();
        }
    }
    
    public void selecionarOrientacao(Orientacao orientacao){
        this.navioSelecionado.setOrientacao(orientacao);
    }
    
    public void preparar(Jogador jogador){
        jogador.setPreparado(true);
        if(jogador.getTipo() == TipoJogador.ADVERSARIO){
            if(jogadorLocal.isPreparado()){
                janelaPrincipal.jogar();
            } else {
                jogadorAdversario.setComecaJogando(true);
                janelaPrincipal.prepararAdversario();
            }
        } else {
            Jogo.modoPreparacao = false;
            //enviarMsgParaAdversario();
            if(jogadorAdversario.isPreparado()){
                janelaPrincipal.jogar();
            } else {
                jogadorLocal.setComecaJogando(true);
            }
        }            
    }
    
    
    public void atacar(TipoJogador tipo, int[] pos){
        if(tipo == TipoJogador.LOCAL){
            jogadorLocal.getTabuleiro().getCasas()[pos[0]][pos[1]].atingido = true;
        } else {
            jogadorAdversario.getTabuleiro().getCasas()[pos[0]][pos[1]].atingido = true;
            //enviarTiroParaAdversario();
        }
    }
    

    public JanelaPrincipal getJanelaPrincipal() {
        return janelaPrincipal;
    }
}
