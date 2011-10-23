package gui.paineis;

import gui.janelas.JanelaPrincipal;
import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        JButton botao = (JButton) evt.getComponent();
                        posicaoMouseOver = getPosicao(botao);
                        mouseOver();
                    }

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        JButton botao = (JButton) evt.getComponent();
                        posicaoMouseOver = getPosicao(botao);
                        ArrayList<Integer[]> coords = getCoordenadasNavioSelecionado(posicaoMouseOver);
                        if(coordenadasEstaoLivres(coords)){
                            selecionarPosicao(posicaoMouseOver);
                        } else {
                            mouseOver(posicaoMouseOver);
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
    
    
    private ArrayList<Navio> getNaviosPosicionados(){
        ArrayList<Navio> navios = new ArrayList<Navio>();
        
        for(int i = 0; i < this.jogador.getNavios().length; i++){
            Navio navio = this.jogador.getNavios()[i];
            
            if(navio.getPos() != null) {
                navios.add(navio);
            }
        }
        
        return navios;
    }
    
    
    public int getQtdeNaviosSemPosicao(){
        int qtde = 0;
        
        for(int i = 0; i < this.jogador.getNavios().length; i++){
            Navio navio = this.jogador.getNavios()[i];            
            if(navio.getPos() == null) {
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
        for(int i = 0; i < navios.size(); i++){
            Navio navio = navios.get(i);

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

    private void limparGrid(boolean limparNavios) {
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                this.grid[i][j].setBackground(this.corPadrao);
                if(limparNavios) {
                    this.grid[i][j].setIcon(this.iconeVazio);
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

    private void selecionarPosicao(int[] pos) {
        pai.selecionarPosicao(pos);
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
                System.out.print(x + "   " + (tamanho - x));
                x = tamanho - navio.getTamanho();
            } else if(navio.getOrientacao() == Orientacao.VERTICAL && y + navio.getTamanho() > tamanho){
                System.out.print(y + "   " + (tamanho - y));
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
    
    private boolean coordenadasSeCruzam(ArrayList<Integer[]> lista1, ArrayList<Integer[]> lista2){
        for(int i = 0; i < lista1.size(); i++){
            for(int j = 0; j < lista2.size(); j++){
                if(lista1.get(i)[0] == lista2.get(j)[0] && lista1.get(i)[1] == lista2.get(j)[1]){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    private boolean coordenadasEstaoLivres(ArrayList<Integer[]> coordenadas){
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
    
    
    public Navio getNavio(int pos[]){
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