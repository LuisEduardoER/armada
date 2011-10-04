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

    private String nome;
    private String criador;
    private String tipoJogo;

    public Sala(String nome, String criador, String tipoJogo) {
        this.nome = nome;
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
}
