/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.paineis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author arthur
 */
public class PainelChat extends JPanel {

    private JScrollPane painelScroll;
    private JTextArea areaTexto;
    private JTextField campoMensagem;

    public PainelChat() {
        painelScroll = new JScrollPane();
        areaTexto = new JTextArea();
        campoMensagem = new JTextField();

        this.setLayout(new BorderLayout());

        areaTexto.setColumns(15);
        areaTexto.setRows(5);
        painelScroll.setViewportView(areaTexto);

        this.add(painelScroll, BorderLayout.CENTER);
        this.add(campoMensagem, BorderLayout.SOUTH);
        
        System.out.println(this.getPreferredSize());
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.add(new PainelChat());        
        frame.setSize(new Dimension(200,200));
        frame.setVisible(true);
    }
}
