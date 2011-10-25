/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.protocolo;

/**
 *
 * @author dolalima
 */
public class Chat {

    private String remetente;
    private String destinatario;
    private String mensagem;

    public Chat(String remetente, String destinatario, String mensagem) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
    }

    public Chat(String nome) {
        this.remetente = nome;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }
    
    
}
