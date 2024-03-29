/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import classes.Casa;
import gui.janelas.JanelaPrincipal;
import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import classes.TipoJogador;
import gui.janelas.JanelaSalas;
import java.util.ArrayList;
import network.datapackage.Package;

/**
 *
 * @author arthur
 */
public class Jogo {

    public static void processClientPackage(Package pack) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private JanelaSalas janelaSalas;
    private JanelaPrincipal janelaPrincipal;
    private Jogador jogadorLocal;
    private Jogador jogadorAdversario;
    private Navio navioSelecionado;
    public static int turno;
    public static boolean modoPreparacao;

    public Jogo() {
        this.jogadorLocal = new Jogador(TipoJogador.LOCAL);
        this.jogadorAdversario = new Jogador();

        Jogo.turno = 0;
        Jogo.modoPreparacao = true;
        
        this.janelaSalas = new JanelaSalas();
        this.janelaPrincipal = new JanelaPrincipal(jogadorLocal, jogadorAdversario);

        iniciarJanelaSalas();
    }
    
    
    public void iniciarJanelaSalas(){
        this.janelaSalas.setVisible(true);
    }
    
    
    public void iniciarJanelaPrincipal(String nomeJogador){
        jogadorLocal.setNome(nomeJogador);
        
        this.janelaPrincipal.setVisible(true);
    }
    
    
    public void conectarJogadorAdversario(String nome){
        jogadorAdversario.setNome(nome);
        janelaPrincipal.conectarJogadorAdversario(nome);
    }
    
    
    public void receberMensagem(String mensagem){
        this.janelaPrincipal.getPainelLateral().getPainelChat().receberMensagem(mensagem);
    }
    

    public void selecionarNavio(Navio navio) {
        this.navioSelecionado = navio;
    }

    public void selecionarPosicao(int[] pos) {
        if(this.navioSelecionado != null){
            this.navioSelecionado.setPos(pos);
            this.navioSelecionado = null;
            this.janelaPrincipal.getPainelPrincipal().getTabuleiroJogador().atualizarPosicaoNavios();
        }
    }

    public void selecionarOrientacao(Orientacao orientacao) {
        this.navioSelecionado.setOrientacao(orientacao);
    }

    public void preparar(Jogador jogador) {
        jogador.setPreparado(true);
        if(jogador.getTipo() == TipoJogador.ADVERSARIO){
            if(jogadorLocal.isPreparado()){
                janelaPrincipal.jogar();
                trocarTurno();
            } else {
                jogadorAdversario.setComecaJogando(true);
                janelaPrincipal.prepararAdversario();
            }
        } else {
            Jogo.modoPreparacao = false;
            //enviarMsgParaAdversario();
            if(jogadorAdversario.isPreparado()){
                janelaPrincipal.jogar();
                trocarTurno();
            } else {
                jogadorLocal.setComecaJogando(true);
            }
        }
        
        Runtime.getRuntime().gc();
    }

    public void trocarTurno() {
        Jogador jogador = Jogo.getJogador(true);
        jogador.getTiros().clear();

        Jogo.turno++;

        if(jogador.getTipo() == TipoJogador.ADVERSARIO){
            janelaPrincipal.getPainelPrincipal().getTabuleiroJogador().limparGrid(false, true);
            janelaPrincipal.getPainelPrincipal().getPlacarJogador().atualizar();
        } else {
            janelaPrincipal.getPainelPrincipal().getTabuleiroAdversario().limparGrid(false, true);
            janelaPrincipal.getPainelPrincipal().getPlacarAdversario().atualizar();
        }

        janelaPrincipal.getPainelPrincipal().atualizarNomeTurno();
        janelaPrincipal.getPainelLateral().getPainelInfo().reiniciar();
    }

    /**
     * Adiciona um ataque à lista de ataques do jogador passado como parametro.
     * @param posicao que irá ser atacada
     * @return true caso o ataque seja uma adição, e false caso ja existia antes ou ja tenha acabado a qtde de tiros.
     */
    public void adicionarAtaque(int[] pos) {
        Jogador atirador = Jogo.getJogador(true);
        Jogador levadorDeBala = Jogo.getJogador(false);

        Integer[] posicao = toInteger(pos);
        int posicaoNaLista = this.getIndexPosicao(atirador.getTiros(), posicao);

        //se for um lugar ja atingido anteriormente, retorne.
        if(levadorDeBala.getTabuleiro().getCasas()[pos[0]][pos[1]].atingido){
            return;
        }

        //se ataque ja existe, exclua ele.
        if(posicaoNaLista >= 0){
            atirador.getTiros().remove(posicaoNaLista);
        } else {
            //se nao acabou o limite de tiros.
            if(atirador.getTiros().size() < atirador.getTabuleiro().getQtdeNaviosVivos()){
                atirador.getTiros().add(posicao);
            }
        }

        janelaPrincipal.getPainelLateral().getPainelInfo().atualizarContagemTiros();
        if(atirador.getTipo() == TipoJogador.ADVERSARIO){
            janelaPrincipal.getPainelPrincipal().getTabuleiroJogador().atualizarTiros();
        } else {
            janelaPrincipal.getPainelPrincipal().getTabuleiroAdversario().atualizarTiros();
        }
    }
    
    
    public void atirar(){
        Jogador atirador = Jogo.getJogador(true);
        Jogador levadorDeBala = Jogo.getJogador(false);
        
        ArrayList<Integer[]> tiros = atirador.getTiros();
        
        for(Integer[] pos : tiros){
            levadorDeBala.getTabuleiro().getCasas()[pos[0]][pos[1]].atingido = true;
        }
        
        this.atualizarStatusNavios(levadorDeBala);
        
        if(levadorDeBala.getTipo() == TipoJogador.LOCAL){
            janelaPrincipal.getPainelPrincipal().getTabuleiroJogador().atirar();
        } else {
            janelaPrincipal.getPainelPrincipal().getTabuleiroAdversario().atirar();
        }
        
        if(levadorDeBala.getTabuleiro().todosNaviosDerrubados()){
            janelaPrincipal.ganhar(atirador);
        }
        
        this.trocarTurno();
    }

    public void atualizarStatusNavios(Jogador jogador) {
        Casa[][] casas = jogador.getTabuleiro().getCasas();
        Navio[] navios = jogador.getTabuleiro().getNavios();

        boolean morto = false;
        int x = 0;
        int y = 0;
        for(Navio navio : navios){
            morto = true;
            x = navio.getCoordenadas().get(0)[0];
            y = navio.getCoordenadas().get(0)[1];
            for(int i = 0; i < navio.getTamanho(); i++){
                if(navio.getOrientacao() == Orientacao.HORIZONTAL){
                    morto = morto && casas[x + i][y].atingido;
                } else {
                    morto = morto && casas[x][y + i].atingido;
                }
            }
            navio.setVivo(!morto);
        }
    }



    /**
     * Converte um array de tipo int (primitivo) par um array de Integer (objeto)
     * @param array
     * @return
     */
    public Integer[] toInteger(int[] array) {
        Integer[] lista = new Integer[array.length];
        int i = 0;
        for(int val : array){
            lista[i++] = val;
        }

        return lista;
    }

    /**
     * Retorna a posicão em que o array de inteiros se encontra no ArrayList.
     * Se não estiver na lista, retorna -1.
     * 
     * @param lista
     * @param pos
     * @return index da array no ArrayList
     */
    public int getIndexPosicao(ArrayList<Integer[]> lista, Integer[] pos) {

        int i = 0;
        for(Integer[] val : lista){
            if(val[0] == pos[0] && val[1] == pos[1]){
                return i;
            }
            i++;
        }

        return -1;
    }

    public static Jogador getJogador(boolean jogandoNesteTurno) {
        int a = 0;
        int b = 1;

        if(jogandoNesteTurno){
            a = 1;
            b = 0;
        }

        if((Jogo.turno % 2 == a && Main.jogo.jogadorLocal.comecaJogando()) || (Jogo.turno % 2 == b && !Main.jogo.jogadorLocal.comecaJogando())){
            return Main.jogo.jogadorLocal;
        } else {
            return Main.jogo.jogadorAdversario;
        }
    }

    public JanelaPrincipal getJanelaPrincipal() {
        return janelaPrincipal;
    }
}
