package gui.janelas;

import classes.Jogador;
import classes.Navio;
import gui.paineis.PainelChat;
import gui.paineis.PainelInformacoes;
import gui.paineis.PainelPlacar;
import gui.paineis.PainelTabuleiro;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import main.Main;

/**
 *
 * @author arthur
 */
public class JanelaPrincipal extends JFrame {
    
    public static final int JOGADOR = 0;
    public static final int ADVERSARIO = 1;
    
    
    //--------PAINEIS-----------
    private JPanel painelPrincipal;
    private JPanel painelPlacares;
    private JPanel painelTabuleiros;
    private JPanel painelMensagens;
    private JPanel painelLateral;
    
    private JSplitPane painelSeparacao;
    private PainelInformacoes painelInfo;
    private PainelChat painelChat;

    //----------ELEMENTOS-DO-PAINEL-PRINCIPAL------------
    private JLabel[] mensagens;
    private PainelPlacar[] placares;
    private PainelTabuleiro[] tabuleiros;    
    
    //---------JOGADORES-----------
    private Jogador jogador1;
    private Jogador jogador2;    
    
    //--------TAMANHO-----------
    private int largura;
    private int altura;
    
    //---------------------
    

    /** Creates new form JanelaPrincipal */
    public JanelaPrincipal(Jogador jogador1, Jogador jogador2) {
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        
        this.construirPaineis();        
        this.construirPlacaresTabuleiros(this.jogador1, this.jogador2);
        this.escolherPosicoesNavios();

        this.setarTamanho();
        
        this.pack();
    }
    
    private void construirPaineis(){
        this.painelLateral = new JPanel();
        this.painelLateral.setLayout(new BorderLayout());
        
        this.painelSeparacao = new JSplitPane();
        this.painelSeparacao.setDividerLocation(180);
        this.painelSeparacao.setDividerSize(2);
        this.painelSeparacao.setLeftComponent(painelLateral);
        
        this.add(painelSeparacao, BorderLayout.CENTER);
        
        this.painelInfo = new PainelInformacoes();
        this.painelLateral.add(this.painelInfo, BorderLayout.NORTH);
        
        this.painelChat = new PainelChat();
        this.painelLateral.add(this.painelChat, BorderLayout.CENTER);

        this.painelPrincipal = new JPanel();
        this.painelPrincipal.setLayout(new java.awt.BorderLayout());
        this.painelSeparacao.setRightComponent(painelPrincipal);
    }
    

    private void construirPlacaresTabuleiros(Jogador jogador1, Jogador jogador2) {
        this.painelTabuleiros = new JPanel();
        this.painelPlacares = new JPanel();
        this.painelMensagens = new JPanel();
        
        JScrollPane sp1 = new JScrollPane();
        JScrollPane sp2 = new JScrollPane();

        painelPlacares.setLayout(new GridLayout(1, 2, 10, 0));
        painelPrincipal.add(painelPlacares, BorderLayout.NORTH);

        painelTabuleiros.setLayout(new GridLayout(1, 2, 10, 0));
        painelPrincipal.add(painelTabuleiros, BorderLayout.CENTER);

        painelMensagens.setLayout(new GridLayout(1, 2, 10, 0));
        painelPrincipal.add(painelMensagens, BorderLayout.SOUTH);
        

        placares = new PainelPlacar[2];
        tabuleiros = new PainelTabuleiro[2];
        mensagens = new JLabel[2];

        tabuleiros[JOGADOR] = new PainelTabuleiro(this, jogador1);
        tabuleiros[ADVERSARIO] = new PainelTabuleiro(this, jogador2);

        placares[0] = new PainelPlacar(this, jogador1);
        placares[1] = new PainelPlacar(this, jogador2);

        
        for(int i = 0; i < 2; i++){
            mensagens[i] = new JLabel("Olá");
            mensagens[i].setFont(new Font("Verdana", Font.BOLD, 14));
            mensagens[i].setHorizontalAlignment(SwingConstants.CENTER);
        }

        sp1.getViewport().add(tabuleiros[JOGADOR]);
        painelTabuleiros.add(sp1);
        
        sp2.getViewport().add(tabuleiros[ADVERSARIO]);
        painelTabuleiros.add(sp2);

        painelPlacares.add(placares[0]);
        painelPlacares.add(placares[1]);
        
        painelMensagens.add(mensagens[0]);
        painelMensagens.add(mensagens[1]);

        painelTabuleiros.setPreferredSize(new Dimension(tabuleiros[JOGADOR].getSize().width * 2, tabuleiros[JOGADOR].getSize().height));
        painelPlacares.setPreferredSize(new Dimension(placares[0].getSize().width * 2, placares[0].getSize().height));
    }

    private void setarTamanho() {
        if(this.painelLateral.isVisible()) {
            this.largura += painelChat.getPreferredSize().width + 30;
        }

        this.largura += tabuleiros[JOGADOR].getPreferredSize().width * 2 + 30;

        this.altura += tabuleiros[JOGADOR].getPreferredSize().height + 70;
        this.altura += placares[0].getPreferredSize().height;

        //this.altura = 800;
        //this.largura = 1500;

        System.out.println(altura);
        System.out.println(largura);

        this.setPreferredSize(new java.awt.Dimension(largura, altura));
    }

    private void escolherPosicoesNavios() {
        //this.painelLateral.setVisible(false);
    }
    
    public void selecionarNavio(Navio navio){
        Main.jogo.selecionarNavio(navio);
    }

    public void selecionarPosicao(int[] pos){
        Main.jogo.selecionarPosicao(pos);
        for(int i = 0; i < this.placares.length; i++){
            this.placares[i].atualizar();
        }
    }

    public PainelTabuleiro[] getTabuleiros() {
        return tabuleiros;
    }

    public PainelPlacar[] getPlacares() {
        return placares;
    }
}
