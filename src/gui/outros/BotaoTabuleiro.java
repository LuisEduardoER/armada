/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.outros;

import classes.Orientacao;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author arthur
 */
public class BotaoTabuleiro extends JButton {

    private IconeTabuleiro icone;
    private Orientacao orientacao;
    private boolean highlightMira;
    private boolean highlightNavio;
    private int numTiro = 0;
    private JLabel labelNumTiro;

    public BotaoTabuleiro(String caminhoBG) {
        this.setLayout(new BorderLayout());

        try {
            icone = new IconeTabuleiro(caminhoBG);
            this.setIcon(icone);
        } catch(NullPointerException npe){
            System.out.println("<<<ERRO>>> Imagem de background do botão não foi encontrada");
        }

        this.orientacao = Orientacao.HORIZONTAL;
    }

    public BotaoTabuleiro(BufferedImage bi) {
        this.setLayout(new BorderLayout());

        try {
            icone = new IconeTabuleiro(bi);
            this.setIcon(icone);
        } catch(NullPointerException npe){
            System.out.println("<<<ERRO>>> Imagem de background do botão não foi encontrada");
        }

        this.orientacao = Orientacao.HORIZONTAL;

        labelNumTiro = new JLabel();
        labelNumTiro.setFont(new Font("Droid Sans Fallback", Font.BOLD, 14));
        labelNumTiro.setVerticalAlignment(SwingConstants.CENTER);
        labelNumTiro.setHorizontalAlignment(SwingConstants.CENTER);
        labelNumTiro.setForeground(Color.red);

        this.add(BorderLayout.CENTER, labelNumTiro);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(1));


        //g2d.draw(new Line2D.Double(0, getHeight()-1, getWidth(), getHeight()-1));  // linha sul
        g2d.draw(new Line2D.Double(0, 0, getWidth(), 0));                          // linha leste
        g2d.draw(new Line2D.Double(0, 0, 0, getHeight()));                         // linha norte
        //g2d.draw(new Line2D.Double(getWidth()-1, 0, getWidth()-1, getHeight()));   // linha oeste

        g2d.setStroke(new BasicStroke(2));

        if(this.getBackground() != Color.white){
            int tam = 0;
            if(highlightNavio){
                tam = 9;
            } else if(highlightMira){
                tam = 2;
            }
            
            g2d.setColor(this.getBackground());
            if(this.orientacao == Orientacao.HORIZONTAL){
                g2d.fill(new Rectangle(0, getHeight() / 2 - tam, 33, tam * 2));
            } else {
                g2d.fill(new Rectangle(getWidth() / 2 - tam, 0, tam * 2, 33));
            }
        }


        if(this.numTiro > 0){
            g2d.setColor(Color.white);
            g2d.fillOval(getWidth() / 2 - 11, getHeight() / 2 - 11, 22, 22);
            g2d.setColor(Color.red);
            g2d.drawOval(getWidth() / 2 - 11, getHeight() / 2 - 11, 22, 22);

            labelNumTiro.setText(this.numTiro + "");
        }

        g2d.dispose();
    }

    public void setImagemNavio(ImageIcon imagem) {
        this.icone.setImagemNavio(imagem);
    }

    public void setImagemExtra(ImageIcon imagem) {
        this.icone.setImagemExtra(imagem);
    }

    public IconeTabuleiro getIcone() {
        return icone;
    }

    public void setIcone(IconeTabuleiro icone) {
        this.icone = icone;
    }

    public Orientacao getOrientacao() {
        return orientacao;
    }

    public void setOrientacao(Orientacao ori) {
        this.orientacao = ori;
    }

    public boolean isHighlightMira() {
        return highlightMira;
    }

    public void setHighlightMira(boolean highlightMira) {
        this.highlightMira = highlightMira;
    }

    public boolean isHighlightNavio() {
        return highlightNavio;
    }

    public void setHighlightNavio(boolean highlightNavio) {
        this.highlightNavio = highlightNavio;
    }

    public int getNumTiro() {
        return numTiro;
    }

    public void setNumTiro(int numTiro) {
        if(numTiro == 0) {
            labelNumTiro.setText("");
        }
        this.numTiro = numTiro;
    }
}
