/**
 * @(#)IconeTabuleiro.java	1.0 03/18/09
 */
package gui.outros;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

/**
 * A composite Icon class used to compose two Icon objects into a single Icon
 * by painting the icons in turn at the precomputed offsets.  For example,
 * this class may be used to add a custom icon to a component like JCheckBox
 * or JRadioButton, in addition to the default icon provided by the
 * Look and Feel.
 * 
 * @version 1.0 03/11/09
 * @author Darryl
 */
public class IconeTabuleiro implements Icon, SwingConstants {

    private ImageIcon imagemNavio;
    private ImageIcon imagemExtra;
    private ImageIcon bg;
    private int width;
    private int height;
    private int navioHOffset;
    private int navioVOffset;
    private int extraHOffset;
    private int extraVOffset;
    private int bgHOffset;
    private int bgVOffset;

    public IconeTabuleiro(String caminhoBG) {
        this.imagemNavio = new ImageIcon();
        this.imagemExtra = new ImageIcon();

        width = 33;
        height = 33;

        this.bg = new ImageIcon(getClass().getResource(caminhoBG));
        bgHOffset = (width - bg.getIconWidth()) / 2;
        bgVOffset = (height - bg.getIconHeight()) / 2;
    }
    
    public IconeTabuleiro(BufferedImage bi) {
        this.imagemNavio = new ImageIcon();
        this.imagemExtra = new ImageIcon();

        width = 33;
        height = 33;

        this.bg = new ImageIcon(bi);
        bgHOffset = (width - bg.getIconWidth()) / 2;
        bgVOffset = (height - bg.getIconHeight()) / 2;
    }

    /**
     * Paints the icons of this compound icon at the specified location
     * with the precomputed offsets.
     * 
     * @param c The component to which the icon is painted, which may be used
     * to get properties useful for painting, e.g. the foreground or background
     * color or selection status.
     * @param g the graphics context
     * @param x the X coordinate of the compound icon's top-left corner
     * @param y the Y coordinate of the compound icon's top-left corner
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        bg.paintIcon(c, g, x + bgHOffset, y + bgVOffset);
        imagemNavio.paintIcon(c, g, x + navioHOffset, y + navioVOffset);
        imagemExtra.paintIcon(c, g, x + extraHOffset, y + extraVOffset);
    }

    public ImageIcon getBg() {
        return bg;
    }

    public void setBg(ImageIcon bg) {
        this.bg = bg;
        bgHOffset = (width - bg.getIconWidth()) / 2;
        bgVOffset = (height - bg.getIconHeight()) / 2;
    }

    public ImageIcon getImagemExtra() {
        return imagemExtra;
    }

    public void setImagemExtra(ImageIcon imagemExtra) {
        this.imagemExtra = imagemExtra;
        this.extraHOffset = (width - imagemExtra.getIconWidth()) / 2;
        this.extraVOffset = (height - imagemExtra.getIconHeight()) / 2;
    }

    public ImageIcon getImagemNavio() {
        return imagemNavio;
    }

    public void setImagemNavio(ImageIcon imagemNavio) {
        this.imagemNavio = imagemNavio;        
        this.navioHOffset = (width - imagemNavio.getIconWidth()) / 2;
        this.navioVOffset = (height - imagemNavio.getIconHeight()) / 2;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
