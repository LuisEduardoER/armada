/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.paineis;

import classes.Jogador;
import classes.TipoJogador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import main.Jogo;
import main.Main;

/**
 *
 * @author arthur
 */
public class PainelInformacoes extends JPanel {

    private static final int TEMPO_TURNO = 30;
    private JLabel labelTimer;
    private JLabel labelAdversarioPreparado;
    private JButton botaoAtirar;
    private JButton botaoPreparar;
    private GridBagConstraints gbcTiros;
    private ArrayList<JRadioButton> tiros;
    private Jogador jogador;
    private Timer timer;
    private int tempo;

    public PainelInformacoes(Jogador jogador) {
        this.jogador = jogador;

        this.gbcTiros = new GridBagConstraints();
        this.ativarModoPreparo();
    }

    public void reiniciar() {
        this.tempo = TEMPO_TURNO + 1;
        this.botaoAtirar.setVisible(false);

        Jogador jog = Jogo.getJogador(true);

        this.atualizarContagemNavios();
        this.atualizarContagemTiros();
        timer.restart();
    }

    private void ativarModoPreparo() {
        this.setLayout(new BorderLayout());

        this.botaoPreparar = new JButton("<html><center>ESTOU<p>PRONTO<center></html>");
        this.botaoPreparar.setFont(new Font("Verdana", Font.PLAIN, 28));
        this.botaoPreparar.setVisible(false);

        this.botaoPreparar.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Main.jogo.preparar(jogador);
                botaoPreparar.setEnabled(false);
            }
        });

        this.labelAdversarioPreparado = new JLabel("<html><center>SEU ADVERSÁRIO ESTÁ PRONTO.<center></html>");
        this.labelAdversarioPreparado.setFont(new Font("Verdana", Font.BOLD, 14));
        this.labelAdversarioPreparado.setVisible(false);
        this.labelAdversarioPreparado.setForeground(Color.red);

        this.add(labelAdversarioPreparado, BorderLayout.NORTH);
        this.add(botaoPreparar, BorderLayout.CENTER);
    }

    public void ativarModoJogo() {
        this.removeAll();
        this.setSize(new Dimension(200, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        labelTimer = new JLabel("00:45");
        labelTimer.setFont(new Font("Droid Sans Fallback", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = this.jogador.getTabuleiro().getNavios().length;
        gbc.insets = new Insets(5, 5, 5, 5);
        this.add(labelTimer, gbc);

        this.tiros = new ArrayList<JRadioButton>();
        gbcTiros.insets = new java.awt.Insets(1, 1, 1, 1);
        gbcTiros.gridwidth = 1;

        int x = 0;
        int y = 1;
        for(int i = 0; i < this.jogador.getTabuleiro().getQtdeNaviosVivos(); i++){
            adicionarTiro(i, x, y);
            if(x == 5){
                x = 0;
                y++;
            } else {
                x++;
            }
        }

        botaoAtirar = new JButton("Atirar");
        botaoAtirar.setVisible(false);
        botaoAtirar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timer.stop();
                Main.jogo.atirar();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = y + 1;
        gbc.gridwidth = this.jogador.getTabuleiro().getNavios().length;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(0, 5, 5, 5);
        this.add(botaoAtirar, gbc);

        this.iniciarTimer();
    }

    private void atualizaTimer(int t) {
        if(t <= 0){
            labelTimer.setText("00:00");
            Main.jogo.trocarTurno();
            this.tempo = TEMPO_TURNO + 1;
        } else if(t < 10){
            labelTimer.setText("00:0" + (t++));
        } else if(t >= 10 && t < 60){
            labelTimer.setText("00:" + (t++));
        } else if(t >= 60 && t < 600 && t % 60 < 10){
            labelTimer.setText("0" + (int) (t++) / 60 + ":0" + (t - 1) % 60);
        } else if(t >= 60 && t < 600){
            labelTimer.setText("0" + (int) (t++) / 60 + ":" + (t - 1) % 60);
        } else if(t >= 600 && t < 3600 && ((t - 1) % 600) % 60 < 10){
            labelTimer.setText((int) (t++) / 60 + ":0" + ((t - 1) % 600) % 60);
        } else if(t >= 600 && t < 3600){
            labelTimer.setText((int) (t++) / 60 + ":" + ((t - 1) % 600) % 60);
        }
    }

    private void iniciarTimer() {
        ActionListener action = new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tempo = tempo - 1;
                atualizaTimer(tempo);

                Jogador jog = Jogo.getJogador(true);

                if(jog.getTipo() == TipoJogador.ADVERSARIO){
                    Random random = new Random();
                    int i = random.nextInt(jogador.getTabuleiro().getTamanho());
                    int j = random.nextInt(jogador.getTabuleiro().getTamanho());

                    if(jog.getTabuleiro().getQtdeNaviosVivos() == jog.getTiros().size()){
                        Main.jogo.atirar();
                    } else {
                        Main.jogo.adicionarAtaque(new int[]{i, j});
                    }
                }
            }
        };

        tempo = TEMPO_TURNO;

        labelTimer.setVisible(true);
        atualizaTimer(tempo);

        timer = new Timer(1000, action);
        timer.start();
    }

    public void atualizarContagemNavios() {
        Jogador jog = Jogo.getJogador(true);

        int qtdeNavios = jog.getTabuleiro().getQtdeNaviosVivos();
        int qtdeTiros = tiros.size();

        for(int i = qtdeTiros; i > qtdeNavios; i--){
            this.remove(tiros.get(i - 1));
            tiros.remove(i - 1);
        }

        int x = 0;
        int y = 1;
        for(int i = 0; i < tiros.size(); i++){
            if(x == 5){
                x = 0;
                y++;
            } else {
                x++;
            }
        }
        
        for(int i = qtdeTiros; i < qtdeNavios; i++){
            adicionarTiro(i, x, y);
            if(x == 5){
                x = 0;
                y++;
            } else {
                x++;
            }
        }
    }

    private void adicionarTiro(int i, int x, int y) {
        tiros.add(i, new JRadioButton());
        tiros.get(i).setSelected(true);
        tiros.get(i).setEnabled(false);
        gbcTiros.gridx = x;
        gbcTiros.gridy = y;
        this.add(tiros.get(i), gbcTiros);
    }

    public void atualizarContagemTiros() {
        Jogador jog = Jogo.getJogador(true);

        //setar tudo como selecionado
        for(int i = 0; i < tiros.size(); i++){
            tiros.get(i).setSelected(true);
        }
        //e depois deseleciona o necessario.
        for(int i = tiros.size() - jog.getTiros().size(); i < tiros.size(); i++){
            tiros.get(i).setSelected(false);
        }

        if(jog.getTipo() == TipoJogador.LOCAL){
            this.botaoAtirar.setVisible(true);
        } else {
            this.botaoAtirar.setVisible(false);
        }
    }

    public JButton getBotaoAtirar() {
        return botaoAtirar;
    }

    public JButton getBotaoPreparar() {
        return botaoPreparar;
    }

    public JLabel getLabelTimer() {
        return labelTimer;
    }

    public ArrayList<JRadioButton> getTiros() {
        return tiros;
    }

    public JLabel getLabelAdversarioPreparado() {
        return labelAdversarioPreparado;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
}
