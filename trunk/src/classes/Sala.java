/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author arthur
 */
public class Sala {

    public static final int ESPERANDO = 0;
    public static final int JOGANDO = 1;
    
    private String nome;
    private int status;
    private String criador;
    private String tipoJogo;

    public Sala(String nome, int status, String criador, String tipoJogo) {
        this.nome = nome;
        this.status = status;
        this.criador = criador;
        this.tipoJogo = tipoJogo;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoJogo() {
        return tipoJogo;
    }

    public void setTipoJogo(String tipoJogo) {
        this.tipoJogo = tipoJogo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
