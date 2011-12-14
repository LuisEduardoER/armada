package gui.janelas;

import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import gui.paineis.PainelLateral;
import gui.paineis.PainelPlacar;
import gui.paineis.PainelPrincipal;
import gui.paineis.PainelTabuleiro;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    private Jogador jogadorLocal;
    private Jogador jogadorAdversario;
    //--------TAMANHO-----------
    private int largura;
    private int altura;
    //---------------------
    private Navio navioSelecionado;

    /** Creates new form JanelaPrincipal */
    public JanelaPrincipal(Jogador jogador1, Jogador jogador2) {
        addKeyListener(this);
                
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        setLocation(x, y);

        this.setTitle("Armada");
        this.setFocusable(true);

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.jogadorLocal = jogador1;
        this.jogadorAdversario = jogador2;

        this.construirPaineis();

        this.redimensionarJanela(1);

        this.pack();
    }
    
    
    public void ganhar(Jogador jogador){
        JanelaVitoria janela = new JanelaVitoria(this, true, jogador.getNome());
        
        janela.setModalityType(java.awt.Dialog.DEFAULT_MODALITY_TYPE);
        janela.setModal(true);
        janela.setVisible(true);
    }
    

    private void construirPaineis() {
        this.painelSeparacao = new JSplitPane();
        this.painelSeparacao.setDividerLocation(180);
        this.painelSeparacao.setDividerSize(2);

        this.painelLateral = new PainelLateral(this.jogadorLocal, this.jogadorAdversario);
        this.painelSeparacao.setLeftComponent(painelLateral);

        this.add(painelSeparacao, BorderLayout.CENTER);

        this.painelPrincipal = new PainelPrincipal(this, this.jogadorLocal, this.jogadorAdversario);
        this.painelSeparacao.setRightComponent(painelPrincipal);
    }

    private void redimensionarJanela(int qtdeJogadores) {
        PainelTabuleiro tabuleiroJogador = painelPrincipal.getTabuleiroJogador();
        PainelPlacar placarJogador = painelPrincipal.getPlacarJogador();
        
        if(qtdeJogadores == 1)
            this.largura = 50;
        else this.largura = -60;
        this.altura = 0;

        this.largura += (this.painelLateral.getPainelChat().getPreferredSize().width) * qtdeJogadores;
        this.largura += (tabuleiroJogador.getPreferredSize().width) * qtdeJogadores;

        this.altura += tabuleiroJogador.getPreferredSize().height + 90;
        this.altura += placarJogador.getPreferredSize().height;

        System.out.println(altura);
        System.out.println(largura);

        this.setPreferredSize(new java.awt.Dimension(largura, altura));
        this.setSize(new java.awt.Dimension(largura, altura));
    }
    

    public void selecionarNavio(Navio navio) {
        this.navioSelecionado = navio;
        this.requestFocus();
    }
    
    
    public void conectarJogadorAdversario(String nome){
        JOptionPane.showMessageDialog(this, nome + " se conectou e é o seu adversário!", "Adversario Conectado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void jogar(){
        this.painelPrincipal.ativarModoJogo();
        this.redimensionarJanela(2);
        this.painelLateral.getPainelInfo().ativarModoJogo();
    }

    
    public void selecionarPosicao(int[] pos) {
        PainelTabuleiro tabuleiroJogador = painelPrincipal.getTabuleiroJogador();
        PainelPlacar[] placares = painelPrincipal.getPlacares();

        //se esta selecionando do placar
        if(this.navioSelecionado != null){
            this.navioSelecionado.setPos(pos);
            this.navioSelecionado = null;
            tabuleiroJogador.atualizarPosicaoNavios();
            
            if(jogadorLocal.getTabuleiro().getQtdeNaviosSemPosicao() == 0){
                this.painelLateral.getPainelInfo().getBotaoPreparar().setVisible(true);
            }

            placares[0].atualizar();
        } else {  //se esta reposicionando o navio
            this.painelLateral.getPainelInfo().getBotaoPreparar().setVisible(false);
            Navio navio = jogadorLocal.getTabuleiro().getNavio(pos);
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
    
    public void prepararAdversario(){
        this.painelLateral.getPainelInfo().getLabelAdversarioPreparado().setVisible(true);
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

    public PainelLateral getPainelLateral() {
        return painelLateral;
    }

    public PainelPrincipal getPainelPrincipal() {
        return painelPrincipal;
    }

    public JSplitPane getPainelSeparacao() {
        return painelSeparacao;
    }
}