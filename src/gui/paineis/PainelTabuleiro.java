package gui.paineis;

import gui.janelas.JanelaPrincipal;
import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import classes.TipoJogador;
import gui.outros.BotaoTabuleiro;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.Jogo;
import main.Main;

/**
 *
 * @author arthur
 */
public class PainelTabuleiro extends JPanel {

    private JanelaPrincipal pai;
    private Jogador jogador;
    private int[] posicaoMouseOver;   //posicao no grid que o cursor esta em cima. É atualizado pelo metodo mouseEntered
    private BotaoTabuleiro[][] grid;
    private int tamanho;  // tamanho do grid (tamanho x tamanho)
    private int t = 33;   //tamanho do botao
    
    //----------CONSTANTES------------
    private final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Color corPadrao = Color.white;
    private final Color corSelecao = new Color(65, 225, 105);
    private final Color corSelecaoErro = new Color(255, 30, 65);
    private final ImageIcon iconeVazio = new ImageIcon();
    private final ImageIcon iconeAcerto = new ImageIcon(getClass().getResource("/images/tiro-acerto.png"));
    private final ImageIcon iconeErro = new ImageIcon(getClass().getResource("/images/tiro-erro.png"));

    /** Creates new form PainelTabuleiro
     * @param pai 
     * @param jogador 
     */
    public PainelTabuleiro(JanelaPrincipal pai, Jogador jogador) {
        this.pai = pai;
        this.jogador = jogador;
        this.tamanho = this.jogador.getTabuleiro().getTamanho();

        int size = tamanho * t + 20;
        this.setSize(new Dimension(size, size));

        this.grid = new BotaoTabuleiro[tamanho][tamanho];

        this.construirGrid();
    }

    private void construirGrid() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        add(new JLabel(), gridBagConstraints);

        for(int i = 1; i < tamanho + 1; i++){
            JLabel labelLetra = new JLabel(String.valueOf(i));
            gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
            add(labelLetra, gridBagConstraints);
        }

        for(int i = 0; i < tamanho; i++){
            JLabel labelLetra = new JLabel(String.valueOf(LETRAS.charAt(i)));
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = i + 1;
            gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
            add(labelLetra, gridBagConstraints);

            BufferedImage bi = null;
            try {
                InputStream is = getClass().getResourceAsStream("/images/bg/mar.jpg");
                bi = ImageIO.read(is);
            } catch(IOException e){
            }

            for(int j = 0; j < tamanho; j++){
                this.grid[i][j] = new BotaoTabuleiro(bi.getSubimage(i * t, j * t, t, t));
                this.grid[i][j].setPreferredSize(new java.awt.Dimension(this.t, this.t));
                this.grid[i][j].setOpaque(true);
                this.grid[i][j].setBackground(this.corPadrao);
                this.grid[i][j].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseEntered(MouseEvent evt) {
                        JButton botao = (JButton) evt.getComponent();
                        posicaoMouseOver = getPosicao(botao);
                        mouseOver();
                    }

                    @Override
                    public void mouseExited(MouseEvent evt) {
                        limparGrid(false, false);
                    }

                    @Override
                    public void mouseReleased(MouseEvent evt) {
                        clicarBotaoGrid(evt);
                    }
                });
                gridBagConstraints.gridx = i + 1;
                gridBagConstraints.gridy = j + 1;
                gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
                add(this.grid[i][j], gridBagConstraints);
            }
        }
    }

    private void clicarBotaoGrid(MouseEvent evt) {
        JButton botao = (JButton) evt.getComponent();
        posicaoMouseOver = getPosicao(botao);

        if(Jogo.modoPreparacao){
            ArrayList<Integer[]> coords = getCoordenadasNavioSelecionado(posicaoMouseOver);

            if(jogador.getTabuleiro().coordenadasEstaoLivres(coords)){
                int[] pos = posicaoMouseOver;
                if(!coords.isEmpty()){
                    pos = new int[]{coords.get(0)[0], coords.get(0)[1]};
                }

                pai.selecionarPosicao(pos);
            } else {
                mouseOver(posicaoMouseOver);
            }
        } else {
            /* Saca só... Isso pode ser dificil de entender... Mas como eu sou legal vou explicar
             * bem direitinho. Se liga:
             * 
             * Se o jogador começa jogando, ele joga só nos turnos impares. (1, 3, 5...)
             * Mas se ele nao começa jogando, ele joga só nos turnos pares. (2, 4, 6...) (duh)
             * Então, pra ele poder adicionar um ataque nesse tabuleiro no modo de jogo, é necessario:
             * - Ser o tabuleiro do jogador adversario. (primeira condicao)
             * - Estar na vez dele de adicionarAtaque.  (segunda condicao)
             * 
             */
            boolean turnoPar = Jogo.turno % 2 == 0;

            if(/*jogador.getTipo() == TipoJogador.ADVERSARIO
                    && */((turnoPar && jogador.comecaJogando()) || (!turnoPar && !jogador.comecaJogando()))){

                Main.jogo.adicionarAtaque(posicaoMouseOver);
            }
        }
    }

    public void atualizarTiros() {
        ArrayList<Integer[]> tiros = Jogo.getJogador(true).getTiros();

        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                if(!jogador.getTabuleiro().getCasas()[i][j].atingido){
                    grid[i][j].setNumTiro(0);
                }
            }
        }

        int i = 1;
        for(Integer[] tiro : tiros){
            grid[tiro[0]][tiro[1]].setNumTiro(i++);
            grid[tiro[0]][tiro[1]].updateUI();
        }
    }
    
    public void atirar() {
        ArrayList<Integer[]> tiros = Jogo.getJogador(true).getTiros();

        for(Integer[] tiro : tiros){
            BotaoTabuleiro botao = this.grid[tiro[0]][tiro[1]];
            botao.setNumTiro(0);
            int[] pos = new int[]{tiro[0], tiro[1]};

            if(jogador.getTabuleiro().acertouNavio(pos)){
                botao.getIcone().setImagemExtra(this.iconeAcerto);
            } else {
                botao.getIcone().setImagemExtra(this.iconeErro);
            }

            botao.updateUI();
        }

        if(jogador.getTipo() == TipoJogador.ADVERSARIO){
            mostrarNaviosMortos();
        }
    }

    public void mostrarNaviosMortos() {
        ArrayList<Navio> navios = jogador.getTabuleiro().getNaviosPosicionados();
        for(int i = 0; i < navios.size(); i++){
            Navio navio = navios.get(i);
            if(!navio.isVivo()){
                int[] pos = new int[]{navio.getPos()[0], navio.getPos()[1]};
                for(int j = 0; j < navio.getTamanho(); j++){
                    this.grid[pos[0]][pos[1]].getIcone().setImagemNavio(new ImageIcon(jogador.getTabuleiro().getCaminhoImagem(pos)));
                    this.grid[pos[0]][pos[1]].updateUI();
                    if(navio.getOrientacao() == Orientacao.HORIZONTAL){
                        pos[0]++;
                    } else {
                        pos[1]++;
                    }
                }
            }
        }
    }

    /**
     * 
     */
    public void atualizarPosicaoNavios() {
        ImageIcon img = null;
        this.limparGrid(true, false);

        ArrayList<Navio> navios = jogador.getTabuleiro().getNaviosPosicionados();
        for(Navio navio : navios){
            int x = navio.getPos()[0];
            int y = navio.getPos()[1];
            for(int j = 1; j <= navio.getTamanho(); j++){
                String ori = "h";
                if(navio.getOrientacao() == Orientacao.VERTICAL){
                    ori = "v";
                }

                img = new ImageIcon(getClass().getResource(navio.getCaminhoImagens() + ori + j + ".png"));
                grid[x][y].setImagemNavio(img);

                if(navio.getOrientacao() == Orientacao.VERTICAL){
                    y += 1;
                } else {
                    x += 1;
                }
            }
        }
    }

    public void limparGrid(boolean limparNavios, boolean limparTiros) {
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                this.grid[i][j].setBackground(this.corPadrao);
                this.grid[i][j].setHighlightMira(false);
                this.grid[i][j].setHighlightNavio(false);
                if(limparTiros){
                    this.grid[i][j].setNumTiro(0);
                    this.grid[i][j].updateUI();
                }
                if(limparNavios){
                    this.grid[i][j].setImagemNavio(iconeVazio);
                }

            }
        }
    }

    private int[] getPosicao(JButton botao) {
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                if(botao.equals(this.grid[i][j])){
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{0, 0};
    }

    private ArrayList<Integer[]> getCoordenadasNavioSelecionado(int[] pos) {
        return this.jogador.getTabuleiro().getCoordenadasNavio(pos, this.pai.getNavioSelecionado());
    }

    private void mouseOver(int[] pos) {
        Navio navio = pai.getNavioSelecionado();
        ArrayList<Integer[]> coords = this.jogador.getTabuleiro().getCoordenadasNavio(pos, navio);

        if(Jogo.modoPreparacao){
            Color cor = this.corSelecao;
            if(!jogador.getTabuleiro().coordenadasEstaoLivres(coords)){
                cor = this.corSelecaoErro;
            }

            for(int i = 0; i < coords.size(); i++){
                this.grid[coords.get(i)[0]][coords.get(i)[1]].setHighlightNavio(true);
                this.grid[coords.get(i)[0]][coords.get(i)[1]].setBackground(cor);
                this.grid[coords.get(i)[0]][coords.get(i)[1]].setOrientacao(navio.getOrientacao());
            }
        } else {
            mirar(pos);
        }
    }

    public void mouseOver() {
        this.mouseOver(posicaoMouseOver);
    }

    private void mirar(int[] pos) {
        if(this.jogador.getTipo() == TipoJogador.ADVERSARIO && Jogo.getJogador(true).getTipo() == TipoJogador.LOCAL){
            Color cor = this.corSelecaoErro;

            for(int i = 0; i < tamanho; i++){
                if(i != pos[1]){
                    this.grid[pos[0]][i].setHighlightMira(true);
                    this.grid[pos[0]][i].setBackground(cor);
                    this.grid[pos[0]][i].setOrientacao(Orientacao.VERTICAL);
                }
            }

            for(int i = 0; i < tamanho; i++){
                if(i != pos[0]){
                    this.grid[i][pos[1]].setHighlightMira(true);
                    this.grid[i][pos[1]].setBackground(cor);
                    this.grid[i][pos[1]].setOrientacao(Orientacao.HORIZONTAL);
                }
            }
        }
    }
}