package gui.paineis;

import gui.janelas.JanelaPrincipal;
import classes.Jogador;
import classes.Navio;
import classes.Orientacao;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author arthur
 */
public class PainelTabuleiro extends JPanel {
        
    private JanelaPrincipal pai;
    private Jogador jogador;

    private final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private JButton[][] grid;
    private JScrollPane painelScroll;
    
    private int tamanho;
    private int tamanhoBotao = 33;
    private boolean habilitado;

    /** Creates new form PainelTabuleiro */
    public PainelTabuleiro(JanelaPrincipal pai, Jogador jogador) {
        this.pai = pai;
        this.jogador = jogador;
        this.tamanho = this.jogador.getTabuleiro().getTamanho();
        
        int size = tamanho * tamanhoBotao + 20;
        this.setSize(new Dimension(size, size));

        this.grid = new JButton[tamanho][tamanho];
        this.painelScroll = new JScrollPane();
        //this.painelScroll.setViewportView(this);
        
        //this.add(painelScroll, BorderLayout.CENTER);

        this.construirGrid();
    }

    public JButton[][] getGrid() {
        return grid;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        if(this.habilitado == habilitado) {
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
                this.grid[i][j].addMouseListener(new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        JButton botao = (JButton) evt.getComponent();
                        selecionarPosicao(getPosicao(botao));
                    }
                });
                gridBagConstraints.gridx = i + 1;
                gridBagConstraints.gridy = j + 1;
                gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
                add(this.grid[i][j], gridBagConstraints);
            }
        }
    }

    public void atualizarPosicaoNavios() {
        ImageIcon img = null;
        
        this.limparGrid();
        
        for(int i = 0; i < this.jogador.getNavios().length; i++){
            Navio navio = this.jogador.getNavios()[i];
            
            if(navio.getPos() == null)
                continue;
            
            int x = navio.getPos()[0];
            int y = navio.getPos()[1];
            for(int j = 1; j <= navio.getTamanho(); j++){
                String ori = "h";
                if(navio.getOrientacao() == Orientacao.VERTICAL) {
                    ori = "v";
                }

                img = new ImageIcon(getClass().getResource(navio.getCaminhoImagens() + ori + j + ".png"));
                grid[x][y].setIcon(img);

                if(navio.getOrientacao() == Orientacao.VERTICAL) {
                    y += 1;
                } else {
                    x += 1;
                }
            }
        }
    }
    
    private void limparGrid(){
        ImageIcon iconeVazio = new ImageIcon();
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                this.grid[i][j].setIcon(iconeVazio);
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

    private void selecionarPosicao(int[] pos){
        pai.selecionarPosicao(pos);
    }
}