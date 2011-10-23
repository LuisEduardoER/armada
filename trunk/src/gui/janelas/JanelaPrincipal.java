package gui.janelas;

import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import gui.paineis.PainelLateral;
import gui.paineis.PainelPlacar;
import gui.paineis.PainelPrincipal;
import gui.paineis.PainelTabuleiro;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

/**
 *
 * @author arthur
 */
public class JanelaPrincipal extends JFrame implements KeyListener {

    //--------PAINEIS-----------
    private PainelPrincipal painelPrincipal;
    private PainelLateral painelLateral;
    private JSplitPane painelSeparacao;
    //---------JOGADORES-----------
    private Jogador jogador1;
    private Jogador jogador2;
    //--------TAMANHO-----------
    private int largura;
    private int altura;
    //---------------------
    private Navio navioSelecionado;

    /** Creates new form JanelaPrincipal */
    public JanelaPrincipal(Jogador jogador1, Jogador jogador2) {
        addKeyListener(this);

        this.setTitle("Armada");
        this.setFocusable(true);

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.jogador1 = jogador1;
        this.jogador2 = jogador2;

        this.construirPaineis();
        this.escolherPosicoesNavios();

        this.setarTamanho();

        this.pack();
    }

    private void construirPaineis() {
        this.painelSeparacao = new JSplitPane();
        this.painelSeparacao.setDividerLocation(180);
        this.painelSeparacao.setDividerSize(2);

        this.painelLateral = new PainelLateral(this.jogador1, this.jogador2);
        this.painelSeparacao.setLeftComponent(painelLateral);

        this.add(painelSeparacao, BorderLayout.CENTER);

        this.painelPrincipal = new PainelPrincipal(this, this.jogador1, this.jogador2);
        this.painelSeparacao.setRightComponent(painelPrincipal);
    }

    private void setarTamanho() {
        PainelTabuleiro tabuleiroJogador = painelPrincipal.getTabuleiroJogador();
        PainelPlacar placarJogador = painelPrincipal.getPlacarJogador();

        this.largura += this.painelLateral.getPainelChat().getPreferredSize().width + 30;
        this.largura += tabuleiroJogador.getPreferredSize().width + 20;

        this.altura += tabuleiroJogador.getPreferredSize().height + 90;
        this.altura += placarJogador.getPreferredSize().height;

        System.out.println(altura);
        System.out.println(largura);

        this.setPreferredSize(new java.awt.Dimension(largura, altura));
    }

    private void escolherPosicoesNavios() {
    }

    public void selecionarNavio(Navio navio) {
        this.navioSelecionado = navio;
        this.requestFocus();
    }

    public void selecionarPosicao(int[] pos) {
        PainelTabuleiro tabuleiroJogador = painelPrincipal.getTabuleiroJogador();
        PainelPlacar[] placares = painelPrincipal.getPlacares();

        //se esta selecionando do placar
        if(this.navioSelecionado != null){
            this.navioSelecionado.setPos(pos);
            this.navioSelecionado = null;
            tabuleiroJogador.atualizarPosicaoNavios();
            
            if(tabuleiroJogador.getQtdeNaviosSemPosicao() == 0){
                this.painelLateral.getPainelInfo().getBotaoPreparar().setVisible(true);
            }

            placares[0].atualizar();
        } else {  //se esta reposicionando o navio
            this.painelLateral.getPainelInfo().getBotaoPreparar().setVisible(false);
            Navio navio = tabuleiroJogador.getNavio(pos);
            if(navio != null){
                navio.setPos(null);
                this.navioSelecionado = navio;
                tabuleiroJogador.atualizarPosicaoNavios();
                tabuleiroJogador.mouseOver();
            }
        }


        this.requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Typed.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.navioSelecionado != null){
            if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){
                this.navioSelecionado.setOrientacao(Orientacao.VERTICAL);
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT){
                this.navioSelecionado.setOrientacao(Orientacao.HORIZONTAL);
            }

            painelPrincipal.getTabuleiroJogador().mouseOver();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Released.");
    }

    public PainelTabuleiro[] getTabuleiros() {
        return painelPrincipal.getTabuleiros();
    }

    public PainelPlacar[] getPlacares() {
        return painelPrincipal.getPlacares();
    }

    public Navio getNavioSelecionado() {
        return navioSelecionado;
    }
}