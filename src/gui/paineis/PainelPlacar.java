package gui.paineis;

import gui.janelas.JanelaPrincipal;
import classes.Jogador;
import classes.Navio;
import gui.outros.BotaoPlacar;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author arthur
 */
public class PainelPlacar extends JPanel {

    private JanelaPrincipal pai;
    private Jogador jogador;
    private ArrayList<Navio> navios;
    private ArrayList<BotaoPlacar> botoesNavios;

    /** Creates new form PainelPlacar */
    public PainelPlacar(JanelaPrincipal pai, Jogador jogador) {
        this.setLayout(new GridBagLayout());

        this.pai = pai;
        this.jogador = jogador;

        this.navios = new ArrayList<Navio>();
        this.botoesNavios = new ArrayList<BotaoPlacar>();

        this.construir();
        this.atualizar();

        this.setSize(new Dimension(BotaoPlacar.LARGURA * 2, this.botoesNavios.size() / 2 * BotaoPlacar.ALTURA));
    }

    private void construir() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        boolean repetido;
        int k = 0;
        for(int i = 0; i < jogador.getTabuleiro().getNavios().length; i++){
            repetido = false;
            for(int j = 0; j < this.navios.size(); j++){
                //se os nomes forem iguais e nao tiver entrado alguma vez na condição.
                if(!repetido && this.navios.get(j).getNome().equals(jogador.getTabuleiro().getNavios()[i].getNome())){
                    repetido = true;
                }
            }

            if(!repetido){
                this.navios.add(jogador.getTabuleiro().getNavios()[i]);
            } else {
                k++;
                for(int j = 0; j < this.botoesNavios.size(); j++){
                    if(this.botoesNavios.get(j).getNavio(0).getNome().equals(jogador.getTabuleiro().getNavios()[i].getNome())){
                        this.botoesNavios.get(j).adicionarNavio(jogador.getTabuleiro().getNavios()[i]);
                    }
                }
                continue;
            }

            BotaoPlacar botao = new BotaoPlacar(jogador.getTabuleiro().getNavios()[i]);
            botao.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    BotaoPlacar botaoPlacar = (BotaoPlacar) evt.getComponent();
                    Navio navioSemPosicao = botaoPlacar.getNavioSemPosicao();
                    if(navioSemPosicao != null){
                        selecionarNavio(navioSemPosicao);
                    }
                }
            });


            this.botoesNavios.add(botao);

            this.add(botoesNavios.get(i - k), gbc);
            gbc.gridx += 1;
            if(gbc.gridx >= 2){
                gbc.gridx = 0;
                gbc.gridy += 1;
            }
        }

        /*for(int i = 0; i < this.botoesNavios.size(); i++){
            botoesNavios.get(i).setPreferredSize(new Dimension(185, 65));
        }*/
    }

    private void selecionarNavio(Navio navio) {
        this.pai.selecionarNavio(navio);
    }

    public void atualizar() {
        for(BotaoPlacar botao : botoesNavios){
            botao.atualizar();
        }
    }
}
