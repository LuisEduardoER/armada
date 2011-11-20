/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.outros;

import classes.Orientacao;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author arthur
 */
public class BotaoTabuleiro extends JButton {

    private DualIcon icone;
    private Orientacao orientacao;

    public BotaoTabuleiro(String caminhoBG) {
        this.setLayout(new BorderLayout());

        try {
            icone = new DualIcon(caminhoBG);
            this.setIcon(icone);
        } catch(NullPointerException npe){
            System.out.println("<<<ERRO>>> Imagem de background do botão não foi encontrada");
        }
        
        this.orientacao = Orientacao.HORIZONTAL;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(1));    
            

        g2d.draw(new Line2D.Double(0, getHeight()-1, getWidth(), getHeight()-1));  // linha sul
        g2d.draw(new Line2D.Double(0, 0, getWidth(), 0));                      // linha leste
        g2d.draw(new Line2D.Double(0, 0, 0, getHeight()));                     // linha norte
        g2d.draw(new Line2D.Double(getWidth()-1, 0, getWidth()-1, getHeight()));   // linha oeste

        if(this.getBackground() != Color.white) {
            g2d.setColor(this.getBackground());
            g2d.setStroke(new BasicStroke(3));
            if(this.orientacao == Orientacao.HORIZONTAL)
                g2d.fill(new Rectangle(0, getHeight()/2-9, 33, 18));
            else
                g2d.fill(new Rectangle(getWidth()/2-9, 0, 18, 33));
        }       
        
        g2d.dispose();
    }
    
    
    public void setImagemNavio(ImageIcon imagem){
        this.icone.setImagemNavio(imagem);
    }
    
    
    public void setImagemExtra(ImageIcon imagem){
        this.icone.setImagemExtra(imagem);
    }

    
    public DualIcon getIcone() {
        return icone;
    }
    public void setIcone(DualIcon icone) {
        this.icone = icone;
    }
    

    public Orientacao getOrientacao() {
        return orientacao;
    }
    public void setOrientacao(Orientacao ori) {
        this.orientacao = ori;
    }
}
