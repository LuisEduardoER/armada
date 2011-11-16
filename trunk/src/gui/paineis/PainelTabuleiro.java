package gui.paineis;

import gui.janelas.JanelaPrincipal;
import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import classes.TipoJogador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    private final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private JButton[][] grid;
    private int tamanho;
    private int tamanhoBotao = 33;
    private boolean habilitado;
    private int[] posicaoMouseOver;
    //----------CONSTANTES------------
    private final ImageIcon iconeVazio = new ImageIcon();
    private final Color corPadrao = new JButton().getBackground();
    private final Color corSelecao = new Color(65, 105, 225);
    private final Color corSelecaoErro = new Color(255, 30, 65);

    /** Creates new form PainelTabuleiro
     * @param pai 
     * @param jogador 
     */
    public PainelTabuleiro(JanelaPrincipal pai, Jogador jogador) {
        this.pai = pai;
        this.jogador = jogador;
        this.tamanho = this.jogador.getTabuleiro().getTamanho();

        int size = tamanho * tamanhoBotao + 20;
        this.setSize(new Dimension(size, size));

        this.grid = new JButton[tamanho][tamanho];

        this.construirGrid();
    }

    /**
     * 
     * @return
     */
    public JButton[][] getGrid() {
        return grid;
    }

    /**
     * 
     * @return
     */
    public boolean isHabilitado() {
        return habilitado;
    }

    /**
     * 
     * @param habilitado
     */
    public void setHabilitado(boolean habilitado) {
        if(this.habilitado == habilitado){
            return;
        } else {
            this.habilitado = habilitado;
        }
        for(int i = 0; i < this.tamanho; i++){
            for(int j = 0; j < this.tamanho; j++){
                this.grid[i][j].setEnabled(habilitado);
            }
        }
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
            for(int j = 0; j < tamanho; j++){
                this.grid[i][j] = new JButton();
                this.grid[i][j].setPreferredSize(new java.awt.Dimension(this.tamanhoBotao, this.tamanhoBotao));
                this.grid[i][j].setOpaque(true);
                this.grid[i][j].addMouseListener(new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(MouseEvent evt) {
                        if(Jogo.modoPreparacao){
                            JButton botao = (JButton) evt.getComponent();
                            posicaoMouseOver = getPosicao(botao);
                            mouseOver();
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent evt) {
                        if(Jogo.modoPreparacao){
                            JButton botao = (JButton) evt.getComponent();
                            posicaoMouseOver = getPosicao(botao);
                            ArrayList<Integer[]> coords = getCoordenadasNavioSelecionado(posicaoMouseOver);

                            if(coordenadasEstaoLivres(coords)){
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
                             * Se o jogador começa jogando, ele joga só nos turnos impares.
                             * Mas se ele nao começa jogando, ele joga só nos turnos pares.
                             * Então, pra ele poder adicionar um ataque nesse tabuleiro no modo de jogo, é necessario:
                             * - Ser o tabuleiro do jogador adversario. (primeira condicao)
                             * - Estar na vez dele de adicionarAtaque.  (segunda condicao)
                             * 
                             * Sim, eu sei que isso pode parecer meio óbvio, mas vai saber quem está lendo
                             * isso aqui né...
                             */
                            boolean turnoPar = Jogo.turno % 2 == 0;

                            if(jogador.getTipo() == TipoJogador.ADVERSARIO
                                    && ((turnoPar && jogador.comecaJogando()) || (!turnoPar && !jogador.comecaJogando()))){

                                JButton botao = (JButton) evt.getComponent();
                                int[] pos = getPosicao(botao);
                                
                                if(jogador.getTabuleiro().getCasas()[pos[0]][pos[1]].atingido) return;

                                if(Main.jogo.adicionarAtaque(pos)){
                                    botao.setBackground(Color.RED);
                                } else {
                                    botao.setBackground(corPadrao);
                                }
                            }
                        }
                    }
                });
                gridBagConstraints.gridx = i + 1;
                gridBagConstraints.gridy = j + 1;
                gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
                add(this.grid[i][j], gridBagConstraints);
            }
        }
    }

    public void atirar() {
        ArrayList<Integer[]> tiros = Main.jogo.getJogador(true).getTiros();

        if(jogador.getTipo() == TipoJogador.ADVERSARIO){
            for(Integer[] pos : tiros){
                JButton botao = this.grid[pos[0]][pos[1]];
                int[] p = new int[]{pos[0], pos[1]};
                
                ImageIcon imagem = getImagem(p);
                if(imagem != null){
                    botao.setIcon(imagem);
                    botao.setBackground(corPadrao);
                } else {
                    botao.setBackground(corSelecao);
                }
            }
        }
    }

    public ImageIcon getImagem(int[] pos) {
        ArrayList<Navio> navios = this.getNaviosPosicionados();
        for(int i = 0; i < navios.size(); i++){
            Navio navio = navios.get(i);

            String ori;
            if(navio.getOrientacao() == Orientacao.VERTICAL){
                ori = "v";
            } else {
                ori = "h";
            }
            int x = navio.getPos()[0];
            int y = navio.getPos()[1];
            for(int j = 1; j <= navio.getTamanho(); j++){
                if(pos[0] == x && pos[1] == y){
                    return new ImageIcon(getClass().getResource(navio.getCaminhoImagens() + ori + j + ".png"));
                }

                if(navio.getOrientacao() == Orientacao.VERTICAL){
                    y++;
                } else {
                    x++;
                }
            }
        }

        return null;
    }

    private ArrayList<Navio> getNaviosPosicionados() {
        ArrayList<Navio> navios = new ArrayList<Navio>();

        for(int i = 0; i < this.jogador.getTabuleiro().getNavios().length; i++){
            Navio navio = this.jogador.getTabuleiro().getNavios()[i];

            if(navio.getPos() != null){
                navios.add(navio);
            }
        }

        return navios;
    }

    public int getQtdeNaviosSemPosicao() {
        int qtde = 0;

        for(int i = 0; i < this.jogador.getTabuleiro().getNavios().length; i++){
            Navio navio = this.jogador.getTabuleiro().getNavios()[i];
            if(navio.getPos() == null){
                qtde++;
            }
        }

        return qtde;
    }

    /**
     * 
     */
    public void atualizarPosicaoNavios() {
        ImageIcon img = null;
        this.limparGrid(true);

        ArrayList<Navio> navios = this.getNaviosPosicionados();
        for(Navio navio : navios){
            int x = navio.getPos()[0];
            int y = navio.getPos()[1];
            for(int j = 1; j <= navio.getTamanho(); j++){
                String ori = "h";
                if(navio.getOrientacao() == Orientacao.VERTICAL){
                    ori = "v";
                }

                img = new ImageIcon(getClass().getResource(navio.getCaminhoImagens() + ori + j + ".png"));
                grid[x][y].setIcon(img);

                if(navio.getOrientacao() == Orientacao.VERTICAL){
                    y += 1;
                } else {
                    x += 1;
                }
            }
        }
    }

    public void limparGrid(boolean limparNavios) {
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                if(!jogador.getTabuleiro().getCasas()[i][j].atingido){
                    this.grid[i][j].setBackground(this.corPadrao);
                    if(limparNavios){
                        this.grid[i][j].setIcon(this.iconeVazio);
                    }
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
        return this.getCoordenadasNavio(pos, this.pai.getNavioSelecionado());
    }

    private ArrayList<Integer[]> getCoordenadasNavio(int[] pos, Navio navio) {
        ArrayList<Integer[]> lista = new ArrayList<Integer[]>();

        if(navio != null){
            this.limparGrid(false);
            int x = pos[0];
            int y = pos[1];
            if(navio.getOrientacao() == Orientacao.HORIZONTAL && x + navio.getTamanho() > tamanho){
                x = tamanho - navio.getTamanho();
            } else if(navio.getOrientacao() == Orientacao.VERTICAL && y + navio.getTamanho() > tamanho){
                y = tamanho - navio.getTamanho();
            }

            for(int i = 0; i < navio.getTamanho(); i++){
                lista.add(new Integer[]{x, y});
                if(navio.getOrientacao() == Orientacao.HORIZONTAL){
                    x++;
                } else {
                    y++;
                }
            }
        }

        return lista;
    }

    private boolean coordenadasSeCruzam(ArrayList<Integer[]> lista1, ArrayList<Integer[]> lista2) {
        for(int i = 0; i < lista1.size(); i++){
            for(int j = 0; j < lista2.size(); j++){
                if(lista1.get(i)[0] == lista2.get(j)[0] && lista1.get(i)[1] == lista2.get(j)[1]){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean coordenadasEstaoLivres(ArrayList<Integer[]> coordenadas) {
        ArrayList<Integer[]> lista = coordenadas;
        ArrayList<Navio> navios = this.getNaviosPosicionados();

        for(int i = 0; i < navios.size(); i++){
            Navio navio = navios.get(i);

            if(this.coordenadasSeCruzam(lista, navio.getCoordenadas())){
                return false;
            }
        }

        return true;
    }

    public void mouseOver(int[] pos) {
        ArrayList<Integer[]> coords = this.getCoordenadasNavio(pos, pai.getNavioSelecionado());

        Color cor = this.corSelecao;
        if(!this.coordenadasEstaoLivres(coords)){
            cor = this.corSelecaoErro;
        }

        for(int i = 0; i < coords.size(); i++){
            this.grid[coords.get(i)[0]][coords.get(i)[1]].setBackground(cor);
        }
    }

    public void mouseOver() {
        this.mouseOver(posicaoMouseOver);
    }

    public Navio getNavio(int pos[]) {
        Navio navio = null;
        ArrayList<Navio> navios = this.getNaviosPosicionados();

        for(int i = 0; i < navios.size(); i++){
            ArrayList<Integer[]> coords = this.getCoordenadasNavio(navios.get(i).getPos(), navios.get(i));
            for(int j = 0; j < coords.size(); j++){
                int x = coords.get(j)[0];
                int y = coords.get(j)[1];
                if(x == pos[0] && y == pos[1]){
                    navio = navios.get(i);
                    break;
                }
            }
        }

        return navio;
    }
}